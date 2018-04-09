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
            getProduct: function (id) {
                return $http.get('api/product/' + id);
            }
        };
    }]);
