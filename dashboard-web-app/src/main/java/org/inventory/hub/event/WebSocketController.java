/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See LICENSE in the project root for
 * license information.
 */

package org.inventory.hub.event;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageExceptionHandler;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Controller;

@Controller
public class WebSocketController {

	@Autowired
	protected SimpMessageSendingOperations messagingTemplate;

	@MessageMapping("/message")
    @SendTo("/topic/reply")
	public String processMessageFromClient(@Payload String message) throws Exception {
		System.out.println("Process Message From Client == " + message);
        // String name = new Gson().fromJson(message, Map.class).get("name").toString();
		return message;
	}
	
	@MessageExceptionHandler
    public String handleException(Throwable exception) {
        messagingTemplate.convertAndSend("/errors", exception.getMessage());
	    return exception.getMessage();
    }

}