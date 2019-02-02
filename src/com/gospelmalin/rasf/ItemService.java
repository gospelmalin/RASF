package com.gospelmalin.rasf;
import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.OPTIONS;
import javax.ws.rs.PATCH;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
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
	   
	   
	   @GET
	   @Path("/items/{itemKey}")
	   @Produces(MediaType.APPLICATION_XML)
	   public Item getItem(@PathParam("itemKey") int itemKey){
	      return itemDao.getItem(itemKey);
	   }
	   
	   @GET
	   @Path("/categories/{categoryKey}/items")
	   @Produces(MediaType.APPLICATION_XML)
	   public List<Item> getItemsForCategory(@PathParam("categoryKey") int categoryKey){
	      return itemDao.getAllForCategory(categoryKey);
	   }
	   
	   @PUT
	   @Path("/items/{itemKey}")
	   @Produces(MediaType.APPLICATION_XML)
	   public String updateItem(@QueryParam("itemKey") int itemKey,
			   @QueryParam("categoryKey") int categoryKey, 
			   @QueryParam("itemName") String itemName, 
			   @QueryParam("unitsAlways") int unitsAlways, 
			   @QueryParam("available") String available, 
			   @QueryParam("numberOfUnits") int numberOfUnits,   
			   @QueryParam("categoryName") String categoryName,
			   @QueryParam("storageplaceKey") int storageplaceKey,
	   		   @QueryParam("storageplaceName") String storageplaceName,
	      @Context HttpServletResponse servletResponse) throws IOException{
	      Item item = new Item(itemKey, categoryKey, itemName, unitsAlways, available, numberOfUnits, categoryName, storageplaceKey, storageplaceName);
	    //  Item item = new Item(itemKey, itemName, unitsAlways, available, numberOfUnits);
	      int result = itemDao.updateItem(item);
	      if(result == 1){
	         return SUCCESS_RESULT;
	      }
	      return FAILURE_RESULT;
	   }
	   
	   @POST
	   @Path("/items")
	   @Produces(MediaType.APPLICATION_XML)
	 //  @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	   public String createItem(
			   @QueryParam("categoryKey") int categoryKey, 
			   @QueryParam("itemName") String itemName, 
			   @QueryParam("unitsAlways") int unitsAlways, 
			   @QueryParam("available") String available, 
			   @QueryParam("numberOfUnits") int numberOfUnits,   
			//   @QueryParam("categoryName") String categoryName,
			   @QueryParam("storageplaceKey") int storageplaceKey,
	   		//   @QueryParam("storageplaceName") String storageplaceName,
	      @Context HttpServletResponse servletResponse) throws IOException{
		   Item item = new Item(categoryKey, itemName, unitsAlways, available, numberOfUnits, storageplaceKey);
	      int result = itemDao.addItem(item);
	      if(result == 1){
	         return SUCCESS_RESULT;
	      }
	      return FAILURE_RESULT;
	   }
	   
	   /*
	   @PUT
	   @Path("/items")
	   @Produces(MediaType.APPLICATION_XML)
	   @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	   public String updateItem(@FormParam("itemKey") int itemKey,
		//	   @FormParam("categoryKey") int categoryKey, 
			   @FormParam("itemName") String itemName, 
			   @FormParam("unitsAlways") int unitsAlways, 
			   @FormParam("available") String available, 
			   @FormParam("numberOfUnits") int numberOfUnits,   
			   //@FormParam("categoryName") String categoryName,
			//   @FormParam("storageplaceKey") int storageplaceKey,
	   		//   @FormParam("storageplaceName") String storageplaceName,
	      @Context HttpServletResponse servletResponse) throws IOException{
	  //    Item item = new Item(itemKey, categoryKey, itemName, unitsAlways, available, numberOfUnits, categoryName, storageplaceKey, storageplaceName);
	      Item item = new Item(itemKey, itemName, unitsAlways, available, numberOfUnits);
	      int result = itemDao.updateItem(item);
	      if(result == 1){
	         return SUCCESS_RESULT;
	      }
	      return FAILURE_RESULT;
	   }
	   */

	   @OPTIONS
	   @Path("/users")
	   @Produces(MediaType.APPLICATION_XML)
	   public String getSupportedOperations(){
	      //return "<operations>GET, PUT, POST, DELETE</operations>";
		   return "<operations>GET, PUT, POST</operations>"; //DELETE FROM ITEM NOT ALLOWED
	   }
	   
}
