package com.gospelmalin.rasf;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

//Själva webservicen
@Path("/CategoryService")
public class CategoryService {

	CategoryDao categoryDao = new CategoryDao();
	   private static final String SUCCESS_RESULT="<result>success</result>";
	   private static final String FAILURE_RESULT="<result>failure</result>";

	   
	   @GET
	   @Path("/categories")
	   @Produces(MediaType.APPLICATION_XML)
	   public List<Category> getCategories(){
	      return categoryDao.getAll();
	   }
	   
	   @GET
	   @Path("/categories/{categoryKey}")
	   @Produces(MediaType.APPLICATION_XML)
	   public Category getCategory(@PathParam("categoryKey") int categoryKey){
	      return categoryDao.getCategory(categoryKey);
	   }

}
