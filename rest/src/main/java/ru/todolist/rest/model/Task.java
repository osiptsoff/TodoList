package ru.todolist.rest.model;

import io.swagger.v3.oas.annotations.media.Schema;

public class Task {
	@Schema(example = "1", required = true)
	private int idtask;
	@Schema(description = "Essense of task",  example = "Visit the dantist", required = true)
	private String description;
	@Schema(description = "Date of task", example = "2012-07-21", required = true)
	private String duedate;
	@Schema(description = "Marker of actuality", example = "true", required = true)
	private boolean actual;
	@Schema(description = "ID of user owning this task", example = "12", required = true)
	private int iduser;
	public int getIdtask() {
		return idtask;
	}
	public void setIdtask(int idtask) {
		this.idtask = idtask;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getDuedate() {
		return duedate;
	}
	public void setDuedate(String duedate) {
		this.duedate = duedate;
	}
	public boolean getActual() {
		return actual;
	}
	public void setActual(boolean actual) {
		this.actual = actual;
	}
	public int getIduser() {
		return iduser;
	}
	public void setIduser(int iduser) {
		this.iduser = iduser;
	}
	
}
