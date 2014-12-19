package control;
	
import javafx.application.Application;
import javafx.stage.Stage;
import model.DbHelper;



public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		
		try {
			DbHelper dbh = new DbHelper();
			
			int res = dbh.dbConnect("localhost/PRODUCTS", "root","panathinaikos"); 
			if(res  != 1){
				System.out.println("local db connet failed... attempting remote host");
				dbh.dbConnect("192.168.5.101:3306/PRODUCTS", "timologia","timologia");
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
