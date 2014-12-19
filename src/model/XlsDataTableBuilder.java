package model;

import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;

public class XlsDataTableBuilder {

	TableView<?> table;
	
	public XlsDataTableBuilder(){	}
	
	
	
	/**
	 * Builds a TableView corresponding to the PRODUCTS table of the database
	 * @return a table view for the PRODUCTS
	 */
	@SuppressWarnings("unchecked")
	public TableView<ProductsParsedFromFile> buildProductsTable(ObservableList<ProductsParsedFromFile> data){
		
		final TableView<ProductsParsedFromFile> table = new TableView<ProductsParsedFromFile>();
		table.setItems(data);
		
		//TableColumn<Products, Integer> keyCol = new TableColumn<>("Α/Α");
		TableColumn<ProductsParsedFromFile, String> catCol = new TableColumn<>("Κατηγορία");
		TableColumn<ProductsParsedFromFile, String> subCatCol = new TableColumn<>("Υποκατηγορία");
		TableColumn<ProductsParsedFromFile, Long> invCodeCol = new TableColumn<>("Κωδικός Τιμολογίου");
		TableColumn<ProductsParsedFromFile, Long> ekemsCodeCol = new TableColumn<>("Κωδικός ΕΚΕΜΣ");
		TableColumn<ProductsParsedFromFile, String> descCol = new TableColumn<>("Περιγραφή");
		TableColumn<ProductsParsedFromFile, Float> priceCol = new TableColumn<>("Αναγραφόμενη Τιμή Τιμολογίου");
		TableColumn<ProductsParsedFromFile, Float> gramsCol = new TableColumn<>("Γραμμάρια που Αναφέρεται η τιμή");
		TableColumn<ProductsParsedFromFile, Float> pricePerKCol = new TableColumn<>("Τιμή ανα Κιλό");
		TableColumn<ProductsParsedFromFile, String> suplCol = new TableColumn<>("Προμηθευτής");
		
		addAttributesToProductsColumns(catCol, subCatCol, invCodeCol, 
										ekemsCodeCol, descCol, priceCol, gramsCol,  
										pricePerKCol, suplCol, table);
		
		table.getColumns().addAll( catCol, subCatCol, invCodeCol, 
									ekemsCodeCol, descCol, priceCol, gramsCol, pricePerKCol, suplCol);
		
		table.setEditable(true);
		return table;
	}//end of buildProductsTable
	
	
	
	public void addAttributesToProductsColumns(
			TableColumn<ProductsParsedFromFile, String> catCol, TableColumn<ProductsParsedFromFile, String> subCatCol,
			TableColumn<ProductsParsedFromFile, Long> invCodeCol, TableColumn<ProductsParsedFromFile, Long> ekemsCodeCol,
			TableColumn<ProductsParsedFromFile, String> descCol, TableColumn<ProductsParsedFromFile, Float> priceCol,
			TableColumn<ProductsParsedFromFile, Float> gramsCol, TableColumn<ProductsParsedFromFile, Float> pricePerKCol,
			TableColumn<ProductsParsedFromFile, String> suplCol, TableView<ProductsParsedFromFile> table){
		
		catCol.setCellFactory(TextFieldTableCell.<ProductsParsedFromFile>forTableColumn());
		catCol.setCellValueFactory( new PropertyValueFactory<ProductsParsedFromFile, String>("category"));
		catCol.setEditable(false);
		
		subCatCol.setCellFactory(TextFieldTableCell.<ProductsParsedFromFile>forTableColumn());
		subCatCol.setCellValueFactory( new PropertyValueFactory<ProductsParsedFromFile, String>("subCategory"));
		subCatCol.setEditable(false);
		
		
		descCol.setCellFactory(TextFieldTableCell.<ProductsParsedFromFile>forTableColumn());
		descCol.setCellValueFactory( new PropertyValueFactory<ProductsParsedFromFile, String>("description"));
		
		
		invCodeCol.setCellValueFactory( new PropertyValueFactory<ProductsParsedFromFile, Long>("invCode"));
		invCodeCol.setEditable(false);
		
		ekemsCodeCol.setCellValueFactory( new PropertyValueFactory<ProductsParsedFromFile, Long>("ekemsCode"));
		ekemsCodeCol.setEditable(false);
		
		priceCol.setCellValueFactory( new PropertyValueFactory<ProductsParsedFromFile, Float>("invPrice"));
		priceCol.setEditable(false);
		
		gramsCol.setCellValueFactory( new PropertyValueFactory<ProductsParsedFromFile, Float>("gramsForInvPrice"));
		gramsCol.setEditable(false);
		
		pricePerKCol.setCellValueFactory( new PropertyValueFactory<ProductsParsedFromFile, Float>("pricePerKilo"));
		pricePerKCol.setEditable(false);
		
		suplCol.setCellFactory(TextFieldTableCell.<ProductsParsedFromFile>forTableColumn());
		suplCol.setCellValueFactory( new PropertyValueFactory<ProductsParsedFromFile, String>("supplier"));
		
		
		
	}//end of addAttributesToProductsColumns
	
}
