# EventHub in "southeastasia"
az group create --name "inventory-hub-southeastasia-dev" --location "southeastasia"
az group deployment create --name "inventory-hub-eventhub-southeastasia-td1" --resource-group "inventory-hub-southeastasia-dev" --template-file createInventoryEventHub.json --parameters namespace_name="inventory-eventhub-southeastasia-dev1" region_name="southeastasia"

# list authorization rule keys and connection strings
az eventhubs eventhub authorization-rule keys list --resource-group "inventory-hub-southeastasia-dev" --namespace-name "inventory-eventhub-southeastasia-dev1" --eventhub-name "eventhub-for-notifications" --name "intakeEvents"
az eventhubs eventhub authorization-rule keys list --resource-group "inventory-hub-southeastasia-dev" --namespace-name "inventory-eventhub-southeastasia-dev1" --eventhub-name "eventhub-for-notifications" --name "dispatchEvents"
az eventhubs eventhub authorization-rule keys list --resource-group "inventory-hub-southeastasia-dev" --namespace-name "inventory-eventhub-southeastasia-dev1" --eventhub-name "eventhub-for-transactions" --name "intakeEvents"
az eventhubs eventhub authorization-rule keys list --resource-group "inventory-hub-southeastasia-dev" --namespace-name "inventory-eventhub-southeastasia-dev1" --eventhub-name "eventhub-for-transactions" --name "dispatchEvents"

