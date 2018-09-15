package org.migzaval.notebookapi.controller;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.migzaval.notebookapi.model.Note;
import org.migzaval.notebookapi.model.Notebook;
import org.migzaval.notebookapi.service.NoteService;
import org.mockito.Mockito;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@RunWith(SpringRunner.class)
@WebMvcTest(value = NoteController.class, secure = false)
public class NoteControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private NoteService noteService;
	
	
	
	@Test
	public void getAllNotesTest() throws Exception{
		
		ArrayList<String> tags = new ArrayList<String>(Arrays.asList(new String("tag1")));
		ArrayList<Note> notes = new ArrayList<Note>(Arrays.asList(
				new Note("note1","this is a note",tags,1,1)));
		Notebook mockNotebook = new Notebook("1","notebook1",notes);
		HashMap<String,Notebook> map = new HashMap<String,Notebook>();
		map.put("1", mockNotebook);
		
		
		//True case
		Mockito.when(noteService.getAllNotes(Mockito.anyString())).thenReturn(notes);

		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.get("/notes?notebookId=1");
		
			
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		MockHttpServletResponse response = result.getResponse();
		String expected = "{notes:[{title:note1,body:\"this is a note\",tags:[tag1],created:1,modified:1}],message:Success}";
		
		JSONAssert.assertEquals(expected, result.getResponse()
				.getContentAsString(), false);
		assertEquals(HttpStatus.OK.value(), response.getStatus());
		
		
		//False case
		Mockito.when(noteService.getAllNotes(Mockito.anyString())).thenReturn(null);

		requestBuilder = MockMvcRequestBuilders
				.get("/notes?notebookId=1");
		
			
		result = mockMvc.perform(requestBuilder).andReturn();
		response = result.getResponse();
		expected = "{message:\"Could not find notes with given information\"}";
		
		JSONAssert.assertEquals(expected, result.getResponse()
				.getContentAsString(), false);
		assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
	
		
	}

	@Test
	public void addNoteTest() throws Exception{
		
		String addNoteString = "{\"note\":{\"title\":\"first note\",\"body\":\"a  note\",\"tags\":[ \"tag2\", \"tag3\" ]},\"notebookId\":\"1\"}";   
		
		//True case
		Mockito.when(noteService.addNote(Mockito.any(Note.class),Mockito.anyString())).thenReturn(true);
		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.post("/notes/addNote")
				.accept(MediaType.APPLICATION_JSON)
				.content(addNoteString)
				.contentType(MediaType.APPLICATION_JSON);
		
			
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		MockHttpServletResponse response = result.getResponse();

		String expected = "{note:{title:\"first note\",body:\"a  note\",tags:[tag2,tag3],created:0,modified:0},notebookId:\"1\",message:\"Note added successfully\"}";
		JSONAssert.assertEquals(expected, result.getResponse()
				.getContentAsString(), false);
		assertEquals(HttpStatus.OK.value(), response.getStatus());

		
		//False case
		Mockito.when(noteService.addNote(Mockito.any(Note.class),Mockito.anyString())).thenReturn(false);
		requestBuilder = MockMvcRequestBuilders
				.post("/notes/addNote")
				.accept(MediaType.APPLICATION_JSON)
				.content(addNoteString)
				.contentType(MediaType.APPLICATION_JSON);
		
			
		result = mockMvc.perform(requestBuilder).andReturn();
		response = result.getResponse();

		expected = "{message:\"Not notebooks found, please check notebook ID\"}";
		JSONAssert.assertEquals(expected, result.getResponse()
				.getContentAsString(), false);
		assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
		
	}
	
	
	@Test
	public void deleteNoteTest() throws Exception{
		
		ArrayList<String> tags = new ArrayList<String>(Arrays.asList(new String("tag1")));
		Note note = new Note("note1","this is a note",tags,1,1);
		String deleteNoteString = "{\"noteNumber\":0,\"notebookId\":\"1\"}";
		
		//True case
		Mockito.when(noteService.deleteNote(Mockito.anyInt(),Mockito.anyString())).thenReturn(note);

		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.delete("/notes/deleteNote")
				.accept(MediaType.APPLICATION_JSON)
				.content(deleteNoteString)
				.contentType(MediaType.APPLICATION_JSON);
		
			
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		MockHttpServletResponse response = result.getResponse();
		String expected = "{deletedNote:{title:note1,body:\"this is a note\",tags:[tag1],created:1,modified:1},message:\"Successfully deleted note\",noteNumber:0,notebookId:\"1\"}";  
		
		JSONAssert.assertEquals(expected, result.getResponse()
				.getContentAsString(), false);
		assertEquals(HttpStatus.ACCEPTED.value(), response.getStatus());
		
		
		//False case 1
		Mockito.when(noteService.deleteNote(Mockito.anyInt(),Mockito.anyString())).thenReturn(null);

		requestBuilder = MockMvcRequestBuilders
				.delete("/notes/deleteNote")
				.accept(MediaType.APPLICATION_JSON)
				.content(deleteNoteString)
				.contentType(MediaType.APPLICATION_JSON);
		
			
		result = mockMvc.perform(requestBuilder).andReturn();
		response = result.getResponse();
		expected = "{message:\"Could not delete note\",noteNumber:0,notebookId:\"1\"}";  
		
		JSONAssert.assertEquals(expected, result.getResponse()
				.getContentAsString(), false);
		assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
		

		//False case 2
		Mockito.when(noteService.deleteNote(Mockito.anyInt(),Mockito.anyString())).thenReturn(null);

		deleteNoteString = "{\"noteNumber\":0,\"notebookId\":\"\"}";

		requestBuilder = MockMvcRequestBuilders
				.delete("/notes/deleteNote")
				.accept(MediaType.APPLICATION_JSON)
				.content(deleteNoteString)
				.contentType(MediaType.APPLICATION_JSON);
		
			
		result = mockMvc.perform(requestBuilder).andReturn();
		response = result.getResponse();
		expected = "{message:\"Note number or notebook ID not found in request\",noteNumber:0,notebookId:\"\"}";  
		
		JSONAssert.assertEquals(expected, result.getResponse()
				.getContentAsString(), false);
		assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
	
	}
	
	
	@Test
	public void getANoteTest() throws Exception{
		

		ArrayList<String> tags = new ArrayList<String>(Arrays.asList(new String("tag1")));
		Note note = new Note("note1","this is a note",tags,1,1);
		
		//True case
		Mockito.when(noteService.getANote(Mockito.anyInt(),Mockito.anyString())).thenReturn(note);

		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.get("/notes/getANote?notebookId=1&noteNumber=0");
		
			
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		MockHttpServletResponse response = result.getResponse();

	
		String expected = "{note:{title:note1,body:\"this is a note\",tags:[tag1],created:1,modified:1},notebookId:\"1\",message:Success,noteNumber:0}";  
		
		JSONAssert.assertEquals(expected, result.getResponse()
				.getContentAsString(), false);
		assertEquals(HttpStatus.OK.value(), response.getStatus());
		
		
		//False case
		Mockito.when(noteService.getANote(Mockito.anyInt(),Mockito.anyString())).thenReturn(null);

		requestBuilder = MockMvcRequestBuilders
				.get("/notes/getANote?notebookId=1&noteNumber=0");
		
			
		result = mockMvc.perform(requestBuilder).andReturn();
		response = result.getResponse();

		expected = "{message:\"No note found with given input\",noteNumber:0,notebookId:\"1\"}";
		
		JSONAssert.assertEquals(expected, result.getResponse()
				.getContentAsString(), false);
		assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
		
		//False case2
		Mockito.when(noteService.getANote(Mockito.anyInt(),Mockito.anyString())).thenReturn(null);

		requestBuilder = MockMvcRequestBuilders
				.get("/notes/getANote?notebookId=1&noteNumber=");
		
			
		result = mockMvc.perform(requestBuilder).andReturn();
		response = result.getResponse();

		expected = "{message:\"No note number or notebook ID found in request\"}";
		JSONAssert.assertEquals(expected, result.getResponse()
				.getContentAsString(), false);
		assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());

	}

	
	@Test
	public void getANoteByTagTest() throws Exception{

		ArrayList<String> tags = new ArrayList<String>(Arrays.asList(new String("tag1")));
		Note note = new Note("note1","this is a note",tags,1,1);
		List<Note> noteList = new ArrayList<Note>();
		noteList.add(note);
		
		
		//True case
		Mockito.when(noteService.getANoteByTag(Mockito.anyString(),Mockito.anyString())).thenReturn(noteList);

		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.get("/notes/getNoteByTag?notebookId=1&tag=tag1");
		
			
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		MockHttpServletResponse response = result.getResponse();

		
		String expected = "{notes:[{title:note1,body:\"this is a note\",tags:[tag1],created:1,modified:1}],notebookId:\"1\",tag:tag1,numberOfNotes:1,message:Success}"; 
		JSONAssert.assertEquals(expected, result.getResponse()
				.getContentAsString(), false);
		assertEquals(HttpStatus.OK.value(), response.getStatus());
		
		//False case
		Mockito.when(noteService.getANoteByTag(Mockito.anyString(),Mockito.anyString())).thenReturn(null);

		requestBuilder = MockMvcRequestBuilders
				.get("/notes/getNoteByTag?notebookId=1&tag=tag1");
		
			
		result = mockMvc.perform(requestBuilder).andReturn();
		response = result.getResponse();

		
		expected = "{message:\"No notes found with given tag\"}"; 
		JSONAssert.assertEquals(expected, result.getResponse()
				.getContentAsString(), false);
		assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
		
		
		//False case
		Mockito.when(noteService.getANoteByTag(Mockito.anyString(),Mockito.anyString())).thenReturn(null);

		requestBuilder = MockMvcRequestBuilders
				.get("/notes/getNoteByTag?notebookId=1&tag=");
		
			
		result = mockMvc.perform(requestBuilder).andReturn();
		response = result.getResponse();

		expected = "{message:\"No tag or notebook ID found in request\"}"; 
		JSONAssert.assertEquals(expected, result.getResponse()
				.getContentAsString(), false);
		assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());

	
		
	}

	
	@Test
	public void updateNoteTest() throws Exception{
		String updateNoteString = "{\"note\":{\"title\":\"1 note\",\"body\":\"a  note\",\"tags\":[\"tag2\", \"tag3\" ]},\"notebookId\":\"1\",\"noteNumber\":\"0\"}";  
		
		
		//True case
		Mockito.when(noteService.updateNote(Mockito.any(Note.class),Mockito.anyString(),Mockito.anyInt())).thenReturn(true);

		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.put("/notes/updateNote")
				.accept(MediaType.APPLICATION_JSON)
				.content(updateNoteString)
				.contentType(MediaType.APPLICATION_JSON);
		
			
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		MockHttpServletResponse response = result.getResponse();
		String expected = "{note:{title:\"1 note\",body:\"a  note\",tags:[tag2,tag3],created:0,modified:0},notebookId:\"1\",message:\"Successfully updated note\"}"; 

		JSONAssert.assertEquals(expected, result.getResponse()
		.getContentAsString(), false);
		assertEquals(HttpStatus.OK.value(), response.getStatus());
		
		//False case
		Mockito.when(noteService.updateNote(Mockito.any(Note.class),Mockito.anyString(),Mockito.anyInt())).thenReturn(false);

		requestBuilder = MockMvcRequestBuilders
				.put("/notes/updateNote")
				.accept(MediaType.APPLICATION_JSON)
				.content(updateNoteString)
				.contentType(MediaType.APPLICATION_JSON);
		
			
		result = mockMvc.perform(requestBuilder).andReturn();
		response = result.getResponse();
		expected = "{message:\"Failed to update note. Make sure inputs are correct\"}"; 

		JSONAssert.assertEquals(expected, result.getResponse()
		.getContentAsString(), false);
		assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
		
	}

	
	

}
