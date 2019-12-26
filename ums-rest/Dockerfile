FROM openjdk:8u212-jdk-stretch as builder

RUN curl -o /run/vault.zip -fSL "https://releases.hashicorp.com/vault/0.7.3/vault_0.7.3_linux_amd64.zip" \
    && unzip -d /usr/local/bin/ /run/vault.zip \
    && rm /run/vault.zip

RUN curl -o /run/consul-template.zip -fSL "https://releases.hashicorp.com/consul-template/0.19.5/consul-template_0.19.5_linux_amd64.zip" \
    && unzip -d /usr/local/bin/ /run/consul-template.zip \
    && rm /run/consul-template.zip

ARG APP_NAME
ARG APP_ENV
ARG APP_VERSION
ARG VAULT_TOKEN

ENV VAULT_TOKEN ${VAULT_TOKEN}
ENV APP_NAME ${APP_NAME}
ENV APP_ENV ${APP_ENV}
ENV APP_VERSION ${APP_VERSION}

COPY ./etc/ /app/etc/
RUN bash /app/etc/docker-build-assets.sh


FROM openjdk:8u232-jre-slim-buster
RUN sed -i s/#networkaddress.cache.ttl=-1/networkaddress.cache.ttl=30/ $JAVA_HOME/lib/security/java.security

ARG APP_ENV
ENV APP_ENV ${APP_ENV}

ARG APP_NAME
ENV APP_NAME ${APP_NAME}

ARG APP_VERSION
ENV APP_VERSION ${APP_VERSION}

RUN mkdir -p /app/
WORKDIR "/app/"

COPY --from=builder /app/application-deployment.yml /app/application-deployment.yml
COPY --from=builder /app/sentry.properties /app/sentry.properties
COPY ./build/libs/ums-rest.jar /app/app.jar

CMD ["java", "-Dorg.jooq.no-logo=true", "-Djava.net.preferIPv4Stack=true", "-Djava.security.egd=file:/dev/urandom",  "-Dlogging.config=classpath:logback-deployment.xml", "-Dspring.profiles.active=default,deployment,noSecurity", "-jar", "/app/app.jar"]

