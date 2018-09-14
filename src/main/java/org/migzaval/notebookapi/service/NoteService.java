package org.migzaval.notebookapi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import org.migzaval.notebookapi.model.*;

@Service
public class NoteService {

	private NotebookService notebookService;

	public NoteService() {
		
	}
	
	@Autowired
	public NoteService(NotebookService notebookService){
		this.notebookService = notebookService;
	}
	
	
	public boolean addNote(Note note,String notebookId){
		
		//Notebook id does not exist. Cannot add note to non-existent notebook
		if(!notebookService.getNotebooks().containsKey(notebookId))
			return false;
		else{
			Notebook nb = notebookService.getNotebooks().get(notebookId);
			ArrayList<Note> notes = nb.getNotes();
			Instant instant = Instant.now();
			note.setModified(instant.toEpochMilli());
			note.setCreated(instant.toEpochMilli());
			notes.add(note);
			nb.setNotes(notes);
			
			notebookService.getNotebooks().put(notebookId, nb);
			return true;
		}
	}
	
	public Note deleteNote(int noteNumber,String notebookId) {
		Note returnNote = null;
		if(notebookService.getNotebooks().containsKey(notebookId)){
			Notebook nb = notebookService.getNotebooks().get(notebookId);
			ArrayList<Note> notes = nb.getNotes();
			
			try{
				returnNote = notes.get(noteNumber);
				notes.remove(noteNumber);
			}catch(IndexOutOfBoundsException exception){
				return returnNote;
			}

			nb.setNotes(notes);
			notebookService.getNotebooks().put(notebookId, nb);
			
			return returnNote;
		}
		else
			return returnNote;
	}

	public boolean updateNote(Note note, String notebookId, Integer noteNumber) {

		if(notebookService.getNotebooks().containsKey(notebookId)){
			Notebook nb = notebookService.getNotebooks().get(notebookId);
			ArrayList<Note> notes = nb.getNotes();
			Instant instant = Instant.now();
			
			note.setModified(instant.toEpochMilli());
			notes.remove(noteNumber);
			
			try{
				Note n = notes.get(noteNumber);
				note.setCreated(n.getCreated());
				notes.set(noteNumber, note);
			}
			catch(Exception IndexOutOfBoundsException){
				return false;
			}
		
			nb.setNotes(notes);
			
			notebookService.getNotebooks().put(notebookId, nb);
			return true;
		}
		else
			return false;
		
	}

	public ArrayList<Note> getAllNotes(String notebookId) {
		if(!notebookService.getNotebooks().containsKey(notebookId))
			return null;
		else
			return notebookService.getNotebooks().get(notebookId).getNotes();
	}

	public Note getANote(Integer noteNumber, String notebookId) {
		
		if(notebookService.getNotebooks().containsKey(notebookId)){
			Notebook nb = notebookService.getNotebooks().get(notebookId);
			ArrayList<Note> notes = nb.getNotes();
			
			try{
				return notes.get(noteNumber);
			}catch(IndexOutOfBoundsException exception){
				return null;
			}


		}
		else
			return null;
	}

	public List<Note> getANoteByTag(String inputTag, String notebookId) {
		List<Note> returnList = new ArrayList<Note>();
		if(notebookService.getNotebooks().containsKey(notebookId)){
			Notebook nb = notebookService.getNotebooks().get(notebookId);
			ArrayList<Note> notes = nb.getNotes();
			
			for(int i = 0; i < notes.size(); i++){
				ArrayList<String> tags = notes.get(i).getTags();
				for(int j = 0; j < tags.size(); j++){
					String tag = tags.get(j);
					if(tag.equals(inputTag)){
						returnList.add(notes.get(i));
						break;
					}
				}
			}
			return returnList;
		}
		else
			return null;
	}
}
