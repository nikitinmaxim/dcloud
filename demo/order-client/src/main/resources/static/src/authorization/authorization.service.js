'use strict';

angular.module('Authorization')
    .factory('authService', function($http, $q){
        return {
            login: function (username, password) {
                var deferred = $q.defer();

                $http({
                    method: "POST", url: "/login/pass",
                    headers: {username: username, password: password}
                }).
                then (function success(response) {
                    deferred.resolve(response.data);
                },function error(response) {
                    deferred.reject(response.status);
                });

                return deferred.promise;
            },
            logout: function () {
                var deferred = $q.defer();

                $http({
                    method: "POST", url: "/logout"
                }).
                then (function success(response) {
                    deferred.resolve(response.data);
                },function error(response) {
                    deferred.reject(response.status);
                });

                return deferred.promise;
            }
        }
    });
