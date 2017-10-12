'use strict';

angular.module('Order')
    .component('ordersList', {
        templateUrl: 'src/order/orders-list.template.html',
        controller: ['orderService', 'authService', 'userService', function(orderService, authService, userService) {
            var self = this;

            self.title = 'Список заказов';
            self.ordersListFilter = '';
            self.orders = [];
            self.customers = [];
            self.catalog = [];

            userService.getCurrentUser();

            orderService.getCustomers().then(function(value) {
                console.log('customers', value);
                self.customers = value;

                orderService.getCatalog().then(function(value) {
                    console.log('catalog', value);
                    self.catalog = value;

                    orderService.getList().then(function(value) {
                        console.log('orders', value);
                        self.orders = value;
                    });
                });
            });

            self.createOrder = function () {
                orderService.createOrder();
            };

            self.logoutUser = function () {
                authService.logout().then(function(value) {
                    if (value.error) {
                        // Сообщить пользователю
                    } else {
                        userService.getCurrentUser().then(function(value) {
                            if (value) {
                                self.user = value;
                            }
                        });
                    }
                });
            };

            self.getCustomerInfo = function(customerId) {
                var result = '';

                self.customers.forEach(function (customer) {
                    if (customer.id === customerId) {
                        result = customer.name + '  <' + customer.email + '>';
                    }
                });

                return result;
            };

            self.getTotalPrice = function(order) {
                var totalPrice = 0;

                self.catalog.forEach(function (item) {
                    order.orderLine.forEach(function(line) {
                        if (item.id === line.itemId) {
                            totalPrice += item.price * line.count;
                        }
                    });
                });

                return '$' + totalPrice;
            };

            self.deleteOrder = function(orderId) {
                orderService.deleteOrder(orderId).then(function(value) {
                    orderService.getList().then(function(value) {
                        console.log('orders', value);
                        self.orders = value;
                    });
                });
            }
        }]
    });