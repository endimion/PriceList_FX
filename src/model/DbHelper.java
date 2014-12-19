package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

import view.LoginView;
import view.ProgressBarPopWindow;
import control.ProductsTableController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.scene.control.ProgressBar;
import javafx.stage.Stage;



public class DbHelper {

	
	
private static Connection conn ;
	
	
	
	//constructor
	// the parameter should be a String which contains the
	// absolute path to the database (access db)
	public DbHelper(){
	}
	
	
	
	
	
	// returns the names of the COLUMNS as they APPEAR in the 
	// DB using the metadata retrived from  a given ResultSet
	public  Vector<String> colNames(ResultSetMetaData metaData){
		Vector<String> columnNames = new Vector<String>();
		try{
		    // names of columns
		    int columnCount = metaData.getColumnCount();
		    for (int column = 1; column <= columnCount; column++){
		    	columnNames.add(metaData.getColumnName(column));
		    }
		}catch(SQLException e){e.printStackTrace();}
		return columnNames;
	}//end of colNames
	
	
	
	
	//public class which builds a connection to the given at 
	// the constructor data base
	public int dbConnect(String dbUri, String uName, String uPass){
			try
	        {
	            Class.forName("com.mysql.jdbc.Driver").newInstance();
	            conn = DriverManager.getConnection
	            		("jdbc:mysql://"+dbUri+"?useUnicode=true&characterEncoding=UTF-8&user="+uName+"&password="+uPass); 
	        
	            return 1;
	        }catch(Exception ex)
	        {
	            
	        	ex.printStackTrace();
	        	return -1;
	        }
	}//END of accessConnect
	
	
	
	// methods which executes a given query which 
	// which retrieves data (which is passed as the string)
	// the_q) to the method and returns the pointer to the results
	// of that query
	// also for debugging purposes this also 
	// prints the results to the shell
	public ResultSet execGet(String the_q){
			ResultSet rs = null;
			
		// first we check if the connection is active
		// if it is still alive we execute the given query
		// and catch the possible exception
		if(conn != null){
			try{
				Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
					    ResultSet.CONCUR_READ_ONLY);
	              	
				rs = stmt.executeQuery(the_q);
	      		}catch(Exception ex){ ex.printStackTrace();}
		}
		return rs;
	}// END OF execQuery
	
	
	
	// this void method should be used to execute only an
	// insert into SQL statement
	public void execAdd(String the_q){
			// first we check if the connection is active
			// if it is still alive we execute the given insert
			if(conn != null){
				try{
					Statement stmt = conn.createStatement();
	               	stmt.executeUpdate(the_q);
					//System.out.println("data succesfully added");
					//stmt.close();
	       		}catch(Exception ex)
				{
	       			ex.printStackTrace();
	       			System.out.println(the_q);
	       		}
			}
			
	}// ENDOF execAdd
	
	
	
	public void updateValue(int key, String col, String tab, boolean val){
		try{
			Statement stmt = conn.createStatement(ResultSet.TYPE_FORWARD_ONLY,
			        ResultSet.CONCUR_UPDATABLE);
		
			String sql = "UPDATE "+ tab + " SET "+col+ " ='"
						 + val +"' WHERE ID ='"+key+"'";
			
			stmt.executeUpdate(sql);
			//stmt.close();
			
		}catch(Exception ex){ex.printStackTrace();}
		
	}
	
	public void updateValue(int key, String col, String tab, float val){
		try{
			Statement stmt = conn.createStatement(ResultSet.TYPE_FORWARD_ONLY,
			        ResultSet.CONCUR_UPDATABLE);
		
			String sql = "UPDATE "+ tab + " SET "+col+ " ='"
						 + val +"' WHERE ID ='"+key+"'";
			
			stmt.executeUpdate(sql);
			//stmt.close();
			System.out.println("update");
		}catch(Exception ex){ex.printStackTrace();}
	}
	
	
	
	public void updateValue(int key, String col, String tab, int val){
		try{
			Statement stmt = conn.createStatement(ResultSet.TYPE_FORWARD_ONLY,
			        ResultSet.CONCUR_UPDATABLE);
		
			String sql = "UPDATE "+ tab + " SET "+col+ " ='"
						 + val +"' WHERE ID ='"+key+"'";
			stmt.executeUpdate(sql);
			//stmt.close();
		}catch(Exception ex){ex.printStackTrace();}
	}
	
	// public method which given the PRIMARY KEY of the table
	// tab changes the value of the col to the Value val
	public void updateValue(int key, String col, String tab, String val){
		try{
			Statement stmt = conn.createStatement(ResultSet.TYPE_FORWARD_ONLY,
			        ResultSet.CONCUR_UPDATABLE);
		
			String sql = "UPDATE "+ tab + " SET "+col+ " ='"
						 + val +"' WHERE ID ='"+key+"'";
			
			System.out.println("updating "+key+ " of "+ tab+ " with"+val);
			stmt.executeUpdate(sql);
			//stmt.close();
			
		}catch(Exception ex){ex.printStackTrace();}
		
	}
	
	
	/**
	 * this method returns the suppliers names, and ids  as a vector
	 * this then can be used to display them as a comboBox
	 * @return
	 */
	public Vector<VectorPairs> getSuppliersIntoVector(){
		
		String the_q = "SELECT *  FROM SUPPLIERS ORDER BY NAME";
		ResultSet rs = execGet(the_q);
		
		Vector<VectorPairs> suppliers =null;
		
		try {
			suppliers = new Vector<VectorPairs>();
			rs.beforeFirst();
			// if the name of the unit we are reading from the result
			// set does not belong to the list of units
			// we add it to the list we will return
			while(rs.next()){
				Integer id = rs.getInt("ID");
				String sup_name = rs.getString("NAME");
				
				SuppliersPair row = new SuppliersPair(sup_name, id);
				suppliers.add(row);
				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return suppliers;
	}//end of getSuppliersIntoVector
	
	
	
	/**
	 * this method returns the sub-categories names, categories ids and ids as a vector
	 * this then can be used to display them as a comboBox
	 * @return
	 */
	public Vector<VectorPairs> getSubCategoriesIntoVector(){
		
		String the_q = "SELECT *  FROM SUB_CATEGORIES WHERE NOT CAT_ID = 'NULL'"
				+ "ORDER BY SUB_CAT_NAME";
		ResultSet rs = execGet(the_q);
		
		Vector<VectorPairs> subCategories =null;
		
		try {
			subCategories = new Vector<VectorPairs>();
			rs.beforeFirst();
			// if the name of the unit we are reading from the result
			// set does not belong to the list of units
			// we add it to the list we will return
			while(rs.next()){
				Integer id = rs.getInt("ID");
				String sub_cat_name = rs.getString("SUB_CAT_NAME");
				int cat_id = rs.getInt("CAT_ID");
				
				SubCatPair row = new SubCatPair(sub_cat_name, cat_id, id);
				subCategories.add(row);
				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return subCategories;
	}//end of getCategoriesIntoList
	
	
	/**
	 * this method returns the categories names and ids as a vector
	 * this then can be used to display them as a comboBox
	 * @return
	 */
	public Vector<VectorPairs> getCategoriesIntoVector(){
		
		String the_q = "SELECT * FROM CATEGORIES ORDER BY CAT_NAME";
		ResultSet rs = execGet(the_q);
		
		Vector<VectorPairs> categories =null;
		
		try {
			categories = new Vector<VectorPairs>();
			rs.beforeFirst();
			// if the name of the unit we are reading from the result
			// set does not belong to the list of units
			// we add it to the list we will return
			while(rs.next()){
				Integer id = rs.getInt("ID");
				String cat_name = rs.getString("CAT_NAME");
				
				CategoriesPair row = new CategoriesPair(cat_name,id);
				categories.add(row);
				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return categories;
	}//end of getCategoriesIntoList
	
	
	/**
	 * 
	 * @param table, the name of the table
	 * @param KeyCol, the name of the column we are searching
	 * @param val, the value we want the KeyCol to contain
	 * @param resCol the column whose value we want to retrieve
	 * @return the first result which matches the given value on the given
	 * column
	 */
	public Object getValueFromCol(String table, String KeyCol, String val, 
			String resCol){
		
		String q = "SELECT  * FROM " +table+ " WHERE "+ 
					KeyCol +"= '"+ val +"'";
		ResultSet rs = execGet(q);
		
		Object result = null;
		
		try {
			while(rs.next()){
				result = rs.getObject(resCol);
			}
		} catch (SQLException e) {e.printStackTrace();}
		
		return result;
	}//end of getValueFromCol
	
	
	
	
	
	
	/**
	 * returns the number of rows of an sql query
	 * @param searchVal the value we are searching for
	 * @param the column which should contain the value
	 * @param tab the table containing the column - value
	 * @return the number of rows of the table matching the search criteria
	 */
	public int rowCount(String searchVal, String tabCol, String tab){
		int the_result = 0;
		
		try{
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT COUNT(*) AS count "
				+ "FROM "+ tab+ " WHERE "+ tabCol +" ='"+searchVal+"'");
			
			
			while(rs.next()){
				the_result=rs.getInt("count");
			}
			
			
		}catch(Exception ex)
		{ ex.printStackTrace();}
		
		return the_result;
	}// ENDOF rowCount polymorphism
	
	
	
	/**
	 * returns the number of rows of an sql query
	 * @param searchVal the value we are searching for
	 * @param the column which should contain the value
	 * @param tab the table containing the column - value
	 * @return the number of rows of the table matching the search criteria
	 */
	public int rowCountWithWhere(String where,  String tab){
		int the_result = 0;
		
		try{
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT COUNT(*) AS count "
				+ "FROM "+ tab+ " WHERE "+ where);
			
			while(rs.next()){
				the_result=rs.getInt("count");
			}
			
			
		}catch(Exception ex)
		{ ex.printStackTrace();}
		
		return the_result;
	}// ENDOF rowCount polymorphism
	
	
	
	/**
	 * 
	 * @param subCat
	 * @return returns a vector array of the String names of the suppliers whihc 
	 * have a product with the given category
	 */
	public Vector<String> findSupplierWithSubCat(String subCat){
		Vector<String> result = new Vector<String>();

		try{
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM "
					+ " PRODUCTS_V WHERE SUB_CAT_NAME ='"+subCat+"'");
			
			
			while(rs.next()){
				String a_result = rs.getString("NAME");
				boolean should_add = true;
				if(result.size() == 0){
					result.add(a_result);
				}else{
					for(int i =0; i < result.size();i++){
						if((result.get(i)).equals(a_result))
						{	
							should_add = false;
						}
					}//end for result loop
					if(should_add){
						result.add(a_result);
					}
					
				}//end else if result has elements 
				
			}//end while rs
		}catch(Exception ex){ ex.printStackTrace();}
		
		return result;
	}//end of findSupplierWithSubCat
	
	
	public ObservableList<Products> getProductsToObservList(String where){
		
		final ObservableList<Products> data = FXCollections.observableArrayList();;
		
		String q="";
		if(where == null){
			q = "select * from PRODUCTS_V";
		}else{
			q = "SELECT * FROM PRODUCTS_V WHERE "+ where;
		}
		ResultSet rs = execGet(q);
		
		try {
			while(rs.next()){
				int key = rs.getInt("ID");
				String category = rs.getString("CAT_NAME");
				String subCategory = rs.getString("SUB_CAT_NAME");
				String descr = rs.getString("DESCR");
				String supl = rs.getString("NAME");
				
				String invCode = rs.getString("INV_CODE");
				String ekemsCode = rs.getString("EKEMS_CODE");
				
				float price = rs.getFloat("PRICE");
				float grams = rs.getFloat("GRAMS");
				float precent = rs.getFloat("PERCENTAGE");
				float pricePerKil = rs.getFloat("PRICE_PER_UNIT");
				float variation = rs.getFloat("VARIATION");
				
				try{
					boolean checked = rs.getBoolean("CHECKED");
					Products pr = new Products(category, key, supl, subCategory, 
							descr, invCode, ekemsCode, price, grams, 
							variation, precent, pricePerKil, checked);
					
					data.add(pr);
				}catch(Exception e){
					
					Products pr = new Products(category, key, supl, subCategory, 
					descr, invCode, ekemsCode, price, grams, 
					variation, precent, pricePerKil, true);
					
					data.add(pr);
				
				}//end of catchBlock
				
			}//end while
		} catch (SQLException e) {e.printStackTrace();}
		
		
		return data;
	}//end of getProductsToObservList
	
	
	
	
	
	/**
	 * this method search the database table for a given value of a column
	 * and if the value is not found an appropriate row is inserted to the table
	 * THIS METHOD CAN ONLY BE USED FOR 
	 */
	public void searchAndAddIfNeeded(String val, String col, 
			String table, Vector<String> cols, Vector <String> vals){
		

		String columnsString = "";
		for(int i = 0; i< cols.size()-1; i++){
			columnsString = cols.get(i) +", ";
		}
		columnsString = columnsString + cols.get(cols.size()-1);
		
		String valuesString ="";
		for(int i = 0; i< vals.size()-1; i++){
			valuesString = vals.get(i) +"' , '";
		}
		valuesString =  valuesString + vals.get(vals.size()-1);
		
		String q ="INSERT INTO " + table + "( " +columnsString +" ) "+ " VALUES ('"
				+ valuesString+"')";
		
		
		if( rowCount(val,col,table) == 0){
			execAdd(q);
			System.out.println(q);
		}
		
		
	}//end of searchAndAddIfNeeded
	
	
	/**
	 * Deletes the row identified by the given key from the given table
	 * @param table, the table to delete the row from
	 * @param key, the id of the row we will delete
	 */
	public void deleteRow(String table, int key){
		String q = "DELETE FROM "+table+" WHERE ID='"+key+"'";
		execAdd(q);
	}//end of delete_row
	
	
	
	
	//TODO
	//this method should be altered and moved to the control package
	public void insertProductsFromXlsToDB(ObservableList<ProductsParsedFromFile> data, 
			final ProgressBar bar, Stage st, LoginView lv){

		Task<Void> task = new Task<Void>(){
			@Override
			protected Void call() throws Exception {
				Vector<VectorPairs> subVect = getSubCategoriesIntoVector(); 
				Vector<VectorPairs> catVect = getCategoriesIntoVector();
				Vector<VectorPairs> supVect = getSuppliersIntoVector();
								
				int progress = 0;
				int max = data.size();
								
				for(ProductsParsedFromFile p: data){
					updateProgress(progress, max);
					
					int cat_id = -1;
					int sub_cat_id = -1;
					int supl_id = -1;
									
					Vector<String> cols = new Vector<String>();
					Vector<String> vals = new Vector<String>();
					cols.add("CAT_NAME");
					vals.add(p.getCategory());
					searchAndAddIfNeeded(p.getCategory(),"CAT_NAME","CATEGORIES",cols, vals);
					catVect = getCategoriesIntoVector();
								
					for(VectorPairs cp : catVect){
						if(cp.getName().trim().equals(p.getCategory().trim())){
							cat_id = cp.getKey();
						}
					}//end of loop catVect
									
					cols = new Vector<String>();
					vals = new Vector<String>();
					cols.add("SUB_CAT_NAME");
					cols.add("CAT_ID");
					vals.add(p.getSubCategory());
					vals.add(""+ new Integer(cat_id));
									
					searchAndAddIfNeeded(p.getSubCategory(),"SUB_CAT_NAME",
											"SUB_CATEGORIES",cols, vals);
					subVect = getSubCategoriesIntoVector(); 
					for(VectorPairs sp : subVect){
						if(sp.getName().trim().equals(p.getSubCategory().trim())){
							sub_cat_id = sp.getKey();
						}
					}//end of loop subVect
									
					cols = new Vector<String>();
					vals = new Vector<String>();
					cols.add("NAME");
					vals.add(p.getSupplier());
					searchAndAddIfNeeded(p.getSupplier(),"NAME","SUPPLIERS",cols, vals);
					supVect = getSuppliersIntoVector();
					for(VectorPairs sp : supVect){
						if(sp.getName().trim().equals(p.getSupplier().trim())){
							supl_id = sp.getKey();
						}
					}//end of loop subVect
								
					if(rowCount(p.getEkemsCode(), "EKEMS_CODE", "PRODUCTS") == 0
							&& supl_id != -1 && cat_id != -1 && sub_cat_id != -1){
						execAdd("INSERT INTO PRODUCTS "
								+ "(CAT_ID, SUB_CAT_ID, INV_CODE, EKEMS_CODE, DESCR," 
								+ "PRICE, GRAMS, SUP_ID, VARIATION, PERCENTAGE, PRICE_PER_UNIT,"
								+ "PACKAGE, CHECKED )"
								+ " VALUES ('"
								+ cat_id+"' , '" 
								+ sub_cat_id  +"', '"
								+ p.getInvCode()+ " ', '"
								+ p.getEkemsCode()+ "', '"
								+ p.getDescription()+"', '"
								+ p.getInvPrice()+"', '"
								+ p.getGramsForInvPrice()+ "' , '"+  
								+ supl_id+ "' , "
								+ "null"+ " , " 
								+ "null"+ " , '" 
								+ p.getPricePerKilo()+ "' , "
								+"null, '1' );") ;
					}//end if rowcount is zero
					else{System.out.println("already in!!!");}
					
					progress++;
				}//end of for loop
				
				return null;
				}//end of call
		};//end of task
		final ProgressBarPopWindow pbw = new ProgressBarPopWindow(bar, "Μεταφορά Εγγραφών στη Βάση Δεδομένων", new Stage());
	
		bar.progressProperty().bind(task.progressProperty());
		new Thread(task).start();
		task.setOnSucceeded(event3->{
	    	ProductsTableController ptc = new ProductsTableController(st, this, lv);
	        ptc.buildAndDisplayProductsTable(false);
	        pbw.getStage().close();
		});
	}//end of insertProductsFromXlsToDB
	
	
}//end of DbHelper
