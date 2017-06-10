package net.teufel.sidplay.web;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URISyntaxException;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.StreamingOutput;

import net.teufel.sidplay.Util;
import net.teufel.sidplay.core.dao.SidDaoJdbc;

@Path("/getSid")
public class GetSidResource {

	@GET
	@Produces("application/octet-stream")
	public Response getSid() throws URISyntaxException {
	    
	    try {
	        InputStream is = new SidDaoJdbc(Util.getDataSource()).getSid();
	        StreamingOutput stream = new StreamingOutput() {
	            @Override
	            public void write(OutputStream output) throws IOException, WebApplicationException {
	                try {
	                    pipe(is, output);
	                } catch (Exception e) {
	                    throw new WebApplicationException(e);
	                }
	            }
	        };
	        return Response.ok(stream).build();
	    } catch (Exception e) {
	        return Response.status(Response.Status.CONFLICT).build();
	    }
	}
	
	public void pipe(InputStream is, OutputStream os) throws IOException {
		int n;
		byte[] buffer = new byte[1024];
		while ((n = is.read(buffer)) > -1) {
			os.write(buffer, 0, n);  
		}
		os.close();
	}

}
