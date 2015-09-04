/**
 * Created by ivsi on 9/2/2015.
 */

var app = angular.module('myApp', []);

app.controller("AppController", function ($scope, $http, $interval) {

    var feedArray = [];
    $scope.tweets = [];

    $interval(function getNewDataAndAppendToFeedArray() {
        $http.get('http://localhost:8080/api/oldest/5').
            success(function (data, status, headers, config) {
                feedArray = feedArray.concat(data);
            }).
            error(function (data, status, headers, config) {
                alert("Error");
            });
    }, 5000);

    $interval(function viewMessagesFIFO() {
        if (feedArray.length > 0) {
            $scope.tweets.push(feedArray[0]);
            feedArray.shift();
            if ($scope.tweets.length > 5) {
                $scope.tweets.shift();
            }
        }
    }, 2000);

})
;


