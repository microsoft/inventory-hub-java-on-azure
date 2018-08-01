if [ "$INVENTORY_HUB_APP_NAME" == "" ]; then echo "Unexpected error: environment variable INVENTORY_HUB_APP_NAME is not defined. Exiting..." && exit 1 
fi

echo "Using ""$INVENTORY_HUB_APP_NAME"" as a prefix for the names of the resources to be created"

# CosmosDB account
az group create --name "$INVENTORY_HUB_APP_NAME""-main-dev" --location "eastus"
az group deployment create --name "$INVENTORY_HUB_APP_NAME""-cosmosdb-td1" --resource-group "$INVENTORY_HUB_APP_NAME""-main-dev" --template-file ../deployment/createInventoryCosmosDB.json --parameters cosmosDBName="$INVENTORY_HUB_APP_NAME""-cosmosdb-main-dev1" primaryLocation="eastus" secondaryLocation="uksouth" thirdLocation="southeastasia" 

# CosmosDB Database
az cosmosdb database create --resource-group "$INVENTORY_HUB_APP_NAME""-main-dev" --name "$INVENTORY_HUB_APP_NAME""-cosmosdb-main-dev1" --db-name "inventory"

# CosmosDB collections
az cosmosdb collection create --resource-group "$INVENTORY_HUB_APP_NAME""-main-dev" --name "$INVENTORY_HUB_APP_NAME""-cosmosdb-main-dev1" --db-name "inventory" --collection-name "product-items" --partition-key-path "/productId" --throughput 5000
az cosmosdb collection create --resource-group "$INVENTORY_HUB_APP_NAME""-main-dev" --name "$INVENTORY_HUB_APP_NAME""-cosmosdb-main-dev1" --db-name "inventory" --collection-name "product-inventory" --partition-key-path "/location" --throughput 2000
az cosmosdb collection create --resource-group "$INVENTORY_HUB_APP_NAME""-main-dev" --name "$INVENTORY_HUB_APP_NAME""-cosmosdb-main-dev1" --db-name "inventory" --collection-name "transaction-events" --partition-key-path "/type" --throughput 2000

# Add another region "westus2"
# az cosmosdb update --resource-group "$INVENTORY_HUB_APP_NAME""-main-dev" --name "$INVENTORY_HUB_APP_NAME""-cosmosdb-main-dev1" --locations "eastus"=0 "uksouth"=1 "southeastasia"=2 "westus2"=3


