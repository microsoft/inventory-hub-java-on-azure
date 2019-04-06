# Build an Initial Layout - Inventory Hub App using Java on Azure #

The purpose of this sample application is to illustrate building responsive systems with serverless, event-driven Java on Azure; the result of this project will be to create a real-time inventory hub.

## Requirements ##

In order to create and deploy this sample application, you need to have the following:

An Azure subscription; if you don't already have an Azure subscription, you can activate your [MSDN subscriber benefits](https://azure.microsoft.com/pricing/member-offers/msdn-benefits-details/) or sign up for a [free Azure account](https://azure.microsoft.com/pricing/free-trial/).

In addition, you will need all of the following components before you go through the steps in this README:

| [Azure CLI](http://docs.microsoft.com/cli/azure/overview) | [Java 8](http://java.oracle.com/) | [Maven 3](http://maven.apache.org/) | [Git](https://github.com/) |

## Build an Initial Layout ##

1. Login to your Azure account and specify which subscription to use:

   ```shell
   az login
   az account set --subscription "<your-azure-subscription>"
   ```

   **NOTE**: You can use either a subscription name or id when specifying which subscription to use; to obtain a list of your subscriptions, type `az account list`.

2. Set a unique prefix for creating an initial layout on Azure.

   ```shell
   export INVENTORY_HUB_APP_NAME="<your-unique-prefix>"
   ```

3. Create Cosmos DB and Event Hubs

```shell
   source az_cmd_createInventoryCosmosDB.sh
   source az_cmd_createInventoryEventHubs_eastus.sh
   source az_cmd_createInventoryEventHubs_uksouth.sh
   source az_cmd_createInventoryEventHubs_southeastasia.sh
   ```