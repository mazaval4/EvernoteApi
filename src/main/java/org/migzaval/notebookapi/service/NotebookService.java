package org.migzaval.notebookapi.service;

import java.util.HashMap;

import org.migzaval.notebookapi.model.Notebook;
import org.springframework.stereotype.Service;

@Service
public class NotebookService {

	private HashMap <String,Notebook> notebooks = new HashMap<String,Notebook>();


	public boolean addNotebook(Notebook notebook){
		if(notebooks.containsKey(notebook.getNotebookId()))
			return false;
		else{
			notebooks.put(notebook.getNotebookId(), notebook);
			return true;
		}
	}
	
	public HashMap<String,Notebook> getAllNotebooks(){
		if(notebooks.size() == 0)
			return null;
		else
			return notebooks;
	}
	
	
	public boolean deleteNotebook(String notebookId) {
		if(notebooks.containsKey(notebookId)){
			notebooks.remove(notebookId);
			return true;
		}
		else
			return false;
	}

	
	
	
	
	
	
	
	
	
	
	public HashMap<String, Notebook> getNotebooks() {
		return notebooks;
	}

	public void setNotebooks(HashMap<String, Notebook> notebooks) {
		this.notebooks = notebooks;
	}

	
}
