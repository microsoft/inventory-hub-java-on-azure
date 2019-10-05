if [ "$APP_CONFIGURATION_STORE_NAME" == "" ]; then echo "Unexpected error: environment variable APP_CONFIGURATION_STORE_NAME is not defined. Exiting..." && exit 1 
fi
# In this depolyment we are create an App Configuration Store & Key Vault. We will configure our application using standard configurations and key vault references.

echo "Using ""$APP_CONFIGURATION_STORE_NAME"" as a prefix for the names of the resources to be created"

# 1) Creating Key Vault
az keyvault create --name "$KEY_VAULT_NAME" --resource-group "$WEBAPP_RESOURCEGROUP_NAME" --location "$LOCATION"

# 2) Adding Secrets to Key Vault
az keyvault secret set --vault-name "$KEY_VAULT_NAME" --name "COSMOSDB-KEY" --value "$COSMOSDB_KEY"
VALUE="org.apache.kafka.common.security.plain.PlainLoginModule required username=\"\$ConnectionString\" password=$NOTIFICATIONS_EVENT_HUB_CONNECTION_STRING"
az keyvault secret set --vault-name "$KEY_VAULT_NAME" --name "JASS-CONFIG" --value "$VALUE"
az keyvault secret set --vault-name "$KEY_VAULT_NAME" --name "TENANT-NAME" --value "$TENANT_NAME"
az keyvault secret set --vault-name "$KEY_VAULT_NAME" --name "APPINSIGHTS_INSTRUMENTATIONKEY" --value "$APPINSIGHTS_INSTRUMENTATIONKEY"
az keyvault secret set --vault-name "$KEY_VAULT_NAME" --name "B2C_CLIENT_ID" --value "$B2C_CLIENT_ID"
az keyvault secret set --vault-name "$KEY_VAULT_NAME" --name "B2C_CLIENT_SECRET" --value "$B2C_CLIENT_SECRET"

# 3) Creating App Configuration
az appconfig create --name "$APP_CONFIGURATION_STORE_NAME" --resource-group "$WEBAPP_RESOURCEGROUP_NAME" --location "$LOCATION"

# 4) Adding Configurations to App Configuration
az appconfig kv set --name "$APP_CONFIGURATION_STORE_NAME" --key "/inventory-hub/spring.cloud.stream.kafka.binder.configuration.sasl.mechanism" --value "PLAIN" --content-type " " --yes
az appconfig kv set --name "$APP_CONFIGURATION_STORE_NAME" --key "/inventory-hub/spring.cloud.stream.kafka.binder.configuration.security.protocol" --value "SASL_SSL" --content-type " " --yes
az appconfig kv set --name "$APP_CONFIGURATION_STORE_NAME" --key "/inventory-hub/spring.cloud.stream.kafka.binder.brokers" --value "$NOTIFICATIONS_EVENT_HUB_FQDN:9093" --content-type " " --yes
az appconfig kv set --name "$APP_CONFIGURATION_STORE_NAME" --key "/inventory-hub/azure.documentdb.database" --value "$COSMOSDB_DBNAME" --content-type " " --yes
az appconfig kv set --name "$APP_CONFIGURATION_STORE_NAME" --key "/inventory-hub/azure.documentdb.key" --value "{\"uri\":\"https://$KEY_VAULT_NAME.vault.azure.net/secrets/COSMOSDB-KEY\"}" --content-type "application/vnd.microsoft.appconfig.keyvaultref+json;charset=utf-8" --yes
az appconfig kv set --name "$APP_CONFIGURATION_STORE_NAME" --key "/inventory-hub/azure.documentdb.uri" --value "$COSMOSDB_URI" --content-type " " --yes
az appconfig kv set --name "$APP_CONFIGURATION_STORE_NAME" --key "/inventory-hub/spring.cloud.azure.eventhub.connection-string" --value "{\"uri\":\"https://$KEY_VAULT_NAME.vault.azure.net/secrets/NOTIFICATIONS-EVENT-HUB-CONNECTION-STRING\"}" --content-type "application/vnd.microsoft.appconfig.keyvaultref+json;charset=utf-8" --yes
az appconfig kv set --name "$APP_CONFIGURATION_STORE_NAME" --key "/inventory-hub/spring.cloud.stream.bindings.error.destination" --value "$NOTIFICATION_ERRORS_EVENT_HUB_NAME" --content-type " " --yes
az appconfig kv set --name "$APP_CONFIGURATION_STORE_NAME" --key "/inventory-hub/spring.cloud.stream.bindings.input.destination" --value "$NOTIFICATIONS_EVENT_HUB_NAME" --content-type " " --yes
az appconfig kv set --name "$APP_CONFIGURATION_STORE_NAME" --key "/inventory-hub/spring.cloud.stream.bindings.input.group" --value "$NOTIFICATIONS_EVENT_HUB_CONSUMER_GROUP_NAME" --content-type " " --yes
az appconfig kv set --name "$APP_CONFIGURATION_STORE_NAME" --key "/inventory-hub/spring.cloud.stream.kafka.binder.brokers" --value "$NOTIFICATIONS_EVENT_HUB_FQDN:9093" --content-type " " --yes
az appconfig kv set --name "$APP_CONFIGURATION_STORE_NAME" --key "/inventory-hub/spring.cloud.stream.kafka.binder.configuration.sasl.jaas.config" --value "{\"uri\":\"https://$KEY_VAULT_NAME.vault.azure.net/secrets/JASS-CONFIG\"}" --content-type " " --yes
az appconfig kv set --name "$APP_CONFIGURATION_STORE_NAME" --key "/inventory-hub/azure.application-insights.instrumentation-key" --value "{\"uri\":\"https://$KEY_VAULT_NAME.vault.azure.net/secrets/APPINSIGHTS_INSTRUMENTATIONKEY-KEY\"}" --content-type "application/vnd.microsoft.appconfig.keyvaultref+json;charset=utf-8" --yes


az appconfig kv set --name "$APP_CONFIGURATION_STORE_NAME" --key "/inventory-hub_aad/azure.activedirectory.b2c.enabled" --value "true" --content-type " " --yes
az appconfig kv set --name "$APP_CONFIGURATION_STORE_NAME" --key "/inventory-hub_aad/azure.activedirectory.b2c.tenant" --value "{\"uri\":\"https://$KEY_VAULT_NAME.vault.azure.net/secrets/TENANT-NAME\"}" --content-type " " --yes
az appconfig kv set --name "$APP_CONFIGURATION_STORE_NAME" --key "/inventory-hub_aad/azure.activedirectory.b2c.oidc-enabled" --value "true" --content-type " " --yes
az appconfig kv set --name "$APP_CONFIGURATION_STORE_NAME" --key "/inventory-hub_aad/azure.activedirectory.b2c.client-id" --value "{\"uri\":\"https://$KEY_VAULT_NAME.vault.azure.net/secrets/B2C_CLIENT_ID\"}" --content-type " " --yes
az appconfig kv set --name "$APP_CONFIGURATION_STORE_NAME" --key "/inventory-hub_aad/azure.activedirectory.b2c.client-secret" --value "{\"uri\":\"https://$KEY_VAULT_NAME.vault.azure.net/secrets/B2C_CLIENT_SECRET\"}" --content-type " " --yes
az appconfig kv set --name "$APP_CONFIGURATION_STORE_NAME" --key "/inventory-hub_aad/azure.activedirectory.b2c.reply-url" --value "$B2C_REPLY_URL" --content-type " " --yes
az appconfig kv set --name "$APP_CONFIGURATION_STORE_NAME" --key "/inventory-hub_aad/azure.activedirectory.b2c.logout-success-url" --value "$B2C_LOGOUT_SUCCESS_URL" --content-type " " --yes
az appconfig kv set --name "$APP_CONFIGURATION_STORE_NAME" --key "/inventory-hub_aad/azure.activedirectory.b2c.user-flows.sign-up-or-sign-in" --value "$USER_FLOW_SIGNUP_SIGNIN" --content-type " " --yes
az appconfig kv set --name "$APP_CONFIGURATION_STORE_NAME" --key "/inventory-hub_aad/server.use-forward-headers" --value "true" --content-type " " --yes


# A1) Create SPN
SP="$(az ad sp create-for-rbac -n "$USER_IDENTITY")"
CI=SP | jq -r '.appId'
CS=SP | jq -r '.password'
TI=SP | jq -r '.tenant'

# A2) Granting SPN to Key Vault
az keyvault set-policy --name "$KEY_VAULT_NAME" --spn "$CI" --secret-permissions get   

# A3) Getting the Connection String from App Configuration
CONNECTION="$(az appconfig credential list --name '$USER_IDENTITY' | jq -r '.[0].connectionString')"

# A4) Exporting Connection String  and Identity
export CONFIG_STORE_CONNECTION_STRING="$CONNECTION"
export AZURE_CLIENT_ID="$CI"
export AZURE_CLIENT_SECRET="$CS"
export AZURE_TENANT_ID="$TI"
