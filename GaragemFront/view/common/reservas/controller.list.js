(function() {
	'use strict';

	angular.module('joaoApp')
		.controller('ReservaListaCtrl', ReservaListaCtrl);

	function ReservaListaCtrl($injector) {
		var Service = $injector.get('ReservaService');

		Service.list().then(function(data) {
			this.lista = data;
		});
	}

})();