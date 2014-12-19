package model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.Vector;
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.Window;
import view.ProgressBarPopWindow;




public class UnixForFileHelper{

	
	
	/*
	 * the staring numbers indicate where we will start reading for each attribute
	 * the attribute will be read as a string and we will remove the whitespaces
	 * for all these attributes except for the description attribute
	 */
	
	private final int ekemsCodeStart = 0;
	// so we start reading for the ekems code from the 0 col until the 9th
	private final int descrStart = 8;
	
	private final int invCodeStart = 44;
	private final int invCodeEnd = 50;
	
	private final int priceStart = 65;
	private final int priceEnd = 76; // only for this attribute an end is required
	
	File the_file;
	final ObservableList<ProductsParsedFromFile> data ;
	
	Stage st;
	
	//TODO
	private static Object monitor = new Object();
	private static boolean choice;
	
	
	
	
	/**
	 * 
	 * @param unixforFilePath, a string denoting a path to a unixfor file
	 */
	public UnixForFileHelper(File unixforFile, Stage callerStage){
		the_file = unixforFile;
		data = FXCollections.observableArrayList();
		st = callerStage;
		choice = false;
	}
	
	
	
	/**
	 * populates the observable list with the data from the file
	 * @return
	 */
	public ObservableList<ProductsParsedFromFile> ParseFileWithNoCategories(){
		
		String ekemsCode, invCode, priceString, description ="";
		
		
		//how do we get the encoding????
		try(BufferedReader br = new BufferedReader(new InputStreamReader(
                new FileInputStream(the_file), "UTF-8"));) {
	    	
		String line;
		String supplier = "";
		while ((line = br.readLine()) != null) {
			   // process the line.
				if(line.toCharArray().length > 0){
		        	if(Character.isDigit(line.toCharArray()[0])){
		        		if(line.length() > priceEnd){
					        	ekemsCode=line.substring(ekemsCodeStart, descrStart-1).trim();
					        	invCode = line.substring(invCodeStart, invCodeEnd-1).trim();
					        	priceString = line.substring(priceStart,priceEnd ).trim();
					        	description = line.substring(descrStart,invCodeStart-1).trim(); 
					        	description = description.replace("'", "");
					        	
					        	float price =0;
					        	if(priceString.length() > 0){
						        	 price = Float.parseFloat(priceString.
						        			replace(".","").replace(",","."));
					        	}
					        	
					        	
					        	ProductsParsedFromFile pr = 
					        			new ProductsParsedFromFile("N/A", supplier, "N/A", description, 
					        					invCode, ekemsCode, price, 0, 0, 0, 0); 
					        	data.add(pr);
				        	
		        			}else{ if(line.matches(".*/.*")){
			        				supplier = line.split("/")[1].trim();
			        			}
		        		}//end if line matches /
		        			
		        	}//end if first character of line is a digit
	        	}//end if line contains stuff
			}
			br.close();
		}catch(Exception e){e.printStackTrace();}
				
		return data;
	}//end ParseFileWithNoCategories
	
	
	/**
	 * This method stores an ObservableList<ProductsParsedFromFile> object 
	 * to the database 
	*/
	public void saveParsedFileWithNoCategoryToDb(
			ObservableList<ProductsParsedFromFile> data, DbHelper dbh, 
			ProgressBar bar, ProgressBarPopWindow pbw){
		
		final Task<Void> task = new Task<Void>(){
			
			Vector<VectorPairs> 	subCatPairs = dbh.getSubCategoriesIntoVector();
			Vector<VectorPairs> 	categoriesPairs = dbh.getCategoriesIntoVector();
			Vector<VectorPairs> 	suppliersPairs = dbh.getSuppliersIntoVector();
			
			
			@SuppressWarnings("rawtypes")
			@Override
			protected Void call() throws Exception {
				
				int i =0;
				int max = data.size();
				
				
				
				for(ProductsParsedFromFile pr : data){
					
					updateProgress(i,max);
					
					String category =  pr.getCategory();
					String subCategory = pr.getSubCategory();
					String invCode = pr.getInvCode();
					String ekemsCode = pr.getEkemsCode();
					String description = pr.getDescription();
					
					float price = pr.getInvPrice();
					float grams = pr.getGramsForInvPrice();
					
					String supplier = pr.getSupplier();
					
					int subCatKey = getIDfromName(subCatPairs, subCategory);
					int catKey = getIDfromName(categoriesPairs, category);
					int suplKey = getIDfromName(suppliersPairs, supplier);
					
					int  checked = 0;
					
					if(i ==0){
						
					}//end if i == 0
					
					if(catKey == -1 ){
						@SuppressWarnings("unchecked")
						FutureTask<String> futureTask = 
								new FutureTask(
							    new MissingTextPrompt(
							    		pbw.getStage().getScene().getWindow(),
							    		monitor,choice,category));
						Platform.runLater(futureTask);
						
						synchronized(monitor) {
					      try {
					            monitor.wait();
					       } catch(InterruptedException e) {e.printStackTrace();}
					     }//end of synchronize(monitor)
						if(choice){
							dbh.execAdd("INSERT INTO CATEGORIES (CAT_NAME) VALUES ('"+category+"');");
						}else{
							dbh.execAdd("INSERT INTO CATEGORIES (CAT_NAME, ACTIVE) VALUES ('"+
									category+"', '"+ 0+"');");
						}
						categoriesPairs = dbh.getCategoriesIntoVector();
						catKey = getIDfromName(categoriesPairs, category);
					}//end if category was not found

					if(subCatKey ==-1){
						dbh.execAdd("INSERT INTO SUB_CATEGORIES (SUB_CAT_NAME, CAT_ID) "
								+ "VALUES ('"+subCategory+"', '"+ catKey+"' );");
						subCatPairs = dbh.getSubCategoriesIntoVector();
						subCatKey = getIDfromName(subCatPairs, subCategory);
					}//end if subCat was not found
					
					if(suplKey == -1){
						dbh.execAdd("INSERT INTO SUPPLIERS (NAME) "
								+ "VALUES ('"+supplier+"' );");
						suppliersPairs = dbh.getSuppliersIntoVector();
						suplKey = getIDfromName(suppliersPairs, supplier);
					}//end if supplier was not found
					
					
					// the first part of the if should update the price, var and percent
					// of the corresponding Products object if 
					// there exists a difference in the price of that code item
					if(dbh.rowCount(ekemsCode, "EKEMS_CODE", "PRODUCTS") !=0){
						System.out.println("item already found !!!!" + ekemsCode);
						try{
								float old_price = 
								(float) dbh.getValueFromCol("PRODUCTS", 
										"EKEMS_CODE", ekemsCode,"PRICE"); 
								
								float var = old_price -price;
								float percent = (var/old_price) * 100;
								
								percent =Float.parseFloat(
										String.format("%.2f", percent).replace(".", "").
																		replace(",","."));
								var =Float.parseFloat(String.format("%.2f", var).
														replace(".", "").replace(",","."));
								
								int key = (int)dbh.getValueFromCol("PRODUCTS", 
										"EKEMS_CODE", ekemsCode,"ID"); 
								
								System.out.println(key + " "+ ekemsCode + " "+var);
								
								dbh.updateValue(key, "PRICE", "PRODUCTS", price);
								dbh.updateValue(key, "VARIATION", "PRODUCTS", var);
								dbh.updateValue(key, "CHECKED", "PRODUCTS", 0);
								dbh.updateValue(key, "PERCENTAGE", "PRODUCTS", percent);
								
						}catch(Exception e){e.printStackTrace();}
						
					}else{
						if(catKey != -1){
							dbh.execAdd("INSERT INTO PRODUCTS (CAT_ID, SUB_CAT_ID, INV_CODE, "
									+ "EKEMS_CODE, DESCR, PRICE, GRAMS, SUP_ID, VARIATION,"
									+ "PERCENTAGE, PRICE_PER_UNIT, PACKAGE, CHECKED) VALUES "
									+ "('"+catKey+"', '"+ subCatKey  +"', '"+ invCode +"', '"
											+ ekemsCode+"', '"+description +"', '"
											+price +"', '" + grams +"', '" + suplKey +"', '"
											+ 0 +"', '" + 0 +"', '" + 0 +"', '"+ "" +"', '" 
											+checked +"')");
							System.out.println("added "+ ekemsCode);
						}//end if catKey is -1
					}//end of if ekems code is not found
				
					i++;
				}//end of for loop
				
				updateProgress(max,max);
				
				return null;
			}//end of call
		};//end of task
		
		bar.progressProperty().bind(task.progressProperty());
		new Thread(task).start();
		
		task.setOnSucceeded(eve ->{
			if(pbw!=null){pbw.getStage().close();}
		});
		
		
		
	}//end saveParsedFileWithNoCategoryToDb
	
	

	
	private int getIDfromName(Vector<VectorPairs> cats,
			String name) {
		int the_key= -1;
			
		for(VectorPairs vp : cats){
			if((vp.getName().trim()).equals(name.trim())){
				the_key = vp.getKey();
			}
		}
		return the_key;
	}//end of getIDfromName


	
	/**
	 * populates the observable list with the data from the file
	 * @return
	 */
	public ObservableList<ProductsParsedFromFile> ParseFileWithCategories(){
		
		int ekemsCodeStart = 39;
		int ekemsCodeEnd = 54;
		
		int invCodeStart = 0;
		int invCodeEnd = 8;
		
		int descrStart = 9 ;
		int descrEnd = 39;
		
		int supNameStart = 54;
		int supNameEnd = 75;
		
		int priceStart =84;
		int priceEnd = 92;
		
		String ekemsCode ="", invCode="", priceString="", description="", supplier="", 
					category="", subcategory = "", code = "";
		
		
		//how do we get the encoding????
		try(BufferedReader br = new BufferedReader(new InputStreamReader(
                new FileInputStream(the_file), "UTF-8"));) {
	    	
		String line;
		
		while ((line = br.readLine()) != null) {
			   // process the line.
				if(line.toCharArray().length > 0){
		        	if(Character.isDigit(line.toCharArray()[0])){
		        		if(line.length() > priceEnd){
					        	ekemsCode=line.substring(ekemsCodeStart, ekemsCodeEnd).trim();
					        	invCode = line.substring(invCodeStart, invCodeEnd).trim();
					        	priceString = line.substring(priceStart,priceEnd).trim();
					        	description = line.substring(descrStart,descrEnd).trim(); 
					        	description = description.replace("'", "");
					        	supplier = line.substring(supNameStart, supNameEnd).trim();
					        	
					        	
					        	float price =0;
					        	if(priceString.length() > 0){
						        	 price = Float.parseFloat(priceString.
						        			replace(".","").replace(",","."));
					        	}
					        	
					        	
					        	ProductsParsedFromFile pr = 
					        			new ProductsParsedFromFile(category, supplier, 
					        					subcategory, description, 
					        					invCode, ekemsCode, price, 0, 0, 0, 0); 
					        	data.add(pr);
				        	
		        			}else{ if(line.matches(".*/.*")){
		        					code = line.split("/")[0].trim();
		        							if( code.length() > 5){
		        								subcategory = line.split("/")[1].trim();
		        							}else{
		        								category = line.split("/")[1].trim();
		        							}//end if code length less then 5 digits
		        				
		        					supplier = line.split("/")[1].trim();
			        			}//end if  line contains a /
		        		}//end if line matches /
		        			
		        	}//end if first character of line is a digit
	        	}//end if line contains stuff
			}
			br.close();
		}catch(Exception e){e.printStackTrace();}
				
		return data;
	}//end ParseFileWithNoCategories
	
	
	
	/*private String convertToUTF8(String input){
		
		try {
			byte[] b = input.getBytes("cp1253");
			String s = new String(b, "UTF-8");
			
			return s;
		} catch (UnsupportedEncodingException e) {e.printStackTrace(); }
		
		return " ";
	}
	*/
	
	//TODO
	 class MissingTextPrompt implements Callable<String> {
		 final Window owner;
		 Object monitor;
		 String catName;
		 
		 MissingTextPrompt(Window owner, Object monitor, 
				 boolean choice, String catName) {
			 this.owner = owner;
			 this.monitor = monitor;
			 this.catName = catName;
			 
		 }
		  
		 @Override 
		 public String call() throws Exception {
			 final Stage dialog = new Stage();
			 dialog.setTitle("ΝΕΑ ΚΑΤΗΓΟΡΙΑ");
			 dialog.initOwner(owner);
			 dialog.initStyle(StageStyle.UTILITY);
			 dialog.initModality(Modality.WINDOW_MODAL);
			 
			 //final TextField textField = new TextField();
			 /*try {
					parentTask.wait();
			 } catch (InterruptedException e) { e.printStackTrace();	}
			 */
			 Text message = new Text("ΕΝΕΡΓΟΠΟΙΗΣΗ ΚΑΤΗΓΟΡΙΑΣ "+ catName+"????");
			 final Button submitButton = new Button("NAI");
			 final Button cancelButton = new Button("ΟΧΙ");
			 
			 submitButton.setDefaultButton(true);
			 submitButton.setOnAction(event->{
					 dialog.close();
					 choice = true;
					 synchronized(monitor) {
					        monitor.notifyAll();
					 }
					 
					 
			 });
			 
			 cancelButton.setDefaultButton(true);
			 cancelButton.setOnAction(event->{
					 dialog.close();
					 choice = false;
					 synchronized(monitor) {
					        monitor.notifyAll();
					 }
					 
					 
			 });
			  
			 final VBox layout = new VBox(10);
			 layout.setAlignment(Pos.CENTER_RIGHT);
			 layout.setStyle("-fx-background-color: azure; -fx-padding: 10;");
			 
			 HBox buttons = new HBox(5);
			 buttons.getChildren().addAll(submitButton, cancelButton);
			 
			 layout.getChildren().setAll( message, buttons);
			  
			 dialog.setScene(new Scene(layout));
			 dialog.showAndWait();
			 
			 return "";
		 }
	}//end of MissingTextPrompt
	 
	
	 
	 
	
	
}//end of class
