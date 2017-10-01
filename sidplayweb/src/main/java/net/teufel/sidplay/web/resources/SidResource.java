package net.teufel.sidplay.web.resources;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/sids")
public class SidResource {

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String getSids() {
        return "Hello World cccc!!!!!!";
    }

}
