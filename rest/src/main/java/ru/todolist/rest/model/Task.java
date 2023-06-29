package ru.todolist.rest.model;


public class Task {
	private int idtask;
	private String description;
	private String duedate;
	private boolean actual;
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
