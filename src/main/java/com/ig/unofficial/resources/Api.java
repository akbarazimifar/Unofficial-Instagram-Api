package com.ig.unofficial.resources;

import java.io.IOException;
import java.net.ProtocolException;
import javax.ws.rs.Consumes;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.json.JSONObject;

/**
 *
 * @author Nakul
 */
@Path("api")
public class Api {

    @GET
    @Consumes("application/json")
    @Path("video")
    public Response video(@DefaultValue("https://www.instagram.com/p/CHFJkibFHuV/") @QueryParam("link") String link) throws IOException, IllegalArgumentException, IllegalAccessException, NoSuchFieldException {
        SSLCertificate.sslCertificate();
        if (link.isEmpty()) {
            return Response.status(200).entity("{\"status\" : \"REQUEST_DENIED\", \"video_link\" : \"NULL\"}").build();
        }
        String videos = Funs.videos(link);
        return Response.status(200).entity(videos).build();
    }

    @GET
    @Path("user")
    @Consumes("application/json")
    public Response userProfileLink(@DefaultValue("https://www.instagram.com/instagram/") @QueryParam("link") String link) throws ProtocolException, IOException {
         SSLCertificate.sslCertificate();
        String getUserId = Funs.getUserId(link);
         if (link.isEmpty()) {
            return Response.status(200).entity("{\"status\" : \"REQUEST_DENIED\", \"link\" : \"NULL\"}").build();
        } else if (link.contains("https://instagram.com/")) {
            link = link.replace("https://instagram.com", "https://www.instagram.com").split("\\?")[0].concat("/");
            System.out.println(link);
            return Response.status(200).entity(getUserId).build();
        } if (!link.contains("https://www.instagram.com/") && !link.contains("https://instagram.com/")) {
                
            return Response.status(200).entity("{\"status\" : \"REQUEST_DENIED\", \"link\" : \"INVALID\"}").build();
        }
        
        return Response.status(200).entity(getUserId).build();
    }
    
    @GET
    @Path("profile")
    @Consumes("application/json")
    @Produces(MediaType.APPLICATION_JSON)
    public Response userProfile(@DefaultValue("25025320") @QueryParam("userid") String userId) throws IOException, NoSuchFieldException, IllegalArgumentException, IllegalAccessException {

        if (userId.isEmpty()) {
            return Response.status(200).entity("{\"status\" : \"REQUEST_DENIED\", \"user_id\" : \"NULL\"}").build();
        }
        SSLCertificate.sslCertificate();
        String data = Funs.getUserProfile(userId);
        JSONObject ob = Funs.userDetails(data);
        return Response.status(200).entity(ob.toString()).build();
    }
    
    @GET
    @Path("topsearch")
    @Consumes("application/json")
    @Produces(MediaType.APPLICATION_JSON)
    public Response topUserSearch(@DefaultValue("instagram") @QueryParam("query") String query) throws IOException {
        SSLCertificate.sslCertificate();
        String topSearch = Funs.topSearch(query);
        return Response.status(200).entity(topSearch).build();
    }
    
}