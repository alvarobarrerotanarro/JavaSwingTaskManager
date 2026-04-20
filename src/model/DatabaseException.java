package model;

public class DatabaseException extends RuntimeException {
	public DatabaseException(String message, Throwable e) {
		super(message, e);
	}

	public DatabaseException(String message) {
		super(message);
	}

	@Override
	public String toString() {
		return String.format("DatabaseException: " + getMessage());
	}
}
