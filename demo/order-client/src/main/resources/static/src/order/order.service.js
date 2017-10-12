'use strict';

angular.module('Order')
    .factory('orderService', function($http, $q, $location){
        var ordersList = undefined;
        var orderItemList = undefined;
        var customersList = undefined;

        function Order(id, customerId, orderLine) {
            this.id = id;
            this.customerId = customerId;
            this.orderLine = orderLine;
        }

        function Customer(id, name, email) {
            this.id = id;
            this.name = name;
            this.email = email;
        }

        function CatalogItem(id, name, price) {
            this.id = id;
            this.name = name;
            this.price = price;
        }

        return{
            getList: function(){
                var deferred = $q.defer();
                if (!ordersList) {
                    $http({
                        method: 'GET', url: '/api/orders'
                    }).
                    then (function success(response) {
                        ordersList = [];
                        for (var i = 0; i < response.data.length; i++) {
                            ordersList.push(new Order(response.data[i].id, response.data[i].customerId, response.data[i].orderLine));
                        }
                        deferred.resolve(ordersList);
                    },function error(response) {
                        deferred.reject(response.status);
                    });
                } else {
                    deferred.resolve(ordersList);
                }
                return deferred.promise;
            },
            getOrder: function(orderID){
                var deferred = $q.defer();
                var orderDetail = undefined;
                this.getList().
                then(function success() {
                    for (var i = 0; i < ordersList.length; i++) {
                        if (ordersList[i].id === orderID) {
                            orderDetail = ordersList[i];
                            deferred.resolve(JSON.parse(JSON.stringify(orderDetail)));
                            break;
                        }
                    }
                },function error(orders) {
                    deferred.reject(orders.status);
                });

                return deferred.promise;
            },
            getCatalog: function(){
                var deferred = $q.defer();
                if (orderItemList === undefined) {
                    $http({
                        method: 'GET', url: '/api/catalog'
                    }).
                    then (function success(response) {
                        orderItemList = [];
                        for (var i = 0; i < response.data.length; i++) {
                            orderItemList.push(new CatalogItem(response.data[i].id, response.data[i].name, response.data[i].price));
                        }
                        deferred.resolve(orderItemList);
                    },function error(response) {
                        deferred.reject(response.status);
                    });
                } else {
                    deferred.resolve(orderItemList);
                }

                return deferred.promise;
            },
            getCustomers: function(){
                var deferred = $q.defer();
                if (!customersList) {
                    $http({
                        method: 'GET', url: '/api/customers'
                    }).
                    then (function success(response) {
                        customersList = [];
                        for (var i = 0; i < response.data.length; i++) {
                            customersList.push(new Customer(response.data[i].id, response.data[i].firstname + ' ' + response.data[i].name, response.data[i].email));
                        }
                        deferred.resolve(customersList);
                    },function error(response) {
                        deferred.reject(response.status);
                    });
                } else {
                    deferred.resolve(customersList);
                }

                return deferred.promise;
            },
            saveOrder: function(order){
                var deferred = $q.defer();
                if (order.id === 'create') {
                    order.id = null;

                    $http({
                        method: "POST",
                        url : "/api/orders",
                        data: JSON.stringify(order)
                    }).
                    then(function success(response) {
                        ordersList = undefined;
                        deferred.resolve(response);
                    },function error(response) {
                        deferred.reject(response.status);
                    });
                } else {
                    deferred.resolve(ordersList);
                }

                return deferred.promise;
            },
            createOrder: function(orderID){
                var deferred = $q.defer();
                this.getList().
                then(function success(orders) {
                    deferred.resolve(JSON.parse(JSON.stringify(ordersList)));
                },function error(orders) {
                    deferred.reject(orders.status);
                });

                return deferred.promise;
            },
            deleteOrder: function(orderId) {
                var deferred = $q.defer();

                $http({
                    method: "DELETE",
                    url : "/api/orders/" + orderId
                }).
                then(function success(response) {
                    ordersList = undefined;
                    deferred.resolve(response);
                },function error(response) {
                    deferred.reject(response.status);
                });

                return deferred.promise;
            }
        }
    });