# Script to be run post build to set the content for running the function locally using the environment variable settings
sed -i -e "s@POT_FUNCTION_APP_STORAGE_ACCOUNT_CONNECTION_STRING@$POT_FUNCTION_APP_STORAGE_ACCOUNT_CONNECTION_STRING@g" target/azure-functions/*/local.settings.json
sed -i -e "s@TRANSACTIONS_EVENT_HUB_CONNECTION_STRING@$TRANSACTIONS_EVENT_HUB_CONNECTION_STRING@g" target/azure-functions/*/local.settings.json
sed -i -e "s@PRODUCT_ITEMS_COSMOSDB_CONNECTION_STRING@$PRODUCT_ITEMS_COSMOSDB_CONNECTION_STRING@g" target/azure-functions/*/local.settings.json
