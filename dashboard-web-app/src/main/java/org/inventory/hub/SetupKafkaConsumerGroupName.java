/*
 * Copyright (c) Microsoft. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */
package org.inventory.hub;

import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class SetupKafkaConsumerGroupName implements ApplicationListener<ApplicationStartedEvent>
{	

	/**
	 * This event is executed as late as conceivably possible to indicate that 
	 * the application is ready to service requests.
	 */

	@Override
	public void onApplicationEvent(final ApplicationStartedEvent applicationStartedEvent) {
		//System.out.println("DEBUGGING: Found new Spring Boot App property : " + applicationStartedEvent.getApplicationContext().getEnvironment().getProperty("spring.cloud.stream.bindings.input.group"));

		System.out.println("======= ApplicationStartingEvent ===== ");
	    System.out.println("What is the Event Hubs Consumer Group Name?");
		System.out.println("NOTIFICATIONS_EVENT_HUB_CONSUMER_GROUP_NAME=" +
				applicationStartedEvent.
						getApplicationContext().
						getEnvironment().
						getProperty("spring.cloud.stream.bindings.input.group"));
	}
	
}



