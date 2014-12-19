package control;

import java.util.Vector;


import javafx.application.Platform;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.MenuBar;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.BestPriceService;
import model.Categories;
import model.DbHelper;
import model.Products;
import model.SubCategories;
import model.TableBuilder;
import model.VectorPairs;
import model.XlsHelper;
import view.LoginView;
import view.NewCategoryPopUP;
import view.NewSubCategoryView;
import view.ProgressBarPopWindow;
import view.TableViewer;




public class ProductsTableController {

	
	Stage st;
	DbHelper dbh;
	TableView<Products> tvp;
	LoginView lv;
	view.TableViewer tv;
	
	
	final Button categoriesButton = new Button();
	final Button productsButton = new Button(); 
	final Button subCatButton = new Button();
	final Button search = new Button();
	final Button delete = new Button();
	final Button addSubCategory = new Button();
	final Button addCategory = new Button();
	final Button sortProducts = new Button();
	final Button exportToXLS = new Button();
	Button newProd = new Button();
	
	ObservableList<String> subCatList = FXCollections.observableArrayList();;
	ObservableList<String> suplList = FXCollections.observableArrayList();;
	ObservableList<String> catList = FXCollections.observableArrayList();;
	
	ChoiceBox<String> supChoice  ;
	ChoiceBox<String> catChoice ;
	ChoiceBox<String> subCatChoice;
	
	final Vector<VectorPairs> catVect;
	final Vector<VectorPairs> supVect;
	final Vector<VectorPairs> subVect; 
	
	final TextField txt;
	final control.MenuBuilder mb;
	MenuBar mbar ;
	final TableBuilder tb;
	
	
	ObservableList<Products> data ;
	
	public ProductsTableController(Stage primaryStage, DbHelper dbh,LoginView lv){
		this.dbh = dbh;
		st = primaryStage;
		this.lv = lv;
		
		catVect = dbh.getCategoriesIntoVector();
		supVect = dbh.getSuppliersIntoVector();
		subVect = dbh.getSubCategoriesIntoVector(); 
		
		buidChoiceBoxLists(catVect, 
				supVect, subVect, null, null);
		
		txt = new TextField();
		mb = new control.MenuBuilder(st,dbh,lv,catChoice);
		mbar = mb.getMenu();
		tb = new TableBuilder(dbh);
	}
	
	
	
	
	
	
	public void buildAndDisplayProductsTable(boolean isFirstLogin){
		
		
		newProd.setOnAction(e1 -> {
			NewProductCotroller pc = new  NewProductCotroller(dbh);
			
			ObservableList<String> nCatList = buildStartingList(catVect);
			ObservableList<String> nSuplList = 	buildStartingList(supVect);
			ObservableList<String> nSubCatList = buildStartingList(subVect);
				
			ChoiceBox<String> nSupChoice = new ChoiceBox<String>(nSuplList) ;
			ChoiceBox<String> nCatChoice = new ChoiceBox<String>(nCatList) ;
			ChoiceBox<String> nSubCatChoice = new ChoiceBox<String>(nSubCatList) ;
			
			pc.pop(nCatChoice, nSubCatChoice, nSupChoice);
		});//end of newProd.setOnAction
		
		
		
		search.setOnAction(event ->{
			String whereClause = "CAT_NAME LIKE '%"+txt.getText()+"%' OR SUB_CAT_NAME LIKE '%"+
					txt.getText()+"%' OR INV_CODE LIKE '%"+ txt.getText()+"%' OR EKEMS_CODE LIKE '%"
					+txt.getText()+"%' OR DESCR LIKE '%" +txt.getText()+"%' OR PRICE LIKE '%"
					+txt.getText()+"%' OR GRAMS LIKE '%" +txt.getText()+"%' OR VARIATION LIKE '%"
					+txt.getText()+"%' OR PERCENTAGE LIKE '%" +txt.getText()+"%' OR PRICE_PER_UNIT LIKE '%"
					+txt.getText()+"%' OR NAME LIKE '%" +txt.getText()+"%'"
					;
			
			buildAndDisplayTable(catVect, supVect, subVect, supChoice, 
					catChoice, subCatChoice, subCatButton, productsButton, 
					categoriesButton, st, mbar, tb, whereClause,
					search,txt,delete,false,addCategory,addSubCategory,sortProducts,
					newProd,exportToXLS);
		});//end of search.setOnAction(
		
		supChoiceAddListener(supChoice);
		subCatChoiceAddListener(subCatChoice);
		catChoiceAddListener(catChoice);
		
		
		
		
		
				
		productsButton.setOnAction(e3 -> {
			ProductsTableController ptc = new ProductsTableController(st, dbh, lv);
			ptc.buildAndDisplayProductsTable(false);
		});//end of productsButton.setOnAction
				
				
		subCatButton.setOnAction(e2->{
					TableView<SubCategories> sc = tb.buildSubCategoriesTable();
					
					tv = new TableViewer(st, sc, mbar,
							null,"",categoriesButton,"Κατηγορίες", 
							subCatButton, "Υποκατ/ίες",productsButton,"   Προιόντα",
							supChoice, catChoice, subCatChoice,search,txt,
							"Υποκατηγορίες",delete,addCategory,addSubCategory,sortProducts,
							newProd,exportToXLS);
						tv.displayView();
					
					
					delete.setOnAction(event3->{
						SubCategories sybCat = sc.getSelectionModel().getSelectedItem();
						//tbv.getSelectionModel().
						int key = sybCat.getKey();
						
						sc.getItems().remove(sybCat);
						dbh.deleteRow("SUB_CATEGORIES", key);
						//System.out.println(prod.getEkemsCode());
					});
					
					
		});//end of subcat.setOnAction
				
				
				
		categoriesButton.setOnAction(e1->{
				TableView<Categories>  ct  = tb.buildCategoriesTable();
					
				delete.setOnAction(event3->{
					Categories cat = ct.getSelectionModel().getSelectedItem();
					//tbv.getSelectionModel().
					int key = cat.getKey();
					
					ct.getItems().remove(cat);
					dbh.deleteRow("CATEGORIES", key);
					//System.out.println(prod.getEkemsCode());
				});
				
					tv = new TableViewer(st, ct, mbar,
							null,"",categoriesButton,"Κατηγορίες", 
							subCatButton, "Υποκατ/ίες",productsButton,"   Προιόντα",
							supChoice, catChoice, subCatChoice,search,txt,"Κατηγορίες",
							delete,addCategory,addSubCategory,
							sortProducts,newProd,exportToXLS);
						tv.displayView();
		});//end of categoriesButton.setOnAction
				
		delete.setOnAction(event3->{
			Products prod = tvp.getSelectionModel().getSelectedItem();
			//tbv.getSelectionModel().
			int key = prod.getKey();
			
			tvp.getItems().remove(prod);
			dbh.deleteRow("PRODUCTS", key);
			//System.out.println(prod.getEkemsCode());
			
		});//end of delete.setOnAction

		
		addCategory.setOnAction(event ->{
			final TextField catNameText = new TextField();
			final Stage st = new Stage();
			
			Button save = new Button("Αποθήκευση");
			save.setId("save");
			save.setOnAction(e1->{
				String catName = catNameText.getText();
				
				if(!catName.isEmpty()){
					int exists = dbh.rowCount(catName, "CAT_NAME", "CATEGORIES");
					if(exists ==0){
						dbh.execAdd("INSERT INTO CATEGORIES (CAT_NAME) VALUES ('" 
										+ catName +"')");
					}//end if the category does not exist
				}//end if there is an input in the textfield
				
				st.close();
			});
			
			Button cancel = new Button("Ακύρωση");
			cancel.setId("back");
			cancel.setOnAction(e1->{
				st.close();
			});
			
			new NewCategoryPopUP(st,catNameText, save,cancel);
		});//end of addCategory
		
		
		
		addSubCategory.setOnAction(event->{
			Stage stage = new Stage();
			
			final TextField subCatNameText = new TextField();
			final ChoiceBox<String> catChoice1 = new ChoiceBox<String>(catList) ;
			Button save = new Button("Αποθήκευση");
			save.setId("save");
			
			save.setOnAction(ev ->{
				String category = catChoice1.getSelectionModel().getSelectedItem();
				if(!category.isEmpty() && !subCatNameText.getText().isEmpty()){
					int exists = dbh.rowCount(subCatNameText.getText(), 
							"SUB_CAT_NAME", "SUB_CATEGORIES");
					int catId = (int) dbh.getValueFromCol("CATEGORIES", "CAT_NAME", category, 
							"ID");
					
					if(exists ==0){
						dbh.execAdd("INSERT INTO SUB_CATEGORIES (SUB_CAT_NAME, CAT_ID) "
								+ "VALUES ('" + subCatNameText.getText() +"', '" + catId+" ')");
					}//end if the category does not exist
					
					stage.close();
				}//end if there is an input in the textfield
				
			});//end of save
			
			Button cancel = new Button("Ακύρωση");
			cancel.setId("back");
			cancel.setOnAction(e1 ->{
				stage.close();
			});
			new NewSubCategoryView(stage, subCatNameText, catChoice1, save, cancel);
		});//end of addSubCategory
		
		
		exportToXLS.setOnAction(ev2 ->{
			 XlsHelper xlsh  = new XlsHelper(dbh);
			 xlsh.writeObsListToXLS(data, "aaaa");
		});
		
		
		sortProducts.setOnAction(event ->{
			
			
			ProgressBar pbar = new ProgressBar();	
			BestPriceService bps = new BestPriceService(tvp.getItems(), tv);
			final Stage stage = new Stage();
			
			bps.setOnSucceeded(ev1 ->{
				TableView<Products> sortedTableView = tb.buildSortedProductsTable(catVect, 
				    				supVect,subVect, bps.getSortedData()); 
				
				data = sortedTableView.getItems();
				
				tv = new TableViewer(st, sortedTableView, mbar,
						null,"",categoriesButton,"Κατηγορίες",
				    	subCatButton,"Υποκατ/ίες",
				    	productsButton,"    Προιόντα", 
				    	supChoice, catChoice, subCatChoice,search,txt,
				    	"Τιμοκατάλογος Μονάδων Ε.Κ.Ε.Μ.Σ.",
				    	delete, addCategory,addSubCategory,
				    	sortProducts,newProd,exportToXLS);
				    	tv.displayView();
				    			
				    	stage.close();
				    	//System.out.println("did is succeeeddddd????");
			});//end of on succeed
			
			bps.start();
			pbar.progressProperty().bind(bps.progressProperty());
			
        	
        	
			
        	
			
			final	Task<Void> showTask = new Task<Void>() {
			    @Override
			    protected Void call() throws Exception {
			        Platform.runLater(new Runnable() {
			            public void run() {
			            	ProgressBarPopWindow pbpw = 
			            			new ProgressBarPopWindow(pbar,"",stage);
			            	pbpw.pop();
			    		}//end of run
			           });//end of runLater
			            return null;
			       }//end of call
			    };//end of task
			    
			   
			    new Thread(showTask).start();
			

	});//end of sortProducts
		
		
		
		
		buildAndDisplayTable(catVect, supVect, subVect, supChoice, 
				catChoice, subCatChoice, subCatButton, productsButton, 
				categoriesButton, st, mbar, tb, null,
				search,txt,delete,isFirstLogin,
				addCategory,addSubCategory,sortProducts,newProd,exportToXLS);
		
		
	}//end of buildAndDisplayProductsTable
	
	
	
	
	
	
	
	
	/**
	 * This method populates the suplList, subCatList, and catList Observable list
	 * parameters with the elements of the corresponding Vectors (catVect, supVect, subVect)
	 * @param suplList
	 * @param subCatList
	 * @param catList
	 * @param catVect
	 * @param supVect
	 * @param subVect
	 */
	private void buidChoiceBoxLists(Vector<VectorPairs> catVect, 
			Vector<VectorPairs> supVect, Vector<VectorPairs> subVect, 
			String catSelection, String subCatSelection){
		
						
			this.catList = buildStartingList(catVect);
			this.suplList = 	buildStartingList(supVect);
			this.subCatList = buildStartingList(subVect);
				
			supChoice = new ChoiceBox<String>(suplList) ;
			catChoice = new ChoiceBox<String>(catList) ;
			subCatChoice = new ChoiceBox<String>(subCatList) ;
			
			//mb.updateCatChoice(catChoice);
				
	}//end of buidChoiceBoxLists
	
	
	private ObservableList<String>  buildStartingList( 
			Vector<VectorPairs> vect){
		
		ObservableList<String> list = FXCollections.observableArrayList();
		
		
		list.add("");
		for(VectorPairs v:vect){
			list.add(v.getName());
		}//end of loop
		
		return list;
   }
	
	
	/**
	 * 
	 * @param category
	 * @param subCat
	 * @return an observablelist of supplier names which have products of the given
	 * 			category and subcategory
	 */
	private ObservableList<String> updateSupList(String category, String subCat){
		
		ObservableList<String> newSuplliers = FXCollections.observableArrayList();
		newSuplliers.add("");
		
			int suplKey = -1;
			String where="";
			
			int catId= -1, subId = -1;
			
			for(VectorPairs cat: catVect){
				if(cat.getName().equals(category)) catId = cat.getKey();
			}
			
			
			for(VectorPairs sub: subVect){
				if(sub.getName().equals(subCat)) subId = sub.getKey();
			}
			
			for(VectorPairs sp: supVect){
				suplKey = sp.getKey();
				
				if(catId != -1){
					if(subId != -1){
						where = "SUP_ID ='"+suplKey+"' AND CAT_ID ='"+catId+"' AND "
								+ "SUB_CAT_ID ='"+subId+"'";
					}else{
						where = "SUP_ID ='"+suplKey+"' AND CAT_ID ='"+catId+"'";
					}
						
				}else{ //if cat is empty
					if(subId != -1){
						where = "SUP_ID ='"+suplKey+"' AND SUB_CAT_ID ='"+subId+"'";
					}
				}//end of else
				
				if(dbh.rowCountWithWhere(where, "PRODUCTS") > 0){
					newSuplliers.add(sp.getName());
					//System.out.println(sp.getName() + " "+suplKey);
				}
			}//end of for loop
		return newSuplliers;
	}//end of updateSupList
	
	
	/**
	 * 
	 * @param category
	 * @param subCat
	 * @return an observable list of subCategory names which have products of the given
	 * 			category 
	 */
	private ObservableList<String> updateSubCatList(String category){
		
		ObservableList<String> newSubCatList = FXCollections.observableArrayList();
		newSubCatList.add("");
		
			String where="";
			int catId= -1, subId = -1;
			
			for(VectorPairs cat: catVect){
				if(cat.getName().equals(category)){ 
					catId = cat.getKey(); 
					break;
				}//end if cat name equals the given category 
			}//end of for loop
			
			for(VectorPairs sb: subVect){
				subId = sb.getKey();
				
				if(catId != -1){
					where = "ID ='"+subId+"' AND CAT_ID ='"+catId+"'";
				}//end of if
				
				if(dbh.rowCountWithWhere(where, "SUB_CATEGORIES") > 0){
					newSubCatList.add(sb.getName());
					System.out.println(sb.getName() + " "+subId);
				}
			}//end of for loop
		return newSubCatList;
	}//end of updateSupList
	
	
	
	
	
	/**
	 * adds the appropriate listener to the suppliers choiceBox
	 */
	private void supChoiceAddListener(ChoiceBox<String> sup){
		
		sup.getSelectionModel().selectedIndexProperty().addListener(
				(ObservableValue<? extends Number> ov, Number old_value, Number new_value)
				->{
					String newSupl = suplList.get((int)new_value);
					
					String whereClause= ""; 
					if(!newSupl.isEmpty()){ whereClause= " NAME LIKE '%"+newSupl+"%'"; 
					}else{ 	whereClause= " NAME LIKE '%'"; 	}
					
					if(catChoice.getValue()!=null && !catChoice.getValue().isEmpty() ){
						whereClause = whereClause + "AND CAT_NAME  ='" + catChoice.getValue() +"'";
					}
					if(subCatChoice.getValue()!=null && !subCatChoice.getValue().isEmpty() ){
						whereClause = whereClause + "AND SUB_CAT_NAME  ='" + subCatChoice.getValue() +"'";
					}
					
					buildAndDisplayTable(catVect, supVect, subVect, sup, 
							catChoice, subCatChoice, subCatButton, productsButton, 
							categoriesButton, st, mbar, tb, whereClause,
							search,txt,delete,false,addCategory,
							addSubCategory,sortProducts,newProd,exportToXLS);
		});
		
	}//end of supChoiceAddListener
	
	
	
	private void  catChoiceAddListener(ChoiceBox<String> cat){
		
		cat.getSelectionModel().selectedIndexProperty().addListener((ObservableValue<? extends Number> ov, Number old_value, Number new_value)
				->{
					String newCat = catList.get((int)new_value);
					String whereClause ="";
					
					if(!newCat.isEmpty()){
						whereClause= " CAT_NAME ='"+newCat+"'"; 
					}else{
						whereClause= " CAT_NAME LIKE '%'"; 
					}
					if(supChoice.getValue()!=null && !supChoice.getValue().isEmpty() ){
						whereClause = whereClause + "AND NAME  ='" + supChoice.getValue() +"'";
					}
					if(subCatChoice.getValue()!=null && !subCatChoice.getValue().isEmpty() ){
						whereClause = whereClause + "AND SUB_CAT_NAME  ='" + subCatChoice.getValue() +"'";
					}
					
					//TODO
					ObservableList<String> newList = FXCollections.observableArrayList();
					ObservableList<String> newSubCatList = FXCollections.observableArrayList();
					if(!newCat.isEmpty()){
						String subCatName = subCatChoice.getValue();
						newList = updateSupList(newCat,subCatName);
						newSubCatList = updateSubCatList(newCat);
						suplList = newList;
						supChoice.setItems(newList);
						
						subCatList = newSubCatList;
						subCatChoice.setItems(newSubCatList);
					
					}else{
						suplList.removeAll();
						subCatList.removeAll();
						
						suplList = buildStartingList(supVect);
						subCatList = buildStartingList(subVect);
						
						subCatChoice = new ChoiceBox<String>();
						subCatChoice.setItems(subCatList);
						
						supChoice = new ChoiceBox<String>();
						supChoice.setItems(suplList);
						
						subCatChoiceAddListener(subCatChoice);
						supChoiceAddListener(supChoice);
						//supChoice.getSelectionModel().clearSelection();
						//supChoice.setValue(null);
						//subCatChoice.getSelectionModel().clearSelection();
						//subCatChoice.setValue(null);
					}
					
					
					
					System.out.println("cat where " + whereClause);
					
					buildAndDisplayTable(catVect, supVect, subVect, supChoice, 
							cat, subCatChoice, subCatButton, productsButton, 
							categoriesButton, st, mbar, tb, whereClause,search,
							txt,delete,false,addCategory,addSubCategory,
							sortProducts,newProd,exportToXLS);
		});
				
		
		
	}//end of catChoiceAddListener
	 
	
	
	
	private void subCatChoiceAddListener(ChoiceBox<String> sub){
	
		sub.getSelectionModel().selectedIndexProperty().addListener((ObservableValue<? extends Number> ov, Number old_value, Number new_value)
				->{
					String newSupl = subCatList.get((int)new_value);
					String whereClause ="";
					
					if(!newSupl.isEmpty()){
						whereClause= " SUB_CAT_NAME ='"+newSupl+"'"; 
					}else{
						whereClause= " SUB_CAT_NAME LIKE '%'"; 
					}
					
					
					if(supChoice.getValue()!=null && !supChoice.getValue().isEmpty() ){
						whereClause = whereClause + "AND NAME  ='" + supChoice.getValue() +"'";
					}
					if(catChoice.getValue()!=null && !catChoice.getValue().isEmpty() ){
						whereClause = whereClause + "AND CAT_NAME  ='" + catChoice.getValue() +"'";
					}
					
					System.out.println(whereClause);
					
					buildAndDisplayTable(catVect, supVect, subVect, supChoice, 
							catChoice, sub, subCatButton, productsButton, 
							categoriesButton, st, mbar, tb, whereClause,
							search,txt,delete,false,addCategory,addSubCategory,
							sortProducts,newProd,exportToXLS);
				});
	}//end of subCatChoiceAddListener
	
	
	
	/**
	 * This method is used to generate the data and then display
	 * a Products object tableView with its buttons
	 * @param catVect
	 * @param supVect
	 * @param subVect
	 * @param supChoice
	 * @param catChoice
	 * @param subCatChoice
	 * @param subCatButton
	 * @param productsButton
	 * @param categoriesButton
	 * @param primaryStage
	 * @param mbar
	 * @param tb
	 * @param whereClause
	 * @param search
	 * @param txt
	 * @return the generated tableView
	 */
	private TableView<Products> buildAndDisplayTable(Vector<VectorPairs> catVect, 
			Vector<VectorPairs> supVect,
			Vector<VectorPairs> subVect, ChoiceBox<String> supChoice, 
			ChoiceBox<String> catChoice,
			ChoiceBox<String> subCatChoice, Button subCatButton, Button productsButton,
			Button categoriesButton, Stage primaryStage, MenuBar mbar, 
			TableBuilder tb, String whereClause, Button search, 
			TextField txt, Button delete, boolean isFirstLogin, Button addCategory,
			Button addSubCategory, Button sortProducts, Button newProd, Button export){
		

		this.supChoice = supChoice;
		this.catChoice = catChoice;
		mb.updateCatChoice(catChoice);
		
		this.subCatChoice = subCatChoice;
		
	 	final TableServiceController service = new TableServiceController(tb, 
				catChoice, supChoice, subCatChoice, subCatButton, productsButton,
				categoriesButton, primaryStage, mbar, 
				whereClause, search, txt,delete, catVect,  supVect, subVect, addCategory
				,addSubCategory, sortProducts);
	 			
	 	service.start();
    	
    	
	 	
	 	
	 	service.setOnSucceeded(event->{
    		tvp = service.getTableView();
    		data = tvp.getItems();
    		
    		 tv = new TableViewer(primaryStage, tvp, mbar,
					null,"",categoriesButton,"Κατηγορίες",
					subCatButton,"Υποκατ/ίες",
					productsButton,"Προιόντα", 
					supChoice, catChoice, subCatChoice,search,txt,
					"Τιμοκατάλογος Μονάδων Ε.Κ.Ε.Μ.Σ.",delete, addCategory,
					addSubCategory,sortProducts,newProd,export);
					
			 tv.displayView();
	 	});
    	
    	ProgressIndicator progressIndicator = new ProgressIndicator();
    	progressIndicator.visibleProperty().bind(service.runningProperty());
		
    	
    	Task<Void> showTask = new Task<Void>() {
		    @Override
		    protected Void call() throws Exception {
		        Platform.runLater(new Runnable() {
		            public void run() {
		           			//new ProgressView(primaryStage, progressIndicator)  ;
		            	if(isFirstLogin)lv.AddProgressView(st, progressIndicator);
		               }//end of run
		           });
		            return null;
		       }
		    };//end of showTask
		
		    new Thread(showTask).start();
		   
		
		return tvp;
	}//end of buildAndDisplayTable
	
	
	
}//end of ProductsTableControllerClass
