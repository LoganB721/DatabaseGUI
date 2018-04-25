package gsu.dbs.auction.login;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import gsu.dbs.auction.Launcher;
import gsu.dbs.auction.ui.Page;
import gsu.dbs.auction.wrapper.BiddingItem;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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
			if ( search == null
					|| b.getProduct().getProductName().toLowerCase().contains(search.toLowerCase())
					|| b.getProduct().getProductDescription().toLowerCase().contains(search.toLowerCase()) ) {
				displayProduct(itemGrid, i, 0, objects[i]);
			}
		}
		
		itemPane.getChildren().add(itemGrid);
	}

	public static void displayProduct(GridPane grid, int column, int row, BiddingItem object) {
		VBox itemBox = new VBox();
		itemBox.setAlignment(Pos.CENTER);
		grid.add(itemBox, column, row);
		
		StackPane pane = new StackPane();
		pane.setMinHeight(100);
		pane.setMinWidth(100);
		pane.setMaxWidth(pane.getMinWidth());
		pane.setMaxHeight(pane.getMinHeight());
		pane.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
		itemBox.getChildren().add(pane);
		
		StackPane ivp = new StackPane();
		ivp.setAlignment(Pos.CENTER);
		ivp.setMaxWidth(pane.getMinWidth());
		ivp.setMaxHeight(pane.getMinHeight());
		pane.getChildren().add(ivp);
		ImageView iv = new ImageView();
		Image img = new Image(object.getProduct().getProductImage(), true);
		img.progressProperty().addListener((observable, oldImage, newImage) -> {
			if ( ((double)newImage) >= 1.0 ) {
				if ( img.getWidth() > img.getHeight() ) {
					iv.setFitWidth(pane.getMinWidth());
				} else {
					iv.setFitHeight(pane.getMinHeight());
				}
			}
		});
		iv.setImage(img);
		iv.setPreserveRatio(true);
		ivp.getChildren().add(iv);
		ivp.setOnMouseClicked(event -> {
			Launcher.loadPage(new DisplayItem(object));	
		});
		
		Hyperlink nl = new Hyperlink(object.getProduct().getProductName());
		nl.setOnAction(event -> {
			Launcher.loadPage(new DisplayItem(object));
		});		
		itemBox.getChildren().add(nl);
	}
	

}