<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <meta name="layout" content="bootstrap"/>
    <r:require modules="bootstrap"/>
</head>
<body>

    <div class="container">
        <g:form class="form-signin" role="form" action="search">
            <h2 class="form-signin-heading">EPN : authentification</h2>
            <div class="form-group">
                <g:textField name="name" class="form-control" placeholder="Nom de famille"/>
            </div>
            <g:submitButton name="submit" class="btn btn-lg btn-primary btn-block" value="Rechercher"/>
        </g:form>
    </div>

    <g:if test="${foundUsers}">
        <div class="container">
            <h2>Utilisateurs trouvés :</h2>
            <g:each in="${foundUsers}" var="user">
                <h4>${user.nom} ${user.prenom}</h4>
            </g:each>
        </div>
    </g:if>

</body>
</html>