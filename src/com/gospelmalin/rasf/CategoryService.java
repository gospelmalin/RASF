package com.gospelmalin.rasf;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.DELETE;
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

/**
 * The Class CategoryService is the actual webservice for category.
 */
@Path("/CategoryService")
public class CategoryService {

	CategoryDao categoryDao = new CategoryDao();
	   private static final String SUCCESS_RESULT="<result>success</result>";
	   private static final String FAILURE_RESULT="<result>failure</result>";

	   /**
	    * Show all categories
	    *
	    */
	   @GET
	   @Path("/categories")
	   @Produces(MediaType.APPLICATION_XML)
	   public List<Category> getCategories(){
	      return categoryDao.getAll();
	   }
	   
	   /**
	    * Show information about selected category
	    *
	    */
	   @GET
	   @Path("/categories/{categoryKey}")
	   @Produces(MediaType.APPLICATION_XML)
	   public Category getCategory(@PathParam("categoryKey") int categoryKey){
	      return categoryDao.getCategory(categoryKey);
	   }
	   
	 
	   /**
		 * Add a new category
		 *
		 */
	   @POST
	   @Path("/categories")
	   @Produces(MediaType.APPLICATION_XML)
	 //  @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	   public String createCategory(
			   @QueryParam("categoryName") String categoryName, 
			   @Context HttpServletResponse servletResponse) throws IOException{
		  Category category = new Category(categoryName);
	      int result = categoryDao.addCategory(category);
	      if(result == 1){
	         return SUCCESS_RESULT;
	      }
	      return FAILURE_RESULT;
	   }
	   
	   /**
		 * Update category name
		 *
		 */
	   @PUT
	   @Path("/categories/{categoryKey}")
	   @Produces(MediaType.APPLICATION_XML)
	   public String updateCategory(@PathParam("categoryKey") int categoryKey,
			   @QueryParam("categoryName") String categoryName, 
			   @Context HttpServletResponse servletResponse) throws IOException{
	      Category category = new Category(categoryKey, categoryName);
	      int result = categoryDao.updateCategory(category);
	      if(result == 1){
	         return SUCCESS_RESULT;
	      }
	      return FAILURE_RESULT;
	   }
	   
	   /**
	    * Delete a category.
	    * Delete only allowed for categories with no linked items,
	    * as deleting a category otherwise would also delete those items.
	    *
	    */
	   @DELETE
	   @Path("/categories/{categoryKey}")
	   @Produces(MediaType.APPLICATION_XML)
	   public String deleteCategory(@PathParam("categoryKey") int categoryKey){
	      int result = categoryDao.deleteCategory(categoryKey);
	      if(result == 1){
	         return SUCCESS_RESULT;
	      }
	      return FAILURE_RESULT;
	   }
	   
	   /**
	    * Show options for the category service
	    *
	    */
	   @OPTIONS
	   @Path("/categories")
	   @Produces(MediaType.APPLICATION_XML)
	   public String getSupportedOperations(){
		   return "<operations>GET, PUT, POST, DELETE</operations>"; // Note that deleting a category will also delete any linked items.
	   }
	   
}
