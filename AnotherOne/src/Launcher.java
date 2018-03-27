
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class Launcher extends Application {
	private static StackPane pane;
	
	public void start(Stage stage) {
		pane = new StackPane();
		
		stage.setTitle("My JavaFX Application");
		stage.setScene(new Scene(pane, 1280, 720));
		stage.show();
		
		loadPage(new LoginPage());
	}
	
	public static void loadPage(Page page) {
		pane.getChildren().clear();
		page.loadPage(pane);
	}

	public static void main(String[] args) {
		launch(args);
	}
}


