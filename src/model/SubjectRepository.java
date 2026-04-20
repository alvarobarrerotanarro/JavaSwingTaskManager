package model;

import java.util.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

public class SubjectRepository {
	private Database db;

	public SubjectRepository(Database db) {
		this.db = db;
	}

	public SubjectRepository() {
	}

	public Subject create(String name) throws DatabaseOperationException {
		Connection conn = db.getConn();
		Subject subject = new Subject();

		try {
			PreparedStatement stmt = conn.prepareStatement("INSERT INTO subject (name) VALUES (?)",
					PreparedStatement.RETURN_GENERATED_KEYS);
			stmt.setString(1, name);
			stmt.executeUpdate();

			ResultSet resultSet = stmt.getGeneratedKeys();
			resultSet.next();
			int insertId = resultSet.getInt(1);

			subject.setSubjectId(insertId);
			subject.setName(name);
		} catch (SQLException e) {
			throw new DatabaseOperationException("Subject insert failed", e);
		}

		return subject;
	}

	public Subject findById(int subjectId) throws DatabaseOperationException {
		if (db == null) {
			throw new DatabaseOperationException("Database is not configured for this repository");
		}

		Connection conn = db.getConn();
		Subject subject = null;

		try {
			PreparedStatement stmt = conn.prepareStatement("SELECT * FROM subject WHERE subject_id = ?");
			stmt.setInt(1, subjectId);
			ResultSet resultSet = stmt.executeQuery();

			if (resultSet.next()) {
				subject = new Subject();
				subject.setSubjectId(resultSet.getInt(1));
				subject.setName(resultSet.getString(2));
			}
		} catch (SQLException e) {
			throw new DatabaseOperationException("Subject select failed", e);
		}

		return subject;
	}

	public List<Subject> findManyByName(String name) throws DatabaseOperationException {
		if (db == null) {
			throw new DatabaseOperationException("Database is not configured for this repository");
		}

		Connection conn = db.getConn();
		List<Subject> subjectList = new ArrayList<>();
		Subject subject = null;

		try {
			PreparedStatement stmt = conn.prepareStatement("SELECT * FROM subject WHERE name LIKE ?");
			stmt.setString(1, name);
			ResultSet resultSet = stmt.executeQuery();

			while (resultSet.next()) {
				subject = new Subject();
				subject.setSubjectId(resultSet.getInt(1));
				subject.setName(resultSet.getString(2));

				subjectList.add(subject);
			}
		} catch (SQLException e) {
			throw new DatabaseOperationException("Subject select failed", e);
		}

		return subjectList;
	}

	public void update(Subject subject) throws DatabaseOperationException {
		if (db == null) {
			throw new DatabaseOperationException("Database is not configured for this repository");
		}

		Connection conn = db.getConn();

		try {
			PreparedStatement stmt = conn.prepareStatement("UPDATE subject SET name=? WHERE subject_id = ?");
			stmt.setString(1, subject.getName());
			stmt.setInt(2, subject.getSubjectId());

			if (stmt.executeUpdate() < 1) {
				throw new DatabaseOperationException("Subject update failed: executeUpdate() < 1");
			}
		} catch (SQLException e) {
			throw new DatabaseOperationException("Subjectupdate failed", e);
		}
	}

	public void deleteById(int subjectId) throws DatabaseOperationException {
		if (db == null) {
			throw new DatabaseOperationException("Database is not configured for this repository");
		}

		Connection conn = db.getConn();

		try {
			PreparedStatement stmt = conn.prepareStatement("DELETE FROM subject WHERE subject_id = ?");
			stmt.setInt(1, subjectId);
			if (stmt.executeUpdate() < 1) {
				throw new DatabaseOperationException("Subject delete failed: executeUpdate() < 1");
			}
		} catch (SQLException e) {
			throw new DatabaseOperationException("Subject delete failed", e);
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
		return String.format("SubjectRepository [db=%s]", db);
	}
}
