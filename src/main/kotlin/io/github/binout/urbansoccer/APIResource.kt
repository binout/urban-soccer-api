package io.github.binout.urbansoccer

import com.github.kevinsawicki.http.HttpRequest.get
import org.eclipse.microprofile.config.inject.ConfigProperty
import org.jsoup.Jsoup
import javax.json.Json
import javax.ws.rs.GET
import javax.ws.rs.Path
import javax.ws.rs.PathParam
import javax.ws.rs.Produces
import javax.ws.rs.core.MediaType

@Path("/ping")
class PingResource {

    @ConfigProperty(name = "url")
    lateinit var baseUrl: String

    @GET
    fun ping() = get(baseUrl).ok()
}

@Path("/api")
@Produces(MediaType.APPLICATION_JSON)
class ApiResource {

    @ConfigProperty(name = "url")
    lateinit var baseUrl: String

    @GET
    @Path("/leagues/{id}")
    fun league(@PathParam("id") id: String): String {
        val teams = Json.createArrayBuilder()
        Jsoup.connect("$baseUrl/league/?idLeague=$id").get()
                .select("li[data-team]").toList()
                .filter { it.attr("data-team") != "0"}
                .map { it.child(0).text() }
                .forEach { teams.add(it) }
        return Json.createObjectBuilder()
                .add("id", id)
                .add("teams", teams)
                .build()
                .toString()
    }
}