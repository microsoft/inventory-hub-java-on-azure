sed -i -e 's/documentdb/cosmosDb/g' target/azure-functions/*/Update-Inventory/function.json
sed -i -e 's/path/eventHubName/g' target/azure-functions/*/Update-Inventory/function.json
sed -i -e '17s/connection/connectionStringSetting/' target/azure-functions/*/Update-Inventory/function.json

sed -i -e "s/DOCUMENTDB_DBNAME/$DOCUMENTDB_DBNAME/g" target/azure-functions/*/Update-Inventory/function.json
sed -i -e "s/DOCUMENTDB_COLLECTION_NAME/$DOCUMENTDB_COLLECTION_NAME/g" target/azure-functions/*/Update-Inventory/function.json

sed -i -e "s/TRANSACTIONS_EVENT_HUB_NAME/$TRANSACTIONS_EVENT_HUB_NAME/g" target/azure-functions/*/Update-Inventory/function.json
sed -i -e "s/TRANSACTIONS_EVENT_HUB_CONSUMER_GROUP_NAME/$TRANSACTIONS_EVENT_HUB_CONSUMER_GROUP_NAME/g" target/azure-functions/*/Update-Inventory/function.json
