package model;

import java.util.*;
import java.sql.Statement;
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

			stmt.close();
			resultSet.close();
		} catch (SQLException e) {
			throw new DatabaseOperationException("Subject select failed", e);
		}

		return subject;
	}

	/**
	 * Allows to search as many subjects as specified in the whereClause parameter
	 * sorted by the orderClause criteria. Parameters are specified as ? sybmols and
	 * their values are passed as vararg Object ...
	 * 
	 * Allowed parameter data types are String, Integer, Boolean and Date.
	 * 
	 * @param whereClause The SQL WHERE clause
	 * @param orderClause THe SQL ORDER BY clause
	 * @param params      The parameter values acording to the ? symbols in the
	 *                    previous parameters.
	 * @return
	 * @throws DatabaseOperationException In case there is an internal SQL failure
	 *                                    or the repository is disconnected from the
	 *                                    database.
	 */
	public List<Subject> findMany(String whereClause, String orderClause, Object... params)
			throws DatabaseOperationException {
		if (db == null) {
			throw new DatabaseOperationException("Database is not configured for this repository");
		}

		Connection conn = db.getConn();
		List<Subject> subjectList = new ArrayList<>();
		Subject subject = null;

		String query = "SELECT * FROM subject";

		if (whereClause != null) {
			query += " WHERE " + whereClause;
		}

		if (orderClause != null) {
			query += " ORDER BY " + orderClause;
		}

		try {
			PreparedStatement stmt = conn.prepareStatement(query);

			for (int i = 0; i < params.length; i++) {
				Object param = params[i];

				if (param instanceof String) {
					stmt.setString(i + 1, (String) param);
				} else if (param instanceof Integer) {
					stmt.setInt(i + 1, (Integer) param);
				} else if (param instanceof Boolean) {
					stmt.setBoolean(i + 1, (Boolean) param);
				} else if (param instanceof Date) {
					Date dparam = (Date) param;
					stmt.setTimestamp(i + 1, Timestamp.from(dparam.toInstant()));
				} else {
					throw new IllegalArgumentException();
				}
			}

			ResultSet resultSet = stmt.executeQuery();

			while (resultSet.next()) {
				subject = new Subject();
				subject.setSubjectId(resultSet.getInt("subject_id"));
				subject.setName(resultSet.getString("name"));

				subjectList.add(subject);
			}

			stmt.close();
			resultSet.close();
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
