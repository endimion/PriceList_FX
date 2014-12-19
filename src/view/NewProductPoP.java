package view;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class NewProductPoP {

	
	
	public NewProductPoP(Stage stage, TextField ekemsCode,
			TextField invCode, ChoiceBox<String> catChoice,
			ChoiceBox<String> subCatChoice, ChoiceBox<String> supChoice,
			Button save, Button cancel){
		
		stage.setTitle("Εισαγωγή νέου Προιόντος");
		
		HBox ekemsBox = new HBox(5);
		ekemsBox.getChildren().addAll(new Text("Κωδικός ΕΚΕΜΣ: "), ekemsCode);
		
		HBox invBox = new  HBox(5);
		invBox.getChildren().addAll(new Text("Κωδικός Τιμογ/ιου: "), invCode);
		
		HBox catBox = new HBox(5);
		catBox.getChildren().addAll(new Text("Κατηγορία: "), catChoice);
		
		HBox subCatBox = new HBox(5);
		subCatBox.getChildren().addAll(new Text("Υποκ/ορία: "), subCatChoice);
		
		HBox supBox = new HBox(5);
		supBox.getChildren().addAll(new Text("Προμ/ης: "), supChoice);
		
		
		HBox buttonsBox = new HBox(5);
		buttonsBox.getChildren().addAll(save, cancel);
		
		VBox wrapper = new VBox(10);
		wrapper.getChildren().addAll(ekemsBox,invBox ,catBox,
				subCatBox,supBox,buttonsBox);
		wrapper.setPadding(new Insets(15, 15, 15, 15));
		
		
	
		
		
		Scene scene  = new Scene(wrapper);
		scene.getStylesheets().add(getClass().getResource("/resources/application.css").toExternalForm());
		
		stage.setScene(scene);
	    stage.show();
	}
	
	
}
