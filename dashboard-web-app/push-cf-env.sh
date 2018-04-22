# Setup Cloud Foundry Environment Variables for Inventory Hub
# Insert environment values and run source push-cf-env.sh

cf set-env inventory-hub  DOCUMENTDB_URI put-your-documentdb-uri-here
cf set-env inventory-hub DOCUMENTDB_KEY put-your-documentdb-key-here
cf set-env inventory-hub DOCUMENTDB_DBNAME put-your-documentdb-databasename-here

cf set-env inventory-hub NOTIFICATIONS_EVENT_HUB_NAME put-your-eventhub-for-notifications
cf set-env inventory-hub NOTIFICATIONS_EVENT_HUB_CONSUMER_GROUP_NAME put-your-eventhub-consumer-group-for-notifications 
cf set-env inventory-hub NOTIFICATIONS_EVENT_HUB_CONNECTION_STRING put-your-event-hub-connection-string
cf set-env inventory-hub NOTIFICATIONS_STORAGE_CONTAINER_NAME put-your-storage-container-name
cf set-env inventory-hub NOTIFICATIONS_STORAGE_CONNECTION_STRING put-your-storage-connection-string

