//package net.teufel.sidplay.endpoints;
//
//import com.fasterxml.jackson.core.JsonProcessingException;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import net.teufel.sidplay.dao.SidDaoJdbc;
//
//import javax.inject.Inject;
//import javax.ws.rs.GET;
//import javax.ws.rs.Path;
//import javax.ws.rs.Produces;
//import javax.ws.rs.core.MediaType;
//import javax.ws.rs.core.Response;
//
//@Path("/types")
//public class TypesEndpoint {
//
//    @Inject
//    SidDaoJdbc sidDao;
//
//    @GET
//    @Produces(MediaType.APPLICATION_JSON)
//    public Response getTypes() throws JsonProcessingException {
//        return Response.ok(new ObjectMapper().writeValueAsString(sidDao.getTypes())).build();
//    }
//
//}
