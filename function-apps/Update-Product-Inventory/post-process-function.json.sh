sed -i -e 's/documentdb/cosmosDb/g' target/azure-functions/*/Update-Product-Inventory/function.json
sed -i -e 's/path/eventHubName/g' target/azure-functions/*/Update-Product-Inventory/function.json
sed -i -e '17s/connection/connectionStringSetting/' target/azure-functions/*/Update-Product-Inventory/function.json

sed -i -e "s/PRODUCT_INVENTORY_DOCUMENTDB_DBNAME/$PRODUCT_INVENTORY_DOCUMENTDB_DBNAME/g" target/azure-functions/*/Update-Product-Inventory/function.json
sed -i -e "s/PRODUCT_INVENTORY_DOCUMENTDB_COLLECTION_NAME/$PRODUCT_INVENTORY_DOCUMENTDB_COLLECTION_NAME/g" target/azure-functions/*/Update-Product-Inventory/function.json

sed -i -e "s/TRANSACTIONS_EVENT_HUB_NAME/$TRANSACTIONS_EVENT_HUB_NAME/g" target/azure-functions/*/Update-Product-Inventory/function.json
sed -i -e "s/TRANSACTIONS_EVENT_HUB_CONSUMER_GROUP_NAME/$TRANSACTIONS_EVENT_HUB_CONSUMER_GROUP_NAME/g" target/azure-functions/*/Update-Product-Inventory/function.json
