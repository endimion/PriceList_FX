package control;

import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.DbHelper;
import view.LoginView;


public class LoginController{

	Stage st;
	TextField nameF;
	PasswordField passF;
	DbHelper dbh;
	
	Button login;
	LoginView lv;
	
	public LoginController(Stage primaryStage, DbHelper dbh){
		
		nameF = new TextField();
		passF = new PasswordField();
		this.dbh = dbh;
		st = primaryStage;
		
		
		
		login = new Button();
		login.setOnAction(event->{
			String uName = nameF.getText();
			String pass = passF.getText();
			if(uName.equals("nikos")&&pass.equals("stark")){
				ProductsTableController ptc = new ProductsTableController(st, dbh,lv);
				ptc.buildAndDisplayProductsTable(true);
			}//end if
		});
	}//end of LoginController
	
	
	
	
	public void displayView(){
		lv = new LoginView(st, nameF, passF, login);
		lv.showView();
		
	}
	
	
	
	

	
	
	
}//LoginController
