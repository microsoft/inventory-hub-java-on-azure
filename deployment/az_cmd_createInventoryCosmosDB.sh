az group deployment create --name "inventory-hub-cosmosdb-dev-td" --resource-group "inventory-hub-dev" --template-file ../deployment/createInventoryCosmosDB.json --parameters cosmosDBName="inventory-hub-cosmosdb-dev" primaryLocation="eastus" secondaryLocation="uksouth" thirdLocation="southeastasia" 
az cosmosdb database create --resource-group "inventory-hub-dev" --name "inventory-hub-cosmosdb-dev" --db-name "inventory"
az cosmosdb collection create --resource-group "inventory-hub-dev" --name "inventory-hub-cosmosdb-dev" --db-name "inventory" --collection-name "product-items"
az cosmosdb collection create --resource-group "inventory-hub-dev" --name "inventory-hub-cosmosdb-dev" --db-name "inventory" --collection-name "product-inventory"
az cosmosdb collection create --resource-group "inventory-hub-dev" --name "inventory-hub-cosmosdb-dev" --db-name "inventory" --collection-name "transaction-events"

