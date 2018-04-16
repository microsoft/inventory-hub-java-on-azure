/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See LICENSE in the project root for
 * license information.
 */
package org.inventory.hub.controller;

import org.inventory.hub.event.TransactionsFIFO;
import org.inventory.hub.event.Transaction;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class TransactionsController {

    public static TransactionsFIFO transactions = new TransactionsFIFO();

    public TransactionsController() {
    }

    /**
     * HTTP GET ALL
     */
    @RequestMapping(value = "/api/transactions", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<?> getTransactions() {

        if (transactions == null)
            transactions = new TransactionsFIFO();

        try {
            
            System.out.println("======= /api/transactions ===== ");
            Transaction[] transactionsNow = transactions.toArray(new 
                                            Transaction[transactions.size()]);
            System.out.println("====== success");

            return new ResponseEntity<Transaction[]> (transactionsNow, HttpStatus.OK);
        
        } catch (Exception e) {
            System.out.println("======== EXCEPTION =======");
            e.printStackTrace();
            return new ResponseEntity<String>("Nothing found", HttpStatus.NOT_FOUND);
        }
    }
}
