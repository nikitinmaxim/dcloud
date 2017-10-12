'use strict';

angular.module('User')
    .factory('userService', function($http, $q, $location){
        return {
            getCurrentUser: function(){
                var deferred = $q.defer();

                $http({
                    method: "GET", url: "/api/user"
                }).
                then(function success(response) {
                    if (response.data.error) {
                        $location.path('/');
                    } else {
                        $location.path('/order/list');
                    }
                    deferred.resolve(response.data.data);
                },function error(response) {
                    $location.path('/');
                    deferred.reject(response.status);
                });

                return deferred.promise;
            }
        }
    });
