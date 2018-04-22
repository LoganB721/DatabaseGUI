package gsu.dbs.auction.login;

import gsu.dbs.auction.Launcher;
import gsu.dbs.auction.ui.Page;
import gsu.dbs.auction.wrapper.BiddingItem;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Hyperlink;
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
			Launcher.loadPage(new BrowsePage());
		});
		
		// Create divider
		HBox divider = new HBox();
		divider.setFillHeight(true);
		mainPage.getChildren().add(divider);
		
		// Left space
		StackPane sp = new StackPane();
		sp.setMinWidth(128);
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
		
		// Display name
		Text t = new Text(b.getProduct().getProductName());
		t.setFont(Font.font("Tahoma", FontWeight.NORMAL, 28));
		pp.getChildren().add(t);
		
		// Display image
		StackPane ivp = new StackPane();
		ivp.setAlignment(Pos.TOP_LEFT);
		ivp.setPrefWidth(300);
		pp.getChildren().add(ivp);
		ImageView iv = new ImageView(new Image(b.getProduct().getProductImage()));
		iv.setFitWidth(300);
		iv.setPreserveRatio(true);
		ivp.getChildren().add(iv);
	}

}
