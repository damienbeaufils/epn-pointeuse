class UrlMappings {

	static mappings = {
        "/$controller/$action?/$id?(.$format)?"{
            constraints {
                // apply constraints here
            }
        }

        "/"(controller: "user")

        "/debug"(view:"/debug")
        "500"(view:'/error')
	}
}
