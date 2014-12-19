package view;


import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class MessagePop {

	Stage dialogStage;
	
	public MessagePop(Stage st, Button okB, Button closeB){
		
		
		dialogStage = st;
		dialogStage.initModality(Modality.WINDOW_MODAL);
		
		VBox vbox = new VBox(5);
		vbox.getChildren().addAll(new Text("Hi"),okB, closeB);
		vbox.setAlignment(Pos.CENTER);
		    //alignment(Pos.CENTER).padding(new Insets(5)).build()));
		
		Scene scene  = new Scene(vbox);
		scene.getStylesheets().add(getClass().getResource("/resources/application.css").toExternalForm());
		
		dialogStage.setScene(scene);
		
		
		
		
	}
	
	public void dispMessage(){
		dialogStage.show();
	}
	
}
