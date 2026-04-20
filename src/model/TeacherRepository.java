package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class TeacherRepository {

	private Database db;

	public TeacherRepository(Database db) {
		this.db = db;
	}

	public TeacherRepository() {
	}

	public Teacher create(String name) throws DatabaseOperationException {
		Connection conn = db.getConn();
		Teacher teacher = new Teacher();

		try {
			PreparedStatement stmt = conn.prepareStatement("INSERT INTO teacher (name) VALUES (?)",
					PreparedStatement.RETURN_GENERATED_KEYS);
			stmt.setString(1, name);
			stmt.executeUpdate();

			ResultSet resultSet = stmt.getGeneratedKeys();
			resultSet.next();
			int insertId = resultSet.getInt(1);

			teacher.setTeacherId(insertId);
			teacher.setName(name);
		} catch (SQLException e) {
			throw new DatabaseOperationException("Teacher insert failed", e);
		}

		return teacher;
	}

	public Teacher findById(int teacherId) throws DatabaseOperationException {
		if (db == null) {
			throw new DatabaseOperationException("Database is not configured for this repository");
		}

		Connection conn = db.getConn();
		Teacher teacher = null;

		try {
			PreparedStatement stmt = conn.prepareStatement("SELECT * FROM teacher WHERE teacher_id = ?");
			stmt.setInt(1, teacherId);
			ResultSet resultSet = stmt.executeQuery();

			if (resultSet.next()) {
				teacher = new Teacher();
				teacher.setTeacherId(resultSet.getInt(1));
				teacher.setName(resultSet.getString(2));
			}
		} catch (SQLException e) {
			throw new DatabaseOperationException("Teacher select failed", e);
		}

		return teacher;
	}

	public List<Teacher> findManyByName(String name) throws DatabaseOperationException {
		if (db == null) {
			throw new DatabaseOperationException("Database is not configured for this repository");
		}

		Connection conn = db.getConn();
		List<Teacher> teacherList = new ArrayList<>();
		Teacher teacher = null;

		try {
			PreparedStatement stmt = conn.prepareStatement("SELECT * FROM teacher WHERE name LIKE ?");
			stmt.setString(1, name);
			ResultSet resultSet = stmt.executeQuery();

			while (resultSet.next()) {
				teacher = new Teacher();
				teacher.setTeacherId(resultSet.getInt(1));
				teacher.setName(resultSet.getString(2));

				teacherList.add(teacher);
			}
		} catch (SQLException e) {
			throw new DatabaseOperationException("Teacher select failed", e);
		}

		return teacherList;
	}

	public void update(Teacher teacher) throws DatabaseOperationException {
		if (db == null) {
			throw new DatabaseOperationException("Database is not configured for this repository");
		}

		Connection conn = db.getConn();

		try {
			PreparedStatement stmt = conn.prepareStatement("UPDATE teacher SET name=? WHERE teacher_id = ?");
			stmt.setString(1, teacher.getName());
			stmt.setInt(2, teacher.getTeacherId());
			if (stmt.executeUpdate() < 1) {
				throw new DatabaseOperationException("Teacher update failed: executeUpdate() < 1");
			}
		} catch (SQLException e) {
			throw new DatabaseOperationException("Task update failed", e);
		}
	}

	public void deleteById(int teacherId) throws DatabaseOperationException {
		if (db == null) {
			throw new DatabaseOperationException("Database is not configured for this repository");
		}

		Connection conn = db.getConn();

		try {
			PreparedStatement stmt = conn.prepareStatement("DELETE FROM teacher WHERE teacher_id = ?");
			stmt.setInt(1, teacherId);
			if (stmt.executeUpdate() < 1) {
				throw new DatabaseOperationException("Subject delete failed: executeUpdate() < 1");
			}
		} catch (SQLException e) {
			throw new DatabaseOperationException("Teacher delete failed", e);
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
		return String.format("TeacherRepository [db=%s]", db);
	}

}
