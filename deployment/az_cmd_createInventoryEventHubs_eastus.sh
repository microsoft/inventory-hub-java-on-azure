if [ "$INVENTORY_HUB_APP_NAME" == "" ]; then echo "Unexpected error: environment variable INVENTORY_HUB_APP_NAME is not defined. Exiting..." && exit 1 
fi

echo "Using ""$INVENTORY_HUB_APP_NAME"" as a prefix for the names of the resources to be created"

# EventHub in "eastus"
az group create --name "$INVENTORY_HUB_APP_NAME""-eastus-dev" --location "eastus"
az group deployment create --name "$INVENTORY_HUB_APP_NAME""-eventhub-eastus-td1" --resource-group "$INVENTORY_HUB_APP_NAME""-eastus-dev" --template-file createInventoryEventHub.json --parameters namespace_name="$INVENTORY_HUB_APP_NAME""-eventhub-eastus" region_name="eastus"

# List authorization rule keys and connection strings
az eventhubs eventhub authorization-rule keys list --resource-group "$INVENTORY_HUB_APP_NAME""-eastus-dev" --namespace-name "$INVENTORY_HUB_APP_NAME""-eventhub-eastus" --eventhub-name "eventhub-for-notifications" --name "intakeEvents"
az eventhubs eventhub authorization-rule keys list --resource-group "$INVENTORY_HUB_APP_NAME""-eastus-dev" --namespace-name "$INVENTORY_HUB_APP_NAME""-eventhub-eastus" --eventhub-name "eventhub-for-notifications" --name "dispatchEvents"
az eventhubs eventhub authorization-rule keys list --resource-group "$INVENTORY_HUB_APP_NAME""-eastus-dev" --namespace-name "$INVENTORY_HUB_APP_NAME""-eventhub-eastus" --eventhub-name "eventhub-for-transactions" --name "intakeEvents"
az eventhubs eventhub authorization-rule keys list --resource-group "$INVENTORY_HUB_APP_NAME""-eastus-dev" --namespace-name "$INVENTORY_HUB_APP_NAME""-eventhub-eastus" --eventhub-name "eventhub-for-transactions" --name "dispatchEvents"
