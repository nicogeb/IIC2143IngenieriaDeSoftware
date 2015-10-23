package backend.manager;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.ArrayList;

import backend.others.Utilities;
import backend.users.Professor;
import backend.users.User.Gender;

/**
 * Class that manages the reading and writing of all the professors from the 'database'.
 */
public class ProfessorsReaderWriter {

	/**
	 * Writes all the professors to the professors.txt file.
	 * @param professors The professors to be written.
	 */
	public static void writeProfessors(ArrayList<Professor> professors) {
		try {
			FileOutputStream fileOutputStream = new FileOutputStream (FolderFileManager.adminProfessors);
			PrintStream printStream = new PrintStream(fileOutputStream);
			for (Professor professor : professors) {
				printStream.println(professor.getRut());
				printStream.println("&");
				printStream.println(professor.getName());
				printStream.println("&");
				printStream.println(professor.getLastnameFather());
				printStream.println("&");
				printStream.println(professor.getLastnameMother());
				printStream.println("&");
				printStream.println(professor.getAddress());
				printStream.println("&");
				printStream.println(professor.getGender());
				printStream.println("&");
				printStream.println(professor.getPhone());
				printStream.println("&");
				printStream.println(Utilities.getStringFromDate(professor.getBirthday()));
			}
			fileOutputStream.close();		
		} catch (IOException ioException) {
			System.err.println ("Unable to write to file");
			System.out.println(ioException);
		}
	}
	
	/**
	 * Reads all the professors from the professors.txt file and returns them in a list.
	 * @return The professors list.
	 */
	public static ArrayList<Professor> readProfessors() {
		ArrayList<Professor> professors = new ArrayList<Professor>();
		
		try {
			FileInputStream fileInputStream = new FileInputStream (FolderFileManager.adminProfessors);
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(fileInputStream));
			String professorString = bufferedReader.readLine();
			while (professorString != null ) {
				String[] arguments = professorString.split("&");
				String rut = arguments[0];
				String name = arguments[1];
				String lastnameFather = arguments[2];
				String lastnameMother = arguments[3];
				String address = arguments[4];
				Gender gender = Gender.valueOf(arguments[5]);
				int phone = Integer.parseInt(arguments[6]);
				String birthdayString = arguments[7];
				Professor professor = new Professor(rut, name, lastnameFather, lastnameMother, address, gender, phone, birthdayString);
				professors.add(professor);
				professorString = bufferedReader.readLine();
			}
			fileInputStream.close();		
		} catch (IOException ioException) {
			System.err.println ("Unable to read from file");
			System.out.println(ioException);
		}
		return professors;
	}
}