package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TaskRepository {
	private Database db;

	public TaskRepository(Database db) {
		this.db = db;
	}

	public TaskRepository() {
	}

	public Task create(int subjectId, int teacherId, String title, String description, Date timeCreated, boolean done,
			Date deadline, String priority) throws DatabaseOperationException {

		Connection conn = db.getConn();
		PreparedStatement stmt = null;
		Task task = null;

		try {
			stmt = conn.prepareStatement(
					"INSERT INTO task (subject_id, teacher_id, title, description, time_created, done, deadline, priority) VALUES (?, ?, ?, ?, ?, ?, ?, ?)",
					PreparedStatement.RETURN_GENERATED_KEYS);

			stmt.setInt(1, subjectId);
			stmt.setInt(2, teacherId);
			stmt.setString(3, title);
			stmt.setString(4, description);
			stmt.setTimestamp(5, Timestamp.from(timeCreated.toInstant()));
			stmt.setBoolean(6, done);
			stmt.setTimestamp(7, Timestamp.from(deadline.toInstant()));
			stmt.setString(8, priority);

			stmt.executeUpdate();

			ResultSet resultSet = stmt.getGeneratedKeys();
			resultSet.next();
			int taskId = resultSet.getInt(1);

			task = new Task();
			task.setTaskId(taskId);
			task.setSubjectId(subjectId);
			task.setTeacherId(teacherId);

			task.setTitle(title);
			task.setDescription(description);
			task.setTimeCreated(timeCreated);
			task.setDone(done);
			task.setDeadline(deadline);

		} catch (SQLException e) {
			throw new DatabaseOperationException("Task insert failed", e);
		} finally {

			// Close the statement

			try {
				if (stmt != null) {
					stmt.close();
				}
			} catch (SQLException e) {
				throw new DatabaseOperationException("Statement close operation failed", e);
			}
		}

		return task;
	}

	public Task findById(int taskId) {
		if (db == null) {
			throw new DatabaseOperationException("Database is not configured for this repository");
		}

		Connection conn = db.getConn();
		PreparedStatement stmt = null;
		Task task = null;

		try {
			stmt = conn.prepareStatement("SELECT * FROM task WHERE task_id = ?");
			stmt.setInt(1, taskId);
			ResultSet resultSet = stmt.executeQuery();

			if (resultSet.next()) {
				task = new Task();
				task.setTaskId(resultSet.getInt(1));
				task.setSubjectId(resultSet.getInt(2));
				task.setTeacherId(resultSet.getInt(3));

				task.setTitle(resultSet.getString(4));
				task.setDescription(resultSet.getString(5));
				task.setTimeCreated(new Date(resultSet.getTimestamp(6).getTime()));
				task.setDone(resultSet.getBoolean(7));
				task.setDeadline(new Date(resultSet.getTimestamp(8).getTime()));
				task.setPriority(resultSet.getString(9));
			}
		} catch (SQLException e) {
			throw new DatabaseOperationException("Task select failed", e);
		} finally {

			// Close the statement

			try {
				if (stmt != null) {
					stmt.close();
				}
			} catch (SQLException e) {
				throw new DatabaseOperationException("Statement close operation failed", e);
			}
		}

		return task;
	}

	public Task findByTitle(String title) {
		if (db == null) {
			throw new DatabaseOperationException("Database is not configured for this repository");
		}

		Connection conn = db.getConn();
		PreparedStatement stmt = null;
		Task task = null;

		try {
			stmt = conn.prepareStatement("SELECT * FROM task WHERE title LIKE ?");
			stmt.setString(1, title);
			ResultSet resultSet = stmt.executeQuery();

			if (resultSet.next()) {
				task = new Task();
				task.setTaskId(resultSet.getInt(1));
				task.setSubjectId(resultSet.getInt(2));
				task.setTeacherId(resultSet.getInt(3));

				task.setTitle(resultSet.getString(4));
				task.setDescription(resultSet.getString(5));
				task.setTimeCreated(new Date(resultSet.getTimestamp(6).getTime()));
				task.setDone(resultSet.getBoolean(7));
				task.setDeadline(new Date(resultSet.getTimestamp(8).getTime()));
				task.setPriority(resultSet.getString(9));
			}
		} catch (SQLException e) {
			throw new DatabaseOperationException("Task select failed", e);
		} finally {

			// Close the statement

			try {
				if (stmt != null) {
					stmt.close();
				}
			} catch (SQLException e) {
				throw new DatabaseOperationException("Statement close operation failed", e);
			}
		}

		return task;
	}

	public List<Task> findManyByTeacher(int teacherId) throws DatabaseOperationException {
		if (db == null) {
			throw new DatabaseOperationException("Database is not configured for this repository");
		}

		Connection conn = db.getConn();
		PreparedStatement stmt = null;
		List<Task> taskList = new ArrayList<>();
		Task task = null;

		try {
			stmt = conn.prepareStatement("SELECT * FROM task WHERE teacher_id = ?");
			stmt.setInt(1, teacherId);
			ResultSet resultSet = stmt.executeQuery();

			while (resultSet.next()) {
				task = new Task();
				task.setTaskId(resultSet.getInt(1));
				task.setSubjectId(resultSet.getInt(2));
				task.setTeacherId(resultSet.getInt(3));

				task.setTitle(resultSet.getString(4));
				task.setDescription(resultSet.getString(5));
				task.setTimeCreated(new Date(resultSet.getTimestamp(6).getTime()));
				task.setDone(resultSet.getBoolean(7));
				task.setDeadline(new Date(resultSet.getTimestamp(8).getTime()));
				task.setPriority(resultSet.getString(9));

				taskList.add(task);
			}
		} catch (SQLException e) {
			throw new DatabaseOperationException("Task select failed", e);
		} finally {
			// Close the statement

			try {
				if (stmt != null) {
					stmt.close();
				}
			} catch (SQLException e) {
				throw new DatabaseOperationException("Statement close operation failed", e);
			}
		}

		return taskList;
	}

	public List<Task> findManyBySubject(int subjectId) throws DatabaseOperationException {
		if (db == null) {
			throw new DatabaseOperationException("Database is not configured for this repository");
		}

		Connection conn = db.getConn();
		PreparedStatement stmt = null;
		List<Task> taskList = new ArrayList<>();
		Task task = null;

		try {
			stmt = conn.prepareStatement("SELECT * FROM task WHERE subject_id = ?");
			stmt.setInt(1, subjectId);
			ResultSet resultSet = stmt.executeQuery();

			while (resultSet.next()) {
				task = new Task();
				task.setTaskId(resultSet.getInt(1));
				task.setSubjectId(resultSet.getInt(2));
				task.setTeacherId(resultSet.getInt(3));

				task.setTitle(resultSet.getString(4));
				task.setDescription(resultSet.getString(5));
				task.setTimeCreated(new Date(resultSet.getTimestamp(6).getTime()));
				task.setDone(resultSet.getBoolean(7));
				task.setDeadline(new Date(resultSet.getTimestamp(8).getTime()));
				task.setPriority(resultSet.getString(9));

				taskList.add(task);
			}
		} catch (SQLException e) {
			throw new DatabaseOperationException("Task select failed", e);
		} finally {
			// Close the statement

			try {
				if (stmt != null) {
					stmt.close();
				}
			} catch (SQLException e) {
				throw new DatabaseOperationException("Statement close operation failed", e);
			}
		}

		return taskList;
	}

	public TaskDetails findDetailsById(int taskId) throws DatabaseOperationException {
		if (db == null) {
			throw new DatabaseOperationException("Database is not configured for this repository");
		}

		Connection conn = db.getConn();
		;
		TaskDetails taskDetails = null;
		PreparedStatement stmt = null;
		Subject subject = null;
		Teacher teacher = null;

		try {
			stmt = conn.prepareStatement(
					"SELECT T.task_id, T.subject_id, T.teacher_id, T.title, T.description, T.time_created, T.done, T.deadline, T.priority, S.name \"subject_name\", TE.name \"teacher_name\" FROM task T INNER JOIN subject S ON T.subject_id = S.subject_id INNER JOIN teacher TE ON T.teacher_id = TE.teacher_id WHERE T.task_id = ?");
			stmt.setInt(1, taskId);
			ResultSet resultSet = stmt.executeQuery();

			if (resultSet.next()) {
				taskDetails = new TaskDetails();
				taskDetails.setTaskId(resultSet.getInt(1));

				taskDetails.setSubjectId(resultSet.getInt(2));
				taskDetails.setTeacherId(resultSet.getInt(3));

				subject = new Subject();
				subject.setSubjectId(resultSet.getInt(2));
				subject.setName(resultSet.getString(10));
				taskDetails.setSubject(subject);

				teacher = new Teacher();
				teacher.setTeacherId(resultSet.getInt(3));
				teacher.setName(resultSet.getString(11));
				taskDetails.setTeacher(teacher);

				taskDetails.setTitle(resultSet.getString(4));
				taskDetails.setDescription(resultSet.getString(5));
				taskDetails.setTimeCreated(new Date(resultSet.getTimestamp(6).getTime()));
				taskDetails.setDone(resultSet.getBoolean(7));
				taskDetails.setDeadline(new Date(resultSet.getTimestamp(8).getTime()));
				taskDetails.setPriority(resultSet.getString(9));
			}

		} catch (SQLException e) {
			throw new DatabaseOperationException("Task select failed", e);
		} finally {
			// Close the statement

			try {
				if (stmt != null) {
					stmt.close();
				}
			} catch (SQLException e) {
				throw new DatabaseOperationException("Statement close operation failed", e);
			}
		}

		return taskDetails;
	}

	public List<TaskDetails> findManyDetailsByTitle(String title) throws DatabaseOperationException {
		if (db == null) {
			throw new DatabaseOperationException("Database is not configured for this repository");
		}

		Connection conn = db.getConn();
		;
		PreparedStatement stmt = null;
		List<TaskDetails> taskDetailsList = new ArrayList<>();
		TaskDetails taskDetails = null;
		Subject subject = null;
		Teacher teacher = null;

		try {
			stmt = conn.prepareStatement(
					"SELECT T.task_id, T.subject_id, T.teacher_id, T.title, T.description, T.time_created, T.done, T.deadline, T.priority, S.name \"subject_name\", TE.name \"teacher_name\" FROM task T INNER JOIN subject S ON T.subject_id = S.subject_id INNER JOIN teacher TE ON T.teacher_id = TE.teacher_id WHERE T.title LIKE ?");
			stmt.setString(1, title);
			ResultSet resultSet = stmt.executeQuery();

			while (resultSet.next()) {
				taskDetails = new TaskDetails();
				taskDetails.setTaskId(resultSet.getInt(1));

				taskDetails.setSubjectId(resultSet.getInt(2));
				taskDetails.setTeacherId(resultSet.getInt(3));

				subject = new Subject();
				subject.setSubjectId(resultSet.getInt(2));
				subject.setName(resultSet.getString(10));
				taskDetails.setSubject(subject);

				teacher = new Teacher();
				teacher.setTeacherId(resultSet.getInt(3));
				teacher.setName(resultSet.getString(11));
				taskDetails.setTeacher(teacher);

				taskDetails.setTitle(resultSet.getString(4));
				taskDetails.setDescription(resultSet.getString(5));
				taskDetails.setTimeCreated(new Date(resultSet.getTimestamp(6).getTime()));
				taskDetails.setDone(resultSet.getBoolean(7));
				taskDetails.setDeadline(new Date(resultSet.getTimestamp(8).getTime()));
				taskDetails.setPriority(resultSet.getString(9));

				taskDetailsList.add(taskDetails);
			}

		} catch (SQLException e) {
			throw new DatabaseOperationException("Task select failed", e);
		} finally {
			// Close the statement

			try {
				if (stmt != null) {
					stmt.close();
				}
			} catch (SQLException e) {
				throw new DatabaseOperationException("Statement close operation failed", e);
			}
		}

		return taskDetailsList;
	}

	public List<TaskDetails> findManyDetailsBySubject(int subjectId) throws DatabaseOperationException {
		if (db == null) {
			throw new DatabaseOperationException("Database is not configured for this repository");
		}

		Connection conn = db.getConn();
		PreparedStatement stmt = null;
		List<TaskDetails> taskDetailsList = new ArrayList<>();
		TaskDetails taskDetails = null;
		Subject subject = null;
		Teacher teacher = null;

		try {
			stmt = conn.prepareStatement(
					"SELECT T.task_id, T.subject_id, T.teacher_id, T.title, T.description, T.time_created, T.done, T.deadline, T.priority, S.name \"subject_name\", TE.name \"teacher_name\" FROM task T INNER JOIN subject S ON T.subject_id = S.subject_id INNER JOIN teacher TE ON T.teacher_id = TE.teacher_id WHERE T.subject_id = ?");
			stmt.setInt(1, subjectId);
			ResultSet resultSet = stmt.executeQuery();

			while (resultSet.next()) {
				taskDetails = new TaskDetails();
				taskDetails.setTaskId(resultSet.getInt(1));

				taskDetails.setSubjectId(resultSet.getInt(2));
				taskDetails.setTeacherId(resultSet.getInt(3));

				subject = new Subject();
				subject.setSubjectId(resultSet.getInt(2));
				subject.setName(resultSet.getString(10));
				taskDetails.setSubject(subject);

				teacher = new Teacher();
				teacher.setTeacherId(resultSet.getInt(3));
				teacher.setName(resultSet.getString(11));
				taskDetails.setTeacher(teacher);

				taskDetails.setTitle(resultSet.getString(4));
				taskDetails.setDescription(resultSet.getString(5));
				taskDetails.setTimeCreated(new Date(resultSet.getTimestamp(6).getTime()));
				taskDetails.setDone(resultSet.getBoolean(7));
				taskDetails.setDeadline(new Date(resultSet.getTimestamp(8).getTime()));
				taskDetails.setPriority(resultSet.getString(9));

				taskDetailsList.add(taskDetails);
			}

		} catch (SQLException e) {
			throw new DatabaseOperationException("Task select failed", e);
		} finally {
			// Close the statement

			try {
				if (stmt != null) {
					stmt.close();
				}
			} catch (SQLException e) {
				throw new DatabaseOperationException("Statement close operation failed", e);
			}
		}

		return taskDetailsList;
	}

	public List<TaskDetails> findManyDetailsByTeacher(int teacherId) throws DatabaseOperationException {
		if (db == null) {
			throw new DatabaseOperationException("Database is not configured for this repository");
		}

		Connection conn = db.getConn();
		PreparedStatement stmt = null;
		List<TaskDetails> taskDetailsList = new ArrayList<>();
		TaskDetails taskDetails = null;
		Subject subject = null;
		Teacher teacher = null;

		try {
			stmt = conn.prepareStatement(
					"SELECT T.task_id, T.subject_id, T.teacher_id, T.title, T.description, T.time_created, T.done, T.deadline, T.priority, S.name \"subject_name\", TE.name \"teacher_name\" FROM task T INNER JOIN subject S ON T.subject_id = S.subject_id INNER JOIN teacher TE ON T.teacher_id = TE.teacher_id WHERE T.teacher_id = ?");
			stmt.setInt(1, teacherId);
			ResultSet resultSet = stmt.executeQuery();

			while (resultSet.next()) {
				taskDetails = new TaskDetails();
				taskDetails.setTaskId(resultSet.getInt(1));

				taskDetails.setSubjectId(resultSet.getInt(2));
				taskDetails.setTeacherId(resultSet.getInt(3));

				subject = new Subject();
				subject.setSubjectId(resultSet.getInt(2));
				subject.setName(resultSet.getString(10));
				taskDetails.setSubject(subject);

				teacher = new Teacher();
				teacher.setTeacherId(resultSet.getInt(3));
				teacher.setName(resultSet.getString(11));
				taskDetails.setTeacher(teacher);

				taskDetails.setTitle(resultSet.getString(4));
				taskDetails.setDescription(resultSet.getString(5));
				taskDetails.setTimeCreated(new Date(resultSet.getTimestamp(6).getTime()));
				taskDetails.setDone(resultSet.getBoolean(7));
				taskDetails.setDeadline(new Date(resultSet.getTimestamp(8).getTime()));
				taskDetails.setPriority(resultSet.getString(9));

				taskDetailsList.add(taskDetails);
			}

		} catch (SQLException e) {
			throw new DatabaseOperationException("Task select failed", e);
		} finally {
			// Close the statement

			try {
				if (stmt != null) {
					stmt.close();
				}
			} catch (SQLException e) {
				throw new DatabaseOperationException("Statement close operation failed", e);
			}
		}

		return taskDetailsList;
	}

	public void update(Task task) throws DatabaseOperationException {
		if (db == null) {
			throw new DatabaseOperationException("Database is not configured for this repository");
		}

		Connection conn = db.getConn();
		PreparedStatement stmt = null;

		try {
			stmt = conn.prepareStatement(
					"UPDATE task SET subject_id=?, teacher_id=?, title=?, description=?, time_created=?, done=?, deadline=?, priority=? WHERE task_id = ?");
			stmt.setInt(1, task.getSubjectId());
			stmt.setInt(2, task.getTeacherId());

			stmt.setString(3, task.getTitle());
			stmt.setTimestamp(4, Timestamp.from(task.getTimeCreated().toInstant()));
			stmt.setBoolean(5, task.isDone());
			stmt.setInt(6, task.getSubjectId());
			stmt.setTimestamp(7, Timestamp.from(task.getDeadline().toInstant()));
			stmt.setString(8, task.getPriority());

			stmt.setInt(9, task.getTaskId());

			if (stmt.executeUpdate() < 1) {
				throw new DatabaseOperationException("Task update failed: executeUpdate() < 1");
			}
		} catch (SQLException e) {
			throw new DatabaseOperationException("Task update failed", e);
		} finally {
			// Close the statement

			try {
				if (stmt != null) {
					stmt.close();
				}
			} catch (SQLException e) {
				throw new DatabaseOperationException("Statement close operation failed", e);
			}
		}
	}

	public void deleteById(int taskId) throws DatabaseOperationException {
		if (db == null) {
			throw new DatabaseOperationException("Database is not configured for this repository");
		}

		Connection conn = db.getConn();
		PreparedStatement stmt = null;

		try {
			stmt = conn.prepareStatement("DELETE FROM task WHERE task_id = ?");
			stmt.setInt(1, taskId);
			if (stmt.executeUpdate() < 1) {
				throw new DatabaseOperationException("Task delete failed: executeUpdate() < 1");
			}
		} catch (SQLException e) {
			throw new DatabaseOperationException("Task delete failed", e);
		} finally {
			// Close the statement
			try {
				if (stmt != null) {
					stmt.close();
				}
			} catch (SQLException e) {
				throw new DatabaseOperationException("Statement close operation failed", e);
			}
		}
	}

	public Database getDb() {
		return db;
	}

	public void setDb(Database db) {
		this.db = db;
	}

	@Override
	public String toString() {
		return String.format("TaskRepository [db=%s]", db);
	}

}
