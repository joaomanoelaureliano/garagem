(function() {
	'use strict';

var cadastro_modulo = angular.module('cadastro_modulo',[]);

cadastro_modulo.controller("cadastro_veiculo", function ($scope){
	$scope.veiculos = [{{}}, {{}}, {{}}];

		$scope.seleciona_veiculo=  function(veiculo_selecionada) {
			$scope.selecao = veiculo_selecionada;
		};

		$scope.excluir = function() {
			$scope.veiculos.splice($scope.veiculos.indexOf($scope.selecao),1);
			$scope.limpar_campos();
		};

});
