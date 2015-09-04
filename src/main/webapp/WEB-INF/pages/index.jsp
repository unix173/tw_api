<!DOCTYPE html>
<html ng-app="myApp">

<head lang="en">
    <meta charset="UTF-8">
    <title></title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.4.4/angular.min.js"></script>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.3/jquery.min.js"></script>
    <script src="${pageContext.request.contextPath}/resources/js/ang.js"></script>
</head>

<body ng-controller="AppController">

<div ng-repeat="tweet in tweets" class="fontana scroll-down">
    <div ng-class="message media well col-md-6 col-md-offset-3 focus">
        <figure class="pull-left media-object">
            <img class="img-thumbnail" ng-src={{tweet.user.profile_image_url}}>
        </figure>
        <div class="media-body">
            <div class="media-heading"><cite> <span class="tweet.user.name">{{tweet.user.name}}</span>
                <small class="text-muted"><span class="screen_name">{{tweet.user.screen_name}}</span>
                    <time class="time pull-right">
                        {{tweet.created_at}}
                    </time>
                </small>
            </cite></div>
            <div class="text lead">{{tweet.text}}
            </div>
        </div>
    </div>
</div>

</body>
</html>