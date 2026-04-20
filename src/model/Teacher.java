package model;

public class Teacher {
	private int teacherId;
	private String name;

	public Teacher(int teacherId, String name) {
		this.teacherId = teacherId;
		this.name = name;
	}
	
	public Teacher() {}

	public int getTeacherId() {
		return teacherId;
	}

	public void setTeacherId(int teacherId) {
		this.teacherId = teacherId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return String.format("Teacher [teacherId=%s, name=%s]", teacherId, name);
	}

}
