package org.migzaval.notebookapi.controller;

import java.util.HashMap;
import java.util.Map;

import org.migzaval.notebookapi.model.Notebook;
import org.migzaval.notebookapi.service.NotebookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class NotebookController {

	
	
	private NotebookService notebookService;
	
	@Autowired
	public NotebookController(NotebookService notebookService){
		this.notebookService = notebookService;
	}
	
	
	@RequestMapping("/notebooks")
	public ResponseEntity<HashMap<String, Object>> getAllNotebooks(){

	    HashMap<String, Object> map = new HashMap<>();
		HashMap<String,Notebook>  notes = notebookService.getAllNotebooks();
		ResponseEntity<HashMap<String, Object>> entity = null;
		
		if(notes != null){
			map.putAll(notes);
			map.put("message", "Notebooks found successfully");
			entity = new ResponseEntity<HashMap<String, Object>>(map,HttpStatus.OK);
		}
		else{
			map.put("message", "No notebooks found");
			entity = new ResponseEntity<HashMap<String, Object>>(map,HttpStatus.BAD_REQUEST);
		}
		return entity;
	}

	@RequestMapping(method=RequestMethod.POST,value="/notebooks/addNotebook")
	public ResponseEntity<HashMap<String, Object>> addNoteBook(@RequestBody Notebook notebook){
		ResponseEntity<HashMap<String, Object>> entity = null;
	    HashMap<String, Object> map = new HashMap<>();

	    map.put("notebook", notebook);
		
		if(notebookService.addNotebook(notebook)){
			map.put("message", "Notebook added successfully");
			entity = new ResponseEntity<HashMap<String, Object>>(map,HttpStatus.OK);
		}
		else{
			map.put("message", "Notebook already exists");
			entity = new ResponseEntity<HashMap<String, Object>>(map,HttpStatus.BAD_REQUEST);
		}
		return entity;

	}
	
	@RequestMapping(method=RequestMethod.DELETE,value="/notebooks/deleteNotebook")
	public ResponseEntity<HashMap<String, String>> deleteNotebook(@RequestBody Map<String,Object> requestBody){
		String notebookId = (String) requestBody.get("notebookId");
		HashMap<String,String> map = new HashMap<String,String>();
		ResponseEntity<HashMap<String, String>> entity = null;
		
		map.put("notebookId", notebookId);

		if(notebookId != null){
			if(notebookService.deleteNotebook(notebookId)){
				map.put("message", "Deleted successfully");
				entity = new ResponseEntity<HashMap<String, String>>(map,HttpStatus.ACCEPTED);
			}
			else{
				map.put("message", "Notebook ID not found");
				entity = new ResponseEntity<HashMap<String, String>>(map,HttpStatus.BAD_REQUEST);
			}	
		}else{
			map.put("message", "Notebook ID not found in request");
			entity =  new ResponseEntity<HashMap<String, String>>(map,HttpStatus.BAD_REQUEST);
		}
		return entity;
	}

}
