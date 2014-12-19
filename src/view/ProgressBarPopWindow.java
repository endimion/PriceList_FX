package view;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class ProgressBarPopWindow {

	Stage st;
	
	public ProgressBarPopWindow(ProgressBar bar, String message, Stage st){
		VBox hbox = new VBox(15);
		hbox.setPadding(new Insets(30, 30, 30, 30));
		bar.setPrefSize(200, 24);
		hbox.getChildren().addAll(new Text(message), bar);
		
		this.st = st;
		Scene scene = new Scene(hbox);
		st.setTitle("Παρακαλώ Περιμένετε...");
		st.setScene(scene);
		
		st.show();
	}
	
	public Stage getStage(){
		return st;
	}
	
	
	public void pop(){
		st.show();
	}
}
