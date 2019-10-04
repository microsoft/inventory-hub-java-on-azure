/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See LICENSE in the project root for
 * license information.
 */

package org.inventory.hub.controller;

import java.security.Principal;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@ResponseBody
public class SecurityController {

    public SecurityController() {
    }

    @RequestMapping(value = "/api/username", method = RequestMethod.GET)
    public String currentUsername(final Principal principal) {

        System.out.println("======= /api/username ===== ");
        String username = new String();
        if (principal != null)
                username = principal.getName();

        System.out.println("username=" + username);
        System.out.println("====== success");

        return username;
    }

}