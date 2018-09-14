package org.migzaval.notebookapi.service;

import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import org.junit.Test;
import org.migzaval.notebookapi.model.Note;
import org.migzaval.notebookapi.model.Notebook;


public class NotebookServiceTest {

	public NotebookService notebookService = new NotebookService();
	ArrayList<String> tags = new ArrayList<String>(Arrays.asList(new String("ere")));
	ArrayList<Note> notes = new ArrayList<Note>(Arrays.asList(
			new Note("note1","this is a note",tags,1,1)));
	Notebook mockNotebook = new Notebook("1","notebook1",notes);
	
	@Test
	public void addNotebookTest() throws Exception{
		boolean result = notebookService.addNotebook(mockNotebook);
		assertTrue(result);

		result = notebookService.addNotebook(mockNotebook);
		assertFalse(result);
	}
	
	@Test
	public void getAllNotebooksTest() throws Exception{
		HashMap<String,Notebook> map = new HashMap<String,Notebook>();
		map.put("1", mockNotebook);
		HashMap<String,Notebook> result = notebookService.getAllNotebooks();
		assertNull(result);
		
		notebookService.addNotebook(mockNotebook);
		result = notebookService.getAllNotebooks();
		assertEquals(map,result);
		

	}
	
	@Test
	public void deleteNotebookTest() throws Exception{

		notebookService.addNotebook(mockNotebook);
		boolean result = notebookService.deleteNotebook("1");
		assertTrue(result);
		
		result = notebookService.deleteNotebook("33");
		assertFalse(result);
	}
	
	
	
}
