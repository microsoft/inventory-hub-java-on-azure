/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See LICENSE in the project root for
 * license information.
 */

'use strict';
angular.module('inventoryHubApp')
    .controller('productsCtrl', ['$scope', '$location', 'productsSvc', 
            function ($scope, $location, productsSvc) {
        $scope.error = '';
        $scope.loadingMessage = '';
        $scope.products = null;

        $scope.populateProducts = function () {
            productsSvc.getProducts().success(function (results) {
                $scope.products = results;
            }).error(function (err) {
                $scope.error = err;
                $scope.loadingMessage = '';
            })
        };

        $scope.pushEvents = function () {
            productsSvc.getProducts().success(function (results) {
                $scope.products = results;
            }).error(function (err) {
                $scope.error = err;
                $scope.loadingMessage = '';
            })
        };

    }]);
