/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See LICENSE in the project root for
 * license information.
 */
package org.inventory.hub.controller;

import org.inventory.hub.dao.ProductsInventoryRepository;
import org.inventory.hub.model.Product;
import org.inventory.hub.model.ProductsInventory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Collection;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;
import java.util.UUID;

@RestController
public class ProductsInventoryController {

    @Autowired
    private ProductsInventoryRepository productsInventoryRepository;

    public ProductsInventoryController() {
    }

    @RequestMapping("/home")
    public Map<String, Object> home() {
        final Map<String, Object> model = new HashMap<String, Object>();
        model.put("id", UUID.randomUUID().toString());
        model.put("content", "home");
        return model;
    }

    @RequestMapping(value = "/api/products/{productName}",
            method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<?> getProduct(@PathVariable("productName") String productName) {
        try {

            final Flux<ProductsInventory> productsInventoriesFlux = productsInventoryRepository.findAll();
            final Iterable<ProductsInventory> productsInventories = productsInventoriesFlux.toIterable();

            final ResponseEntity<Iterable<ProductsInventory>> productInventories =
            new ResponseEntity<Iterable<ProductsInventory>>(productsInventories, HttpStatus.OK);


            System.out.println("======= /api/products/{productName} ===== ");
            System.out.println(productInventories.toString());

            final Iterable <ProductsInventory> iterable = (Iterable<ProductsInventory>) productInventories.getBody();
            final Iterator <ProductsInventory> it = makeCollection(iterable).iterator();
            
            final Product product = new Product();
            ProductsInventory productInventory = null;

            while (it.hasNext()) {
                productInventory = it.next();
                product.setName(productInventory.getProductName());
                product.getCountByLocation()
                    .put(productInventory.getLocation(), 
                        Integer.valueOf(productInventory.getTotalCount()));
            }
            
            return new ResponseEntity<Product> (product, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<String>(productName + " not found", 
                HttpStatus.NOT_FOUND);
        }
    }

    private static <E> Collection<E> makeCollection(Iterable<E> iter) {
        Collection<E> list = new ArrayList<E>();
        for (E item : iter) {
            list.add(item);
        }
        return list;
    }

    @RequestMapping(value = "/api/products", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<?> getProducts() {
        try {
            // return new ResponseEntity<List<ProductsInventory>>(ProductsInventoryRepository.findAll(), HttpStatus.OK);

            final Flux<ProductsInventory> productsInventoriesFlux = productsInventoryRepository.findAll();
            final Iterable<ProductsInventory> productsInventories = productsInventoriesFlux.toIterable();

            final ResponseEntity<Iterable<ProductsInventory>> productInventories =
                new ResponseEntity<Iterable<ProductsInventory>>(productsInventories, HttpStatus.OK);


            System.out.println("======= /api/products ===== ");
            System.out.println(productInventories.toString());

            final Iterable <ProductsInventory> iterable = (Iterable<ProductsInventory>) productInventories.getBody();
            final Iterator <ProductsInventory> it = makeCollection(iterable).iterator();
            
            Product product;
            final Map<String, Product> products = new TreeMap<String, Product>();

            final Map<String, Integer> locations = new HashMap<>();
            
            ProductsInventory productInventory = null;

            while (it.hasNext()) {
                productInventory = it.next();
                product = products.get(productInventory.getProductName());

                if (product == null) {
                    product = new Product();
                    product.setName(productInventory.getProductName());
                    product.setDescription(productInventory.getDescription());
                    products.put(productInventory.getProductName(), product);
                }

                final String currentLocation = productInventory.getLocation();
                Integer totalItems = locations.get(currentLocation);
                if (totalItems == null) {
                    totalItems = Integer.valueOf(productInventory.getTotalCount());
                    locations.put(currentLocation, totalItems);
                } else {
                    locations.put(currentLocation, totalItems + Integer.valueOf(productInventory.getTotalCount()));
                }

                System.out.println("== inventory === " + productInventory);

                product.getCountByLocation()
                    .put(productInventory.getLocation(), 
                        Integer.valueOf(productInventory.getTotalCount()));
            }
            for (final Map.Entry<String, Integer> locationEntry : locations.entrySet()) {
                System.out.format("== inventory total per location === %s: %d\n",
                    locationEntry.getKey(), locationEntry.getValue());
                for (final Map.Entry<String, Product> productEntry : products.entrySet()) {
                    final Map<String, Integer> productByLocationMap = productEntry.getValue().getCountByLocation();
                    final Integer currentCount = productByLocationMap.get(locationEntry.getKey());
                    if (currentCount == null) {
                        productByLocationMap.put(locationEntry.getKey(), 0);
                    }
                }
            }

            System.out.println("====== success");

            return new ResponseEntity<Map<String, Product>> (products, HttpStatus.OK);
        
        } catch (Exception e) {
            System.out.println("======== EXCEPTION =======");
            e.printStackTrace();
            return new ResponseEntity<String>("Nothing found", HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(value = "/api/locations", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<?> getLocations() {
        try {

            final Flux<ProductsInventory> productsInventoriesFlux = productsInventoryRepository.findAll();
            final Iterable<ProductsInventory> productsInventories = productsInventoriesFlux.toIterable();

            final ResponseEntity<Iterable<ProductsInventory>> productInventories =
                new ResponseEntity<Iterable<ProductsInventory>>(productsInventories, HttpStatus.OK);

            System.out.println("======= /api/locations ===== ");
            System.out.println(productInventories.toString());

            final Iterable <ProductsInventory> iterable = (Iterable<ProductsInventory>) productInventories.getBody();
            final Iterator <ProductsInventory> it = makeCollection(iterable).iterator();

            final Map<String, Integer> locations = new HashMap<>();

            ProductsInventory productInventory = null;

            while (it.hasNext()) {
                productInventory = it.next();

                final String currentLocation = productInventory.getLocation();
                Integer totalItems = locations.get(currentLocation);
                if (totalItems == null) {
                    totalItems = Integer.valueOf(productInventory.getTotalCount());
                    locations.put(currentLocation, totalItems);
                } else {
                    locations.put(currentLocation, totalItems + Integer.valueOf(productInventory.getTotalCount()));
                }
            }
            for (final Map.Entry<String, Integer> locationEntry : locations.entrySet()) {
                System.out.format("== locations === %s: %d\n",
                    locationEntry.getKey(), locationEntry.getValue());
            }

            System.out.println("====== success");

            return new ResponseEntity<Map<String, Integer>> (locations, HttpStatus.OK);

        } catch (Exception e) {
            System.out.println("======== EXCEPTION =======");
            e.printStackTrace();
            return new ResponseEntity<String>("Nothing found", HttpStatus.NOT_FOUND);
        }
    }
}
