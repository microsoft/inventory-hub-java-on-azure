# Deploy Point-of-Sale or Point-of-Inventory Simulator - Inventory Hub App using Java on Azure #

The purpose of this function app is to illustrate how to pick up transactions represented as streamed events in Event Hub and update documents in Cosmos DB.

## Requirements

In order to create and deploy this function app, you need to have the following:

An Azure subscription; if you don't already have an Azure subscription, you can activate your [MSDN subscriber benefits](https://azure.microsoft.com/pricing/member-offers/msdn-benefits-details/) or sign up for a [free Azure account](https://azure.microsoft.com/pricing/free-trial/).

In addition, you will need all of the following components before you go through the steps in this README:

| [Azure CLI](http://docs.microsoft.com/cli/azure/overview) | [Java 8](http://java.oracle.com/) | [Maven 3](http://maven.apache.org/) | [Git](https://github.com/) |

## Login to Azure Account

Login to your Azure account and specify which subscription to use:

   ```shell
   az login
   az account set --subscription "<your-azure-subscription>"
   ```

   **NOTE**: You can use either a subscription name or id when specifying which subscription to use; to obtain a list of your subscriptions, type `az account list`.

## Configuration
Set these values in system environment variables:

``` txt
export UPDATE_PRODUCT_INVENTORY_FUNCTION_APP_ID="<put-your-unique-guid>"
export UPDATE_PRODUCT_INVENTORY_FUNCTION_APP_NAME="<put-your-unique-function-app-name>"
export UPDATE_PRODUCT_INVENTORY_REGION_NAME="<put-your-region>"
export UPDATE_PRODUCT_INVENTORY_RESOURCE_GROUP_NAME="<put-your-resource-group-name>"

export TRANSACTIONS_EVENT_HUB_NAME="<put-your-eventhub-for-transactions-name>"
export TRANSACTIONS_EVENT_HUB_CONSUMER_GROUP_NAME="<put-your-eventhub-for-transactions-consumer-group-name>"
export TRANSACTIONS_EVENT_HUB_CONNECTION_STRING="<put-your-eventhub-for-transactions-connection-string>"

export PRODUCT_INVENTORY_COSMOSDB_CONNECTION_STRING="<put-your-cosmosdb-connection-string>"
export PRODUCT_INVENTORY_COSMOSDB_DBNAME="<put-your-cosmosdb-database-name>"
export PRODUCT_INVENTORY_COSMOSDB_COLLECTION_NAME="<put-your-cosmosdb-collection-name>"

export UPI_FUNCTION_APP_STORAGE_ACCOUNT_CONNECTION_STRING="<put-your-storage-account-connection-string>"
```

# Deploy Notify Inventory Update Function

```shell
mvn clean package azure-functions:deploy
```