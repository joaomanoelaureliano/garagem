(function() {
	'use strict';

	angular.module('joaoApp')
		.controller('HomeCtrl', HomeCtrl);

	function HomeCtrl($state) {
		this.go = go;

		function go(state){
			$state.go(state);
		}
	}

})();