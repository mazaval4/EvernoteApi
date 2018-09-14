package org.migzaval.notebookapi.model;

import java.util.ArrayList;

public class Notebook {

	private String notebookId, name;
	private ArrayList<Note> notes;
	
	public Notebook(){
		this.notes = new ArrayList<Note>();
	}

	public Notebook(String notebookId, String name, ArrayList<Note> notes) {
		super();
		this.notebookId = notebookId;
		this.name = name;
		this.notes = notes;
	}

	public String getNotebookId() {
		return notebookId;
	}

	public void setNotebookId(String notebookId) {
		this.notebookId = notebookId;
	}
	
	public ArrayList<Note> getNotes() {
		return notes;
	}

	public void setNotes(ArrayList<Note> notes) {
		this.notes = notes;
	}

	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "{notebookId:" + notebookId + ", name:" + name + ", notes:" + notes + "}";
		
	}
	
	
	
}
