/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See LICENSE in the project root for
 * license information.
 */

'use strict';
angular.module('inventoryHubApp')
    .factory('productsSvc', ['$http', function ($http) {
        return {
            getProducts: function () {
                return $http.get('api/products');
            },
            getLocations: function () {
                return $http.get('api/locations');
            },
            getTransactions: function () {
                return $http.get('api/transactions');
            },
            getUsername: function () {
                return $http.get('api/username');
            },
            getProduct: function (id) {
                return $http.get('api/product/' + id);
            }
        };
    }]);
