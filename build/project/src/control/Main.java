package control;
	
import javafx.application.Application;
import javafx.stage.Stage;
import model.DbHelper;



public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		
		try {
			DbHelper dbh = new DbHelper();
			
			int res = dbh.dbConnect("192.168.5.101:3306/PRODUCTS", "timologia","timologia");
			if(res  != 1){
				dbh.dbConnect("localhost/PRODUCTS", "root","panathinaikos"); 
			}
			
			
			
			LoginController login = new LoginController(primaryStage, dbh);
			login.displayView();
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
