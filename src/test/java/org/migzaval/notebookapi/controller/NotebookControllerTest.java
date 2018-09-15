package org.migzaval.notebookapi.controller;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.migzaval.notebookapi.model.Note;
import org.migzaval.notebookapi.model.Notebook;
import org.migzaval.notebookapi.service.NotebookService;
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
@WebMvcTest(value = NotebookController.class, secure = false)
public class NotebookControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private NotebookService notebookService;
	String exampleNotebook = "{\"notebookId\":\"1\",\"name\":\"first notebook\"}";
	
	@Test
	public void getAllNotebooksTest() throws Exception{
		
		ArrayList<String> tags = new ArrayList<String>(Arrays.asList(new String("tag1")));
		ArrayList<Note> notes = new ArrayList<Note>(Arrays.asList(
				new Note("note1","this is a note",tags,1,1)));
		Notebook mockNotebook = new Notebook("1","notebook1",notes);
		HashMap<String,Notebook> map = new HashMap<String,Notebook>();
		map.put("1", mockNotebook);
		
		//True case
		Mockito.when(notebookService.getAllNotebooks()).thenReturn(map);

		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.get("/notebooks")
				.accept(MediaType.APPLICATION_JSON);
		
			
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		MockHttpServletResponse response = result.getResponse();

		String expected = "{message:\"Notebooks found successfully\",\"1\":{notebookId:\"1\",name:notebook1,notes:[{title:note1,body:\"this is a note\",tags:[\"tag1\"],created:1,modified:1}]}}"; 

		JSONAssert.assertEquals(expected, result.getResponse()
				.getContentAsString(), false);
		assertEquals(HttpStatus.OK.value(), response.getStatus());
		
		//False case 
		Mockito.when(notebookService.getAllNotebooks()).thenReturn(null);

		requestBuilder = MockMvcRequestBuilders
				.get("/notebooks")
				.accept(MediaType.APPLICATION_JSON);
		
			
		result = mockMvc.perform(requestBuilder).andReturn();
		response = result.getResponse();

		expected = "{message:\"No notebooks found\"}";
		JSONAssert.assertEquals(expected, result.getResponse()
				.getContentAsString(), false);
		assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
		
	}
	
	@Test
	public void addNotebookTest () throws Exception {

		//True case
		Mockito.when(notebookService.addNotebook(Mockito.any(Notebook.class))).thenReturn(true);

		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.post("/notebooks/addNotebook")
				.accept(MediaType.APPLICATION_JSON)
				.content(exampleNotebook)
				.contentType(MediaType.APPLICATION_JSON);
		
			

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		MockHttpServletResponse response = result.getResponse();

		String expected = "{message:\"Notebook added successfully\",notebook:{notebookId:\"1\",name:\"first notebook\",notes:[]}}";     

		JSONAssert.assertEquals(expected, result.getResponse()
				.getContentAsString(), false);

		assertEquals(HttpStatus.OK.value(), response.getStatus());
		
		
		//False case
		Mockito.when(notebookService.addNotebook(Mockito.any(Notebook.class))).thenReturn(false);

		requestBuilder = MockMvcRequestBuilders
				.post("/notebooks/addNotebook")
				.accept(MediaType.APPLICATION_JSON)
				.content(exampleNotebook)
				.contentType(MediaType.APPLICATION_JSON);
		
			

		result = mockMvc.perform(requestBuilder).andReturn();
		response = result.getResponse();
		
		expected = "{message:\"Notebook already exists\",notebook:{notebookId:\"1\",name:\"first notebook\",notes:[]}}";     

		JSONAssert.assertEquals(expected, result.getResponse()
				.getContentAsString(), false);

		assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
		

	}

	@Test
	public void deleteNotebookTest() throws Exception{
		String notebookId = "{\"notebookId\":\"1\"}";

		//True case
		Mockito.when(notebookService.deleteNotebook(Mockito.anyString())).thenReturn(true);

		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.delete("/notebooks/deleteNotebook")
				.accept(MediaType.APPLICATION_JSON)
				.content(notebookId)
				.contentType(MediaType.APPLICATION_JSON);
		
			
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		MockHttpServletResponse response = result.getResponse();
		String expected = "{notebookId:\"1\",message:\"Deleted successfully\"}";
		
		JSONAssert.assertEquals(expected, result.getResponse()
				.getContentAsString(), false);
		assertEquals(HttpStatus.ACCEPTED.value(), response.getStatus());
		
		
		//False case 
		Mockito.when(notebookService.deleteNotebook(Mockito.anyString())).thenReturn(false);

		requestBuilder = MockMvcRequestBuilders
				.delete("/notebooks/deleteNotebook")
				.accept(MediaType.APPLICATION_JSON)
				.content(notebookId)
				.contentType(MediaType.APPLICATION_JSON);
		
			
		result = mockMvc.perform(requestBuilder).andReturn();
		response = result.getResponse();
		expected = "{notebookId:\"1\",message:\"Notebook ID not found\"}";
		
		JSONAssert.assertEquals(expected, result.getResponse()
				.getContentAsString(), false);
		assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
		
		
		//Null case 
		Mockito.when(notebookService.deleteNotebook(Mockito.anyString())).thenReturn(false);
		notebookId = "{\"notebookId\":\"\"}";

		requestBuilder = MockMvcRequestBuilders
				.delete("/notebooks/deleteNotebook")
				.accept(MediaType.APPLICATION_JSON)
				.content(notebookId)
				.contentType(MediaType.APPLICATION_JSON);
		
			
		result = mockMvc.perform(requestBuilder).andReturn();
		response = result.getResponse();
		expected = "{notebookId:\"\",message:\"Notebook ID not found in request\"}";
		
		JSONAssert.assertEquals(expected, result.getResponse()
				.getContentAsString(), false);
		assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
		
	}

}
