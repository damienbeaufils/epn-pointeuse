<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <meta name="layout" content="bootstrap"/>
    <r:require modules="bootstrap"/>
</head>

<body>

<div class="container">
    <div class="page-header">
        <h1>Merci de vous être identifié <b>${fullName}</b> !</h1>
    </div>

    <h3 class="form-signin-heading alert alert-success">Vous allez être redirigé dans quelques secondes...</h3>

    <div class="progress progress-striped active">
        <div class="progress-bar progress-bar-success" role="progressbar" aria-valuenow="10" aria-valuemin="0" aria-valuemax="100">
            <span class="sr-only">Redirection en cours...</span>
        </div>
    </div>
</div>

<g:javascript>
    var redirectionTimeout = 3000;
    var progressBarSplit = 5;

    window.setTimeout(function() {
        window.location.href = "${redirectionUrl}";
    }, redirectionTimeout);

    window.setInterval(function () {
        var progressBar = $('.progress-bar');
        var newAriaValue = parseInt(progressBar.attr('aria-valuenow'), 10) + ( 100 / progressBarSplit );
        progressBar.css('width', newAriaValue+'%').attr('aria-valuenow', newAriaValue);
    }, (redirectionTimeout / progressBarSplit));
</g:javascript>

</body>
</html>