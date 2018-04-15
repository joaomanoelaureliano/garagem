(function() {
	'use strict';

	angular.module('joaoApp')
		.controller('ListaVeiculoCtrl', ListaVeiculoCtrl);

	function ListaVeiculoCtrl($injector) {
		var Service = $injector.get('VeiculoService');

		Service.list().then(function(carro) {
			this.lista = carro;
		});
	}
	
})();


