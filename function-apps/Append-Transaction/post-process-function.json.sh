# Script to be run post build to set the content for running the function locally using the environment variable settings
sed -i -e "s@AT_FUNCTION_APP_STORAGE_ACCOUNT_CONNECTION_STRING@$AT_FUNCTION_APP_STORAGE_ACCOUNT_CONNECTION_STRING@g" target/azure-functions/*/local.settings.json
sed -i -e "s@TRANSACTIONS_EVENT_HUB_CONNECTION_STRING@$TRANSACTIONS_EVENT_HUB_CONNECTION_STRING@g" target/azure-functions/*/local.settings.json
sed -i -e "s@TRANSACTIONS_COSMOSDB_CONNECTION_STRING@$TRANSACTIONS_COSMOSDB_CONNECTION_STRING@g" target/azure-functions/*/local.settings.json
