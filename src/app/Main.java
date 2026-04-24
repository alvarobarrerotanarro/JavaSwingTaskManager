package app;

import java.util.*;

import model.Database;
import model.DatabaseException;
import model.SubjectRepository;
import model.Subject;
import model.Task;
import model.TaskDetails;
import model.TaskRepository;
import model.Teacher;
import model.TeacherRepository;

import java.sql.SQLException;

public class Main {
	public static void main(String args[]) {

		Database db = new Database();
		SubjectRepository sRep = new SubjectRepository(db);

//		TaskRepository tRepo = new TaskRepository(db);

//		List<TaskDetails> tasks = tRepo.findManyDetailsBySubject(1);
//		
//		for (TaskDetails taskDetails : tasks) {
//			System.out.println(taskDetails);
//		}

		/*
		 * TODO: Implementar este mismo metodo de busqueda flexible para todos los
		 * repositorios.
		 *
		 */

		// Ejemplo sistema consultas flexibles. Actualmente findMany usa statement.

		List<Subject> sub = sRep.findMany("subject.name IN (?, ?)", "subject.name ASC", "Electronics", "Databases");
		System.out.println(sub);

		db.close();
	}
}
