package gsu.dbs.auction.login;

import gsu.dbs.auction.Launcher;
import gsu.dbs.auction.ui.Page;
import gsu.dbs.auction.wrapper.BiddingItem;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

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

		GridPane grid = new GridPane();
		grid.setAlignment(Pos.CENTER);
		grid.setHgap(15);
		grid.setVgap(10);
		grid.setPadding(new Insets(50, 50, 50, 50));
	}

}
