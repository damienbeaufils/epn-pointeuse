<!DOCTYPE html>
<html lang="fr">
<head>
    <title><g:layoutTitle default="L’@nnexe - Espace Numérique à Paris 12e" /></title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css"/>
    <asset:stylesheet src="vendor/typeaheadjs.css"/>
    <asset:stylesheet src="pointeuse.css"/>
    <asset:javascript src="jquery-2.2.0.min.js"/>
    <asset:javascript src="vendor/typeahead.bundle.min.js"/>
</head>

<body>
    <g:layoutBody/>

    <g:javascript>
        (function(i,s,o,g,r,a,m){i['GoogleAnalyticsObject']=r;i[r]=i[r]||function(){
            (i[r].q=i[r].q||[]).push(arguments)},i[r].l=1*new Date();a=s.createElement(o),
                m=s.getElementsByTagName(o)[0];a.async=1;a.src=g;m.parentNode.insertBefore(a,m)
        })(window,document,'script','//www.google-analytics.com/analytics.js','ga');

        ga('create', '${grailsApplication.config.pointeuse.googleAnalytics.id}', 'auto');
        ga('send', 'pageview');
    </g:javascript>
</body>
</html>