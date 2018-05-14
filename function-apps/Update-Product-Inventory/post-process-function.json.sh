sed -i -e 's/documentdb/cosmosDb/g' target/azure-functions/*/Update-Product-Inventory/function.json
sed -i -e 's/path/eventHubName/g' target/azure-functions/*/Update-Product-Inventory/function.json
sed -i -e '17s/connection/connectionStringSetting/' target/azure-functions/*/Update-Product-Inventory/function.json
sed -i -e '26s/connection/connectionStringSetting/' target/azure-functions/*/Update-Product-Inventory/function.json

sed -i -e "s/PRODUCT_INVENTORY_DOCUMENTDB_DBNAME/$PRODUCT_INVENTORY_DOCUMENTDB_DBNAME/g" target/azure-functions/*/Update-Product-Inventory/function.json
sed -i -e "s@PRODUCT_INVENTORY_DOCUMENTDB_COLLECTION_NAME@$PRODUCT_INVENTORY_DOCUMENTDB_COLLECTION_NAME@g" target/azure-functions/*/Update-Product-Inventory/function.json

sed -i -e "s/TRANSACTIONS_EVENT_HUB_NAME/$TRANSACTIONS_EVENT_HUB_NAME/g" target/azure-functions/*/Update-Product-Inventory/function.json
sed -i -e "s@TRANSACTIONS_EVENT_HUB_CONSUMER_GROUP_NAME@$TRANSACTIONS_EVENT_HUB_CONSUMER_GROUP_NAME@g" target/azure-functions/*/Update-Product-Inventory/function.json

sed -i -e "s@UPI_FUNCTION_APP_STORAGE_ACCOUNT_CONNECTION_STRING@$UPI_FUNCTION_APP_STORAGE_ACCOUNT_CONNECTION_STRING@g" target/azure-functions/*/local.settings.json
sed -i -e "s@TRANSACTIONS_EVENT_HUB_CONNECTION_STRING@$TRANSACTIONS_EVENT_HUB_CONNECTION_STRING@g" target/azure-functions/*/local.settings.json
sed -i -e "s@PRODUCT_INVENTORY_DOCUMENTDB_CONNECTION_STRING@$PRODUCT_INVENTORY_DOCUMENTDB_CONNECTION_STRING@g" target/azure-functions/*/local.settings.json

rm target/azure-functions/*/lib/azure-functions-java-core-1.0.0-beta-2.jar
