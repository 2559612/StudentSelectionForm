package view;


import javafx.event.ActionEvent;  
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.VPos;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;

public class OverviewSelectionPane extends GridPane{

	private TextArea tf1, tf2, tf3;
	private Button btnSaveOverview;
	
	public OverviewSelectionPane() {
		
		//create textfields, set styling
		tf1 = new TextArea("Profile will appear here.");
		tf1.setEditable(false);
		tf1.setWrapText(true);
		tf1.setPrefHeight(100);
		tf1.setPrefWidth(400);

		tf2 = new TextArea("Selected modules will appear here.");
		tf2.setEditable(false);
		tf2.setWrapText(true);
		tf2.setPrefHeight(200);
		tf2.setPrefWidth(220);

		tf3 = new TextArea("Reserved modules will appear here.");
		tf3.setEditable(false);
		tf3.setWrapText(true);
		tf3.setPrefHeight(200);
		tf3.setPrefWidth(220);

		//create button
		btnSaveOverview = new Button("Save Overview");
		
		
	   //set gridpane constraints for all the nodes
	   GridPane.setConstraints(tf1, 0, 0, 2, 1, HPos.CENTER, VPos.CENTER, Priority.NEVER, Priority.NEVER, new Insets(10, 10, 10, 10));
	   GridPane.setConstraints(tf2, 0, 1, 1, 1, HPos.CENTER, VPos.CENTER, Priority.ALWAYS, Priority.ALWAYS, new Insets(10, 10, 10, 10));
	   GridPane.setConstraints(tf3, 1, 1, 1, 1, HPos.CENTER, VPos.CENTER, Priority.ALWAYS, Priority.ALWAYS, new Insets(10, 10, 10, 10));
	   GridPane.setConstraints(btnSaveOverview, 0, 2, 2, 1, HPos.CENTER, VPos.CENTER, Priority.NEVER, Priority.NEVER, new Insets(10, 10, 10, 10));
	   
	   //For resizing the selected and reserved modules textfields in particular
	   GridPane.setVgrow(tf2, Priority.ALWAYS);
	   GridPane.setVgrow(tf3, Priority.ALWAYS);	

	   //add everything to the gridpane
	   this.getChildren().addAll(tf1, tf2, tf3, btnSaveOverview);
	   this.setGridLinesVisible(false);
		
	}
	
	//Event Handler Methods
	public void addSaveOverview(EventHandler<ActionEvent> handler) {
		btnSaveOverview.setOnAction(handler);
	}
	
	//Getters and Setters for all the private fields
	public TextArea getTf1() {
		return tf1;
	}

	public void setTf1(TextArea tf1) {
		this.tf1 = tf1;
	}

	public TextArea getTf2() {
		return tf2;
	}

	public void setTf2(TextArea tf2) {
		this.tf2 = tf2;
	}

	public TextArea getTf3() {
		return tf3;
	}

	public void setTf3(TextArea tf3) {
		this.tf3 = tf3;
	}

	public Button getBtnSaveOverview() {
		return btnSaveOverview;
	}

	public void setBtnSaveOverview(Button btnSaveOverview) {
		this.btnSaveOverview = btnSaveOverview;
	}

}

