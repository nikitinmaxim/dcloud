'use strict';

angular.module('app', ['ngRoute', 'Authorization', 'User', 'Order']);

angular.module('app')
    .config( ['$routeProvider', function($routeProvider) {
        $routeProvider
            .when('/', {
                template: '<authorization></authorization>'
            })
            .when('/order/list', {
                template: '<orders-list></orders-list>'
            })
            .when('/order/:orderID', {
                template: '<order-details></order-details>'
            })
            .otherwise({
                redirectTo: '/'
            });
    }]);