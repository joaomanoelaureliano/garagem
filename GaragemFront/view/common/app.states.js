(function() {
	'use strict';

	var modulo = angular.module('joaoApp', [
		'ui.router', 'ui.bootstrap', 'restangular'
	]);

	modulo.config(function($urlRouterProvider, $stateProvider) {

		$urlRouterProvider.otherwise('/home');

		$stateProvider.state('home', {
			url: '/home',
			controller: 'HomeCtrl as HomeCtrl',
			templateUrl: 'common/home/home.html'
		});

		$stateProvider.state('veiculos', {
			url: '/veiculos',
			controller: 'VeiculoCtrl as VeiculoCtrl',
			templateUrl: 'common/veiculos/cadastro.html'
		});

		$stateProvider.state('reservas', {
			url: '/reservas',
			controller: 'ReservaCtrl as ReservaCtrl',
			templateUrl: 'common/reservas/cadastro.html'
		});

		$stateProvider.state('reservas-lista', {
			url: '/reservas-lista',
			controller: 'ReservaListaCtrl as ReservaListaCtrl',
			templateUrl: 'common/reservas/lista.html'
		});
		
		$stateProvider.state('veiculos-lista', {
			url: '/veiculos-lista',
			controller: 'ListaVeiculoCtrl as ListaVeiculoCtrl',
			templateUrl: 'common/veiculos/lista.html'
		});


	});

})();