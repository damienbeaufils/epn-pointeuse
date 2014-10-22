<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <meta name="layout" content="bootstrap"/>
    <r:require modules="bootstrap"/>
</head>
<body>
<div class="container">
    <div class="page-header">
        <h1>Liste des utilisateurs identifiés</h1>
    </div>
    <div class="table-responsive">
        <table class="table table-striped">
            <thead>
                <tr>
                    <th>Nom</th>
                    <th>Date</th>
                </tr>
            </thead>
            <tbody>
                <g:each in="${signedUsers}" var="signedUser">
                    <tr>
                        <td>${signedUser.fullName}</td>
                        <td><g:formatDate format="dd/MM/yyyy HH:mm:ss" timeZone="Europe/Paris" date="${signedUser.dateCreated}"/></td>
                    </tr>
                </g:each>
            </tbody>
        </table>
    </div>
</div>
</body>
</html>