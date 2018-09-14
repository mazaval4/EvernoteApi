package org.migzaval.notebookapi.service;

import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.migzaval.notebookapi.model.Note;
import org.migzaval.notebookapi.model.Notebook;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class NoteServiceTest {

	@Mock
	private NotebookService notebookService;
	
    @InjectMocks
	public NoteService noteService = new NoteService();
	
	ArrayList<String> tags = new ArrayList<String>(Arrays.asList(new String("tag1")));
	ArrayList<Note> notes = new ArrayList<Note>(Arrays.asList(
			new Note("note1","this is a note",tags,1,1)));
	Notebook mockNotebook = new Notebook("1","notebook1",notes);
	Note mockNote = new Note("note1","this is a note",tags,1,1);
	
	
	
	@Test
	public void addNoteTest()throws Exception{
		HashMap<String,Notebook> mockNotebookmap = new HashMap<String,Notebook>();
		mockNotebookmap.put("1", mockNotebook);
		Mockito.when(notebookService.getNotebooks()).thenReturn(mockNotebookmap);
		
		boolean result = noteService.addNote(mockNote, "1");
		assertTrue(result);
		

		result = noteService.addNote(mockNote, "0");
		assertFalse(result);
		
	}
	
	@Test
	public void deleteNoteTest(){
		HashMap<String,Notebook> mockNotebookmap = new HashMap<String,Notebook>();
		mockNotebookmap.put("1", mockNotebook);
		Mockito.when(notebookService.getNotebooks()).thenReturn(mockNotebookmap);
		

		Note testNote = noteService.deleteNote(11, "1");
		assertNull(testNote);
		
		testNote = noteService.deleteNote(0, "1");
		assertEquals(mockNote.getBody(),testNote.getBody());
		assertEquals(mockNote.getTitle(),testNote.getTitle());
		assertEquals(mockNote.getTags(),testNote.getTags());
		
		
		testNote = noteService.deleteNote(0, "1");
		assertNull(testNote);
		
		
	}
	
	@Test 
	public void updateNoteTest(){
		HashMap<String,Notebook> mockNotebookmap = new HashMap<String,Notebook>();
		mockNotebookmap.put("1", mockNotebook);
		Mockito.when(notebookService.getNotebooks()).thenReturn(mockNotebookmap);
		
		
		boolean result = noteService.updateNote(mockNote, "1", 0);		
		assertTrue(result);
		
		result = noteService.updateNote(mockNote, "1", 11);
		assertFalse(result);
		

		result = noteService.updateNote(mockNote, "11", 0);
		assertFalse(result);
		
	}
	
	@Test
	public void getAllNotesTest(){
		HashMap<String,Notebook> mockNotebookmap = new HashMap<String,Notebook>();
		mockNotebookmap.put("1", mockNotebook);
		Mockito.when(notebookService.getNotebooks()).thenReturn(mockNotebookmap);
		
		ArrayList<Note> result = noteService.getAllNotes("1");
		assertEquals(notes,result);
		
		result = noteService.getAllNotes("0");
		assertNull(result);
		
	}
	
	@Test
	public void getANoteTest(){
		HashMap<String,Notebook> mockNotebookmap = new HashMap<String,Notebook>();
		mockNotebookmap.put("1", mockNotebook);
		Mockito.when(notebookService.getNotebooks()).thenReturn(mockNotebookmap);
		
		Note result = noteService.getANote(0, "1");
		assertEquals(mockNote.getBody(),result.getBody());
		assertEquals(mockNote.getTitle(),result.getTitle());
		assertEquals(mockNote.getTags(),result.getTags());
		
		

		result = noteService.getANote(1, "1");
		assertNull(result);
		

		result = noteService.getANote(1, "22");
		assertNull(result);
	}
	
	@Test
	public void getANoteByTagTest(){
		HashMap<String,Notebook> mockNotebookmap = new HashMap<String,Notebook>();
		mockNotebookmap.put("1", mockNotebook);
		Mockito.when(notebookService.getNotebooks()).thenReturn(mockNotebookmap);
		List<Note> mockListResult = new ArrayList<Note>();
		mockListResult.add(mockNote);
		
		List<Note> result = noteService.getANoteByTag("tag1", "1");
		int compare = mockListResult.toString().compareTo(result.toString());
		assertEquals(0,compare);
		
		result = noteService.getANoteByTag("tag1", "19");
		assertNull(result);
		

		result = noteService.getANoteByTag("ee", "1");
		compare = mockListResult.toString().compareTo(result.toString());
		assertNotEquals(0,compare);
		
	}
}
