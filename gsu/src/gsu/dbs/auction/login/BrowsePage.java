package gsu.dbs.auction.login;

import gsu.dbs.auction.Launcher;
import gsu.dbs.auction.LoginInformation;
import gsu.dbs.auction.newuser.NewUserPage;
import gsu.dbs.auction.newvendor.NewVendorPage;
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
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class BrowsePage extends Page {

	@Override
	public void loadPage(Pane canvas) {
		VBox mainPage = new VBox();
		mainPage.setFillWidth(true);
		canvas.getChildren().add(mainPage);
	
		Launcher.topBar(mainPage, "Auction Store Mockup");
		
		Label temp = new Label("			Auctions live now");
		mainPage.getChildren().add(temp);
		
		// Display temp item list
		
				GridPane gp = new GridPane();
				gp.setAlignment(Pos.CENTER);
				gp.setHgap(10);
				gp.setVgap(10);
				
				for(int i=0; i<3; i++){
					for(int j=0; j<6; j++){
						Rectangle space = new Rectangle(100, 100);
						
						
						space.setOnMouseEntered(e->{
							space.setStroke(Color.BLACK);	
						});
						space.setOnMouseExited(e->{
							space.setStroke(Color.GREY);
						});
						space.setOnMouseClicked(e->{
							
						});
						space.setFill(Color.WHITE);
						
						space.setStroke(Color.GREY);
							gp.add(space, j, i);
					}
				}
				mainPage.getChildren().add(gp);
	}
	

}

