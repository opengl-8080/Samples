package sample.web

import javax.ws.rs.Path
import javax.ws.rs.GET
import javax.ws.rs.QueryParam


@Path("hello")
class HelloResource {
    
    @GET
    fun hello(@QueryParam("arg") arg : String) : String {
        println("arg=$arg")
        return "Hello Kotlin Java EE!!"
    }
}