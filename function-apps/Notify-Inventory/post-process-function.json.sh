# Script to be run post build to set the content for running the function locally using the environment variable settings
sed -i -e "s@NI_FUNCTION_APP_STORAGE_ACCOUNT_CONNECTION_STRING@$NI_FUNCTION_APP_STORAGE_ACCOUNT_CONNECTION_STRING@g" target/azure-functions/*/local.settings.json
sed -i -e "s@NOTIFICATIONS_EVENT_HUB_CONNECTION_STRING@$NOTIFICATIONS_EVENT_HUB_CONNECTION_STRING@g" target/azure-functions/*/local.settings.json
sed -i -e "s@NOTIFICATIONS_COSMOSDB_CONNECTION_STRING@$NOTIFICATIONS_COSMOSDB_CONNECTION_STRING@g" target/azure-functions/*/local.settings.json
