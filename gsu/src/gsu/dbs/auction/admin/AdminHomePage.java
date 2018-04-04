package gsu.dbs.auction.admin;

import gsu.dbs.auction.Launcher;
import gsu.dbs.auction.login.BrowsePage;
import gsu.dbs.auction.ui.Page;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class AdminHomePage extends Page{

	@Override
	public void loadPage(Pane canvas) {
		VBox mainPage = new VBox();
		mainPage.setFillWidth(true);
		canvas.getChildren().add(mainPage);
		
		Launcher.topBar(mainPage, "Administrator");
		
		GridPane grid = new GridPane();
		grid.setAlignment(Pos.CENTER);
		grid.setHgap(15);
		grid.setVgap(10);
		grid.setPadding(new Insets(25, 25, 25, 25));    

		Hyperlink userEdit = new Hyperlink("Edit Users");
		userEdit.setOnAction(new EventHandler<ActionEvent>(){
			public void handle(ActionEvent arg0) {
				Launcher.loadPage(new EditUser());
				
			}
			
		});
		grid.add(userEdit, 0, 0);
		
		Hyperlink customerEdit = new Hyperlink("Edit Customers");
		customerEdit.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent arg0) {
				Launcher.loadPage(new EditCustomer());
			}
			
		});
		grid.add(customerEdit, 0, 1);
		
		//Back Button
		Button back = new Button("Back to browse page");
		HBox hbBack = new HBox(10);
		hbBack.setAlignment(Pos.BOTTOM_CENTER);
		hbBack.getChildren().add(back);
		grid.add(hbBack, 0, 4);
		
		back.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				Launcher.loadPage(new BrowsePage());
			}
		});
		
		mainPage.getChildren().add(grid);
	}
	
		
}
	
	

