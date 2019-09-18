if [ "$APP_CONFIGURATION_STORE_NAME" == "" ]; then echo "Unexpected error: environment variable APP_CONFIGURATION_STORE_NAME is not defined. Exiting..." && exit 1 
fi

echo "Using ""$APP_CONFIGURATION_STORE_NAME"" as a prefix for the names of the resources to be created"

# Create resource group 
az group create --name "$APP_CONFIGURATION_STORE_NAME""eastus-dev" --location eastus
az appconfig create --name "$APP_CONFIGURATION_STORE_NAME" --resource-group "$APP_CONFIGURATION_STORE_NAME""eastus-dev" --location eastus
az appconfig kv import --name "$APP_CONFIGURATION_STORE_NAME" --source file --path application.properties --format properties --prefix "/inventory-hub/" --yes
