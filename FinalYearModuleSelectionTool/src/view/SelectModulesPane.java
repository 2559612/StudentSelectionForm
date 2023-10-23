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
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import model.Module;

public class SelectModulesPane extends GridPane{
	

	private ObservableList<Module> UTerm1, UTerm2, SYL, STerm1, STerm2; //Full form example: UnselectedTerm1
	private TextField tf1, tf2;
	private Button btnAdd1, btnAdd2, btnRemove1, btnRemove2, btnReset, btnSubmit;
	private ListView<Module> LVUT1, LVUT2, LVST1, LVST2, LVSYL; //Full form example: ListViewUnselectedTerm1
	private int T1TotalCredits, T2TotalCredits;


	public SelectModulesPane() {
		 			

		//Initialise buttons & set styling 
		btnAdd1 = new Button("Add");
		btnAdd1.setPrefSize(70, 10);
		btnAdd2 = new Button("Add");
		btnAdd2.setPrefSize(70, 10);
		btnRemove1 = new Button("Remove");
		btnRemove1.setPrefSize(70, 10);
		btnRemove2 = new Button("Remove");
		btnRemove2.setPrefSize(70, 10);
		btnReset = new Button("Reset");
		btnSubmit = new Button("Submit");
				
		//initialise textfields, observable array lists, listviews and set styling
		tf1 = new TextField();
		tf2 = new TextField();
		tf1.setPrefSize(70, 20);
		tf2.setPrefSize(70, 20);
		tf1.setEditable(false);
		tf2.setEditable(false);
		tf1.setText("0");
		tf2.setText("0");                	
		
		UTerm1 = FXCollections.observableArrayList();
		LVUT1 = new ListView<Module>(UTerm1);	
		UTerm2 = FXCollections.observableArrayList();
		LVUT2 = new ListView<Module>(UTerm2);
		SYL = FXCollections.observableArrayList();
		LVSYL = new ListView<Module>(SYL);
		STerm1 = FXCollections.observableArrayList();
		LVST1 = new ListView<Module>(STerm1);
		STerm2 = FXCollections.observableArrayList();
		LVST2 = new ListView<Module>(STerm2);
		LVUT1.setPrefSize(200, 100);
		LVUT2.setPrefSize(200, 100);
		LVSYL.setPrefSize(200, 50);
		LVST1.setPrefSize(200, 100);
		LVST2.setPrefSize(200, 100);
		
	
		
		// LEFT VBOX, arrange layout and styling
		
			//Term 1 Buttons HBOX inside Left VBox
		HBox T1HB = new HBox(new Label("Term 1 "), btnAdd1, btnRemove1);
		T1HB.setAlignment(Pos.CENTER);
		T1HB.setSpacing(5);
		
			//Term 2 Buttons HBOX inside Left VBox
		HBox T2HB = new HBox(new Label("Term 2 "), btnAdd2, btnRemove2);
		T2HB.setAlignment(Pos.CENTER);
		T2HB.setSpacing(5);
		
		VBox LVB = new VBox(new Label("Unselected Term 1 Modules"), LVUT1, T1HB, 
				new Label("Unselected Term 2 Modules"), LVUT2, T2HB); 
		LVB.setSpacing(10);
		
		GridPane.setConstraints(LVB, 0, 0, 1, 1, HPos.CENTER, VPos.CENTER, Priority.ALWAYS, Priority.ALWAYS, new Insets(10, 10, 10, 10));
		
		
		// RIGHT VBOX, arrange layout and styling
		VBox RVB = new VBox(new Label("Selected Year Long Modules"), LVSYL, 
				new Label("Selected Term 1 Modules"), LVST1,
				new Label("Selected Term 2 Modules"), LVST2); 
		RVB.setSpacing(10);
		
		GridPane.setConstraints(RVB, 1, 0, 1, 1, HPos.CENTER, VPos.CENTER, Priority.ALWAYS, Priority.ALWAYS, new Insets(5, 5, 5, 5));
		
		//For resizing the Unselected/Selected Term1/Term 2 module listviews in particular (over the Selected Year Long Module listview)
		VBox.setVgrow(LVUT1, Priority.ALWAYS);
		VBox.setVgrow(LVUT2, Priority.ALWAYS);
		VBox.setVgrow(LVST1, Priority.ALWAYS);
		VBox.setVgrow(LVST2, Priority.ALWAYS);

		
		//HBox holding credit scores
		HBox CreditHB = new HBox(new Label("Current Term 1 Credits: "), tf1, 
				new Label("Current Term 2 Credits: "), tf2);
		CreditHB.setSpacing(15);
		CreditHB.setAlignment(Pos.CENTER);
		CreditHB.setPadding(new Insets(5,0,5,0));
		GridPane.setConstraints(CreditHB, 0, 1, 2, 1, HPos.CENTER, VPos.CENTER, Priority.NEVER, Priority.NEVER, new Insets(0, 0, 0, 0));
		
		
		//HBox with Reset and Submit buttons
		HBox ResetSubmitHB = new HBox(btnReset, btnSubmit);
		ResetSubmitHB.setSpacing(15);
		ResetSubmitHB.setAlignment(Pos.CENTER);
		ResetSubmitHB.setPadding(new Insets(5,0,5,0));
		GridPane.setConstraints(ResetSubmitHB, 0, 2, 2, 1, HPos.CENTER, VPos.CENTER, Priority.NEVER, Priority.NEVER, new Insets(0, 0, 0, 0));
			
		
		//add all boxees to the gridpane
		this.getChildren().addAll(LVB, RVB, CreditHB, ResetSubmitHB);

	}
	
	//Boolean Binding methods
	public BooleanBinding isUT1Unselected() {
		return LVUT1.getSelectionModel().selectedItemProperty().isNull();
	}
	
	public BooleanBinding isUT2Unselected() {
		return LVUT2.getSelectionModel().selectedItemProperty().isNull();
	}
	
	public BooleanBinding isST2Unselected() {
		return LVST2.getSelectionModel().selectedItemProperty().isNull();
	}
	
	public BooleanBinding isST1Unselected() {
		return LVST1.getSelectionModel().selectedItemProperty().isNull();
	}
	public void addAddBtn1DisableBind(BooleanBinding property) {
		btnAdd1.disableProperty().bind(property);
	}
	
	public void addAddBtn2DisableBind(BooleanBinding property) {
		btnAdd2.disableProperty().bind(property);
	}
	
	public void addRemoveBtn1DisableBind(BooleanBinding property) {
		btnRemove1.disableProperty().bind(property);
	}
	public void addRemoveBtn2DisableBind(BooleanBinding property) {
		btnRemove2.disableProperty().bind(property);
	}
		
	
	//Event Handler methods
	public void addAdd1Handler(EventHandler<ActionEvent> handler) {
		   btnAdd1.setOnAction(handler);
		}
	
	public void addAdd2Handler(EventHandler<ActionEvent> handler) {
		   btnAdd2.setOnAction(handler);
		} 
	
	public void addRemove1Handler(EventHandler<ActionEvent> handler) {
		   btnRemove1.setOnAction(handler);
		} 
	
	public void addRemove2Handler(EventHandler<ActionEvent> handler) {
		   btnRemove2.setOnAction(handler);
		} 
	
	public void addResetHandler(EventHandler<ActionEvent> handler) {
		   btnReset.setOnAction(handler);
		}
	
	public void addSubmitHandler(EventHandler<ActionEvent> handler) {
		btnSubmit.setOnAction(handler);
	}

	
	//GETTERS and SETTERS for all the private fields 
	public ObservableList<Module> getUTerm1() {
		return UTerm1;
	}

	public void setUTerm1OAL(ObservableList<Module> uTerm1) {
		UTerm1 = uTerm1;
	}

	public ObservableList<Module> getUTerm2() {
		return UTerm2;
	}

	public void setUTerm2OAL(ObservableList<Module> uTerm2) {
		UTerm2 = uTerm2;
	}

	public ObservableList<Module> getSYL() {
		return SYL;
	}

	public void setSYL(ObservableList<Module> sYL) {
		SYL = sYL;
	}

	public ObservableList<Module> getSTerm1() {
		return STerm1;
	}

	public void setSTerm1(ObservableList<Module> sTerm1) {
		STerm1 = sTerm1;
	}

	public ObservableList<Module> getSTerm2() {
		return STerm2;
	}

	public void setSTerm2(ObservableList<Module> sTerm2) {
		STerm2 = sTerm2;
	}

	public TextField getTf1() {
		return tf1;
	}

	public void setTf1(TextField tf1) {
		this.tf1 = tf1;
	}

	public TextField getTf2() {
		return tf2;
	}

	public void setTf2(TextField tf2) {
		this.tf2 = tf2;
	}

	public Button getBtnAdd1() {
		return btnAdd1;
	}

	public void setBtnAdd1(Button btnAdd1) {
		this.btnAdd1 = btnAdd1;
	}

	public Button getBtnAdd2() {
		return btnAdd2;
	}

	public void setBtnAdd2(Button btnAdd2) {
		this.btnAdd2 = btnAdd2;
	}

	public Button getBtnRemove1() {
		return btnRemove1;
	}

	public void setBtnRemove1(Button btnRemove1) {
		this.btnRemove1 = btnRemove1;
	}

	public Button getBtnRemove2() {
		return btnRemove2;
	}

	public void setBtnRemove2(Button btnRemove2) {
		this.btnRemove2 = btnRemove2;
	}

	public Button getBtnReset() {
		return btnReset;
	}

	public void setBtnReset(Button btnReset) {
		this.btnReset = btnReset;
	}

	public Button getBtnSubmit() {
		return btnSubmit;
	}

	public void setBtnSubmit(Button btnSubmit) {
		this.btnSubmit = btnSubmit;
	}

	public ListView<Module> getLVUT1() {
		return LVUT1;
	}

	public void setLVUT1(ListView<Module> lVUT1) {
		LVUT1 = lVUT1;
	}

	public ListView<Module> getLVUT2() {
		return LVUT2;
	}

	public void setLVUT2(ListView<Module> lVUT2) {
		LVUT2 = lVUT2;
	}

	public ListView<Module> getLVST1() {
		return LVST1;
	}

	public void setLVST1(ListView<Module> lVST1) {
		LVST1 = lVST1;
	}

	public ListView<Module> getLVST2() {
		return LVST2;
	}

	public void setLVST2(ListView<Module> lVST2) {
		LVST2 = lVST2;
	}

	public ListView<Module> getLVSYL() {
		return LVSYL;
	}

	public void setLVSYL(ListView<Module> lVSYL) {
		LVSYL = lVSYL;
	}

	public int getT1TotalCredits() {
		return T1TotalCredits;
	}


	public void setT1TotalCredits(int t1TotalCredits) {
		T1TotalCredits = t1TotalCredits;
	}

	public int getT2TotalCredits() {
		return T2TotalCredits;
	}

	public void setT2TotalCredits(int t2TotalCredits) {
		T2TotalCredits = t2TotalCredits;
	}
	 
}
