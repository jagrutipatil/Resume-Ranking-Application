var myApp = angular.module("filterApp", []);

myApp.controller('filterController', function($scope, $http, $window) {

	$scope.getRow = function(n) {
		console.log(n);
        var row = n.parentNode.parentNode;
        var cols = row.getElementsByTagName("td");
        var i=0;
        while (i < cols.length) {
            console.log(cols[i].textContent);
            i++;
        }
    }; 	

});