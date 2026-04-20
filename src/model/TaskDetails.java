package model;

import java.util.Date;

public class TaskDetails extends Task {
	private Subject subject;
	private Teacher teacher;

	public TaskDetails(int taskId, int subjectId, int teacherId, String title, String description, Date timeCreated,
			boolean done, Date deadline, Subject subject, Teacher teacher) {
		super(taskId, subjectId, teacherId, title, description, timeCreated, done, deadline);
		this.subject = subject;
		this.teacher = teacher;
	}

	public TaskDetails() {
		super();
	}

	public Subject getSubject() {
		return subject;
	}

	public void setSubject(Subject subject) {
		this.subject = subject;
	}

	public Teacher getTeacher() {
		return teacher;
	}

	public void setTeacher(Teacher teacher) {
		this.teacher = teacher;
	}

	@Override
	public String toString() {
		return String.format(
				"TaskDetails [subject=%s, teacher=%s, taskId=%s, subjectId=%s, teacherId=%s, title=%s, description=%s, timeCreated=%s, done=%s, deadline=%s]",
				subject, teacher, taskId, subjectId, teacherId, title, description, timeCreated, done, deadline);
	}
}
