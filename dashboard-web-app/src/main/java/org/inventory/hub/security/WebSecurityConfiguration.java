/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See LICENSE in the project root for
 * license information.
 */
package org.inventory.hub.security;

import com.microsoft.azure.spring.autoconfigure.b2c.AADB2COidcLoginConfigurer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@EnableWebSecurity
public class WebSecurityConfiguration
 extends WebSecurityConfigurerAdapter {

    @Autowired(required = false)
    private AADB2COidcLoginConfigurer configurer;

    @Value("${azure.activedirectory.b2c.enabled:false}")
    private boolean aadEnabled;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        if (aadEnabled) {
            http.authorizeRequests()
                .anyRequest()
                .authenticated()
                .and()
                .apply(configurer);
        } else {
            http.authorizeRequests()
                    .antMatchers("*")
                    .permitAll();
        }
    }
}
