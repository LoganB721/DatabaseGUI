package gsu.dbs.auction.sell;

import gsu.dbs.auction.Launcher;
import gsu.dbs.auction.login.BrowsePage;
import gsu.dbs.auction.login.LoginPage;
import gsu.dbs.auction.ui.Page;
import javafx.scene.layout.Pane;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class SellPage extends Page {

	@Override
	public void loadPage(Pane canvas) {
		VBox mainHolder = new VBox();
		mainHolder.setAlignment(Pos.CENTER);
		

		GridPane grid = new GridPane();
		grid.setAlignment(Pos.CENTER);
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(25, 25, 25, 25));

		Text scenetitle = new Text("SELL AN ITEM");
		scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
		grid.add(scenetitle, 0, 0, 2, 1);

		Label itemName = new Label("Item Name:");
		grid.add(itemName, 0, 1);

		TextField itemname = new TextField();
		grid.add(itemname, 1, 1);

		Label start = new Label("Starting Price:");
		grid.add(start, 0, 2);

		TextField category = new TextField();
		grid.add(category, 1, 2);
		
		Label pt = new Label("Product Type:");
		grid.add(pt, 0, 3);

		TextField producttype = new TextField();
		grid.add(producttype, 1, 3);
		
		Button btn = new Button("POST");
		btn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				Launcher.loadPage(new ConfirmSellPage()); 

			}
		});
		HBox hbBtn = new HBox(10);
		hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
		hbBtn.getChildren().add(btn);
		grid.add(hbBtn, 1, 4);

		
		mainHolder.getChildren().add(grid);
		canvas.getChildren().add(mainHolder);
	}

}

