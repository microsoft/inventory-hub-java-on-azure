sed -i -e 's/documentdb/cosmosDb/g' target/azure-functions/*/Notify-Inventory-Update/function.json
sed -i -e 's/path/eventHubName/g' target/azure-functions/*/Notify-Inventory-Update/function.json
sed -i -e '17s/connection/connectionStringSetting/' target/azure-functions/*/Notify-Inventory-Update/function.json

sed -i -e "s/NOTIFICATIONS_DOCUMENTDB_DBNAME/$NOTIFICATIONS_DOCUMENTDB_DBNAME/g" target/azure-functions/*/Notify-Inventory-Update/function.json
sed -i -e "s/NOTIFICATIONS_DOCUMENTDB_COLLECTION_NAME/$NOTIFICATIONS_DOCUMENTDB_COLLECTION_NAME/g" target/azure-functions/*/Notify-Inventory-Update/function.json

sed -i -e "s/TRANSACTIONS_EVENT_HUB_NAME/$TRANSACTIONS_EVENT_HUB_NAME/g" target/azure-functions/*/Notify-Inventory-Update/function.json
sed -i -e "s/TRANSACTIONS_EVENT_HUB_CONSUMER_GROUP_NAME/$TRANSACTIONS_EVENT_HUB_CONSUMER_GROUP_NAME/g" target/azure-functions/*/Notify-Inventory-Update/function.json

sed -i -e "s/TRANSACTIONS_EVENT_HUB_NAME/$TRANSACTIONS_EVENT_HUB_NAME/g" target/azure-functions/*/Notify-Inventory-Update/function.json
sed -i -e "s/TRANSACTIONS_EVENT_HUB_CONSUMER_GROUP_NAME/$TRANSACTIONS_EVENT_HUB_CONSUMER_GROUP_NAME/g" target/azure-functions/*/Notify-Inventory-Update/function.json

sed -i -e "s/NOTIFICATIONS_EVENT_HUB_NAME/$NOTIFICATIONS_EVENT_HUB_NAME/g" target/azure-functions/*/Notify-Inventory-Update/function.json
sed -i -e "s/NOTIFICATIONS_EVENT_HUB_CONSUMER_GROUP_NAME/$NOTIFICATIONS_EVENT_HUB_CONSUMER_GROUP_NAME/g" target/azure-functions/*/Notify-Inventory-Update/function.json

rm target/azure-functions/*/lib/azure-functions-java-core-1.0.0-beta-2.jar
