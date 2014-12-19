package model;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.MouseEvent;
import javafx.util.Callback;




public class TableBuilder {

	
	DbHelper dbh;
	TableView<?> table;
	
	public TableBuilder(DbHelper dbh){
		this.dbh = dbh;
	}
	
	
	
	
	/**
	 * Builds a TableView corresponding to teh PRODUCTS table of the database
	 * @return a table view for the PRODUCTS
	 */
	@SuppressWarnings("unchecked")
	public TableView<Products> buildProductsTable(Vector<VectorPairs> catV,
			Vector<VectorPairs> supV, 
			Vector<VectorPairs> subV, String where){
		
		
		final ObservableList<Products> data = buildProductsTableData(where);
		
		final TableView<Products> table = new TableView<Products>();
		table.setItems(data);
		
		TableColumn<Products, Integer> keyCol = new TableColumn<>("Α/Α");
		TableColumn<Products, String> catCol = new TableColumn<>("Κατηγορία");
		TableColumn<Products, String> subCatCol = new TableColumn<>("Υποκατηγορία");
		TableColumn<Products, Long> invCodeCol = new TableColumn<>("Κωδικός Τιμολογίου");
		TableColumn<Products, Long> ekemsCodeCol = new TableColumn<>("Κωδικός ΕΚΕΜΣ");
		TableColumn<Products, String> descCol = new TableColumn<>("Περιγραφή");
		TableColumn<Products, Float> priceCol = new TableColumn<>("Αναγραφόμενη Τιμή Τιμολογίου");
		TableColumn<Products, Float> gramsCol = new TableColumn<>("Γραμμάρια που Αναφέρεται η τιμή");
		TableColumn<Products, Float> varCol = new TableColumn<>("Μεταβολή στη τιμή");
		TableColumn<Products, Float> varPercCol = new TableColumn<>("Μεταβολή % στη τιμή");
		TableColumn<Products, Float> pricePerKCol = new TableColumn<>("Τιμή ανα Κιλό");
		
		TableColumn<Products, String> suplCol = new TableColumn<>("Προμηθευτής");
		
		addAttributesToProductsColumns(keyCol, catCol, subCatCol, invCodeCol, 
										ekemsCodeCol, descCol, priceCol, gramsCol, varCol, 
										varPercCol, pricePerKCol, suplCol, table,
										catV, supV, subV);
		table.getColumns().addAll(keyCol, catCol, subCatCol, invCodeCol, 
									ekemsCodeCol, descCol, priceCol, gramsCol, pricePerKCol, varCol, 
									varPercCol,  suplCol);
		
		table.setEditable(true);
		return table;
	}//end of buildProductsTable
	
	
	/**
	 * Builds a TableView corresponding to teh PRODUCTS table of the database
	 * @return a table view for the PRODUCTS
	 */
	@SuppressWarnings("unchecked")
	public TableView<Products> buildSortedProductsTable(Vector<VectorPairs> catV,
			Vector<VectorPairs> supV, 
			Vector<VectorPairs> subV, ObservableList<Products> data ){
		
		final TableView<Products> table = new TableView<Products>();
		table.setItems(data);
		
		TableColumn<Products, Integer> keyCol = new TableColumn<>("Α/Α");
		TableColumn<Products, String> catCol = new TableColumn<>("Κατηγορία");
		TableColumn<Products, String> subCatCol = new TableColumn<>("Υποκατηγορία");
		TableColumn<Products, Long> invCodeCol = new TableColumn<>("Κωδικός Τιμολογίου");
		TableColumn<Products, Long> ekemsCodeCol = new TableColumn<>("Κωδικός ΕΚΕΜΣ");
		TableColumn<Products, String> descCol = new TableColumn<>("Περιγραφή");
		TableColumn<Products, Float> priceCol = new TableColumn<>("Αναγραφόμενη Τιμή Τιμολογίου");
		TableColumn<Products, Float> gramsCol = new TableColumn<>("Γραμμάρια που Αναφέρεται η τιμή");
		TableColumn<Products, Float> varCol = new TableColumn<>("Μεταβολή στη τιμή");
		TableColumn<Products, Float> varPercCol = new TableColumn<>("Μεταβολή % στη τιμή");
		TableColumn<Products, Float> pricePerKCol = new TableColumn<>("Τιμή ανα Κιλό");
		
		TableColumn<Products, String> suplCol = new TableColumn<>("Προμηθευτής");
		
		addAttributesToProductsColumns(keyCol, catCol, subCatCol, invCodeCol, 
										ekemsCodeCol, descCol, priceCol, gramsCol, varCol, 
										varPercCol, pricePerKCol, suplCol, table,
										catV, supV, subV);
		table.getColumns().addAll(keyCol, catCol, subCatCol, invCodeCol, 
									ekemsCodeCol, descCol, priceCol, gramsCol, pricePerKCol, varCol, 
									varPercCol,  suplCol);
		
		table.setEditable(true);
		return table;
	}//end of buildProductsTable
	
	
	
	
	
	
	
	/**
	 * returns an observable list of Products object which represents the
	 * products table of the database
	 * @param a where clause of for the SQL querie which will build the table
	 * @return an ObservableList<Products> which represents the
	 * products table of the database
	 */
	public ObservableList<Products> buildProductsTableData(String where){
		
		final ObservableList<Products> data = FXCollections.observableArrayList();;
		
		String q="";
		if(where == null){
			q = "select * from PRODUCTS_V";
		}else{
			q = "SELECT * FROM PRODUCTS_V WHERE "+ where;
		}
		ResultSet rs = dbh.execGet(q);
		
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
		
		//TableDataSorter tds = new TableDataSorter();
		//tds.sortProductsDataBySubCategory(data);
		
		return data;
		
	}//end of buildProductsTableData
	
	
	
	
	/**
	 * Adds the requiered functionality to the TableView columns of the Prodcuts Table
	 * @param keyCol
	 * @param catCol
	 * @param subCatCol
	 * @param invCodeCol
	 * @param ekemsCodeCol
	 * @param descCol
	 * @param priceCol
	 * @param gramsCol
	 * @param varCol
	 * @param varPercCol
	 * @param pricePerKCol
	 * @param suplCol
	 */
	public void addAttributesToProductsColumns(TableColumn<Products, Integer> keyCol,
			TableColumn<Products, String> catCol, TableColumn<Products, String> subCatCol,
			TableColumn<Products, Long> invCodeCol, TableColumn<Products, Long> ekemsCodeCol,
			TableColumn<Products, String> descCol, TableColumn<Products, Float> priceCol,
			TableColumn<Products, Float> gramsCol, TableColumn<Products, Float> varCol,
			TableColumn<Products, Float> varPercCol, TableColumn<Products, Float> pricePerKCol,
			TableColumn<Products, String> suplCol, TableView<Products> table,
			Vector<VectorPairs> catV, Vector<VectorPairs> supV, 
			Vector<VectorPairs> subV
			){
		
		
		
		
		final Vector<VectorPairs> catVect = catV;
		final Vector<VectorPairs> supVect = supV;
		final Vector<VectorPairs> subVect = subV; 

		final ObservableList<String> subCatList = FXCollections.observableArrayList();;
		final ObservableList<String> suplList = FXCollections.observableArrayList();;
		
		for(VectorPairs spl:supVect){
			suplList.add(spl.getName());
		}//end of loop
		
		for(VectorPairs sbp:subVect){
			subCatList.add(sbp.getName());
		}//end of loop
		
		keyCol.setCellValueFactory( new PropertyValueFactory<Products, Integer>("key"));
		keyCol.setEditable(false);
		
		catCol.setCellFactory(TextFieldTableCell.<Products>forTableColumn());
		catCol.setCellValueFactory( new PropertyValueFactory<Products, String>("category"));
		catCol.setEditable(false);
		
		
		subCatCol.setCellValueFactory( new PropertyValueFactory<Products, String>("subCategory"));
		subCatCol.setCellFactory(new  Callback<TableColumn<Products, String>,TableCell<Products, String>>(){
			@Override
	        public TableCell<Products, String> call(final TableColumn<Products, String> param) {

				final ChoiceBox<String> choice = new ChoiceBox<String>(subCatList) ;
				final ClickCounter clicks = new ClickCounter();
				
				final TableCell<Products, String> cell = new TableCell<Products, String>(){
					@Override
					protected void updateItem(final String st, boolean empty){
							
						if(clicks.getClicks() == 0){
							setText(st);
						}else{
							choice.getSelectionModel().select(subCatList.indexOf(st));
							setGraphic(choice);
						}
					}//endOf updateItem
				};
				
			
				cell.addEventFilter(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>(){
					@Override
					public void handle(MouseEvent event) {
						cell.getTableView().getSelectionModel().select(cell.getIndex());
						clicks.setClicks(clicks.getClicks()+1);
						
					}
					
				});
				
				choice.getSelectionModel().selectedIndexProperty().addListener( 
						new ChangeListener<Number>(){
							@Override
							public void changed(
									ObservableValue<? extends Number> observable,
									Number oldValue, Number newValue) {
									int cellRowNum = cell.getIndex();
								if(cellRowNum > 0){
									int cellKey = cell.getTableView().getItems().get(cellRowNum).getKey();
									int selectedRow = table.getSelectionModel().getSelectedIndex();
									
									if(cellRowNum == selectedRow){
										String newSubCat = subCatList.get((int)newValue);
										String newCat ="";
										int newSubId = -1;
										int newCatId = -1;
										
										for(VectorPairs scp: subVect){
											if(scp.getName().equals(newSubCat)){
												newSubId = scp.getKey();
												newCatId = ((SubCatPair)scp).getCatID();
											}//end if 
										}//end for loop
										
										for(VectorPairs cp : catVect){
											if(cp.getKey() == newCatId)
												newCat = cp.getName();
										}
										
										cell.getTableView().getSelectionModel().getSelectedItem().setCategory(newCat);
										
										
										dbh.updateValue(cellKey, "SUB_CAT_ID", "PRODUCTS", newSubId);
										dbh.updateValue(cellKey, "CAT_ID", "PRODUCTS", newCatId);
										
									}//end if cell key is the selected row key
								}//end if cellRow >0
							}//end of changed
						}//end of changeListener
					);//end of choicelistener
				
				
				
				return cell;  //cells.get(cells.size()-1);
			}
		});//end setCellFactory
		subCatCol.setEditable(true);
		
		
		invCodeCol.setCellValueFactory(new PropertyValueFactory<Products, Long>("invCode"));
		invCodeCol.setOnEditCommit(
	            (CellEditEvent<Products, Long> t) -> {
	                int key = ( (Products) t.getTableView().getItems().get(
	                        t.getTablePosition().getRow())
	                        ).getKey();
	                dbh.updateValue(key, "INV_CODE", "PRODUCTS", t.getNewValue());
	        });
		
		ekemsCodeCol.setCellValueFactory(new PropertyValueFactory<Products, Long>("ekemsCode"));
		ekemsCodeCol.setOnEditCommit(
	            (CellEditEvent<Products, Long> t) -> {
	                int key = ( (Products) t.getTableView().getItems().get(
	                        t.getTablePosition().getRow())
	                        ).getKey();
	                dbh.updateValue(key, "EKEMS_CODE", "PRODUCTS", t.getNewValue());
	        });
		
		descCol.setCellFactory(TextFieldTableCell.<Products>forTableColumn());
		descCol.setCellValueFactory( new PropertyValueFactory<Products, String>("description"));
		descCol.setOnEditCommit(
	            (CellEditEvent<Products, String> t) -> {
	                int key = ( (Products) t.getTableView().getItems().get(
	                        t.getTablePosition().getRow())
	                        ).getKey();
	                dbh.updateValue(key, "DESCR", "PRODUCTS", t.getNewValue());
	     });
		
		priceCol.setCellValueFactory(new PropertyValueFactory<Products, Float>("invPrice"));
		priceCol.setCellFactory(new  Callback<TableColumn<Products,Float>,TableCell<Products,Float>>(){
			@Override
			public TableCell<Products,Float> call(TableColumn<Products,Float> p) {
				TableCell<Products,Float> cell = new TableCell<Products,Float>(){
					TextField textField;
					TableCellHelper tch = new TableCellHelper(this, textField, dbh);
						
					@Override
					public void startEdit() {
						super.startEdit();
					    if (textField == null) {
					       	textField = tch.createInvPriceTextField();
					    }
					   	setGraphic(textField);
						setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
						textField.selectAll();
					   	
					}//end of startEdit
					
					@Override	
					public void commitEdit(Float t){
						setText(t.toString());
						setContentDisplay(ContentDisplay.TEXT_ONLY);
						textField = null;
					}
					
					@Override
					public void cancelEdit(){
						setContentDisplay(ContentDisplay.TEXT_ONLY);
						textField = null;
					}
					@Override
					protected void updateItem(Float fl, boolean empty){
						if(fl!=null){
							setText(fl.toString());
						}else{
							setText("");
						}
					}//endOf updateItem
				
				}; //end of cell
					
				cell.setEditable(true);
		        return cell;
	          }//end of call
		});
		priceCol.setEditable(true);
		
		
		gramsCol.setCellValueFactory(new PropertyValueFactory<Products, Float>("gramsForInvPrice"));
		gramsCol.setCellFactory(new  Callback<TableColumn<Products,Float>,TableCell<Products,Float>>(){
			@Override
			public TableCell<Products,Float> call(TableColumn<Products,Float> p) {
					
				TableCell<Products,Float> cell = new TableCell<Products,Float>(){
					
					TextField textField;
					TableCellHelper tch = new TableCellHelper(this, textField, dbh);
						
					@Override
					public void startEdit() {
						super.startEdit();
						
					    if (textField == null) {
					       	textField = tch.createKilosTextField();
					    }
					   	setGraphic(textField);
						setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
						textField.selectAll();
					   	
					}//end of startEdit
					
					@Override	
					public void commitEdit(Float t){
						setText(t.toString());
						setContentDisplay(ContentDisplay.TEXT_ONLY);
						textField = null;
					}
					
					@Override
					public void cancelEdit(){
						super.cancelEdit();
						setContentDisplay(ContentDisplay.TEXT_ONLY);
						textField = null;
					}
					
					@Override
					protected void updateItem(Float fl, boolean empty){
						
						if(getIndex() >0 && getTableRow()!=null){
							Products pr = (Products) getTableRow().getItem();
							if(pr!=null){
								if(pr.getChecked()){
									setId("checked");
								}else{
									setId("notChecked");
								}
							}//end if pr is null
						}//end if cell index is >=0 && getTableRow()!=null
						
						if(fl!=null){
							setText(fl.toString());
						}else{
							setText("");
						}
					}//endOf updateItem
				
				}; //end of cell
					
				cell.setEditable(true);
		        return cell;
	          }//end of call
			});
		gramsCol.setEditable(true);
		
		
		
		varCol.setCellValueFactory(new PropertyValueFactory<Products, Float>("absVar"));
		varCol.setCellFactory(new  Callback<TableColumn<Products,Float>,TableCell<Products,Float>>(){
			@Override
	        public TableCell<Products,Float> call(TableColumn<Products,Float> p) {
				
				TableCell<Products,Float> cell = new TableCell<Products,Float>(){
					@Override
					protected void updateItem(Float fl, boolean empty){
						//setGraphic(fl);
						//setItem(fl);
						super.updateItem(fl, empty);
						if(fl != null){
							setText(fl.toString());
							if(fl > 0){
								this.setId("positive");
							}else{
								if(fl<0) this.setId("negative");
								else{if(fl == 0) this.setId("zero");}
							}
						}else{
							setText("");
						}
					}//endOf updateItem
				};
	               return cell;
            }//end of call
		});
		
		varCol.setOnEditCommit(
	            (CellEditEvent<Products, Float> t) -> {
	                int key = ( (Products) t.getTableView().getItems().get(
	                        t.getTablePosition().getRow())
	                        ).getKey();
	                dbh.updateValue(key, "VARIATION", "PRODUCTS", t.getNewValue());
	     });
		
		
		varPercCol.setCellValueFactory(new PropertyValueFactory<Products, Float>("percentVar"));
		pricePerKCol.setCellValueFactory(new PropertyValueFactory<Products, Float>("pricePerKilo"));
		
		suplCol.setCellFactory(new  Callback<TableColumn<Products, String>,TableCell<Products, String>>(){
			@Override
	        public TableCell<Products, String> call(final TableColumn<Products, String> param) {

				final ChoiceBox<String> choice = new ChoiceBox<String>(suplList) ;
				final ClickCounter clicks = new ClickCounter(); 
				
				
				final TableCell<Products, String> cell = new TableCell<Products, String>(){
					@Override
					protected void updateItem(final String st, boolean empty){
							if(clicks.getClicks() == 0){
								setText(st);
							}else{
								choice.getSelectionModel().select(suplList.indexOf(st));
								setGraphic(choice);
							}
							
						}//endOf updateItem
				};
				
				cell.addEventFilter(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>(){
					@Override
					public void handle(MouseEvent event) {
						cell.getTableView().getSelectionModel().select(cell.getIndex());
						clicks.setClicks(clicks.getClicks()+1);
					}
				});
				
				choice.getSelectionModel().selectedIndexProperty().addListener( 
						new ChangeListener<Number>(){
							@Override
							public void changed(
									ObservableValue<? extends Number> observable,
									Number oldValue, Number newValue) {
								
								int cellRowNum = cell.getIndex();
								
								if(cellRowNum >= 0){
									int cellKey = cell.getTableView().getItems().get(cellRowNum).getKey();
									int selectedRow = table.getSelectionModel().getSelectedIndex();
									
									if(cellRowNum == selectedRow){
										String newSup = suplList.get((int)newValue);
										int newSupId = -1;
										
										for(VectorPairs spl: supVect){
											if(spl.getName().equals(newSup)){
												newSupId = spl.getKey();
											}//end if 
										}//end for loop
										
										dbh.updateValue(cellKey, "SUP_ID", "PRODUCTS", newSupId);
									}//end if cell key is the selected row key
								}//end if cellRow >0
							}//end of changed
						}//end of changeListener
					);//end of choicelistener
				
	              return cell;
            }//end of call
		});
		suplCol.setCellValueFactory( new PropertyValueFactory<Products, String>("supplier"));
		suplCol.setEditable(true);
		
	}//end addAttributes
	
	
	
	
	
	
	
	
	
	
	
	
	/**
	 * Builds a TableView for the SUPPLIERS Table of the Database
	 * @return TableView for the SUPPLIERS Table
	 */
	@SuppressWarnings("unchecked")
	public TableView<Suppliers> buildSuppliersTable(){
		
		final ObservableList<Suppliers> data = FXCollections.observableArrayList();;
		
		String q = "select * from SUPPLIERS";
		ResultSet rs = dbh.execGet(q);
		
		try {
			while(rs.next()){
				int key = rs.getInt("ID");
				String name = rs.getString("NAME");
				Suppliers sup = new Suppliers(name, key);
				
				data.add(sup);
			}//end while
		} catch (SQLException e) {e.printStackTrace();}

		final TableView<Suppliers> table = new TableView<Suppliers>();
		table.setItems(data);
		
		TableColumn<Suppliers, Integer> keyCol = new TableColumn<>("Α/Α");
		TableColumn<Suppliers, String> nameCol = new TableColumn<>("Όνομα");
		
		addAttributesToSupplierColumns(keyCol,nameCol);
		
		table.getColumns().addAll(keyCol,nameCol);
		table.setEditable(true);
		
		return table;
	}//end of buildSuppliersTable
	
	/**
	 * Adds the required functionality to the TableView 
	 * columns of the SUPPLIERS Table
	 * */
	public void addAttributesToSupplierColumns(TableColumn<Suppliers, Integer> keyCol,
			TableColumn<Suppliers, String> nameCol){
		
		keyCol.setCellValueFactory( new PropertyValueFactory<Suppliers, Integer>("key"));
		keyCol.setEditable(false);
		
		nameCol.setCellFactory(TextFieldTableCell.<Suppliers>forTableColumn());
		nameCol.setCellValueFactory( new PropertyValueFactory<Suppliers, String>("name"));
		nameCol.setOnEditCommit(
	            (CellEditEvent<Suppliers, String> t) -> {
	                int key = ( (Suppliers) t.getTableView().getItems().get(
	                        t.getTablePosition().getRow())
	                        ).getKey();
	                dbh.updateValue(key, "NAME", "SUPPLIERS", t.getNewValue());
	        });
		
	}//end addAttributes
	
	
	/**
	 * Builds a TableView for the CATEGORIES Table of the Database
	 * @return TableView for the CATEGORIES Table
	 */
	@SuppressWarnings("unchecked")
	public TableView<Categories> buildCategoriesTable(){
		
		final ObservableList<Categories> data = FXCollections.observableArrayList();;
		
		String q = "SELECT * FROM CATEGORIES;";
		ResultSet rs = dbh.execGet(q);
		
		try {
			while(rs.next()){
				int key = rs.getInt("ID");
				String name = rs.getString("CAT_NAME");
				boolean act = rs.getBoolean("ACTIVE");
				Categories cat = new Categories(name, key,act);
				
				data.add(cat);
			}//end while
		} catch (SQLException e) {e.printStackTrace();}

		final TableView<Categories> table = new TableView<Categories>();
		table.setItems(data);
		
		TableColumn<Categories, Integer> keyCol = new TableColumn<>("Α/Α");
		TableColumn<Categories, String> nameCol = new TableColumn<>("Όνομα");
		TableColumn<Categories, Boolean> actCol = new TableColumn<>("ΕΝΕΡΓΗ");
		
		addAttributesToCategoriesColumns(keyCol, nameCol,actCol, table);
		
		table.getColumns().addAll(keyCol,nameCol,actCol);
		table.setEditable(true);
		
		return table;
	}//end of buildSuppliersTable
	
	
	
	/**
	 * Adds the required functionality to the TableView 
	 * columns of the SUPPLIERS Table
	 * */
	public void addAttributesToCategoriesColumns(TableColumn<Categories, Integer> keyCol,
			TableColumn<Categories, String> nameCol,
			TableColumn<Categories, Boolean> actCol, TableView<Categories> tab){
		
		keyCol.setCellValueFactory( new PropertyValueFactory<Categories, Integer>("key"));
		keyCol.setEditable(false);
		
		nameCol.setCellFactory(TextFieldTableCell.<Categories>forTableColumn());
		nameCol.setCellValueFactory( new PropertyValueFactory<Categories, String>("name"));
		nameCol.setOnEditCommit(
	            (CellEditEvent<Categories, String> t) -> {
	                int key = ( (Categories) t.getTableView().getItems().get(
	                        t.getTablePosition().getRow())
	                        ).getKey();
	                dbh.updateValue(key, "NAME", "CATEGORIES", t.getNewValue());
	                
	        });
		
		actCol.setCellValueFactory(new PropertyValueFactory<Categories, Boolean>("active"));
		
		
		
		//actCol.setCellFactory(CheckBoxTableCell.<Categories>forTableColumn(actCol));
		//actCol.setSelectedStateCallback
		
		actCol.setCellFactory(new Callback<TableColumn<Categories, Boolean>, TableCell<Categories, Boolean>>() {

			public TableCell<Categories, Boolean> call(TableColumn<Categories, Boolean> p) {
				CheckBoxTableCell<Categories, Boolean> cell = 
						new CheckBoxTableCell<Categories, Boolean>();
				
				cell.setSelectedStateCallback(new Callback<Integer, ObservableValue<Boolean>>() {
				    @Override
				    public ObservableValue<Boolean> call(Integer index) {
				       
				        boolean  checkBoxVal = tab.getItems().get(index).activeProperty().get();
				        int key = tab.getItems().get(index).getKey();
				        if(checkBoxVal == false){
				        	dbh.updateValue(key, "ACTIVE", "CATEGORIES", 0);
				        }else{
				        	dbh.updateValue(key, "ACTIVE", "CATEGORIES", 1);
				        }
				        
				        return tab.getItems().get(index).activeProperty();
				    	//return true;
				    }
				    
				});
				
                return cell;
 
            }
 
        });
		
		actCol.setOnEditCommit(
	            (CellEditEvent<Categories, Boolean> t) -> {
	                int key = ( (Categories) t.getTableView().getItems().get(
	                        t.getTablePosition().getRow())
	                        ).getKey();
	                System.out.println("aaa "+t.getNewValue());
	                dbh.updateValue(key, "ACTIVE", "CATEGORIES", t.getNewValue());
	        });
		actCol.setEditable(true);
		
		
	}//end addAttributes
	
	
	
	
	/**
	 * Builds a TableView for the SUBCATEGORIES Table of the Database
	 * @return TableView for the SUBCATEGORIES Table
	 */
	@SuppressWarnings("unchecked")
	public TableView<SubCategories> buildSubCategoriesTable(){
		
		final ObservableList<SubCategories> data = FXCollections.observableArrayList();;
		
		String q = "SELECT * FROM SUBCAT_V;";
		ResultSet rs = dbh.execGet(q);
		
		try {
			while(rs.next()){
				int key = rs.getInt("ID");
				String name = rs.getString("SUB_CAT_NAME");
				String cat = rs.getString("CAT_NAME");
				
				SubCategories sub = new SubCategories(name, key, cat);
				data.add(sub);
			}//end while
		} catch (SQLException e) {e.printStackTrace();}

		final TableView<SubCategories> table = new TableView<SubCategories>();
		table.setItems(data);
		
		TableColumn<SubCategories, Integer> keyCol = new TableColumn<>("Α/Α");
		TableColumn<SubCategories, String> nameCol = new TableColumn<>("Υποκατηγορία");
		TableColumn<SubCategories, String> subCatCol = new TableColumn<>("Κατηγορία");
		
		addAttributesToSubCategoriesColumns(keyCol, nameCol, subCatCol);
		
		table.getColumns().addAll(keyCol,nameCol,subCatCol);
		table.setEditable(true);
		
		return table;
	}//end of buildSuppliersTable
	
	
	/**
	 * Adds the required functionality to the TableView 
	 * columns of the SubCategories Table
	 * */
	public void addAttributesToSubCategoriesColumns(TableColumn<SubCategories, Integer> keyCol,
			TableColumn<SubCategories, String> nameCol, 
			TableColumn<SubCategories, String> subCatCol){
		
		keyCol.setCellValueFactory( new PropertyValueFactory<SubCategories, Integer>("key"));
		keyCol.setEditable(false);
		
		nameCol.setCellFactory(TextFieldTableCell.<SubCategories>forTableColumn());
		nameCol.setCellValueFactory( new PropertyValueFactory<SubCategories, String>("name"));
		nameCol.setOnEditCommit(
	            (CellEditEvent<SubCategories, String> t) -> {
	                int key = ( (SubCategories) t.getTableView().getItems().get(
	                        t.getTablePosition().getRow())
	                        ).getKey();
	                dbh.updateValue(key, "NAME", "CATEGORIES", t.getNewValue());
	        });
		
		subCatCol.setCellFactory(TextFieldTableCell.<SubCategories>forTableColumn());
		subCatCol.setCellValueFactory( new PropertyValueFactory<SubCategories, String>("name"));
	
	}//end addAttributes
	
	
	
	
	
	
	
}//end of TableBuilder
