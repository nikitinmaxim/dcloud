'use strict';

angular.module('Authorization')
    .component('authorization', {
        templateUrl: 'src/authorization/authorization.template.html',
        controller: ['authService', 'userService', function(authService, userService) {
            var self = this;
            self.user = null;
            self.notCorrect = function () {
                self.hint = true;
            };

            userService.getCurrentUser().then(function(value) {
                if (value) {
                    self.user = value;
                }
            });

            self.loginUser = function (username, password) {
                authService.login(username, password).then(function(value) {
                    if (value.error !== 'OK') {
                        self.notCorrect();
                    } else {
                        userService.getCurrentUser().then(function(value) {
                            if (value) {
                                self.user = value;
                            }
                        });
                    }
                });
            };
        }]
    });