/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See LICENSE in the project root for
 * license information.
 */
package com.microsoft.azure.sample.controller;

import com.microsoft.azure.sample.dao.ProductsInventoryRepository;
import com.microsoft.azure.sample.model.Product;
import com.microsoft.azure.sample.model.ProductsInventory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
public class ProductsInventoryController {

    @Autowired
    private ProductsInventoryRepository ProductsInventoryRepository;

    public ProductsInventoryController() {
    }

    @RequestMapping("/home")
    public Map<String, Object> home() {
        final Map<String, Object> model = new HashMap<String, Object>();
        model.put("id", UUID.randomUUID().toString());
        model.put("content", "home");
        return model;
    }

    /**
     * HTTP GET
     */
    @RequestMapping(value = "/api/products/{productName}",
            method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<?> getProduct(@PathVariable("productName") String productName) {
        try {

            final ResponseEntity<List<ProductsInventory>> productInventories = 
                new ResponseEntity<List<ProductsInventory>>(ProductsInventoryRepository
                    .findAll(productName), HttpStatus.OK);
            //        .findByProductName(productName), HttpStatus.OK);

            final List<ProductsInventory> productInventories2 = productInventories.getBody();
            
            final Iterator <ProductsInventory> it = productInventories2.iterator();
            
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

    /**
     * HTTP GET ALL
     */
    @RequestMapping(value = "/api/products", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<?> getProducts() {
        try {
            // return new ResponseEntity<List<ProductsInventory>>(ProductsInventoryRepository.findAll(), HttpStatus.OK);
        
            final ResponseEntity<List<ProductsInventory>> productInventories = 
                new ResponseEntity<List<ProductsInventory>>(ProductsInventoryRepository
                    .findAll(), HttpStatus.OK);
            
            System.out.println("======= /api/products ===== ");
            System.out.println(productInventories.toString());

            final List<ProductsInventory> productInventories2 = productInventories.getBody();
            final Iterator <ProductsInventory> it = productInventories2.iterator();
            
            Product product = new Product();
            final HashMap<String, Product> products = new HashMap<String, Product>();
            
            ProductsInventory productInventory = null;

            while (it.hasNext()) {
                productInventory = it.next();
                product = products.get(productInventory.getProductName());

                if (product == null) {
                    product = new Product();
                    product.setName(productInventory.getProductName());
                    products.put(productInventory.getProductName(), product);
                }

                System.out.println("== inventory === " + productInventory);

                product.getCountByLocation()
                    .put(productInventory.getLocation(), 
                        Integer.valueOf(productInventory.getTotalCount()));
            }

            System.out.println("====== success");

            return new ResponseEntity<HashMap<String, Product>> (products, HttpStatus.OK);
        
        } catch (Exception e) {
            System.out.println("======== EXCEPTION =======");
            e.printStackTrace();
            return new ResponseEntity<String>("Nothing found", HttpStatus.NOT_FOUND);
        }
    }

}
