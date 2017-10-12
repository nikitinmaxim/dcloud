'use strict';

angular.module('Order')
    .component('orderDetails', {
        templateUrl: 'src/order/order-details.template.html',
        controller: ['orderService', 'authService', 'userService', '$routeParams', '$location', function(orderService, authService, userService, $routeParams, $location) {
            var self = this;

            self.title = 'Детали заказа';
            self.editMode = false;

            self.order = {id: $routeParams.orderID};

            self.customers = [];
            self.catalog = [];
            self.selectedCustomer = undefined;
            self.totalPrice = 0;

            if (self.order.id === 'create') {
                self.editMode = true;
            }

            orderService.getCustomers().then(function(value) {
                self.customers = value;
                self.selectedCustomer = self.customers[0].id;

                orderService.getCatalog().then(function(value) {
                    self.catalog = value;
                    self.catalog.forEach(function (item) {
                        item.count = 0;
                    });

                    if (self.order.id !== 'create') {
                        orderService.getOrder(parseInt(self.order.id)).then(function (value) {
                            console.log ('value', value);

                            self.order.id = value.id;
                            self.selectedCustomer = value.customerId;

                            self.customers.forEach(function (customer) {
                                if (customer.id === self.selectedCustomer) {
                                    self.customerName = customer.name + '  <' + customer.email + '>';
                                }
                            });

                            self.catalog.forEach(function (item) {
                                value.orderLine.forEach(function (line) {
                                    if (item.id === line.itemId) {
                                        item.count = line.count;
                                    }
                                })
                            });

                            calculateTotalPrice();
                        });
                    }
                });
            });

            self.saveOrder = function () {
                if (self.order.id === 'create') {
                    var orderLine = [];

                    self.catalog.forEach(function (item) {
                        if (item.count) {
                            orderLine.push({id: null, itemId: item.id, count: item.count});
                        }
                    });

                    self.order.customerId = self.selectedCustomer;
                    self.order.orderLine = orderLine;

                    orderService.saveOrder(self.order).then(function(value) {
                        $location.path('/order/list');
                    });
                }
            };

            self.selectedItemsMinus = function (item) {
                if ((item.count > 0))
                    item.count--;

                calculateTotalPrice();
            };

            self.selectedItemsPlus = function (item) {
                item.count++;

                calculateTotalPrice();
            };

            function calculateTotalPrice() {
                self.totalPrice = 0;

                self.catalog.forEach(function (item) {
                    self.totalPrice += item.price * item.count;
                });
            }
        }]
    });