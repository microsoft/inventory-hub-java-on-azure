# Deploy Inventory Processor (Transactions++) - Inventory Hub App using Java on Azure #

The purpose of this function app is to illustrate how to pick up events or transactions arriving through Event Hub and append those transactions to a Cosmos DB collection.

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
export APPEND_TRANSACTION_FUNCTION_APP_ID="<put-your-unique-guid>"
export APPEND_TRANSACTION_FUNCTION_APP_NAME="<put-your-unique-function-app-name>"
export APPEND_TRANSACTION_REGION_NAME="<put-your-region>"
export APPEND_TRANSACTION_RESOURCE_GROUP_NAME="<put-your-resource-group-name>"

export TRANSACTIONS_EVENT_HUB_NAME="<put-your-eventhub-for-transactions-name>"
export TRANSACTIONS_EVENT_HUB_CONSUMER_GROUP_NAME="<put-your-eventhub-for-transactions-consumer-group-name>"
export TRANSACTIONS_EVENT_HUB_CONNECTION_STRING="<put-your-eventhub-for-transactions-connection-string>"

export TRANSACTIONS_DOCUMENTDB_CONNECTION_STRING="<put-your-cosmosdb-connection-string>"
export TRANSACTIONS_DOCUMENTDB_DBNAME="<put-your-cosmosdb-database-name>"
export TRANSACTIONS_DOCUMENTDB_COLLECTION_NAME="<put-your-cosmosdb-collection-name>"
```

# Deploy Append Transactions

```shell
   mvn clean package azure-functions:deploy
   ```

# Contributing

This project welcomes contributions and suggestions.  Most contributions require you to agree to a
Contributor License Agreement (CLA) declaring that you have the right to, and actually do, grant us
the rights to use your contribution. For details, visit https://cla.microsoft.com.

When you submit a pull request, a CLA-bot will automatically determine whether you need to provide
a CLA and decorate the PR appropriately (e.g., label, comment). Simply follow the instructions
provided by the bot. You will only need to do this once across all repos using our CLA.

This project has adopted the [Microsoft Open Source Code of Conduct](https://opensource.microsoft.com/codeofconduct/).
For more information see the [Code of Conduct FAQ](https://opensource.microsoft.com/codeofconduct/faq/) or
contact [opencode@microsoft.com](mailto:opencode@microsoft.com) with any additional questions or comments.