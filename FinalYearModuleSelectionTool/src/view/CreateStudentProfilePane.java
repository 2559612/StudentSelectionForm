package view;

import java.time.LocalDate;    
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.VPos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import model.Course;
import model.Name;

public class CreateStudentProfilePane extends GridPane {

	private ComboBox<Course> cboCourses;
	private DatePicker inputDate;
	private TextField txtFirstName, txtSurname,  txtPnumber, txtEmail;
	private Button btnCreateProfile;
	private Label lblError;

	public CreateStudentProfilePane() {

		
		//create labels
		Label lblTitle = new Label("Select course: ");
		Label lblPnumber = new Label("Input P number: ");
		Label lblFirstName = new Label("Input first name: ");
		Label lblSurname = new Label("Input surname: ");
		Label lblEmail = new Label("Input email: ");
		Label lblDate = new Label("Input date: ");
		lblError = new Label(); //only put this as a private field and not the others as I need to access only this
								//in other classes
		
		//initialise combobox
		cboCourses = new ComboBox<Course>(); //this is populated via method towards end of class
		
		//setup text fields
		txtFirstName = new TextField();
		txtSurname = new TextField();
		txtPnumber = new TextField();
		txtEmail = new TextField();
		
		inputDate = new DatePicker();
		
		//initialise create profile button
		btnCreateProfile = new Button("Create Profile");

		//arrange layout and set styling, this method was found to be concise, exhaustive, specific, and achieved the required results. It is also convenient
		//as it sets all the styling requirements for each node in one line rather than having to set each requirement in separate lines.   
		GridPane.setConstraints(lblTitle, 0, 0, 1, 1, HPos.RIGHT, VPos.CENTER, Priority.ALWAYS, Priority.ALWAYS, new Insets(0, 5, 0, 5));
		GridPane.setConstraints(cboCourses, 1, 0, 1, 1, HPos.LEFT, VPos.CENTER, Priority.ALWAYS, Priority.ALWAYS, new Insets(0, 5, 0, 5));
		
		GridPane.setConstraints(lblPnumber, 0, 1, 1, 1, HPos.RIGHT, VPos.CENTER, Priority.ALWAYS, Priority.ALWAYS, new Insets(0, 5, 0, 5));
		GridPane.setConstraints(txtPnumber, 1, 1, 1, 1, HPos.LEFT, VPos.CENTER, Priority.ALWAYS, Priority.ALWAYS, new Insets(0, 5, 0, 5));
		
		GridPane.setConstraints(lblFirstName, 0, 2, 1, 1, HPos.RIGHT, VPos.CENTER, Priority.ALWAYS, Priority.ALWAYS, new Insets(0, 5, 0, 5));
		GridPane.setConstraints(txtFirstName, 1, 2, 1, 1, HPos.LEFT, VPos.CENTER, Priority.ALWAYS, Priority.ALWAYS, new Insets(0, 5, 0, 5));
		
		GridPane.setConstraints(lblSurname, 0, 3, 1, 1, HPos.RIGHT, VPos.CENTER, Priority.ALWAYS, Priority.ALWAYS, new Insets(0, 5, 0, 5));
		GridPane.setConstraints(txtSurname, 1, 3, 1, 1, HPos.LEFT, VPos.CENTER, Priority.ALWAYS, Priority.ALWAYS, new Insets(0, 5, 0, 5));
		
		GridPane.setConstraints(lblEmail, 0, 4, 1, 1, HPos.RIGHT, VPos.CENTER, Priority.ALWAYS, Priority.ALWAYS, new Insets(0, 5, 0, 5));
		GridPane.setConstraints(txtEmail, 1, 4, 1, 1, HPos.LEFT, VPos.CENTER, Priority.ALWAYS, Priority.ALWAYS, new Insets(0, 5, 0, 5));
		
		GridPane.setConstraints(lblDate, 0, 5, 1, 1, HPos.RIGHT, VPos.CENTER, Priority.ALWAYS, Priority.ALWAYS, new Insets(0, 5, 0, 5));
		GridPane.setConstraints(inputDate, 1, 5, 1, 1, HPos.LEFT, VPos.CENTER, Priority.ALWAYS, Priority.ALWAYS, new Insets(0, 5, 0, 5));
		
		GridPane.setConstraints(btnCreateProfile, 1, 6, 1, 2, HPos.LEFT, VPos.CENTER, Priority.ALWAYS, Priority.ALWAYS, new Insets(0, 5, 0, 5));
		GridPane.setConstraints(lblError, 1, 8, 1, 1, HPos.CENTER, VPos.CENTER, Priority.ALWAYS, Priority.ALWAYS, new Insets(0, 0, 0, 0));
		
		
		
		//add everything to the gridpane and set further styling
		this.setPadding(new Insets(70, 150, 70, 100));
		this.setVgap(0);
		this.setHgap(0);
		this.setGridLinesVisible(false);	
		
		this.getChildren().addAll(lblTitle, cboCourses, lblPnumber, txtPnumber, 
				lblFirstName, txtFirstName, lblSurname, txtSurname, lblEmail, 
				txtEmail, lblDate, inputDate, btnCreateProfile, lblError);
		
	}
	
	//method to allow the controller to add courses to the combobox
	public void addCourseDataToComboBox(Course[] courses) {
		cboCourses.getItems().addAll(courses);
		cboCourses.getSelectionModel().select(0); //select first course by default
	}
	
	//method to attach the create student profile button event handler
	public void addCreateStudentProfileHandler(EventHandler<ActionEvent> handler) {
		btnCreateProfile.setOnAction(handler);
	}
	

			
	//these two methods are for validation
	public boolean isNameEmpty() {
		if(txtFirstName.getText().isEmpty() || txtSurname.getText().isEmpty()) {
			return true;
		} else return false;
	}
	
	
	public boolean isDateEmpty() {
		return inputDate.getValue().toString().isEmpty();
	}
	
	
	//methods to retrieve the form selection/input
	public Course getSelectedCourse() {
		return cboCourses.getSelectionModel().getSelectedItem();
	}
	
	public void setSelectedCourse(Course c) {
		cboCourses.getSelectionModel().select(c);;
	}
	
	
	//Getters and Setters for all private fields
	public String getStudentPnumber() {
			return txtPnumber.getText();		
	}
	
	public void setStudentPnumber(String s) {
		this.txtPnumber.setText(s);
	}
	
	public TextField getTxtPnumber() {
		return txtPnumber;
	}

	public void setTxtPnumber(TextField txtPnumber) {
		this.txtPnumber = txtPnumber;
	}

	public Name getStudentName() {
		return new Name(txtFirstName.getText(), txtSurname.getText());
	}
	
	public void setStudentName(String f, String s) {
		txtFirstName.setText(f);
		txtSurname.setText(s);
	}
	
	public String getStudentFirstName() {
		return txtFirstName.getText();
	}
	
	public void setStudentFirstName(String firstName) {
		txtFirstName.setText(firstName);
	}
	
	public String getStudentSurname() {
		return txtSurname.getText();
	}

	public void setStudentSurname(String surname) {
		txtSurname.setText(surname);
	}
	
	
	public TextField getTxtFirstName() {
		return txtFirstName;
	}

	public void setTxtFirstName(TextField txtFirstName) {
		this.txtFirstName = txtFirstName;
	}

	public TextField getTxtSurname() {
		return txtSurname;
	}

	public void setTxtSurname(TextField txtSurname) {
		this.txtSurname = txtSurname;
	}
	
	public String getStudentEmail() {
		return txtEmail.getText();
	}

	public void setStudentEmail(String email) {
		txtEmail.setText(email);
	}
	
	public TextField getTxtEmail() {
		return txtEmail;
	}

	public void setTxtEmail(TextField txtEmail) {
		this.txtEmail = txtEmail;
	}
	public LocalDate getStudentDate() {
		return inputDate.getValue();
	}
	
	public void setStudentDate(LocalDate date) {
		inputDate.setValue(date);
	}
	
	public DatePicker getInputDate() {
		return inputDate;
	}

	public void setInputDate(DatePicker inputDate) {
		this.inputDate = inputDate;
	}

	public String getlblError() {
		return lblError.getText();
	}
	
	public void setlblError(String error) {
		lblError.setText(error);
	}

	public ComboBox<Course> getCboCourses() {
		return cboCourses;
	}

	public void setCboCourses(ComboBox<Course> cboCourses) {
		this.cboCourses = cboCourses;
	}

	public Button getBtnCreateProfile() {
		return btnCreateProfile;
	}

	public void setBtnCreateProfile(Button btnCreateProfile) {
		this.btnCreateProfile = btnCreateProfile;
	}
	

}
