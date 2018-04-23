package gsu.dbs.auction.sell;

import gsu.dbs.auction.Launcher;
import gsu.dbs.auction.login.BrowsePage;
import gsu.dbs.auction.ui.Page;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class ConfirmSellPage extends Page {

	@Override
	public void loadPage(Pane canvas) {
		VBox mainPage = new VBox();
		mainPage.setFillWidth(true);
		mainPage.setAlignment(Pos.CENTER);
		canvas.getChildren().add(mainPage);
		
		
		GridPane grid = new GridPane();
		grid.setAlignment(Pos.CENTER);
		grid.setHgap(15);
		grid.setVgap(10);
		grid.setPadding(new Insets(25, 25, 25, 25));    

		// Vendor Created
		Label confirm = new Label("Item Listed");
		grid.add(confirm, 0, 1);;
		
		//Back Button
		Button back = new Button("Back to browse page");
		HBox hbBack = new HBox(10);
		hbBack.setAlignment(Pos.BOTTOM_CENTER);
		hbBack.getChildren().add(back);
		grid.add(hbBack, 0, 4);
		
		back.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
			
					Launcher.loadPage(new BrowsePage(null));
			
			}
		});
		
		mainPage.getChildren().add(grid);
	}

}
