sed -i -e 's/documentdb/cosmosDb/g' target/azure-functions/*/Append-Transaction/function.json
sed -i -e 's/path/eventHubName/g' target/azure-functions/*/Append-Transaction/function.json
sed -i -e '17s/connection/connectionStringSetting/' target/azure-functions/*/Append-Transaction/function.json

sed -i -e "s/TRANSACTIONS_DOCUMENTDB_DBNAME/$TRANSACTIONS_DOCUMENTDB_DBNAME/g" target/azure-functions/*/Append-Transaction/function.json
sed -i -e "s/TRANSACTIONS_DOCUMENTDB_COLLECTION_NAME/$TRANSACTIONS_DOCUMENTDB_COLLECTION_NAME/g" target/azure-functions/*/Append-Transaction/function.json

sed -i -e "s/TRANSACTIONS_EVENT_HUB_NAME/$TRANSACTIONS_EVENT_HUB_NAME/g" target/azure-functions/*/Append-Transaction/function.json
sed -i -e "s/TRANSACTIONS_EVENT_HUB_CONSUMER_GROUP_NAME/$TRANSACTIONS_EVENT_HUB_CONSUMER_GROUP_NAME/g" target/azure-functions/*/Append-Transaction/function.json

rm target/azure-functions/*/lib/azure-functions-java-core-1.0.0-beta-2.jar