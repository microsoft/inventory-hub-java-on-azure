/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See LICENSE in the project root for
 * license information.
 */
package com.microsoft.azure.sample;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.event.EventListener;

@SpringBootApplication
public class InventoryHub extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(InventoryHub.class);
    }

    public static void main(String[] args) throws Exception {
        SpringApplication.run(InventoryHub.class, args);
    }

}

/*
@SpringBootApplication
public class InventoryHub {

    public static void main(String[] args) {
        SpringApplication.run(InventoryHub.class, args);
    }
}
*/
