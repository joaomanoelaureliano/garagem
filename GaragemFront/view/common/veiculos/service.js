'use strict';

angular.module('joaoApp')
	.factory('VeiculoService', function(RestService) {
		return {
			save: function(carro) {
				return RestService
				.all('carros')
				.customPOST(carro);
			},
			list: function() {
				return RestService
				.all('carros')
				.getList();
			}
		};
	});
