/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See LICENSE in the project root for
 * license information.
 */

'use strict';
angular.module('inventoryHubApp')
    .controller('mytestCtrl', ['$scope', '$http',
        function ($scope, $http) {

            $scope.error = '';
            $scope.loadingMessage = '';
            $scope.products = null;
            $scope.locations = null;
            $scope.transactions = null;

            $scope.populateProducts = function () {
            };


            $scope.initialTableValues = function () {
            };
    }]);
