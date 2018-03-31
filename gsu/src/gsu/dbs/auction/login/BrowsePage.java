package gsu.dbs.auction.login;

import gsu.dbs.auction.Launcher;
import gsu.dbs.auction.LoginInformation;
import gsu.dbs.auction.newuser.NewUserPage;
import gsu.dbs.auction.newvendor.NewVendorPage;
import gsu.dbs.auction.ui.Page;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
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
	
		Launcher.topBar(mainPage, "Auction Store Mockup");
		
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

