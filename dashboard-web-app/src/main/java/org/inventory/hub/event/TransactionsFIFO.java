/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See LICENSE in the project root for
 * license information.
 */

package org.inventory.hub.event;

import org.inventory.hub.controller.TransactionsController;

import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;

import java.util.ArrayDeque;

@Component
public class TransactionsFIFO 
    extends ArrayDeque<Transaction> 
    implements ApplicationListener<ApplicationReadyEvent> {

    private int size = 100;

    protected SimpMessageSendingOperations webSocket;

    @Autowired
    private ApplicationContext context;

    public TransactionsFIFO(){
    }

    @Override
    public void addFirst (Transaction transaction) {
        if (size() > size)
            removeLast();

        super.addFirst(transaction);
        if (webSocket != null) {
            String message = "{ \"name\":\"" + transaction.toString() + "\"}";
            System.out.println("Sending to /topic/reply with message = " + message);
            //webSocket.convertAndSend("/app/message", message);
            webSocket.convertAndSend("/topic/reply", message);
        }
		else
		    System.out.println("===== Web Socket Sender is NULL (Transactions FIFO) =====");
    }

    public void init(){
        // init code goes here

        System.out.println("=== POST CONSTRUCTION ===");
        System.out.println("--- Context = " + context.toString());
        WebSocketController controller = (WebSocketController) context.getBean(WebSocketController.class);
        System.out.println("--- WebSocket Controller = " + controller.toString());
        System.out.println("--- Message Template = " + controller.messagingTemplate);
        System.out.println("--- Setting webSocket for push ...");
        TransactionsController.transactions.webSocket = controller.messagingTemplate;
        System.out.println("--- Set webSocket for push " + TransactionsController.transactions.webSocket);

    }

    /**
	 * This event is executed as late as conceivably possible to indicate that 
	 * the application is ready to service requests.
	 */
	@Override
	public void onApplicationEvent(final ApplicationReadyEvent event) {
	
		System.out.println("hello world, I have just started up");
		System.out.println("=== app ready event inside transactions fifo ===\n" + event);

        init();
        System.out.println("====== Initialized Web Socket for Push =====");
	
		return;
	}

}