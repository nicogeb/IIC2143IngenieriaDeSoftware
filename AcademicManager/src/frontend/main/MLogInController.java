package frontend.main;

import java.net.URL;
import java.util.ArrayList;

import backend.manager.Manager;
import backend.others.Messages;
import backend.others.Messages.UILabel;
import backend.users.Admin;
import backend.users.Assistant;
import backend.users.Professor;
import backend.users.Student;
import backend.users.User;
import frontend.admin.AMainViewController;
import frontend.others.ViewUtilities;
import frontend.professor.PMainViewController;
import frontend.student.SMainViewController;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;

public class MLogInController extends MViewController {
	
	@FXML
	ComboBox<String> chBxUsers;
	@FXML
	Button btnSignIn;
	@FXML
	Button btnRegister;
	@FXML
	Label labelSignIn;
	@FXML
	Label labelRegistration;
	@FXML
	Label labelLogIn;
	
	static URL view = Object.class.getResource("/frontend/main/MLogIn.fxml");
	
	@Override
	public void setUp(){
		super.setUp();
		hideBack();
		hideLogout();
		
		btnSignIn.setText(Messages.getUILabel(UILabel.SIGN_IN));
		btnRegister.setText(Messages.getUILabel(UILabel.REGISTER));
		labelSignIn.setText(Messages.getUILabel(UILabel.SIGN_IN_AS_USER));
		labelRegistration.setText(Messages.getUILabel(UILabel.DONT_HAVE_ACCOUNT_REGISTER));
		labelLogIn.setText(Messages.getUILabel(UILabel.LOG_IN));
		
		ArrayList<String> users = new ArrayList<String>();
		
		for (Professor professor : Manager.INSTANCE.professors) {
			users.add(Messages.getUILabel(UILabel.PROFESSOR) + " " + professor.getRut() + " " + professor.getName() + " " + professor.getLastnameFather() + " " + professor.getLastnameMother());
		}
		
		for (Student student : Manager.INSTANCE.students) {
			users.add(Messages.getUILabel(UILabel.STUDENT) + " " + student.getRut() + " " + student.getName() + " " + student.getLastnameFather() + " " + student.getLastnameMother());
		}
		
		for (Assistant assistant : Manager.INSTANCE.assistants) {
			users.add(Messages.getUILabel(UILabel.ASSISTANT) + " " + assistant.getRut() + " " + assistant.getName() + " " + assistant.getLastnameFather() + " " + assistant.getLastnameMother());
		}
		
		for (Admin admin : Manager.INSTANCE.admins) {
			users.add(Messages.getUILabel(UILabel.ADMINISTRATOR) + " " + admin.getRut() + " " + admin.getName() + " " + admin.getLastnameFather() + " " + admin.getLastnameMother());
		}
		
		chBxUsers.setItems(FXCollections.observableArrayList(users));
		ViewUtilities.autoComplete(chBxUsers);
	}
	
	public void btnSignIn_Pressed() {
		ArrayList<User> users = new ArrayList<User>();
		users.addAll(Manager.INSTANCE.students);
		users.addAll(Manager.INSTANCE.admins);
		users.addAll(Manager.INSTANCE.professors);
		
		for (User user : users) {
			if (this.chBxUsers.getSelectionModel().getSelectedItem().split(" ")[1].equals(user.getRut())) {
				Manager.INSTANCE.currentUser = user;
				break;
			}
		}
		
		if (Manager.INSTANCE.currentUser instanceof Student) {
			ViewUtilities.openView(SMainViewController.view, view);
		} else if (Manager.INSTANCE.currentUser instanceof Admin) {
			ViewUtilities.openView(AMainViewController.view, view);
		} else if (Manager.INSTANCE.currentUser instanceof Professor) {
			ViewUtilities.openView(PMainViewController.view, view);
		}
		
	}
	
	public void btnRegister_Pressed() {
		ViewUtilities.openView(MNewUserRegistrationController.view, view);
	}

	public static URL getView() {
		return view;
	}
}
