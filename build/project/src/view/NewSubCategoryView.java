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


public class NewSubCategoryView {
		
		
	public NewSubCategoryView(Stage stage, TextField subCatNameText, 
				ChoiceBox<String> catChoice,
				Button save, Button cancel){
			
			stage.setTitle("Εισαγωγή νέας υπό-κατηγορίας");
			
			HBox subCatBox = new HBox(5);
			subCatBox.getChildren().addAll(new Text("Όνομα: "), subCatNameText);
			
			HBox catBox = new HBox(5);
			catBox.getChildren().addAll(new Text("Κατηγορία: "), catChoice);
			
			HBox buttonsBox = new HBox(5);
			buttonsBox.getChildren().addAll(save, cancel);
			
			VBox wrapper = new VBox(10);
			wrapper.getChildren().addAll(subCatBox,catBox, buttonsBox);
			wrapper.setPadding(new Insets(15, 15, 15, 15));
			
			
			Scene scene  = new Scene(wrapper);
			scene.getStylesheets().add(getClass().getResource("/resources/application.css").toExternalForm());
			
			stage.setScene(scene);
		    stage.show();
		}
		
		
		
}//end of class