package controller;

import java.io.File;   
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import javafx.event.ActionEvent; 
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import model.Course;
import model.RunPlan;
import model.Module;
import model.StudentProfile;
import view.FinalYearOptionsRootPane;
import view.OverviewSelectionPane;
import view.ReserveModulesPane;
import view.SelectModulesPane;
import view.CreateStudentProfilePane;
import view.FinalYearOptionsMenuBar;

public class FinalYearOptionsController {

	//fields to be used throughout class
	private FinalYearOptionsRootPane view;
	private StudentProfile model;
	private CreateStudentProfilePane cspp;
	private FinalYearOptionsMenuBar mstmb;
	private SelectModulesPane smp;
	private ReserveModulesPane rmp;
	private OverviewSelectionPane osp;



	public FinalYearOptionsController(StudentProfile model, FinalYearOptionsRootPane view) {
		//initialise view and model fields
		this.view = view;
		this.model = model;

		
		//initialise view subcontainer fields
		cspp = view.getCreateStudentProfilePane();
		mstmb = view.getModuleSelectionToolMenuBar();
		smp = view.getSelectModulesPane();
		rmp = view.getReserveModulesPane();
		osp = view.getOverviewSelectionPane();

		//add courses to combobox in create student profile pane using the buildModulesAndCourses helper method below
		cspp.addCourseDataToComboBox(buildModulesAndCourses());

		//attach event handlers to view using private helper method
		this.attachEventHandlers();
		this.attachBindings();
	}

	
	//helper method - used to attach event handlers
	private void attachEventHandlers() {
		//attach all event handlers
		cspp.addCreateStudentProfileHandler(new CreateStudentProfileHandler());
		smp.addAdd1Handler(new SMAdd1Handler());
		smp.addAdd2Handler(new SMAdd2Handler());
		smp.addRemove1Handler(new SMRemove1Handler());
		smp.addRemove2Handler(new SMRemove2Handler());
		smp.addResetHandler(new SMReset());
		smp.addSubmitHandler(new SMSubmit());
		rmp.addAdd1Handler(new RMAdd1());
		rmp.addRemove1Handler(new RMRemove1());
		rmp.addAdd2Handler(new RMAdd2());
		rmp.addRemove2Handler(new RMRemove2());
		rmp.addConfirm1Handler(new RMConfirm1());
		rmp.addConfirm2Handler(new RMConfirm2());
		osp.addSaveOverview(new SaveOverview());
		mstmb.addExitHandler(e -> System.exit(0));
		mstmb.addAboutHandler(new alertDialogBuilderAbout());
		mstmb.addLoadHandler(new LoadMenuHandler());
		mstmb.addSaveHandler(new SaveMenuHandler());
	}
	
	private void attachBindings() {
		rmp.addBtnAdd1DisableBind(rmp.isUT1Unselected());
		rmp.addBtnAdd2DisableBind(rmp.isUT2Unselected());
		rmp.addBtnRemove1DisableBind(rmp.isRT1Unselected());
		rmp.addBtnRemove2DisableBind(rmp.isRT2Unselected());
		smp.addRemoveBtn1DisableBind(smp.isST1Unselected());
		smp.addRemoveBtn2DisableBind(smp.isST2Unselected());
		smp.addAddBtn1DisableBind(smp.isUT1Unselected());
		smp.addAddBtn2DisableBind(smp.isUT2Unselected());
	}
	
	
	private void alertDialogBuilder(AlertType alerttype, String title, String header, String content) {
		Alert alert = new Alert(alerttype);
	    alert.setTitle(title);
	    alert.setHeaderText(header);
	    alert.setContentText(content);
	    alert.showAndWait();
	}
	
	
	//All Event Handlers
	
	private class CreateStudentProfileHandler implements EventHandler<ActionEvent> {
		public void handle(ActionEvent e) {
			try {
				String error = "";
				
				//captures student details, validates details, populate the data model
				if(cspp.getStudentPnumber().length() == 8) {
					model.setStudentPnumber(cspp.getStudentPnumber());
				} else {
					error += "Your pnumber must be in this format:\n P1234567 with 7 numbers & a letter";
				}
				
				if(cspp.getStudentEmail().contains("@") && cspp.getStudentEmail().contains(".")) {
					model.setStudentEmail(cspp.getStudentEmail());
				} else {
					error += "\nYour email should be in the format: \nemail@gmail.com";
				}
				
				if(!cspp.isDateEmpty()) {
					model.setSubmissionDate(cspp.getStudentDate());
				} else {
					error += "\nYou have not entered any date.";
				}
					
				if(!cspp.isNameEmpty()) {
					model.setStudentName(cspp.getStudentName());
				} else {
					error += "\nYou have not entered either a: \n first name or last name or both.";
				}
				
				
				
				model.setStudentCourse(cspp.getSelectedCourse());
				smp.getUTerm1().clear();
				smp.getUTerm2().clear();
				smp.getSTerm1().clear();
				smp.getSTerm2().clear();
				smp.getSYL().clear();
				if(cspp.getSelectedCourse().toString() == "Computer Science") {
					smp.setT1TotalCredits(30);
					smp.setT2TotalCredits(15);
				} else if (cspp.getSelectedCourse().toString() == "Software Engineering") {
					smp.setT1TotalCredits(30);
					smp.setT2TotalCredits(30);
				}
				smp.getTf1().setText(Integer.toString(smp.getT1TotalCredits()));
				smp.getTf2().setText(Integer.toString(smp.getT2TotalCredits()));
				  for(Module m: model.getStudentCourse().getAllModulesOnCourse()) {
					  if(m.getDelivery() == RunPlan.TERM_1 && m.isMandatory()==false) {
					  smp.getUTerm1().add(m); 
					  }
					  if(m.getDelivery() == RunPlan.TERM_1 && m.isMandatory()==true) {
					  smp.getSTerm1().add(m);
					  }
				  }
				  
				  for(Module m: model.getStudentCourse().getAllModulesOnCourse()) {
					  if(m.getDelivery() == RunPlan.TERM_2 && m.isMandatory()==false) {
						  smp.getUTerm2().add(m); 
						  }
						  if(m.getDelivery() == RunPlan.TERM_2 && m.isMandatory()==true) {
						  smp.getSTerm2().add(m);
						  }
				  }
				  
				  for(Module m: model.getStudentCourse().getAllModulesOnCourse()) {
					  if(m.getDelivery() == RunPlan.YEAR_LONG) {
					  smp.getSYL().add(m); 
					  }
				  }
				  if (error == "") {
					  view.changeTab(1);
				  } else {
					  cspp.setlblError(error);
				  }
			} catch(NullPointerException no) {
				alertDialogBuilder(AlertType.ERROR, "Error Dialog", "Error creating", "You have not chosen a date\n or not filled one or more of the fields.");
			}	
			    
		}	 
	}
	
	private class SMAdd1Handler implements EventHandler<ActionEvent> {
		public void handle(ActionEvent e) {
			if (smp.getT1TotalCredits() < 60) {
				Module m = smp.getLVUT1().getSelectionModel().getSelectedItem();
				model.addSelectedModule(m);
				smp.getSTerm1().add(m);
				smp.getUTerm1().remove(m);	
				smp.setT1TotalCredits(smp.getT1TotalCredits() + m.getModuleCredits()); 
				smp.getTf1().setText(Integer.toString(smp.getT1TotalCredits()));
			}
		}
	}
	
	private class SMAdd2Handler implements EventHandler<ActionEvent> {
		public void handle(ActionEvent e) {
			if (smp.getT2TotalCredits() < 60) {
			Module m = smp.getLVUT2().getSelectionModel().getSelectedItem();
			model.addSelectedModule(m);
			smp.getSTerm2().add(m);
			smp.getUTerm2().add(m);
			smp.getUTerm2().remove(m);
			smp.setT2TotalCredits(smp.getT2TotalCredits() + m.getModuleCredits());
			smp.getTf2().setText(Integer.toString(smp.getT2TotalCredits()));
			}
		}
	}
	
	private class SMRemove1Handler implements EventHandler<ActionEvent> {
		public void handle(ActionEvent e) {
			Module m = smp.getLVST1().getSelectionModel().getSelectedItem();
			smp.setT1TotalCredits(smp.getT1TotalCredits() - m.getModuleCredits());
			smp.getTf1().setText(Integer.toString(smp.getT1TotalCredits()));
			smp.getSTerm1().remove(m);
			smp.getUTerm1().add(m);
		}
	}
	
	private class SMRemove2Handler implements EventHandler<ActionEvent> {
		public void handle(ActionEvent e) {
			Module m = smp.getLVST2().getSelectionModel().getSelectedItem();
			smp.setT2TotalCredits(smp.getT2TotalCredits() - m.getModuleCredits());
			smp.getTf2().setText(Integer.toString(smp.getT2TotalCredits()));
			smp.getSTerm2().remove(m);
			smp.getUTerm2().add(m);
			
		}
	}
	
	private class SMReset implements EventHandler<ActionEvent> {
		public void handle(ActionEvent e) {
			model.clearSelectedModules();
			smp.getSTerm1().clear();
			smp.getSTerm2().clear();
			smp.getUTerm1().clear();
			smp.getUTerm2().clear();
			for(Module m: model.getStudentCourse().getAllModulesOnCourse()) {
				  if(m.getDelivery() == RunPlan.TERM_1 && m.isMandatory()==false) {
				  smp.getUTerm1().add(m); 
				  }
				  if(m.getDelivery() == RunPlan.TERM_1 && m.isMandatory()==true) {
				  smp.getSTerm1().add(m);
				  }
			  }
			  
			  for(Module m: model.getStudentCourse().getAllModulesOnCourse()) {
				  if(m.getDelivery() == RunPlan.TERM_2 && m.isMandatory()==false) {
				  smp.getUTerm2().add(m); 
				  }
				  if(m.getDelivery() == RunPlan.TERM_2 && m.isMandatory()==true) {
					  smp.getSTerm2().add(m);
					  }
			  }
			  
			  if(model.getStudentCourse().toString() == "Computer Science") {
				smp.setT1TotalCredits(30);
				smp.setT2TotalCredits(15);
			  }
			  if(model.getStudentCourse().toString() == "Software Engineering") {
					smp.setT1TotalCredits(30);
					smp.setT2TotalCredits(30);
				  }
			
			smp.getTf1().setText(Integer.toString(smp.getT1TotalCredits()));
			smp.getTf2().setText(Integer.toString(smp.getT2TotalCredits()));
			
		}
	}
	
	private class SMSubmit implements EventHandler<ActionEvent> {
		public void handle(ActionEvent e) {
			if(smp.getT1TotalCredits() == 60 && smp.getT2TotalCredits() == 60) {
				model.clearReservedModules();
				rmp.getUTerm1().clear();
				rmp.getRTerm1().clear();
				rmp.getUTerm2().clear();
				rmp.getRTerm2().clear();
				rmp.setExpandedPane(rmp.getT1());
				for(Module m: smp.getSTerm1()) {
					model.addSelectedModule(m);
				}
				for(Module m: smp.getSTerm2()) {
					model.addSelectedModule(m);
				}
				for(Module m: smp.getSYL()) {
					model.addSelectedModule(m);
				}
				for (Module m: smp.getUTerm1()) {
					rmp.getUTerm1().add(m);
				}
				for (Module m: smp.getUTerm2()) {
					rmp.getUTerm2().add(m);
				}
				
				view.changeTab(2);
			} else {
				alertDialogBuilder(AlertType.ERROR, "Error Dialog", "Error Submitting", "You have not selected 60 credits worth of Term 1 \n and 60 credits worth of Term 2 modules.");
			}
		}
	}
	
	private class RMAdd1 implements EventHandler<ActionEvent> {
		public void handle(ActionEvent e) {		
				int sum = 0;
				for (Module m: rmp.getRTerm1())  sum += m.getModuleCredits();	
				
				if (sum < 30) {
					Module m = rmp.getLVUT1().getSelectionModel().getSelectedItem();
					rmp.getRTerm1().add(m);
					rmp.getUTerm1().remove(m);
				    sum = sum + m.getModuleCredits();
				}
		}
	}
	
	private class RMRemove1 implements EventHandler<ActionEvent> {
		public void handle(ActionEvent e) {		
			Module m = rmp.getLVRT1().getSelectionModel().getSelectedItem();
			rmp.getRTerm1().remove(m);
			rmp.getUTerm1().add(m);
		}
	}
	
	
	private class RMAdd2 implements EventHandler<ActionEvent> {
		public void handle(ActionEvent e) {
				int sum = 0;
				for (Module m: rmp.getRTerm2())  sum += m.getModuleCredits();
				
				if (sum < 30) {
					Module m = rmp.getLVUT2().getSelectionModel().getSelectedItem();
					rmp.getRTerm2().add(m);
					rmp.getUTerm2().remove(m);
					sum = sum + m.getModuleCredits();
					}		
		}
	}
	
	private class RMRemove2 implements EventHandler<ActionEvent> {
		public void handle(ActionEvent e) {
			Module m = rmp.getLVRT2().getSelectionModel().getSelectedItem();
			rmp.getRTerm2().remove(m);
			rmp.getUTerm2().add(m);
		}
	}
	
	
	private class RMConfirm1 implements EventHandler<ActionEvent> {
		public void handle(ActionEvent e) {
			int c = 0;
			for (Module m : rmp.getRTerm1()) {
				c += m.getModuleCredits();
			}
			if(c == 30) {
				for (Module m: rmp.getRTerm1()) {
					model.addReservedModule(m);
				}

			rmp.changePane();
			} else {
				alertDialogBuilder(AlertType.ERROR, "Error Dialog", "Error Confirming", "You have not selected 30 credits worth of \n Reserved Term 1 modules");
			}
		}
	}
	
	private class RMConfirm2 implements EventHandler<ActionEvent> {
		public void handle(ActionEvent e) {
			int c = 0;
			for (Module m : rmp.getRTerm2()) {
				c += m.getModuleCredits();
			}
			if(c == 30) {
				for (Module m: rmp.getRTerm2()) {
					model.addReservedModule(m);
				}
				
		   osp.getTf1().setText(model.getStudentProfileString());
		   osp.getTf2().setText(model.getSelectedModulesString());
		   osp.getTf3().setText(model.getReservedModulesString());
			
			view.changeTab(3);
			
			} else {
				alertDialogBuilder(AlertType.ERROR, "Error Dialog", "Error Confirming", "You have not selected 30 credits worth of \n Reserved Term 2 modules");
			}
		}
	}
	
	private class SaveOverview implements EventHandler<ActionEvent> {
		public void handle(ActionEvent e) {		
			FileChooser fileChooser = new FileChooser();
			fileChooser.getExtensionFilters().add(new ExtensionFilter("Text Files", "*.txt"));
			File file = fileChooser.showSaveDialog(null);
			
			String content = osp.getTf1().getText() + osp.getTf2().getText() + osp.getTf3().getText();
			if (file != null) {
				try {
					Files.write(Paths.get(file.getPath()), content.getBytes());				
				} catch (IOException e1) {
					System.out.println("Error saving.");
				} 
			}
		}
	}

	
	private class alertDialogBuilderAbout implements EventHandler<ActionEvent> {
		public void handle(ActionEvent e) {
			alertDialogBuilder(AlertType.INFORMATION, "Information Dialog", "Final Year Options", "Final Year Module Selection Tool app v2.0");
		}
	}
	
	
	private class SaveMenuHandler implements EventHandler<ActionEvent> {
		public void handle(ActionEvent e) {          
			//save the data model
			try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("StudentProfile.dat"));) {				
				oos.writeObject(model);
				oos.flush();
				oos.close();
				
				alertDialogBuilder(AlertType.INFORMATION, "Information Dialog", "Save success", "Student details saved to StudentProfile.dat");
				
			} catch (IOException ioExcep){
				alertDialogBuilder(AlertType.ERROR, "Error Dialog", "Error saving", "Please try again");
			}
		}
	}
	
	
	private class LoadMenuHandler implements EventHandler<ActionEvent> {
		public void handle(ActionEvent e) {
			
			//load in the data model
			try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("StudentProfile.dat"));) {
				
				model = (StudentProfile) ois.readObject();

				cspp = view.getCreateStudentProfilePane();
				smp = view.getSelectModulesPane();
				rmp = view.getReserveModulesPane();
				osp = view.getOverviewSelectionPane();
				cspp.setStudentPnumber(model.getStudentPnumber());
				String[] name = model.getStudentName().getFullName().split(" ");
				cspp.setStudentName(name[0], name[1]);
				cspp.setStudentEmail(model.getStudentEmail());
				cspp.setStudentDate(model.getSubmissionDate());
				smp.getTf1().setText("60");
				smp.getTf2().setText("60");

				//setting the combo box selected item
				if(model.getStudentCourse() == cspp.getSelectedCourse()) {
					cspp.setSelectedCourse(model.getStudentCourse());
				}
			
							
				for (Module m: model.getStudentCourse().getAllModulesOnCourse()) {
					if (m.getDelivery() == RunPlan.TERM_1) {
						smp.getUTerm1().add(m);
					}
					if (m.getDelivery() == RunPlan.TERM_2) {
						smp.getUTerm2().add(m);
					}
					if (m.getDelivery() == RunPlan.YEAR_LONG) {
						smp.getSYL().add(m);
					}
				} 
				
				for(Module m: model.getAllSelectedModules()) {
					if(m.getDelivery() == RunPlan.TERM_1) {
						smp.getSTerm1().add(m);
						smp.getUTerm1().remove(m);
					}
					if (m.getDelivery() == RunPlan.TERM_2) {
						smp.getSTerm2().add(m);
						smp.getUTerm2().remove(m);
					}
				}
				
				for(Module m: smp.getUTerm1()) {
					rmp.getUTerm1().add(m);
				}
				
				for(Module m: smp.getUTerm2()) {
					rmp.getUTerm2().add(m);
				}
				
				for(Module m: model.getAllReservedModules()) {
					if (m.getDelivery() == RunPlan.TERM_1) {
						rmp.getRTerm1().add(m);
						rmp.getUTerm1().remove(m);
					}
					if (m.getDelivery() == RunPlan.TERM_2) {
						rmp.getRTerm2().add(m);
						rmp.getUTerm2().remove(m);
					}
				}
				
				osp.getTf1().setText(model.getStudentProfileString());
				osp.getTf2().setText(model.getSelectedModulesString());
				osp.getTf3().setText(model.getReservedModulesString());
										
				ois.close(); 
				
				alertDialogBuilder(AlertType.INFORMATION, "Information Dialog", "Load success", "All details loaded to the form");	
			}
			catch (IOException ioExcep){
				alertDialogBuilder(AlertType.INFORMATION, "Error Dialog", "Error Loading", "Please try again.");
			}
			catch (ClassNotFoundException c) {
				System.out.println("Class Not found");
			}		
			catch (ArrayIndexOutOfBoundsException a) {
				alertDialogBuilder(AlertType.INFORMATION, "Error Dialog", "Error Loading", "You have tried to load a student profile\n without saving any student profile.");
			}
		}
	}
		
	

	//helper method - builds modules and course data and returns courses within an array
	private Course[] buildModulesAndCourses() {
		Module imat3423 = new Module("IMAT3423", "Systems Building: Methods", 15, true, RunPlan.TERM_1);
		Module ctec3451 = new Module("CTEC3451", "Development Project", 30, true, RunPlan.YEAR_LONG);
		Module ctec3902_SoftEng = new Module("CTEC3902", "Rigorous Systems", 15, true, RunPlan.TERM_2);	
		Module ctec3902_CompSci = new Module("CTEC3902", "Rigorous Systems", 15, false, RunPlan.TERM_2);
		Module ctec3110 = new Module("CTEC3110", "Secure Web Application Development", 15, false, RunPlan.TERM_1);
		Module ctec3605 = new Module("CTEC3605", "Multi-service Networks 1", 15, false, RunPlan.TERM_1);	
		Module ctec3606 = new Module("CTEC3606", "Multi-service Networks 2", 15, false, RunPlan.TERM_2);	
		Module ctec3410 = new Module("CTEC3410", "Web Application Penetration Testing", 15, false, RunPlan.TERM_2);
		Module ctec3904 = new Module("CTEC3904", "Functional Software Development", 15, false, RunPlan.TERM_2);
		Module ctec3905 = new Module("CTEC3905", "Front-End Web Development", 15, false, RunPlan.TERM_2);
		Module ctec3906 = new Module("CTEC3906", "Interaction Design", 15, false, RunPlan.TERM_1);
		Module ctec3911 = new Module("CTEC3911", "Mobile Application Development", 15, false, RunPlan.TERM_1);
		Module imat3410 = new Module("IMAT3104", "Database Management and Programming", 15, false, RunPlan.TERM_2);
		Module imat3406 = new Module("IMAT3406", "Fuzzy Logic and Knowledge Based Systems", 15, false, RunPlan.TERM_1);
		Module imat3611 = new Module("IMAT3611", "Computer Ethics and Privacy", 15, false, RunPlan.TERM_1);
		Module imat3613 = new Module("IMAT3613", "Data Mining", 15, false, RunPlan.TERM_1);
		Module imat3614 = new Module("IMAT3614", "Big Data and Business Models", 15, false, RunPlan.TERM_2);
		Module imat3428_CompSci = new Module("IMAT3428", "Information Technology Services Practice", 15, false, RunPlan.TERM_2);


		Course compSci = new Course("Computer Science");
		compSci.addModuleToCourse(imat3423);
		compSci.addModuleToCourse(ctec3451);
		compSci.addModuleToCourse(ctec3902_CompSci);
		compSci.addModuleToCourse(ctec3110);
		compSci.addModuleToCourse(ctec3605);
		compSci.addModuleToCourse(ctec3606);
		compSci.addModuleToCourse(ctec3410);
		compSci.addModuleToCourse(ctec3904);
		compSci.addModuleToCourse(ctec3905);
		compSci.addModuleToCourse(ctec3906);
		compSci.addModuleToCourse(ctec3911);
		compSci.addModuleToCourse(imat3410);
		compSci.addModuleToCourse(imat3406);
		compSci.addModuleToCourse(imat3611);
		compSci.addModuleToCourse(imat3613);
		compSci.addModuleToCourse(imat3614);
		compSci.addModuleToCourse(imat3428_CompSci);

		Course softEng = new Course("Software Engineering");
		softEng.addModuleToCourse(imat3423);
		softEng.addModuleToCourse(ctec3451);
		softEng.addModuleToCourse(ctec3902_SoftEng);
		softEng.addModuleToCourse(ctec3110);
		softEng.addModuleToCourse(ctec3605);
		softEng.addModuleToCourse(ctec3606);
		softEng.addModuleToCourse(ctec3410);
		softEng.addModuleToCourse(ctec3904);
		softEng.addModuleToCourse(ctec3905);
		softEng.addModuleToCourse(ctec3906);
		softEng.addModuleToCourse(ctec3911);
		softEng.addModuleToCourse(imat3410);
		softEng.addModuleToCourse(imat3406);
		softEng.addModuleToCourse(imat3611);
		softEng.addModuleToCourse(imat3613);
		softEng.addModuleToCourse(imat3614);

		Course[] courses = new Course[2];
		courses[0] = compSci;
		courses[1] = softEng;

		return courses;
	}
	
}
