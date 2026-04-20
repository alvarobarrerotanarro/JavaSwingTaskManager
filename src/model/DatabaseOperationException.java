package model;

public class DatabaseOperationException extends DatabaseException {
	public DatabaseOperationException(String message, Throwable e) {
		super(message, e);
	}

	public DatabaseOperationException(String message) {
		super(message);
	}

	@Override
	public String toString() {
		return String.format("DatabaseOperationException: " + getMessage());
	}
}
