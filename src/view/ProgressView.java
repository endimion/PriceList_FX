package view;

import javafx.scene.Scene;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class ProgressView {

	public ProgressView(Stage primaryStage, ProgressIndicator pi){
		
		HBox gp = new HBox();
		Text txt = new Text("Αρχικοποίηση των δεδομένων, Παρακαλώ περιμένετε...");
		gp.getChildren().addAll(txt,pi);
		
		Scene scene = new Scene(gp);

		scene.getStylesheets().add(getClass().getResource("/resources/application.css").toExternalForm());
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	
	
	
	
	
	
	
	
	
	
	
}//end of class
