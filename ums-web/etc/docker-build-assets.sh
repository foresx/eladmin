#!/bin/bash

set -euo pipefail
IFS=$'\n\t'

export VAULT_ADDR=${VAULT_ADDR:-http://vault.castlery.internal:8200}

echo "Waiting for consul-template to refresh config files"
consul-template -config "/app/etc/consul-template.hcl" -once
