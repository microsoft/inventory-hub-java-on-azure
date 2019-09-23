# Deploy Inventory Hub Java Web App =TO=> Azure App Service

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
* [Integrate with Spring Security using Azure Active Directory B2C](#integrate-with-spring-security-using-azure-active-directory-b2c---optional-step)
* [Configuration](#configuration)
* [Build](#build-inventory-hub-web-app---jar)
* [Run Locally](#run-it-locally---optional-step)
* [Deploy to App Service](#deploy-to-azure-app-service)
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

## Integrate with Spring Security using Azure Active Directory B2C - OPTIONAL STEP

### Set up AAD B2C
- [Create B2C tenant](https://docs.microsoft.com/en-us/azure/active-directory-b2c/tutorial-create-tenant).
- [Register your application](https://docs.microsoft.com/en-us/azure/active-directory-b2c/tutorial-register-applications). For **Reply URL** enter \<your-app-url-copied-from-App-Service-Portal\>/home.
- [Create a sign-up and sign-in user flow](https://docs.microsoft.com/en-us/azure/active-directory-b2c/tutorial-create-user-flows).
- You can also use external identity providers, such as [Google](https://docs.microsoft.com/en-us/azure/active-directory-b2c/active-directory-b2c-setup-goog-app), [LinkedIn](https://docs.microsoft.com/en-us/azure/active-directory-b2c/active-directory-b2c-setup-li-app), [Microsoft Account](https://docs.microsoft.com/en-us/azure/active-directory-b2c/active-directory-b2c-setup-msa-app), and [Facebook](https://docs.microsoft.com/en-us/azure/active-directory-b2c/active-directory-b2c-setup-fb-app).

### Uncomment below properties in `application.properties`
   ```properties
azure.activedirectory.b2c.enabled=true
azure.activedirectory.b2c.tenant=put-your-b2c-tenant-name-here
azure.activedirectory.b2c.oidc-enabled=true
azure.activedirectory.b2c.client-id=put-your-registered-application-client-id-here
azure.activedirectory.b2c.client-secret=put-your-registered-application-client-secret-here
azure.activedirectory.b2c.reply-url=<your-app-url-copied-from-App-Service-Portal>/home
azure.activedirectory.b2c.logout-success-url=<your-app-url-copied-from-App-Service-Portal>/login
azure.activedirectory.b2c.user-flows.sign-up-or-sign-in=put-your-b2c-sign-up-sign-in-user-flow-name-here
server.use-forward-headers=true
   ```
### Uncomment below configurations, related to azure-webapp maven plugin, in `pom.xml`
  ```xml
<property>
   <name>TENANT_NAME</name>
   <value>${TENANT_NAME}</value>
</property>
<property>
   <name>B2C_CLIENT_ID</name>
   <value>${B2C_CLIENT_ID}</value>
</property>
<property>
   <name>B2C_CLIENT_SECRET</name>
   <value>${B2C_CLIENT_SECRET}</value>
</property>
<property>
   <name>B2C_REPLY_URL</name>
   <value>${B2C_REPLY_URL}</value>
</property>
<property>
   <name>B2C_LOGOUT_SUCCESS_URL</name>
   <value>${B2C_LOGOUT_SUCCESS_URL}</value>
</property>
<property>
   <name>USER_FLOW_SIGNUP_SIGNIN</name>
   <value>${USER_FLOW_SIGNUP_SIGNIN}</value>
</property>
  ```

## Build Inventory Hub Web App - JAR

```bash
mvn clean package
```

## Run it locally - OPTIONAL STEP

```bash
mvn spring-boot:run
```

Open `http://localhost:8080/` you can see the Inventory Hub app

## Deploy to Azure App Service

### Deploy

Deploy in one step.

```bash
mvn azure-webapp:deploy
```

```bash
...
...
[INFO] Start deploying to Web App inventory-hub...
[INFO] Authenticate with Azure CLI 2.0
[INFO] Stopping Web App before deploying artifacts...
[INFO] Successfully stopped Web App.
[INFO] Using 'UTF-8' encoding to copy filtered resources.
[INFO] Copying 1 resource to /inventory-hub-java-on-azure/dashboard-web-app/target/azure-webapp/inventory-hub
[INFO] Trying to deploy artifact to inventory-hub...
[INFO] Renaming /inventory-hub-java-on-azure/dashboard-web-app/target/azure-webapp/inventory-hub/inventory-hub-1.0-SNAPSHOT.jar to app.jar
[INFO] Deploying the zip package inventory-hub.zip...
[INFO] Successfully deployed the artifact to https://inventory-hub.azurewebsites.net
[INFO] Starting Web App after deploying artifacts...
[INFO] Successfully started Web App.
[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------
[INFO] Total time: 02:13 min
[INFO] Finished at: 2018-04-17T21:34:57-04:00
[INFO] Final Memory: 70M/730M
[INFO] ------------------------------------------------------------------------
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
