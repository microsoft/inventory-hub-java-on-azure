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
    /*
     * The pom.xml needs to capture an entry to this class as the main class via a Maven configuration manifest in order
     *   to ensure that the SpringBoot is initialized before the function is called.
     */
    public static void main(String[] args) throws Exception {
        SpringApplication.run(FunctionApplication.class, args);
    }

    @Bean
    public Function<TransactionEvent, TransactionDocument> appendTransaction() {
        return eventHubData -> new TransactionDocument(eventHubData.getValue());
    }
}
