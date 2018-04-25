package gsu.dbs.auction.login;

import gsu.dbs.auction.Launcher;
import gsu.dbs.auction.ui.Page;
import gsu.dbs.auction.wrapper.BiddingItem;
import gsu.dbs.auction.wrapper.Review;
import gsu.dbs.auction.wrapper.Vendor;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
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
import javafx.scene.text.Text;

public class DisplayVendor extends Page {
	private Vendor vendor;
	private BiddingItem fromItem;
	
	public DisplayVendor( BiddingItem b, Vendor vendor ) {
		this.fromItem = b;
		this.vendor = vendor;
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
			Launcher.loadPage(new DisplayItem(fromItem));
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
		ScrollPane scroll = new ScrollPane();
		scroll.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
		scroll.setStyle(""
				+ "-fx-background: #FFFFFF;"
				+ "-fx-border-color: #FFFFFF;");
		scroll.setPrefWidth(Integer.MAX_VALUE);
		scroll.setPrefHeight(Integer.MAX_VALUE);
		scroll.setVbarPolicy(ScrollBarPolicy.AS_NEEDED);
		scroll.setHbarPolicy(ScrollBarPolicy.AS_NEEDED);
		divider.getChildren().add(scroll);
		
		VBox pp = new VBox();
		pp.setPadding(new Insets(8,8,8,8));
		pp.setAlignment(Pos.TOP_LEFT);
		pp.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
		scroll.setContent(pp);
		
		HBox hb = new HBox();
		pp.getChildren().add(hb);
		
		VBox tvb = new VBox();
		tvb.setMaxWidth(450);
		hb.getChildren().add(tvb);
		
		// Display name
		Text t = new Text(vendor.getFirstName() + " " + vendor.getLastName());
		t.setFont(Font.font("Tahoma", FontWeight.NORMAL, 28));
		tvb.getChildren().add(t);
		
		Text sold = new Text(vendor.getFirstName() + " has sold " + vendor.getSales() + " item(s)");
		tvb.getChildren().add(sold);
		
		StackPane space = new StackPane();
		space.setMinHeight(48);
		space.setMaxHeight(space.getMinHeight());
		tvb.getChildren().add(space);
		

		BiddingItem[] items = BiddingItem.getItemsSoldByVendor(vendor.getVendorID());
		Text t2 = new Text("Current items for sale (" + items.length + ")");
		t2.setFont(Font.font("Tahoma", FontWeight.NORMAL, 28));
		tvb.getChildren().add(t2);
		
		GridPane g = new GridPane();
		tvb.getChildren().add(g);
		
		int row = 0;
		int column = 0;
		for (int i = 0; i < items.length; i++) {
			if ( column >= 9 ) {
				column = 0;
				row++;
			}
			BrowsePage.displayProduct(g, column++, row, items[i]);
		}
		

		
		StackPane space2 = new StackPane();
		space2.setMinHeight(48);
		space2.setMaxHeight(space2.getMinHeight());
		tvb.getChildren().add(space2);
		
		
		Text t3 = new Text("Reviews of products");
		t3.setFont(Font.font("Tahoma", FontWeight.NORMAL, 28));
		tvb.getChildren().add(t3);
		Review[] reviews = vendor.getReviews();
		if ( reviews.length == 0 ) {
			Text no = new Text("This user has no reviews");
			tvb.getChildren().add(no);
		} else {
			for (int i = 0; i < reviews.length; i++) {
				Review r = reviews[i];
				Text rr = new Text(r.getBuyer().getFirstName() + " said:");
				tvb.getChildren().add(rr);
				
				Text cm = new Text(r.getComment());
				cm.setFont(Font.font(10));
				tvb.getChildren().add(cm);
				
				StackPane small = new StackPane();
				small.setMinHeight(8);
				small.setMaxHeight(small.getMinHeight());
				tvb.getChildren().add(small);
			}
		}
	}

}
