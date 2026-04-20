package model;

/**
 * Related with the opening and closing of the underlying connection.
 */
public class DatabaseConnException extends DatabaseException {
	public DatabaseConnException(String message, Throwable e) {
		super(message, e);
	}

	public DatabaseConnException(String message) {
		super(message);
	}

	@Override
	public String toString() {
		return String.format("DatabaseConnException: " + getMessage());
	}
}
