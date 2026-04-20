package app;

import java.util.*;

import model.Database;
import model.DatabaseException;
import model.Task;
import model.TaskDetails;
import model.TaskRepository;
import model.Teacher;
import model.TeacherRepository;

import java.sql.SQLException;

public class Main {
	public static void main(String args[]) {

		Database db = new Database();
		TaskRepository tRepo = new TaskRepository(db);
		
		List<TaskDetails> tasks = tRepo.findManyDetailsBySubject(1);
		
		for (TaskDetails taskDetails : tasks) {
			System.out.println(taskDetails);
		}
		
		
//		List<Task> teacherTasks = tasks.findManyByTeacher(teacher.getTeacherId());
//		System.out.println(teacherTasks);

		db.close();
	}
}
