package io.github.binout.urbansoccer

import com.github.kevinsawicki.http.HttpRequest.get
import org.eclipse.microprofile.config.inject.ConfigProperty
import javax.ws.rs.GET
import javax.ws.rs.Path
import javax.ws.rs.Produces
import javax.ws.rs.core.MediaType

@Path("/ping")
class PingResource {

    @ConfigProperty(name = "url")
    lateinit var baseUrl: String

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    fun ping() = get(baseUrl).ok()
}