(function() {
	'use strict';

	angular.module('joaoApp')
		.factory('RestService', ['Restangular', function(Restangular) {
			return Restangular.withConfig(function(RestangularConfigurer) {
				RestangularConfigurer.setBaseUrl('http://localhost:8080/garagem-web/api/');
			});
		}]);

})();