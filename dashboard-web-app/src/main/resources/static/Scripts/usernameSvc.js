/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See LICENSE in the project root for
 * license information.
 */

'use strict';
angular.module('inventoryHubApp')
    .factory('usernameSvc', ['$http', function ($http) {
        return {
            getUsername: function(){
                return $http.get('api/username');
            }
        };
    }]);
