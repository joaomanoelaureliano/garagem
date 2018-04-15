'use strict';

angular.module('joaoApp')
	.factory('ReservaService', function(RestService) {
		return {
			save: function(reseva) {
				return RestService
				.all('reservas')
				.customPOST(reseva);
			},
			list: function() {
				return RestService
				.all('reservas')
				.getList();
			}
		};
	});
