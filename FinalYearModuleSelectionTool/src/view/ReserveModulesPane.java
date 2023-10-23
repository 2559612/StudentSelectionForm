package view;

import javafx.beans.binding.BooleanBinding;
import javafx.collections.FXCollections; 
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.control.Accordion;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import model.Module;


public class ReserveModulesPane extends Accordion{


	private TitledPane t1, t2;
	private GridPane grid1, grid2;
	private ObservableList<Module> UTerm1, RTerm1, UTerm2, RTerm2;
	private Button btnAdd1, btnAdd2, btnRemove1, btnRemove2, btnConfirm1, btnConfirm2;
	private ListView<Module> LVUT1, LVRT1, LVUT2, LVRT2; // Full form example: ListViewUnselectedTerm1
	
	public ReserveModulesPane() {
		
	//FORMING THE TERM 1 MODULES PANE	
		
		//create buttons, arraylists and listviews, set styling
		btnAdd1 = new Button("Add");
		btnRemove1 = new Button("Remove");
		btnConfirm1 = new Button("Confirm");
		
		UTerm1 = FXCollections.observableArrayList();
		LVUT1 = new ListView<Module>(UTerm1);
		LVUT1.setPrefHeight(270);
		RTerm1 = FXCollections.observableArrayList();
		LVRT1 = new ListView<Module>(RTerm1);
		LVRT1.setPrefHeight(270);
		
		//arrange layout and set styling
		VBox vbT1U = new VBox(new Label("Unselected Term 1 modules"), LVUT1); //Full form: VBox Term 1 Unselected
		
		VBox vbT1R = new VBox(new Label("Reserved Term 1 modules"), LVRT1);
		
		HBox hb1 = new HBox(new Label("Reserve 30 credits worth of term 1 modules "), btnAdd1, btnRemove1, btnConfirm1);		hb1.setAlignment(Pos.CENTER);
		hb1.setSpacing(15);

		
		//For resizing the listviews in particular
		VBox.setVgrow(LVUT1, Priority.ALWAYS);
		VBox.setVgrow(LVRT1, Priority.ALWAYS);
		
		//set gridpane constraints for all the boxes
		GridPane.setConstraints(vbT1U, 0, 0, 1, 1, HPos.CENTER, VPos.CENTER, Priority.ALWAYS, Priority.ALWAYS, new Insets(10, 10, 10, 10));
		GridPane.setConstraints(vbT1R, 1, 0, 1, 1, HPos.CENTER, VPos.CENTER, Priority.ALWAYS, Priority.ALWAYS, new Insets(10, 10, 10, 10));
		GridPane.setConstraints(hb1, 0, 1, 2, 1, HPos.CENTER, VPos.CENTER, Priority.ALWAYS, Priority.ALWAYS, new Insets(5, 5, 5, 5));
		
		//add everything to the gridpane
		grid1 = new GridPane();
		grid1.getChildren().addAll(vbT1U, vbT1R, hb1);
		grid1.setGridLinesVisible(false);

		
		//create buttons, arraylists and listviews
		btnAdd2 = new Button("Add");
		btnRemove2 = new Button("Remove");
		btnConfirm2 = new Button("Confirm");
		
		UTerm2 = FXCollections.observableArrayList();
		LVUT2 = new ListView<Module>(UTerm2);
		LVUT2.setPrefHeight(270);
		RTerm2 = FXCollections.observableArrayList();
		LVRT2 = new ListView<Module>(RTerm2);
		LVRT2.setPrefHeight(270);
		
		
	//FORMING THE TERM 2 MODULES PANE
		
		//arrange layout and styling
		VBox vbT2U = new VBox(new Label("Unselected Term 2 modules"), LVUT2); //Full form: VBox Term 2 Unselected
		
		VBox vbT2R = new VBox(new Label("Reserved Term 2 modules"), LVRT2);
		
		HBox hb2 = new HBox(new Label("Reserve 30 credits worth of term 2 modules "), btnAdd2, btnRemove2, btnConfirm2);
		hb2.setAlignment(Pos.CENTER);
		hb2.setSpacing(15);
		
		//set gridpane constraints for all the boxes
		GridPane.setConstraints(vbT2U, 0, 0, 1, 1, HPos.CENTER, VPos.CENTER, Priority.ALWAYS, Priority.ALWAYS, new Insets(10, 10, 10, 10));
		GridPane.setConstraints(vbT2R, 1, 0, 1, 1, HPos.CENTER, VPos.CENTER, Priority.ALWAYS, Priority.ALWAYS, new Insets(10, 10, 10, 10));
		GridPane.setConstraints(hb2, 0, 1, 2, 1, HPos.CENTER, VPos.CENTER, Priority.ALWAYS, Priority.ALWAYS, new Insets(5, 5, 5, 5));
		
		//For resizing the listviews in particular
		VBox.setVgrow(LVUT2, Priority.ALWAYS);
		VBox.setVgrow(LVRT2, Priority.ALWAYS);
		
		//add all boxes to the gridpane 
		this.grid2 = new GridPane();
		grid2.getChildren().addAll(vbT2U, vbT2R, hb2);
		grid2.setGridLinesVisible(false);
			
		
		//adding panes to the accordion
		t1 = new TitledPane("Term 1 modules", grid1);
		t2 = new TitledPane("Term 2 modules", grid2);
		this.getPanes().addAll(t1, t2);
		this.setExpandedPane(t1);
		
	}
	
	
	public void changePane() {
		this.setExpandedPane(t2);
	}
	
	
	//Boolean Binding Methods
	public BooleanBinding isUT1Unselected() {
		return LVUT1.getSelectionModel().selectedItemProperty().isNull();
	}
	
	public BooleanBinding isUT2Unselected() {
		return LVUT2.getSelectionModel().selectedItemProperty().isNull();
	}
	
	public BooleanBinding isRT1Unselected() {
		return LVRT1.getSelectionModel().selectedItemProperty().isNull();
	}
	
	public BooleanBinding isRT2Unselected() {
		return LVRT2.getSelectionModel().selectedItemProperty().isNull();
	}
	
	public void addBtnAdd1DisableBind(BooleanBinding property) {
		btnAdd1.disableProperty().bind(property);
	}
	public void addBtnAdd2DisableBind(BooleanBinding property) {
		btnAdd2.disableProperty().bind(property);
	}
	public void addBtnRemove1DisableBind(BooleanBinding property) {
		btnRemove1.disableProperty().bind(property);
	}
	public void addBtnRemove2DisableBind(BooleanBinding property) {
		btnRemove2.disableProperty().bind(property);
	}
	
	//Event Handler methods
	public void addAdd1Handler(EventHandler<ActionEvent> handler) {
		btnAdd1.setOnAction(handler);
	}
	
	public void addRemove1Handler(EventHandler<ActionEvent> handler) {
		btnRemove1.setOnAction(handler);
	}
	
	public void addAdd2Handler(EventHandler<ActionEvent> handler) {
		btnAdd2.setOnAction(handler);
	}
	
	public void addRemove2Handler(EventHandler<ActionEvent> handler) {
		btnRemove2.setOnAction(handler);
	}

	public void addConfirm1Handler(EventHandler<ActionEvent> handler) {
		btnConfirm1.setOnAction(handler);
	}
	public void addConfirm2Handler(EventHandler<ActionEvent> handler) {
		btnConfirm2.setOnAction(handler);
	}
	
	
	//Getters and Setters for all private fields
	public TitledPane getT1() {
		return t1;
	}

	public void setT1(TitledPane t1) {
		this.t1 = t1;
	}

	public TitledPane getT2() {
		return t2;
	}

	public void setT2(TitledPane t2) {
		this.t2 = t2;
	}

	public GridPane getGrid1() {
		return grid1;
	}

	public void setGrid1(GridPane grid1) {
		this.grid1 = grid1;
	}

	public GridPane getGrid2() {
		return grid2;
	}

	public void setGrid2(GridPane grid2) {
		this.grid2 = grid2;
	}

	public ObservableList<Module> getUTerm1() {
		return UTerm1;
	}

	public void setUTerm1(ObservableList<Module> uTerm1) {
		UTerm1 = uTerm1;
	}

	public ObservableList<Module> getRTerm1() {
		return RTerm1;
	}

	public void setRTerm1(ObservableList<Module> rTerm1) {
		RTerm1 = rTerm1;
	}

	public ObservableList<Module> getUTerm2() {
		return UTerm2;
	}

	public void setUTerm2(ObservableList<Module> uTerm2) {
		UTerm2 = uTerm2;
	}

	public ObservableList<Module> getRTerm2() {
		return RTerm2;
	}

	public void setRTerm2(ObservableList<Module> rTerm2) {
		RTerm2 = rTerm2;
	}

	public ListView<Module> getLVUT1() {
		return LVUT1;
	}

	public void setLVUT1(ListView<Module> lVUT1) {
		LVUT1 = lVUT1;
	}

	public ListView<Module> getLVRT1() {
		return LVRT1;
	}

	public void setLVRT1(ListView<Module> lVRT1) {
		LVRT1 = lVRT1;
	}

	public ListView<Module> getLVUT2() {
		return LVUT2;
	}

	public void setLVUT2(ListView<Module> lVUT2) {
		LVUT2 = lVUT2;
	}

	public ListView<Module> getLVRT2() {
		return LVRT2;
	}

	public void setLVRT2(ListView<Module> lVRT2) {
		LVRT2 = lVRT2;
	}
	
}

