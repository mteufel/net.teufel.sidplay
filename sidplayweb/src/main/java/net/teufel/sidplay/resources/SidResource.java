package net.teufel.sidplay.resources;


import net.teufel.sidplay.dao.SidDaoJdbc;
import net.teufel.sidplay.domain.Sid;
import net.teufel.sidplay.domain.TableData;

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

@Path("/sids")
public class SidResource {

    @Inject SidDaoJdbc sidDao;

    @GET
    @Path("/search")
    @Produces(MediaType.APPLICATION_JSON)
    public Response doSearch(@QueryParam("filter") String search) {

        List<Sid> result = sidDao.findSids(search);
        if(result == null) {
            return Response.status(Response.Status.NOT_FOUND).entity("No Data found").build();
        }

        TableData<Sid> tableData = new TableData<>();
        tableData.addColumn("title","Title");
        tableData.addColumn("author","Author");
        tableData.addColumn("release","Release");
        tableData.setTableData(result);

        return Response.ok(tableData, MediaType.APPLICATION_JSON).build();

    }


    @GET
    @Path("/sid/{sidId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getSidDetails(@PathParam("sidId") int sidId) {

        if (!sidDao.isSidAvailable(sidId)) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        return Response.ok(sidDao.getSidDetail(sidId), MediaType.APPLICATION_JSON).build();

    }

    @GET
    @Path("/file/{sidId}")
    @Produces("application/octet-stream")
    public Response getSidFile(@PathParam("sidId") int sidId) {
        System.out.println("sidId=" + sidId);

        if (!sidDao.isSidAvailable(sidId)) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        try {
            InputStream is = sidDao.getSidFile(sidId);
            StreamingOutput stream = output -> {
                try {
                    pipe(is, output);
                } catch (Exception e) {
                    throw new WebApplicationException(e);
                }
            };
            return Response.ok(stream).build();
        } catch (Exception e) {
            e.printStackTrace();
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
