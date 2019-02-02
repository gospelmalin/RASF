package com.gospelmalin.rasf;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.GET;
import javax.ws.rs.OPTIONS;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

//Själva webservicen
@Path("/StorageplaceService")
public class StorageplaceService {

	StorageplaceDao storageplaceDao = new StorageplaceDao();
	   private static final String SUCCESS_RESULT="<result>success</result>";
	   private static final String FAILURE_RESULT="<result>failure</result>";

	   
	   @GET
	   @Path("/storageplaces")
	   @Produces(MediaType.APPLICATION_XML)
	   public List<Storageplace> getStorageplaces(){
	      return storageplaceDao.getAll();
	   }
	   
	   @GET
	   @Path("/storageplaces/{storageplaceKey}")
	   @Produces(MediaType.APPLICATION_XML)
	   public Storageplace getCategory(@PathParam("storageplaceKey") int storageplaceKey){
	      return storageplaceDao.getStorageplace(storageplaceKey);
	   }
	   
	   @POST
	   @Path("/storageplaces")
	   @Produces(MediaType.APPLICATION_XML)
	 //  @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	   public String createStorageplace(
			   @QueryParam("storageplaceName") String storageplaceName, 
			   @Context HttpServletResponse servletResponse) throws IOException{
		  Storageplace storageplace = new Storageplace(storageplaceName);
	      int result = storageplaceDao.addStorageplace(storageplace);
	      if(result == 1){
	         return SUCCESS_RESULT;
	      }
	      return FAILURE_RESULT;
	   }
	   
	   @PUT
	   @Path("/storageplaces/{storageplaceKey}")
	   @Produces(MediaType.APPLICATION_XML)
	   public String updateStorageplace(@PathParam("storageplaceKey") int storageplaceKey,
			   @QueryParam("storageplaceName") String storageplaceName, 
			   @Context HttpServletResponse servletResponse) throws IOException{
		  Storageplace storageplace = new Storageplace(storageplaceKey, storageplaceName);
	      int result = storageplaceDao.updateStorageplace(storageplace);
	      if(result == 1){
	         return SUCCESS_RESULT;
	      }
	      return FAILURE_RESULT;
	   }
	   
	   @OPTIONS
	   @Path("/storageplaces")
	   @Produces(MediaType.APPLICATION_XML)
	   public String getSupportedOperations(){
		   return "<operations>GET, PUT, POST, DELETE</operations>"; // Note that deleting a storageplace will also delete any linked items.
	   }
}	   
