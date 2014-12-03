<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <meta name="layout" content="bootstrap"/>
    <r:require modules="bootstrap"/>
</head>

<body>
<div class="container">
    <div class="row alert alert-success">
        <div class="col-md-3 text-center">
            <img src="${resource(dir: 'images', file: 'cle-acces.png')}" alt=""/>
        </div>

        <div class="col-md-9 ${!isNewUser ? 'leads-x2' : ''}">
            <p class="lead">
                Merci de vous être identifié(e) <b>${fullName}</b>
            </p>
            <g:if test="${isNewUser}">
                <p class="lead">
                    Votre demande d'inscription a été transmise à l'@nnexe
                </p>
            </g:if>
            <p class="lead">
                Vous pouvez accéder à votre session
            </p>
        </div>
    </div>

    <div class="row">
        <div class="col-md-3 text-center">
            <img src="${resource(dir: 'images', file: 'attention.jpg')}" alt=""/>
        </div>

        <div class="col-md-9 leads-x2">
            <p class="lead text-warning">
                <b>POUR DES RAISONS DE SECURITÉ</b>
            </p>

            <p class="lead">
                <b>Pensez à FERMER toutes les fenêtres avant de quitter ce poste</b>
            </p>
        </div>
    </div>


    <div class="row alert alert-warning">
        <div class="col-md-3 text-center">
            <img src="${resource(dir: 'images', file: 'logout.png')}" alt=""/>
        </div>

        <div class="col-md-9">
            <p class="lead">
                Merci de <b>ne pas éteindre la machine</b> quand vous quittez le poste.
            </p>

            <p class="lead">
                Bonne utilisation
            </p>

            <p class="lead">
                L'équipe de l'@nnexe
            </p>
        </div>
    </div>

    <div class="row">
        <div class="col-md-12">
            <h4 class="alert alert-info">Vous allez être redirigé dans quelques secondes...</h4>

            <div class="progress progress-striped active">
                <div class="progress-bar progress-bar-info" role="progressbar"
                     aria-valuenow="10" aria-valuemin="0" aria-valuemax="100">
                    <span class="sr-only">Redirection en cours...</span>
                </div>
            </div>
        </div>
    </div>

</div>

<g:javascript>
    var redirectionTimeout = 10000;
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