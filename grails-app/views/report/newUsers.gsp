<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <meta name="layout" content="bootstrap"/>
    <r:require modules="bootstrap"/>
</head>

<body>
<div class="container">
    <div class="page-header">
        <h1>Liste des demandes d'inscription de nouveaux utilisateurs</h1>
    </div>

    <div class="table-responsive">
        <table class="table table-striped">
            <thead>
            <tr>
                <th>Civilité</th>
                <th>Nom</th>
                <th>Prénom</th>
                <th>Année de naissance</th>
                <th>Rue</th>
                <th>Code postal</th>
                <th>Ville</th>
                <th>Téléphone</th>
                <th>Date</th>
            </tr>
            </thead>
            <tbody>
            <g:each in="${newUsers}" var="newUser">
                <tr>
                    <td>${newUser.title.id}</td>
                    <td>${newUser.lastName}</td>
                    <td>${newUser.firstName}</td>
                    <td>${newUser.birthYear}</td>
                    <td>${newUser.street}</td>
                    <td>${newUser.zipCode}</td>
                    <td>${newUser.city}</td>
                    <td>${newUser.phoneNumber}</td>
                    <td><g:formatDate format="dd/MM/yyyy HH:mm:ss" timeZone="Europe/Paris"
                                      date="${newUser.dateCreated}"/></td>
                </tr>
            </g:each>
            </tbody>
        </table>
    </div>
</div>
</body>
</html>