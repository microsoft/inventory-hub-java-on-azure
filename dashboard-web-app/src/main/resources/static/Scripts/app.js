/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See LICENSE in the project root for
 * license information.
 */

'use strict';
angular.module('inventoryHubApp', ['ngRoute'])
    .config(['$routeProvider',  function ($routeProvider) {
        $routeProvider.when('/Home', {
            controller: 'homeCtrl',
            templateUrl: 'Views/Home.html',
        }).when('/Products', {
            controller: 'productsCtrl',
            templateUrl: 'Views/Products.html',
        }).when('/MyTest', {
            controller: 'mytestCtrl',
            templateUrl: 'Views/MyTest.html',
        }).otherwise({redirectTo: '/Home'});
    }]);
