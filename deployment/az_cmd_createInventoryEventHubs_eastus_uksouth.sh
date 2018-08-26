if [ "$INVENTORY_HUB_APP_NAME" == "" ]; then echo "Unexpected error: environment variable INVENTORY_HUB_APP_NAME is not defined. Exiting..." && exit 1 
fi

echo "Using ""$INVENTORY_HUB_APP_NAME"" as a prefix for the names of the resources to be created"

# EventHub in "eastus"
az group create --name "$INVENTORY_HUB_APP_NAME""-eastus-dev" --location "eastus"
az group deployment create --name "$INVENTORY_HUB_APP_NAME""-eventhub-eastus-td1" --resource-group "$INVENTORY_HUB_APP_NAME""-eastus-dev" --template-file createInventoryEventHub.json --parameters namespace_name="$INVENTORY_HUB_APP_NAME""-eventhub-k-eastus" region_name="eastus"

# List authorization rule keys and connection strings
az eventhubs eventhub authorization-rule keys list --resource-group "$INVENTORY_HUB_APP_NAME""-eastus-dev" --namespace-name "$INVENTORY_HUB_APP_NAME""-eventhub-k-eastus" --eventhub-name "eventhub-for-notifications" --name "intakeEvents"
az eventhubs eventhub authorization-rule keys list --resource-group "$INVENTORY_HUB_APP_NAME""-eastus-dev" --namespace-name "$INVENTORY_HUB_APP_NAME""-eventhub-k-eastus" --eventhub-name "eventhub-for-notifications" --name "dispatchEvents"
az eventhubs eventhub authorization-rule keys list --resource-group "$INVENTORY_HUB_APP_NAME""-eastus-dev" --namespace-name "$INVENTORY_HUB_APP_NAME""-eventhub-k-eastus" --eventhub-name "eventhub-for-transactions" --name "intakeEvents"
az eventhubs eventhub authorization-rule keys list --resource-group "$INVENTORY_HUB_APP_NAME""-eastus-dev" --namespace-name "$INVENTORY_HUB_APP_NAME""-eventhub-k-eastus" --eventhub-name "eventhub-for-transactions" --name "dispatchEvents"

# EventHub in "uksouth"
#   Work around - create Kafka enabled Event Hub in "West Europe" instead of "UK South"
az group create --name "$INVENTORY_HUB_APP_NAME""-uksouth-dev" --location "uksouth"
az group deployment create --name "$INVENTORY_HUB_APP_NAME""-eventhub-uksouth-td1" --resource-group "$INVENTORY_HUB_APP_NAME""-uksouth-dev" --template-file createInventoryEventHub.json --parameters namespace_name="$INVENTORY_HUB_APP_NAME""-eventhub-k-uksouth" region_name="westeurope"

# List authorization rule keys and connection strings
az eventhubs eventhub authorization-rule keys list --resource-group "$INVENTORY_HUB_APP_NAME""-uksouth-dev" --namespace-name "$INVENTORY_HUB_APP_NAME""-eventhub-k-uksouth" --eventhub-name "eventhub-for-notifications" --name "intakeEvents"
az eventhubs eventhub authorization-rule keys list --resource-group "$INVENTORY_HUB_APP_NAME""-uksouth-dev" --namespace-name "$INVENTORY_HUB_APP_NAME""-eventhub-k-uksouth" --eventhub-name "eventhub-for-notifications" --name "dispatchEvents"
az eventhubs eventhub authorization-rule keys list --resource-group "$INVENTORY_HUB_APP_NAME""-uksouth-dev" --namespace-name "$INVENTORY_HUB_APP_NAME""-eventhub-k-uksouth" --eventhub-name "eventhub-for-transactions" --name "intakeEvents"
az eventhubs eventhub authorization-rule keys list --resource-group "$INVENTORY_HUB_APP_NAME""-uksouth-dev" --namespace-name "$INVENTORY_HUB_APP_NAME""-eventhub-k-uksouth" --eventhub-name "eventhub-for-transactions" --name "dispatchEvents"
