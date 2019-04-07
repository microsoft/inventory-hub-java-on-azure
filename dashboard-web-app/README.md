# Deploy Inventory Hub Java Web App =TO=> Tomcat in Azure App Service

This Inventory Hub app is a Java application. It display product
inventory using AngularJS code. 
Behind the scene, the inventory data store 
is [Azure CosmosDB DocumentDB](https://docs.microsoft.com/en-us/azure/cosmos-db/documentdb-introduction). 
This application uses [Azure CosmosDB DocumentDB Spring Boot Starter](https://github.com/Microsoft/azure-spring-boot/tree/master/azure-starters/azure-documentdb-spring-boot-starter) 
and AngularJS to interact with Azure. This sample application 
provides several deployment options to deploy to Azure, pls 
see deployment section below. With Azure support in Spring 
Starters, maven plugins and Eclipse / IntelliJ plugins, 
Java application development and deployment on Azure
are effortless now.


## TOC

* [Requirements](#requirements)
* [Create Azure Cosmos DB and Event Hub](#create-azure-cosmos-db-and-event-hub)
* [Configuration](#configuration)
* [Build](#build-inventory-hub-web-app---war)
* [Run Locally](#run-it-locally---optional-step)
* [Deploy to Tomcat on App Service](#deploy-to-tomcat-on-azure-app-service)
* [Contribution](#contribution)
* [Useful links](#useful-links)

## Requirements

* [JDK](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html) 1.8 and above
* [Maven](https://maven.apache.org/) 3.0 and above
* [Tomcat](https://tomcat.apache.org/download-80.cgi) 8.5 and above

## Create Azure Cosmos DB and Event Hub

You can follow steps described in the [deployment folder][../deployment/README.md].

## Configuration

Note down your DocumentDB uri and key from last step, 
specify a database name but no need to create it. Pick an 
Azure Resource Group name and Web app name for App Service - 
you can use an existing resource group and Web 
app or let the Maven plugin create these for you. 
Set these values in system environment variables:

``` txt
COSMOSDB_URI=put-your-COSMOSDB-uri-here
COSMOSDB_KEY=put-your-COSMOSDB-key-here
COSMOSDB_DBNAME=put-your-COSMOSDB-databasename-here

NOTIFICATIONS_EVENT_HUB_NAME=put-your-eventhub-for-notifications
NOTIFICATIONS_EVENT_HUB_CONSUMER_GROUP_NAME="\$Default"
NOTIFICATIONS_EVENT_HUB_CONNECTION_STRING=put-your-event-hub-connection-string
NOTIFICATIONS_STORAGE_CONTAINER_NAME=put-your-storage-container-name
NOTIFICATIONS_STORAGE_CONNECTION_STRING=put-your-storage-connection-string

WEBAPP_RESOURCEGROUP_NAME=put-your-resourcegroup-name-here
WEBAPP_NAME=put-your-webapp-name-here
WEBAPP_REGION=put-your-region-here
```

Optional. If you plan to test the Web app locally, then 
you must start a local instance of Tomcat. Set another value in
the system environment variable

``` txt
TOMCAT_HOME=put-your-tomcat-home-here
```

## Build Inventory Hub Web App - WAR

```bash
mvn clean package
```

## Run it locally - OPTIONAL STEP

Deploy the Inventory Hub app to local Tomcat. You must start 
a local instance of Tomcat.

```bash
mvn cargo:deploy
```

Open `http://localhost:8080/` you can see the Inventory Hub app

## Deploy to Tomcat on Azure App Service

### Temporary Step - Until the Updated Maven Plugin for Azure Web Apps is released

Install a SNAPSHOT version of the Maven Plugin for Azure Web Apps:

```bash
git clone https://github.com/Microsoft/azure-maven-plugins.git
cd azure-maven-plugins
git checkout cs/wardeploy
mvn clean install -DskipTests
```
### Deploy to Tomcat on Azure App Service

Deploy in one step. You can continue to deploy again and 
again without restarting Tomcat.

```bash
mvn azure-webapp:deploy
```

```bash
...
...
[INFO] Start deploying to Web App inventory-hub...
[INFO] Authenticate with Azure CLI 2.0
[INFO] Updating target Web App...
[INFO] Successfully updated Web App.
[INFO] Starting to deploy the war file...
[INFO] Successfully deployed Web App at https://inventory-hub.azurewebsites.net
[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------
[INFO] Total time: 02:13 min
[INFO] Finished at: 2018-04-17T21:34:57-04:00
[INFO] Final Memory: 70M/730M
[INFO] ------------------------------------------------------------------------
```

### Enable Web Sockets in Azure App Service

```bash
az webapp config set -n ${WEBAPP_NAME} -g ${WEBAPP_RESOURCEGROUP_NAME} --web-sockets-enabled true
az webapp stop -n ${WEBAPP_NAME} -g ${WEBAPP_RESOURCEGROUP_NAME}
az webapp start -n ${WEBAPP_NAME} -g ${WEBAPP_RESOURCEGROUP_NAME}
```

### Open the Inventory Hub Web app

Open it in a browser

![](./media/inventory-hub-app.jpg)

## Clean up

Delete the Azure resources you created by running the following command:

```bash
az group delete -y --no-wait -n <your-resource-group-name>
```

## Contribution

This project welcomes contributions and suggestions.  Most contributions require you to agree to a
Contributor License Agreement (CLA) declaring that you have the right to, and actually do, grant us
the rights to use your contribution. For details, visit https://cla.microsoft.com.

When you submit a pull request, a CLA-bot will automatically determine whether you need to provide
a CLA and decorate the PR appropriately (e.g., label, comment). Simply follow the instructions
provided by the bot. You will only need to do this once across all repos using our CLA.

This project has adopted the [Microsoft Open Source Code of Conduct](https://opensource.microsoft.com/codeofconduct/).
For more information see the [Code of Conduct FAQ](https://opensource.microsoft.com/codeofconduct/faq/) or
contact [opencode@microsoft.com](mailto:opencode@microsoft.com) with any additional questions or comments.

## Useful links
- [Azure Spring Boot Starters](https://github.com/Microsoft/azure-spring-boot)
- [Azure Maven plugins](https://github.com/Microsoft/azure-maven-plugins)
