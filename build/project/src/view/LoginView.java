package view;



import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class LoginView{

	Stage st;
	TextField nameF;
	PasswordField passF;
	
	
	double width, height;
	Button login;
	
	Scene scene;
	VBox sceneWrapper ;
	
	public LoginView(Stage primaryStage, TextField nameF, 
			PasswordField passF, Button login){
		
		Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();
		primaryStage.setX(primaryScreenBounds.getMinX());
		primaryStage.setY(primaryScreenBounds.getMinY());
		
		width = primaryScreenBounds.getWidth();
		height = primaryScreenBounds.getHeight();
		
		primaryStage.setWidth(width);
		primaryStage.setHeight(height);
	
	
		st = primaryStage;
		this.nameF = nameF;
		this.passF = passF;
		this.login = login;
		this.login.setText("Σύνδεση");
		login.setId("singIn");
		
	}//LoginView
	
	
	public void showView(){
		
		HBox wrapper = new HBox(10);
		sceneWrapper = new VBox();
		//wrapper.setPadding(new Insets(height/3, 0, height/3, width/3 ));
		ImageView img = new ImageView(getClass().getResource(
				"/resources/ges.png").toExternalForm());
		
		VBox imgBox = new VBox();
		imgBox.setPadding(new Insets(height/5, 0, 0, 0 ));
		imgBox.getChildren().addAll(img);
		
		HBox nameBox = new HBox(5);
		nameBox.getChildren().addAll(new Text("Όνομα Χρήστη"), nameF);
		HBox passBox = new HBox(5);
		passBox.getChildren().addAll(new Text("Αναγνωριστίκό"), passF);
		
		VBox box = new VBox(10); 
		box.getChildren().addAll(nameBox,passBox,login);
		box.setPadding(new Insets(height/3, 0, 20, width/3 ));
		box.setId("mainBox");
		
		wrapper.getChildren().addAll(box, imgBox);
		sceneWrapper.getChildren().addAll(wrapper);
		
		scene = new Scene(sceneWrapper);
		scene.getStylesheets().add(getClass().getResource("/resources/application.css").toExternalForm());
		st.setScene(scene);
		st.show();
	}//end of showView
	
	
	
	public void AddProgressView(Stage primaryStage, ProgressIndicator pi){
		
		VBox gp = new VBox();
		gp.setAlignment(Pos.CENTER);
		
		Text txt = new Text("Αρχικοποίηση των δεδομένων, Παρακαλώ περιμένετε...");
		txt.setId("waitMessage");
		gp.getChildren().addAll(txt,pi);
		
		pi.setMinWidth(100);
	    pi.setMinHeight(100);
		
		sceneWrapper.getChildren().addAll(gp);
		st.setScene(scene);
		st.show();
	}//end of AddProgressView
	
	
	
	
}
