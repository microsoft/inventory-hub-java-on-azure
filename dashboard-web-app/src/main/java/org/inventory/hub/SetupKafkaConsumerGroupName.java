/*
 * Copyright (c) Microsoft. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */
package org.inventory.hub;

import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.UUID;

@Component
public class SetupKafkaConsumerGroupName implements ApplicationListener<ApplicationStartedEvent>
{	

	/**
	 * This event is executed as late as conceivably possible to indicate that 
	 * the application is ready to service requests.
	 */

	@Override
	public void onApplicationEvent(final ApplicationStartedEvent applicationStartedEvent) {
		// WEBSITE_INSTANCE_ID=3bd0b3a7eafa0e40e89ad50242c3591b222ecc564d8875c1feadd4ac6f52234a
		String webSiteInstanceId = System.getenv("WEBSITE_INSTANCE_ID");
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
		try {
			Map<String, String> env = System.getenv();
			Class<?> cl = env.getClass();
			Field field = cl.getDeclaredField("m");
			field.setAccessible(true);
			Map<String, String> writableEnv = (Map<String, String>) field.get(env);
			writableEnv.put("NOTIFICATIONS_EVENT_HUB_CONSUMER_GROUP_NAME", webSiteInstanceId);
		} catch (Exception e) {
			throw new IllegalStateException("Failed to set environment variable", e);
		}

		System.out.println("DEBUGGING: Found new Spring Boot App consumer group ENV settings: " + applicationStartedEvent.getApplicationContext().getEnvironment().getSystemEnvironment().get("NOTIFICATIONS_EVENT_HUB_CONSUMER_GROUP_NAME"));
		System.out.println("DEBUGGING: Found new Spring Boot App property : " + applicationStartedEvent.getApplicationContext().getEnvironment().getProperty("spring.cloud.stream.bindings.input.group"));
	}
	
}



