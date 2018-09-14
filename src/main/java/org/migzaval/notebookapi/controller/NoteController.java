package org.migzaval.notebookapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.migzaval.notebookapi.controller.wrapperclasses.AddnoteReqBody;
import org.migzaval.notebookapi.controller.wrapperclasses.UpdateNoteReqBody;
import org.migzaval.notebookapi.model.Note;
import org.migzaval.notebookapi.service.*;

@RestController
public class NoteController {

	
	private NoteService noteService;
	
	@Autowired
	public NoteController(NoteService noteService){
		this.noteService = noteService;
	}
	
	@RequestMapping("/note")
	public ResponseEntity<HashMap<String, Object>> getAllNotes(@RequestParam(value = "notebookId" ,required=true) String notebookId){
		ArrayList<Note>  notes = noteService.getAllNotes(notebookId);
		ResponseEntity<HashMap<String, Object>> entity = null;
	    HashMap<String, Object> map = new HashMap<>();
	    map.put("notes", notes);
		if(notes != null){
			map.put("message", "Success");
			entity = new ResponseEntity<HashMap<String, Object>>(map,HttpStatus.OK);
		}
		else{
			map.put("message", "Could not find notes with given information");
			entity = new ResponseEntity<HashMap<String, Object>>(map,HttpStatus.BAD_REQUEST);

		}
		return entity;
	}
	
	@RequestMapping(method=RequestMethod.POST,value="/note/addNote")
	public ResponseEntity<HashMap<String, Object>> addNote(@RequestBody AddnoteReqBody requestBody){

		ResponseEntity<HashMap<String, Object>> entity = null;
		HashMap<String, Object> map = new HashMap<String, Object>();
		
		if(noteService.addNote(requestBody.getNote(), requestBody.getNotebookId())){
			map.put("note", requestBody.getNote());
			map.put("notebookId", requestBody.getNotebookId());
			map.put("message", "Note added successfully");
			entity = new ResponseEntity<HashMap<String, Object>>(map,HttpStatus.OK);
		}
		else{
			map.put("message", "Not notebooks found, please check notebook ID");
			map.put("notebookId", requestBody.getNotebookId());
			entity = new ResponseEntity<HashMap<String, Object>>(map,HttpStatus.BAD_REQUEST);
		}
		return entity;

	}
	
	@RequestMapping(method=RequestMethod.DELETE,value="/note/deleteNote")
	public ResponseEntity<HashMap<String, Object>> deleteNote(@RequestBody Map<String,Object> requestBody){
		Integer noteNumber = (Integer) requestBody.get("noteNumber");
		String notebookId = (String) requestBody.get("notebookId");
		HashMap<String, Object> map = new HashMap<String, Object>();

		ResponseEntity<HashMap<String, Object>> entity = null;
		map.putAll(requestBody);
		

		if(noteNumber != null && notebookId != null){

			Note deletedNote = noteService.deleteNote(noteNumber,notebookId);
					
			if( deletedNote != null){
				map.put("deletedNote", deletedNote);
				map.put("message", "Successfully deleted note");
				entity = new ResponseEntity<HashMap<String, Object>>(map,HttpStatus.ACCEPTED);
			}else{
				map.put("message", "Could not delete note");
				entity = new ResponseEntity<HashMap<String, Object>>(map,HttpStatus.BAD_REQUEST);
			}
		}else{
			map.put("message", "Note number or notebook ID not found in request");
			entity = new ResponseEntity<HashMap<String,Object>>(map,HttpStatus.BAD_REQUEST);
		}
		
		return entity;

	}
	
	@RequestMapping(value="/note/getNote")
	public ResponseEntity<HashMap<String, Object>> getANote(@RequestParam(value = "notebookId",required = true) 
	String notebookId,@RequestParam(value = "noteNumber",required = true) int noteNumber){
		
		ResponseEntity<HashMap<String, Object>> entity = null;
		HashMap<String,Object> map = new HashMap<String,Object>();
		map.put("noteNumber", noteNumber);
		
		if(Integer.valueOf(noteNumber)!=null && notebookId != null){
			Note n = noteService.getANote(noteNumber,notebookId);
			map.put("note", n);
			if(n != null){
				map.put("message", "Success");
				entity = new ResponseEntity<HashMap<String, Object>>(map,HttpStatus.OK);

			}
			else{
				map.put("message", "No note found with given input");
				entity = new ResponseEntity<HashMap<String, Object>>(map,HttpStatus.BAD_REQUEST);
				
			}
		}else{
			map.put("message", "No note number or notebook ID found in request");
			entity = new ResponseEntity<HashMap<String, Object>>(map,HttpStatus.BAD_REQUEST);	
		}
		return entity;

	}
	
	@RequestMapping(value="/note/getNoteByTag")
	public ResponseEntity<HashMap<String,Object>> getANoteByTag(@RequestParam (value = "notebookId" ,required = true) 
	String notebookId,@RequestParam (value = "tag",required = true) String tag){
		
		ResponseEntity<HashMap<String,Object>> entity = null;
		HashMap <String,Object> map = new HashMap<String,Object>();
		map.put("notebookId", notebookId);
		map.put("tag", tag);
		
		if(tag != null && notebookId != null){
			List<Note> notesList = noteService.getANoteByTag(tag,notebookId);
			map.put("notes", notesList);
			if(notesList != null){
				map.put("message", "Success");
				map.put("numberOfNotes", notesList.size());
				entity = new ResponseEntity<HashMap<String,Object>>(map,HttpStatus.OK);
			}
			else{
				map.put("message", "No notes found with given tag");
				entity = new ResponseEntity<HashMap<String,Object>>(map,HttpStatus.BAD_REQUEST);
			}
		}else{
			map.put("message", "No tag or notebook ID found in request");
			entity = new ResponseEntity<HashMap<String,Object>>(map,HttpStatus.BAD_REQUEST);	
		}
		return entity;
	}
	
	@RequestMapping(method=RequestMethod.PUT,value="/note/updateNote")
	public ResponseEntity<HashMap<String,Object>> updateNote(@RequestBody UpdateNoteReqBody requestBody){
		
		ResponseEntity<HashMap<String,Object>> entity = null;
		HashMap<String,Object> map = new HashMap<String,Object>();
		map.put("note", requestBody.getNote());
		map.put("notebookId", requestBody.getNotebookId());
		
		if(noteService.updateNote(requestBody.getNote(), requestBody.getNotebookId(),requestBody.getNoteNumber())){
			map.put("message", "Successfully updated note");
			entity = new ResponseEntity<HashMap<String,Object>>(map,HttpStatus.OK);	
		}
		else{
			map.put("message", "Failed to update note. Make sure inputs are correct");
			entity = new ResponseEntity<HashMap<String,Object>>(map,HttpStatus.BAD_REQUEST);	
		}
		return entity;
	}
	
	
	
}


