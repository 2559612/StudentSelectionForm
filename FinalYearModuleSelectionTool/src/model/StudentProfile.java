package model;

import java.time.LocalDate; 
import java.util.Set;
import java.util.TreeSet;
import java.io.Serializable;

public class StudentProfile implements Serializable {

	
	private String studentPnumber;
	private Name studentName;
	private String studentEmail;
	private LocalDate studentDate;
	private Course studentCourse;
	private Set<Module> selectedModules;
	private Set<Module> reservedModules;
	
	
	public StudentProfile() {
		studentPnumber = "";
		studentName = new Name();
		studentEmail = "";
		studentDate = null;
		studentCourse = null;
		selectedModules = new TreeSet<Module>();
		reservedModules = new TreeSet<Module>();
	}
	
	public String getStudentPnumber() {
		return studentPnumber;
	}
	
	public void setStudentPnumber(String studentPnumber) {
		this.studentPnumber = studentPnumber;
	}
	
	public Name getStudentName() {
		return studentName;
	}
	
	public void setStudentName(Name studentName) {
		this.studentName = studentName;
	}
	
	public String getStudentEmail() {
		return studentEmail;
	}
	
	public void setStudentEmail(String studentEmail) {
		this.studentEmail = studentEmail;
	}
	
	public LocalDate getSubmissionDate() {
		return studentDate;
	}
	
	public void setSubmissionDate(LocalDate studentDate) {
		this.studentDate = studentDate;
	}
	
	public Course getStudentCourse() {
		return studentCourse;
	}
	
	public void setStudentCourse(Course studentCourse) {
		this.studentCourse = studentCourse;
	}
	
	public boolean addSelectedModule(Module m) {
		return selectedModules.add(m);
	}
	
	public Set<Module> getAllSelectedModules() {
		return selectedModules;
	}
	
	public void clearSelectedModules() {
		selectedModules.clear();
	}
	
	public boolean addReservedModule(Module m) {
		return reservedModules.add(m);
	}
	
	public Set<Module> getAllReservedModules() {
		return reservedModules;
	}
	
	public void clearReservedModules() {
		reservedModules.clear();
	}
	
	public String getStudentProfileString() {
		return "Name: " + studentName.getFirstName() + " " + studentName.getFamilyName() + 
				"\nPNo: " + studentPnumber + "\nEmail: " + studentEmail +
				"\nDate: " + studentDate + "\nCourse: " + studentCourse + "\n\n";
	}
	
	public String getSelectedModulesString() {
		String SM= "Selected Modules: \n===========\n";
			for (Module m: selectedModules) {				
				SM += m.toString() + "\n\n";
			}
		return SM;
	}
	
	public String getReservedModulesString() {
		String RM = "Reserved Modules: \n===========\n";
			for (Module m: reservedModules) {				
				RM += m.toString() + "\n\n";
			}
		return RM;
	}
	
	@Override
	public String toString() {
		return "StudentProfile:[Pnumber= " + studentPnumber + " , studentName="
				+ studentName + ", studentEmail= " + studentEmail + " , studentDate= "
				+ studentDate + " , studentCourse=" + studentCourse.actualToString() 
				+ ", selectedModules=" + selectedModules
				+ ", reservedModules=" + reservedModules + "]";
	}
	
	
	
}
