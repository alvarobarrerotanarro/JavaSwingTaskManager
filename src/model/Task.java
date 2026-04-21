package model;

import java.util.*;

public class Task {
	public static final String LOW_PRIORITY = "low";
	public static final String MEDIUM_PRIORITY = "medium";
	public static final String HIGH_PRIORITY = "high";

	protected int taskId;
	protected int subjectId;
	protected int teacherId;

	protected String title;
	protected String description;
	protected Date timeCreated;
	protected boolean done;
	protected Date deadline;
	protected String priority;

	public Task(int taskId, int subjectId, int teacherId, String title, String description, Date timeCreated,
			boolean done, Date deadline, String priority) {
		this.taskId = taskId;
		this.subjectId = subjectId;
		this.teacherId = teacherId;
		this.title = title;
		this.description = description;
		this.timeCreated = timeCreated;
		this.done = done;
		this.deadline = deadline;
		this.priority = priority;
	}

	public Task() {
	}

	public int getTaskId() {
		return taskId;
	}

	public void setTaskId(int taskId) {
		this.taskId = taskId;
	}

	public int getSubjectId() {
		return subjectId;
	}

	public void setSubjectId(int subjectId) {
		this.subjectId = subjectId;
	}

	public int getTeacherId() {
		return teacherId;
	}

	public void setTeacherId(int teacherId) {
		this.teacherId = teacherId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getTimeCreated() {
		return timeCreated;
	}

	public void setTimeCreated(Date timeCreated) {
		this.timeCreated = timeCreated;
	}

	public boolean isDone() {
		return done;
	}

	public void setDone(boolean done) {
		this.done = done;
	}

	public Date getDeadline() {
		return deadline;
	}

	public void setDeadline(Date deadline) {
		this.deadline = deadline;
	}

	public String getPriority() {
		return priority;
	}

	public void setPriority(String priority) {
		this.priority = priority;
	}

	@Override
	public String toString() {
		return String.format(
				"Task [taskId=%s, subjectId=%s, teacherId=%s, title=%s, description=%s, timeCreated=%s, done=%s, deadline=%s, priority=%s]",
				taskId, subjectId, teacherId, title, description, timeCreated, done, deadline, priority);
	}

}
