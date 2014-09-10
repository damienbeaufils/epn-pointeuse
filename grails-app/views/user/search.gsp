<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <meta name="layout" content="bootstrap"/>
    <r:require modules="bootstrap"/>
</head>
<body>

<div class="container">
    <div class="page-header">
        <h1>L’@nnexe - Espace Numérique à Paris 12e</h1>
    </div>

    <g:if test="${flash.message}">
        <h2 class="alert alert-danger">${flash.message}</h2>
    </g:if>

    <g:form class="form-signin" role="form" action="signIn" data-search-url="${createLink(action: 'search', params: ['json' : true])}">
        <h2 class="form-signin-heading alert alert-success">Afin d'accéder à ce poste, merci de rechercher votre <b>nom de famille</b> en utilisant le formulaire ci-dessous.</h2>
        <div class="form-group">
            <g:textField name="fullName" class="form-control" placeholder="Nom de famille" required="required" autofocus="autofocus"/>
        </div>
        <g:submitButton name="submit" class="btn btn-lg btn-primary btn-block" value="S'identifier"/>
        <h3 class="form-signin-heading alert alert-warning">Si votre nom de famille n'apparait pas dans la liste, merci de le signaler à l'EPN.</h3>
    </g:form>
</div>

<g:javascript>

    $('input[name="fullName"]').on('keyup', function(event) {
        var input = $(event.target);
        input.val(input.val().toUpperCase());
    });

    var users = new Bloodhound({
        datumTokenizer: Bloodhound.tokenizers.obj.whitespace('nom'),
        queryTokenizer: Bloodhound.tokenizers.whitespace,
        remote: $('form').data('search-url') + '&name=%QUERY'
    });

    users.initialize();

    $('input[name="fullName"]').typeahead(
        {
            hint: true,
            highlight: true,
            minLength: 3
        }, {
            displayKey: function (user) {
                return user.nom + ' ' + user.prenom
            },
            source: users.ttAdapter()
        }
    );
</g:javascript>
</body>
</html>