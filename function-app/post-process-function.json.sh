sed -i -e 's/documentdb/cosmosDb/g' target/azure-functions/*/Update-Inventory/function.json
sed -i -e 's/path/eventHubName/g' target/azure-functions/*/Point-of-Sale/function.json
sed -i -e 's/path/eventHubName/g' target/azure-functions/*/Update-Inventory/function.json
sed -i -e '17s/connection/connectionStringSetting/' target/azure-functions/*/Update-Inventory/function.json
echo sed -i -e '9s/connection/connectionStringSetting/' target/azure-functions/*/Notify-Inventory-Update/function.json
echo sed -i -e '15s/connection/connectionStringSetting/' target/azure-functions/*/Notify-Inventory-Update/function.json
sed -i -e 's/path/eventHubName/g' target/azure-functions/*/Notify-Inventory-Update/function.json
