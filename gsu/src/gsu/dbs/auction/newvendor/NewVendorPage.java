package gsu.dbs.auction.newvendor;

import gsu.dbs.auction.Launcher;
import gsu.dbs.auction.login.BrowsePage;
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
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class NewVendorPage extends Page{

	@Override
	public void loadPage(Pane canvas) {
		VBox mainPage = new VBox();
		mainPage.setFillWidth(true);
		canvas.getChildren().add(mainPage);
		
		Launcher.topBar(mainPage, "Vendor Information");
		
		GridPane grid = new GridPane();
		grid.setAlignment(Pos.CENTER);
		grid.setHgap(15);
		grid.setVgap(10);
		grid.setPadding(new Insets(25, 25, 25, 25));    

		Label vendorID = new Label("Vendor ID:");
		grid.add(vendorID, 0, 1);

		final TextField vendorIDTextField = new TextField();
		vendorIDTextField.setText("");
		grid.add(vendorIDTextField, 1, 1);

		Label vendorName = new Label("Vendor Name:");
		grid.add(vendorName, 0, 2);

		final TextField vendorNameTextField = new TextField();
		vendorNameTextField.setText("");
		grid.add(vendorNameTextField, 1, 2);

		Label shipAddressID = new Label("Shipping Address ID:");
		grid.add(shipAddressID, 0, 3);
		
		final TextField shipAddressIDTextField = new TextField();
		shipAddressIDTextField.setText("");
		grid.add(shipAddressIDTextField, 1, 3);
		
		Label vendorPhone = new Label("Vendor Phone:");
		grid.add(vendorPhone, 0, 4);
		
		final TextField vendorPhoneTextField = new TextField();
		vendorPhoneTextField.setText("");
		grid.add(vendorPhoneTextField, 1, 4);
		
		Label totalSales = new Label("Total Sales");
		grid.add(totalSales, 0, 5);
		
		final TextField totalSalesTextField = new TextField();
		totalSalesTextField.setText("");
		grid.add(totalSalesTextField, 1, 5);
		
		//Back Button
		Button back = new Button("Back to browse page");
		HBox hbBack = new HBox(10);
		hbBack.setAlignment(Pos.BOTTOM_CENTER);
		hbBack.getChildren().add(back);
		grid.add(hbBack, 0, 6);
				
		back.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				Launcher.loadPage(new BrowsePage(null));
				}
			});
		
		// Create Vendor Button
		Button btn = new Button("Create Vendor");
		HBox hbBtn = new HBox(10);
		hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
		hbBtn.getChildren().add(btn);
		grid.add(hbBtn, 1, 6);
		
		btn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				Launcher.loadPage(new ConfirmVendorCreation());
			}
		});

		mainPage.getChildren().add(grid);
	
	}
	
	
}


