(function() {
	'use strict';

	angular.module('joaoApp')
		.controller('VeiculoCtrl', VeiculoCtrl);

	function VeiculoCtrl($injector) {
		var Service = $injector.get('VeiculoService');

		this.carro = {};
		this.save = save;
		this.list = list;

		function save(){
			Service.save(this.carro);
			console.log(this.carro);
		}

		function list() {
			Service.list();
		}
	}

})();