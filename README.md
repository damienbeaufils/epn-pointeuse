epn-pointeuse
=============

Continuous Integration
---
[![Build Status](https://travis-ci.org/damienbeaufils/epn-pointeuse.svg)](https://travis-ci.org/damienbeaufils/epn-pointeuse)
https://travis-ci.org/damienbeaufils/epn-pointeuse

Functional description
---

* This web application has been designed for a french nonprofit organization to record the free use of computers.
* If you already are a known user, you can search your name and sign in automatically. Your name and the timestamp when you sign in will be saved.
* If you are not a known user, you can fill a form to sign up. Then a mail is sent to administrators and you can continue. 
* More details [here](https://epnrelais59.wordpress.com/2015/01/05/naissance-dune-pointeuse/) (french only).

Technical information
---

* Grails 3.2
* Java 8
* Bootstrap 3

Build and test
---

```
./grailsw clean && ./grailsw test-app
```

Run
---

```
./grailsw clean && ./grailsw run-app
```

Package
---

```
./grailsw clean && ./grailsw package
```

License
---

epn-pointeuse is licensed under the terms of the [Apache License, Version 2.0](http://www.apache.org/licenses/LICENSE-2.0.html).
