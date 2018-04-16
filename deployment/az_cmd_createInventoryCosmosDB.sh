az group create --name "inventory-hub-main-dev" --location "eastus"
az group deployment create --name "inventory-hub-cosmosdb-td1" --resource-group "inventory-hub-main-dev" --template-file ../deployment/createInventoryCosmosDB.json --parameters cosmosDBName="inventory-hub-cosmosdb-main-dev1" primaryLocation="eastus" secondaryLocation="uksouth" thirdLocation="southeastasia" 
az cosmosdb database create --resource-group "inventory-hub-main-dev" --name "inventory-hub-cosmosdb-main-dev1" --db-name "inventory"
az cosmosdb collection create --resource-group "inventory-hub-main-dev" --name "inventory-hub-cosmosdb-main-dev1" --db-name "inventory" --collection-name "product-items"
az cosmosdb collection create --resource-group "inventory-hub-main-dev" --name "inventory-hub-cosmosdb-main-dev1" --db-name "inventory" --collection-name "product-inventory"
az cosmosdb collection create --resource-group "inventory-hub-main-dev" --name "inventory-hub-cosmosdb-main-dev1" --db-name "inventory" --collection-name "transaction-events"

# Add another region "westus2"
# az cosmosdb update --resource-group "inventory-hub-main-dev" --name "inventory-hub-cosmosdb-main-dev1" --locations "eastus"=0 "uksouth"=1 "southeastasia"=2 "westus2"=3


