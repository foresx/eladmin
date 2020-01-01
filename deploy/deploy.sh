#!/bin/bash
# run deployment inside keeper docker image
set -euo pipefail
set -x
IFS=$'\n\t'

DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"
DEPLOY_ENV=$1
DEPLOY_VERSION=$2
shift; shift;

if [[ $DEPLOY_ENV == '' || $DEPLOY_ENV == '' ]];
then
    echo "Usage: $0 <deployment env> <docker image tag>"
    exit 1
fi

# expect $1 region, $2 cluster, $3 namespace, $4 application_name
do_deploy () {
  local region=$1
  local cluster=$2
  local namespace=$3
  local app=$4
  echo "Running deployment ${app} version ${DEPLOY_VERSION} in region ${region} for cluster ${cluster} / ${namespace}"
  # AWS_ACCESS_KEY_ID and AWS_SECRET_ACCESS_KEY is set in Jenkins build
  aws eks --region ${region} update-kubeconfig --name ${cluster}

  local release="${app}-${DEPLOY_ENV}"
  local chart=${DIR}/${app}
  helm upgrade --install --force --atomic --reset-values --timeout 600 ${release} ${chart} \
    --namespace ${namespace} \
    --set image.tag=${DEPLOY_ENV}_${DEPLOY_VERSION} \
    -f ${chart}/values-${DEPLOY_ENV}.yaml
}

# expect $1 application_name to deploy
deploy_app() {
  # count number of deployments to run
  local app_name=$1
  deployment_file=${DIR}/${app_name}/deployment-${DEPLOY_ENV}.yaml
  count="$(yq r $deployment_file 'deployment[*].region' | wc -l)"

  for i in `seq 1 $count`;
  do
    index=$(($i-1))
    region="$(yq r $deployment_file deployment[$index].region)"
    cluster="$(yq r $deployment_file deployment[$index].cluster)"
    namespace="$(yq r $deployment_file deployment[$index].namespace)"
    do_deploy $region $cluster $namespace $app_name
  done
}

# source global env variables
[ -f ${DIR}/env ] && echo "Sourcing ${DIR}/env" && . "${DIR}/env"

set -x
deploy_app "ums-rest"
deploy_app "ums-web"
echo "Done deployemnt"