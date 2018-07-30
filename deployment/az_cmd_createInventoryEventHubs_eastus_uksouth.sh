# EventHub in "eastus"
az group create --name "inventory-hub-eastus-dev" --location "eastus"
az group deployment create --name "inventory-hub-eventhub-eastus-td1" --resource-group "inventory-hub-eastus-dev" --template-file createInventoryEventHub.json --parameters namespace_name="inventory-eventhub-eastus-kafka1" region_name="eastus"

# List authorization rule keys and connection strings
az eventhubs eventhub authorization-rule keys list --resource-group "inventory-hub-eastus-dev" --namespace-name "inventory-eventhub-eastus-kafka1" --eventhub-name "eventhub-for-notifications" --name "intakeEvents"
az eventhubs eventhub authorization-rule keys list --resource-group "inventory-hub-eastus-dev" --namespace-name "inventory-eventhub-eastus-kafka1" --eventhub-name "eventhub-for-notifications" --name "dispatchEvents"
az eventhubs eventhub authorization-rule keys list --resource-group "inventory-hub-eastus-dev" --namespace-name "inventory-eventhub-eastus-kafka1" --eventhub-name "eventhub-for-transactions" --name "intakeEvents"
az eventhubs eventhub authorization-rule keys list --resource-group "inventory-hub-eastus-dev" --namespace-name "inventory-eventhub-eastus-kafka1" --eventhub-name "eventhub-for-transactions" --name "dispatchEvents"

# EventHub in "uksouth"
az group create --name "inventory-hub-uksouth-dev" --location "uksouth"
az group deployment create --name "inventory-hub-eventhub-uksouth-td1" --resource-group "inventory-hub-uksouth-dev" --template-file createInventoryEventHub.json --parameters namespace_name="inventory-eventhub-uksouth-kafka1" region_name="uksouth"

# List authorization rule keys and connection strings
az eventhubs eventhub authorization-rule keys list --resource-group "inventory-hub-uksouth-dev" --namespace-name "inventory-eventhub-uksouth-kafka1" --eventhub-name "eventhub-for-notifications" --name "intakeEvents"
az eventhubs eventhub authorization-rule keys list --resource-group "inventory-hub-uksouth-dev" --namespace-name "inventory-eventhub-uksouth-kafka1" --eventhub-name "eventhub-for-notifications" --name "dispatchEvents"
az eventhubs eventhub authorization-rule keys list --resource-group "inventory-hub-uksouth-dev" --namespace-name "inventory-eventhub-uksouth-kafka1" --eventhub-name "eventhub-for-transactions" --name "intakeEvents"
az eventhubs eventhub authorization-rule keys list --resource-group "inventory-hub-uksouth-dev" --namespace-name "inventory-eventhub-uksouth-kafka1" --eventhub-name "eventhub-for-transactions" --name "dispatchEvents"
