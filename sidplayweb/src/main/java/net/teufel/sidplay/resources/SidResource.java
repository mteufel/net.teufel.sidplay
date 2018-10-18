package net.teufel.sidplay.resources;


import net.teufel.sidplay.dao.SidDaoJdbc;
import net.teufel.sidplay.domain.Sid;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.StreamingOutput;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URISyntaxException;
import java.util.List;

@ApplicationScoped

public class SidResource {

    @Inject SidDaoJdbc sidDao;

    @GET
    @Path("/sids")
    @Produces(MediaType.APPLICATION_JSON)
    public Response doSearch(@QueryParam("search") String search) {

        List<Sid> result = sidDao.findSids(search);
        if(result == null) {
            return Response.status(Response.Status.NOT_FOUND).entity("No Data found").build();
        }
        return Response.ok(result, MediaType.APPLICATION_JSON).build();

    }

    @GET
    @Path("/sid/{sidId}")
    @Produces("application/octet-stream")
    public Response getSid(@PathParam("sidId") int sidId) throws URISyntaxException {

        try {
            InputStream is = sidDao.getSid(sidId);
            StreamingOutput stream = output -> {
                try {
                    pipe(is, output);
                } catch (Exception e) {
                    throw new WebApplicationException(e);
                }
            };
            return Response.ok(stream).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }

    private void pipe(InputStream is, OutputStream os) throws IOException {
        int n;
        byte[] buffer = new byte[1024];
        while ((n = is.read(buffer)) > -1) {
            os.write(buffer, 0, n);
        }
        os.close();
    }

}
