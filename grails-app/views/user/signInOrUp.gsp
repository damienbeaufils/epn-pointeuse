<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <meta name="layout" content="bootstrap"/>
    <r:require modules="bootstrap"/>
</head>

<body>
<div class="container text-center">

    <div class="row page-header">
        <div class="col-md-4">
            <img src="${resource(dir: 'images', file: 'acces-poste.png')}" alt="Accès au poste"/>
        </div>
        <div class="col-md-8 text-left">
            <h2>
                Bonjour et bienvenue à l'@nnexe
                <br/>
                <small>Nous sommes le <g:formatDate timeZone="Europe/Paris" format="dd/MM/yyyy HH:mm"/></small>
            </h2>
        </div>
    </div>

    <g:if test="${flash.message}">
        <div class="row">
            <div class="col-md-12">
                <h3 class="alert alert-danger">${flash.message}</h3>
            </div>
        </div>
    </g:if>

    <div class="row">
        <div class="col-md-6">
            <img src="${resource(dir: 'images', file: 'cle-acces.png')}" alt=""/>
            <g:form class="form-signin input-lg" role="form" action="signIn" data-search-url="${createLink(action: 'search')}">
                <h3 class="alert alert-success">Inscrit(e) à l'@nnexe ?</h3>
                <div class="form-group text-left">
                    <g:textField name="fullName" class="form-control" placeholder="Votre nom" required="required" autofocus="autofocus"/>
                </div>
                <g:submitButton name="submit" class="btn btn-lg btn-primary btn-block" value="M'identifier"/>
            </g:form>
        </div>


        <div class="col-md-6">
            <img src="${resource(dir: 'images', file: 'inscription.png')}" alt=""/>
            <g:form class="form-signin" role="form" action="signUp">
                <h3 class="alert alert-info">1er accès ? Inscrivez-vous !</h3>
                <div class="form-group pull-right ${hasErrors(bean:newUser,field:'birthYear','has-error')}">
                    <g:field type="number" name="birthYear" class="form-control" placeholder="Année de naissance" required="required" maxlength="4" value="${fieldValue(bean: newUser, field:'birthYear')}"/>
                </div>
                <div class="form-group input-lg ${hasErrors(bean:newUser,field:'male','has-error')}">
                    <g:radioGroup name="male" labels="['Monsieur','Madame']" values="['true','false']" required="required" value="${fieldValue(bean: newUser, field:'male')}">
                        <label class="radio-inline">
                            ${it.radio} ${it.label}
                        </label>
                    </g:radioGroup>
                </div>
                <div class="form-group ${hasErrors(bean:newUser,field:'firstName','has-error') || hasErrors(bean:newUser,field:'lastName','has-error')}">
                    <g:textField name="firstName" class="form-control" placeholder="Prénom" required="required" value="${fieldValue(bean: newUser, field:'firstName')}"/>
                    <g:textField name="lastName" class="form-control" placeholder="Nom" required="required" value="${fieldValue(bean: newUser, field:'lastName')}"/>
                </div>
                <div class="form-group text-left ${hasErrors(bean:newUser,field:'street','has-error') || hasErrors(bean:newUser,field:'zipCode','has-error') || hasErrors(bean:newUser,field:'city','has-error')}}">
                    <label>Adresse</label>
                    <g:textField name="street" class="form-control" placeholder="Rue" required="required" value="${fieldValue(bean: newUser, field:'street')}"/>
                    <div class="form-inline">
                        <g:textField name="zipCode" class="form-control" placeholder="Code postal" required="required" pattern="[0-9]{5}" minlength="5" maxlength="5" value="${fieldValue(bean: newUser, field:'zipCode')}"/>
                        <g:textField name="city" class="form-control" placeholder="Ville" required="required" value="${fieldValue(bean: newUser, field:'city')}"/>
                    </div>
                </div>
                <div class="form-group ${hasErrors(bean:newUser,field:'phoneNumber','has-error')}">
                    <g:textField name="phoneNumber" class="form-control" placeholder="Téléphone" required="required" pattern="[0-9]{10,14}" minlength="10" maxlength="14" value="${fieldValue(bean: newUser, field:'phoneNumber')}"/>
                </div>
                <g:submitButton name="submit" class="btn btn-lg btn-primary btn-block" value="Valider"/>
            </g:form>
        </div>
    </div>
</div>

<g:javascript>

    $('input[name="fullName"]').on('keyup', function(event) {
        var input = $(event.target);
        input.val(input.val().toUpperCase());
    });

    var users = new Bloodhound({
        datumTokenizer: Bloodhound.tokenizers.obj.whitespace('nom'),
        queryTokenizer: Bloodhound.tokenizers.whitespace,
        remote: $('form').data('search-url') + '?name=%QUERY',
        limit: 100
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