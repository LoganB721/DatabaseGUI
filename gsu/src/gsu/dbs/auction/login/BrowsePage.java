package gsu.dbs.auction.login;

import gsu.dbs.auction.Launcher;
import gsu.dbs.auction.ui.Page;
import gsu.dbs.auction.wrapper.BiddingItem;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public class BrowsePage extends Page {
	public static String search = null;
	
	public BrowsePage(String search) {
		BrowsePage.search = search;
	}
	
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
		
		BiddingItem[] objects = BiddingItem.getItemsLive();
		
		GridPane itemGrid = new GridPane();
		itemGrid.setAlignment(Pos.TOP_CENTER);
		itemGrid.setHgap(10);
		itemGrid.setVgap(10);
		for (int i = 0; i < objects.length; i++) {
			BiddingItem b = objects[i];
			if ( search == null || b.getProduct().getProductName().contains(search) || b.getProduct().getProductDescription().contains(search) ) {
				loadItem(itemGrid, i, 0, objects[i]);
			}
		}
		
		itemPane.getChildren().add(itemGrid);
	}

	private void loadItem(GridPane grid, int column, int row, BiddingItem object) {
		StackPane pane = new StackPane();
		pane.setMinHeight(100);
		pane.setMinWidth(100);
		pane.setBackground(new Background(new BackgroundFill(Color.LIGHTSTEELBLUE, CornerRadii.EMPTY, Insets.EMPTY)));
		grid.add(pane, column, row);
		
		Hyperlink nl = new Hyperlink(object.getProduct().getProductName());
		nl.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				Launcher.loadPage(new DisplayItem(object));			    
			}
		});		
		pane.getChildren().add(nl);
	}
	

}