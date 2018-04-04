sed -i -e 's/documentdb/cosmosDb/g' target/azure-functions/*/Update-Inventory/function.json
sed -i -e 's/path/eventHubName/g' target/azure-functions/*/Point-of-Sale/function.json
sed -i -e 's/path/eventHubName/g' target/azure-functions/*/Update-Inventory/function.json
sed -i -e 's/connection/connectionStringSetting/1' target/azure-functions/*/Update-Inventory/function.json
