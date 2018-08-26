if [ "$INVENTORY_HUB_APP_NAME" == "" ]; then echo "Unexpected error: environment variable INVENTORY_HUB_APP_NAME is not defined. Exiting..." && exit 1 
fi

echo "Using ""$INVENTORY_HUB_APP_NAME"" as a prefix for the names of the resources to be created"

# EventHub in "southeastasia"
az group create --name "$INVENTORY_HUB_APP_NAME""-southeastasia-dev" --location "southeastasia"
az group deployment create --name "$INVENTORY_HUB_APP_NAME""-eventhub-southeastasia-td1" --resource-group "$INVENTORY_HUB_APP_NAME""-southeastasia-dev" --template-file createInventoryEventHub.json --parameters namespace_name="$INVENTORY_HUB_APP_NAME""-eventhub-k-southeastasia" region_name="southeastasia"

# list authorization rule keys and connection strings
az eventhubs eventhub authorization-rule keys list --resource-group "$INVENTORY_HUB_APP_NAME""-southeastasia-dev" --namespace-name "$INVENTORY_HUB_APP_NAME""-eventhub-k-southeastasia" --eventhub-name "eventhub-for-notifications" --name "intakeEvents"
az eventhubs eventhub authorization-rule keys list --resource-group "$INVENTORY_HUB_APP_NAME""-southeastasia-dev" --namespace-name "$INVENTORY_HUB_APP_NAME""-eventhub-k-southeastasia" --eventhub-name "eventhub-for-notifications" --name "dispatchEvents"
az eventhubs eventhub authorization-rule keys list --resource-group "$INVENTORY_HUB_APP_NAME""-southeastasia-dev" --namespace-name "$INVENTORY_HUB_APP_NAME""-eventhub-k-southeastasia" --eventhub-name "eventhub-for-transactions" --name "intakeEvents"
az eventhubs eventhub authorization-rule keys list --resource-group "$INVENTORY_HUB_APP_NAME""-southeastasia-dev" --namespace-name "$INVENTORY_HUB_APP_NAME""-eventhub-k-southeastasia" --eventhub-name "eventhub-for-transactions" --name "dispatchEvents"

