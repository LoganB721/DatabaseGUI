package gsu.dbs.auction.login;

import gsu.dbs.auction.Launcher;
import gsu.dbs.auction.ui.Page;
import gsu.dbs.auction.wrapper.BidHistory;
import gsu.dbs.auction.wrapper.BiddingItem;
import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.ImageViewBuilder;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class DisplayItem extends Page {
	private BiddingItem b;
	
	public DisplayItem(BiddingItem object) {
		this.b = object;
	}

	@Override
	public void loadPage(Pane canvas) {	
		VBox mainPage = new VBox();
		mainPage.setFillWidth(true);
		canvas.getChildren().add(mainPage);

		Launcher.topBar(mainPage, "Auction Store Mockup");
		
		Hyperlink back = new Hyperlink("Back");
		mainPage.getChildren().add(back);
		back.setOnAction(event -> {
			Launcher.loadPage(new BrowsePage(BrowsePage.search));
		});
		
		// Create divider
		HBox divider = new HBox();
		divider.setFillHeight(true);
		mainPage.getChildren().add(divider);
		
		// Left space
		StackPane sp = new StackPane();
		sp.setMinWidth(150);
		sp.setMaxWidth(sp.getMinWidth());
		divider.getChildren().add(sp);
		
		
		Platform.runLater(() -> {
			StackPane t = new StackPane();
			t.setMinWidth(sp.getMaxWidth());
			t.setMaxWidth(sp.getMinWidth());
			divider.getChildren().add(t);
		});
		
		// Product Pane
		VBox pp = new VBox();
		pp.setPadding(new Insets(8,8,8,8));
		pp.setPrefWidth(Integer.MAX_VALUE);
		pp.setPrefHeight(Integer.MAX_VALUE);
		pp.setAlignment(Pos.TOP_LEFT);
		pp.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
		divider.getChildren().add(pp);
		
		HBox hb = new HBox();
		pp.getChildren().add(hb);
		
		VBox tvb = new VBox();
		tvb.setMaxWidth(450);
		hb.getChildren().add(tvb);
		
		// Display name
		Text t = new Text(b.getProduct().getProductName());
		t.setFont(Font.font("Tahoma", FontWeight.NORMAL, 28));
		tvb.getChildren().add(t);
		
		// Display image
		StackPane ivp = new StackPane();
		ivp.setAlignment(Pos.TOP_LEFT);
		ivp.setPrefWidth(300);
		tvb.getChildren().add(ivp);
		ImageView iv = new ImageView(new Image(b.getProduct().getProductImage()));
		iv.setFitWidth(300);
		iv.setPreserveRatio(true);
		ivp.getChildren().add(iv);
		
		VBox rightPane = new VBox();
		rightPane.setFillWidth(true);
		rightPane.setAlignment(Pos.TOP_RIGHT);
		hb.getChildren().add(rightPane);
		
		VBox rightPane2 = new VBox();
		rightPane2.setAlignment(Pos.TOP_RIGHT);
		rightPane.getChildren().add(rightPane2);

		Text startingPrice = new Text("Starting Price: $" + b.getProduct().getStartingPrice());
		rightPane2.getChildren().add(startingPrice);
		
		int currentBid = b.getCurrentHighestBid();
		if ( currentBid != -1 ) {
			Text currentPrice = new Text("Current Bid: $" + currentBid);
			rightPane2.getChildren().add(currentPrice);
			
			BidHistory[] bids = b.getBidHistory();
			Text amountBids = new Text("Amount of bids: " + bids.length);
			rightPane2.getChildren().add(amountBids);
			
			if ( bids.length >= 3 ) {
				Text hot = new Text("HOT");
				hot.setFill(Color.RED);
				rightPane2.getChildren().add(hot);
			}
		} else {
			Text currentPrice = new Text("No bids yet! Be the first!");
			rightPane2.getChildren().add(currentPrice);
		}
		
		// Bid Button
		Button b = new Button("Bid!");
		rightPane2.getChildren().add(b);
		b.setOnAction(event -> {
			Stage s = Launcher.popup();
			VBox mvb = new VBox();
			mvb.setPadding(new Insets(8,8,8,8));
			mvb.setSpacing(8);
			Scene scene = new Scene(mvb, 300, 150);
			s.setScene(scene);
			s.show();
			
			Text mt = new Text("Would you like to bid on item " + this.b.getProduct().getProductName() + "?");
			mt.setWrappingWidth(300);
			mvb.getChildren().add(mt);
			
			int minBid = this.b.getCurrentHighestBid()+1;
			
			Text mt2 = new Text("Minimum bid: $" + minBid);
			mvb.getChildren().add(mt2);
			
			TextField tf = new TextField();
			tf.setMaxWidth(128);
			tf.setPromptText("$" + minBid);
			mvb.getChildren().add(tf);
			final BooleanProperty firstTime = new SimpleBooleanProperty(true); // Variable to store the focus on stage load
	        tf.focusedProperty().addListener((observable,  oldValue,  newValue) -> {
	            if(newValue && firstTime.get()){
	                mvb.requestFocus(); // Delegate the focus to container
	                firstTime.setValue(false); // Variable value changed for future references
	            }
	        });
			
			Button go = new Button("Bid");
			mvb.getChildren().add(go);
			go.setOnAction(e -> {
				s.close();
				String tt = tf.getText();
				try {
					int tt2 = Integer.parseInt(tt);
					if ( tt2 < minBid ) {
						Launcher.error("Your bid must be at least $" + minBid);
					} else {
						BiddingItem.bidOnItem(this.b,tt2);
						Launcher.loadPage(new DisplayItem(this.b));
					}
				} catch(Exception ee) {
					Launcher.error("Invalid bid amount!");
				}
			});
		});
		
		Text about = new Text("About this product");
		about.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
		tvb.getChildren().add(about);
		
		Text desc = new Text(this.b.getProduct().getProductDescription());
		desc.setWrappingWidth(tvb.getMaxWidth());
		tvb.getChildren().add(desc);
	}

}
