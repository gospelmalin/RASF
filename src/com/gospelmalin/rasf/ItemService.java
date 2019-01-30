package com.gospelmalin.rasf;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

//Själva webservicen
@Path("/ItemService")
public class ItemService {
	
	ItemDao itemDao = new ItemDao();
	   private static final String SUCCESS_RESULT="<result>success</result>";
	   private static final String FAILURE_RESULT="<result>failure</result>";

	   
	   @GET
	   @Path("/items")
	   @Produces(MediaType.APPLICATION_XML)
	   public List<Item> getItems(){
	      return itemDao.getAll();
	   }

}
