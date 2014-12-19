package control;

import view.NewProductPoP;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.DbHelper;

public class NewProductCotroller {

	DbHelper dbh;
	
	public NewProductCotroller(DbHelper dbh){
		this.dbh = dbh;
	}
	
	
	
	public void pop(ChoiceBox<String> catChoice,
			ChoiceBox<String> subCatChoice, ChoiceBox<String> supChoice){
		
		Stage st = new Stage();
		
		Button save = new Button("Αποθήκευση");
		Button cancel = new Button("Επιστροφή");
		
		
		TextField invCode = new TextField();
		TextField ekemsCode = new TextField();
		
		save.setOnAction(event->{
			String catName = catChoice.getSelectionModel().getSelectedItem();
			String subCatName = subCatChoice.getSelectionModel().getSelectedItem();
			String supName = supChoice.getSelectionModel().getSelectedItem();
			
			int catId = (int) dbh.getValueFromCol("CATEGORIES", "CAT_NAME", catName, "ID");
			int subCatId = (int) dbh.getValueFromCol("SUB_CATEGORIES", "SUB_CAT_NAME", subCatName, "ID");
			int supId = (int) dbh.getValueFromCol("SUPPLIERS", "NAME", supName, "ID");
		
			String ekemsCodeVal = ekemsCode.getText();
			String invCodeVal = invCode.getText();
			
			String the_q = "INSERT INTO PRODUCTS "
					+ "(CAT_ID, SUB_CAT_ID, INV_CODE, EKEMS_CODE, DESCR," 
					+ "PRICE, GRAMS, SUP_ID, VARIATION, PERCENTAGE, PRICE_PER_UNIT,"
					+ "PACKAGE, CHECKED )"
					+ " VALUES ('"
					+ catId+"' , '" 
					+ subCatId  +"', '"
					+ invCodeVal+ " ', '"
					+ ekemsCodeVal+ "', '"
					+ null+"', '"
					+ 0+"', '"
					+ 0+ "' , '"+  
					+ supId+ "' , "
					+ "null"+ " , " 
					+ "null"+ " , '" 
					+ 0+ "' , "
					+"null, '0' );";
			
			dbh.execAdd(the_q);
			
			
			st.close();
			
		});
		
		
		cancel.setOnAction(event->{st.close();});
		
	
		
		new NewProductPoP(st, ekemsCode, invCode, catChoice, subCatChoice, supChoice, 
				save, cancel);
		
		
	}//end of pop
	
	
	
	
	
}//end of NewProductCotroller
