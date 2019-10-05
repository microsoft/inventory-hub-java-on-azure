/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See LICENSE in the project root for
 * license information.
 */

'use strict';
angular.module('inventoryHubApp')
    .controller('homeCtrl', ['$scope', '$location', 'productsSvc',
        function ($scope, $location, productsSvc) {
            $scope.error = '';
            $scope.loadingMessage = '';
            $scope.products = null;
            $scope.locations = null;
            $scope.transactions = null;
            $scope.username = null;
            $scope.loggedin = false;

            $scope.fetchProductsAndEvents = function () {
                productsSvc.getProducts().success(function (results) {
                    $scope.products = results;
                }).error(function (err) {
                    $scope.error = err;
                    $scope.loadingMessage = '';
                });

                productsSvc.getLocations().success(function (results) {
                    $scope.locations = results;
                }).error(function (err) {
                    $scope.error = err;
                    $scope.loadingMessage = '';
                });

                productsSvc.getTransactions().success(function (results) {
                    $scope.transactions = results;
                }).error(function (err) {
                    $scope.error = err;
                    $scope.loadingMessage = '';
                });

            };

            $scope.fetchUsername = function () {
                productsSvc.getUsername().success(function (results) {
                    $scope.username = results;
                    $scope.loggedin = true;
                }).error(function (err) {
                    $scope.error = err;
                    $scope.loadingMessage = '';
                });
            };

            $scope.IsLoggedIn = function () {
                return $scope.loggedin;
            };
        }]);
