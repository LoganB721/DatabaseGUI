
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;


public class BrowsePage extends Page {

	@Override
	public void loadPage(Pane canvas) {
		VBox mainPage = new VBox();
		mainPage.setFillWidth(true);
		canvas.getChildren().add(mainPage);
		
		// Top Bar
		StackPane topBar = new StackPane();
		topBar.setAlignment(Pos.CENTER_LEFT);
		topBar.setPadding(new Insets(12,32,12,32));
		topBar.setMinHeight(100);
		topBar.setMaxHeight(100);
	    BackgroundFill fill = new BackgroundFill(Color.rgb(35, 47, 63), CornerRadii.EMPTY, Insets.EMPTY);
		topBar.setBackground(new Background(fill));
		mainPage.getChildren().add(topBar);
		
		// Welcome text
		StackPane tempWelcome = new StackPane();
		tempWelcome.setAlignment(Pos.TOP_RIGHT);
		topBar.getChildren().add(tempWelcome);
		Label welcome = new Label("Welcome, " + LoginInformation.User);
		welcome.setTextFill(Color.WHITESMOKE);
		welcome.setAlignment(Pos.TOP_RIGHT);
		tempWelcome.getChildren().add(welcome);
		
		// Box containing store name AND search bar
		HBox hbox = new HBox();
		hbox.setAlignment(Pos.CENTER_LEFT);
		hbox.setSpacing(32);
		topBar.getChildren().add(hbox);
		
		// Store name
		Label title = new Label("Auction Store Mockup");
		title.setFont(Font.font("Tahoma", FontWeight.NORMAL, 28));
		title.setTextFill(Color.WHITESMOKE);
		hbox.getChildren().add(title);
		
		// Search bar
		TextField searchBar = new TextField();
		searchBar.setText("Search bar");
		searchBar.setMaxWidth(600);
		searchBar.setPrefWidth(600);
		hbox.getChildren().add(searchBar);
		
		// Gradient graphic
		StackPane topBarGradient = new StackPane();
		topBarGradient.setAlignment(Pos.CENTER_LEFT);
		topBarGradient.setMinHeight(8);
		topBarGradient.setMaxHeight(8);
		topBarGradient.setStyle("-fx-background-color: linear-gradient(from 0px 0px to 0px 4px, rgba(0,0,0,0.5), rgba(0,0,0,0));");
		mainPage.getChildren().add(topBarGradient);
		
		Label temp = new Label("			Auctions live now");
		mainPage.getChildren().add(temp);
		
		// Display temp item list
		StackPane itemPane = new StackPane();
		itemPane.setAlignment(Pos.TOP_CENTER);
		itemPane.setMinWidth(600);
		mainPage.getChildren().add(itemPane);
		
		GridPane itemGrid = new GridPane();
		itemGrid.setAlignment(Pos.TOP_CENTER);
		itemGrid.setHgap(10);
		itemGrid.setVgap(10);
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				loadItem(itemGrid, i, j, "TestItem" + (i + j*4));
			}
		}
		
		itemPane.getChildren().add(itemGrid);
	}

	private void loadItem(GridPane grid, int column, int row, String name) {
		StackPane pane = new StackPane();
		pane.setMinHeight(100);
		pane.setMinWidth(100);
		pane.setBackground(new Background(new BackgroundFill(Color.LIGHTSTEELBLUE, CornerRadii.EMPTY, Insets.EMPTY)));
		grid.add(pane, column, row);
		
		Label nl = new Label(name);
		pane.getChildren().add(nl);
	}
}
