package com.gospelmalin.rasf;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class CategoryDao {

	public List<Category> getAll() {
		List<Category> categoriesList = null; 
	      
		categoriesList = new ArrayList<Category>();
		
		
		Database db = new Database();
		try {
			String query = "SELECT * from category;";
			ResultSet rs = db.executeQuery(query);
	  
			
			while(rs.next()) {
			  int categoryKey = rs.getInt("category_key");
			  String categoryName = rs.getString("category_name");
			  System.out.println(categoryKey + " " + categoryName);
			  categoriesList.add(new Category(categoryKey, categoryName));
			}
		} catch (SQLException e) {
		// TODO Auto-generated catch block
			e.printStackTrace();
		}
		/*
		categoriesList.add(new Category(1, "CATEGORY1"));
		categoriesList.add(new Category(2, "CATEGORY2"));
		categoriesList.add(new Category(3, "CATEGORY3"));
		 */
	      return categoriesList; 
	   } 


}
