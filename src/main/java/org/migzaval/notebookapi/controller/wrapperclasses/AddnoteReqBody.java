package org.migzaval.notebookapi.controller.wrapperclasses;

import org.migzaval.notebookapi.model.Note;

public class AddnoteReqBody {
    private Note note;
    private String notebookId;

    public AddnoteReqBody(){}
    	
    @Override
	public String toString() {
		return "AddnoteReqBody [note=" + note + ", id=" + notebookId + "]";
	}

	public AddnoteReqBody(Note note,String notebookId){
    	this.note = note;
    	this.notebookId = notebookId;
    }
	public Note getNote() {
		return note;
	}
	public void setNote(Note note) {
		this.note = note;
	}
	public String getNotebookId() {
		return notebookId;
	}
	public void setNotebookId(String notebookId) {
		this.notebookId = notebookId;
	}
}
