(function() {
	'use strict';

	angular.module('joaoApp')
		.controller('ReservaCtrl', ReservaCtrl);

	function ReservaCtrl($injector) {
		var Service = $injector.get('ReservaService');

		this.reserva = {};
		this.save = save;
		this.list = list;

		function save(){
			Service.save(this.reserva);
			console.log(this.reserva);
		}
		
		function list() {
			Service.list();
		}
	}

})();