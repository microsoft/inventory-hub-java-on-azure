/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See LICENSE in the project root for
 * license information.
 */

'use strict';
angular.module('inventoryHubApp')
    .controller('usernameCtrl', ['$scope', '$location', 'usernameSvc',
        function ($scope, $location, usernameSvc) {
            $scope.error = '';
            $scope.loadingMessage = '';
            $scope.username = null;

            $scope.fetchUsername = function () {
                usernameSvc.getUsername().success(function(results) {
                    $scope.username = results;
                }).error(function (err){
                    $scope.error = err;
                    $scope.loadingMessage = '';

                });
            };
        }]);
