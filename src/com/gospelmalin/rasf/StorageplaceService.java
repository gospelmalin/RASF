package com.gospelmalin.rasf;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.GET;
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
}	   
