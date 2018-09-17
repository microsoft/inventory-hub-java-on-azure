/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */
package org.inventory.hub;

import java.util.function.Function;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class FunctionApplication {
    public static void main(String[] args) throws Exception {
        SpringApplication.run(FunctionApplication.class, args);
    }

    @Bean
    public Function<MyEventHubData, MyCosmosDBDocument> appendTransaction() {
        return eventHubData -> new MyCosmosDBDocument(eventHubData.getValue());
    }
}
