log_level = "warn"

wait {
  min = "5s"
  max = "10s"
}

vault {
  address = "http://vault.castlery.internal:8200"

  grace        = "15s"
  unwrap_token = false
  renew_token  = true
}

template {
  source      = "/app/etc/templates/application-deployment.yml.tmpl"
  destination = "/app/application-deployment.yml"

  error_on_missing_key = true
  perms  = 0644
  backup = true
}

template {
  source      = "/app/etc/templates/sentry.properties.tmpl"
  destination = "/app/sentry.properties"

  error_on_missing_key = true
  perms  = 0644
  backup = true
}
