/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See LICENSE in the project root for
 * license information.
 */
package org.inventory.hub;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.messaging.Message;

import org.codehaus.jackson.map.ObjectMapper;

import org.inventory.hub.controller.TransactionsController;
import org.inventory.hub.event.Transaction;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.UUID;

@EnableBinding(Sink.class)
@SpringBootApplication
public class InventoryHub extends SpringBootServletInitializer {

    ObjectMapper objectMapper = new ObjectMapper();
    Transaction transaction = new Transaction();

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        try {
            // Azure WEBSITE_INSTANCE_ID=3bd0b3a7eafa0e40e89ad50242c3591b222ecc564d8875c1feadd4ac6f52234a
            String webSiteInstanceId = System.getenv("WEBSITE_INSTANCE_ID");
            // Cloud Foundry CF_INSTANCE_GUID=41653aa4-3a3a-486a-4431-ef258b39f042
            if (webSiteInstanceId == null || webSiteInstanceId.isEmpty()) {
                webSiteInstanceId = System.getenv("CF_INSTANCE_GUID");
            }
            if (webSiteInstanceId == null || webSiteInstanceId.isEmpty()) {
                webSiteInstanceId = System.getenv("NOTIFICATIONS_EVENT_HUB_CONSUMER_GROUP_NAME");
            }
            if (webSiteInstanceId == null || webSiteInstanceId.isEmpty()) {
                webSiteInstanceId = UUID.randomUUID().toString();
            }
            if (webSiteInstanceId.length() > 49) {
                webSiteInstanceId = webSiteInstanceId.substring(0, 49);
            }

            // This will replace System environment variable value with the newly computed value
            Map<String, String> env = System.getenv();
            Class<?> cl = env.getClass();
            Field field = cl.getDeclaredField("m");
            field.setAccessible(true);
            Map<String, String> writableEnv = (Map<String, String>) field.get(env);
            writableEnv.put("NOTIFICATIONS_EVENT_HUB_CONSUMER_GROUP_NAME", webSiteInstanceId);
        } catch (Exception e) {
            throw new IllegalStateException("Failed to set environment variable", e);
        }

        return application.sources(InventoryHub.class);
    }

    public static void main(String[] args) throws Exception {
        SpringApplication.run(InventoryHub.class, args);
    }

    @StreamListener(target=Sink.INPUT)
	public void receiveTransaction(String transactionMessage) {

        System.out.println("message is: " + transactionMessage);

        try 
        {
            transaction = objectMapper.readValue(
                new String(transactionMessage.getBytes(), "UTF8"), Transaction.class);

            TransactionsController.transactions.addFirst(transaction);

            System.out.println("=== event data ===\n" + transaction.toString());
        
        } catch (Exception e)
        {
            System.out.println("Processing failed for an event: " + e.toString());
			e.printStackTrace();
        }

    }
    
    @StreamListener("errorChannel")
	public void errorGlobal(Message<?> message) {
		System.out.println("Handling ERROR: " + message);
	}


}
