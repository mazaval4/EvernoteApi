package org.migzaval.notebookapi.controller.wrapperclasses;

import org.migzaval.notebookapi.model.Note;

public class UpdateNoteReqBody {

    private Note note;
    private String notebookId;
    private Integer noteNumber;
    
    public UpdateNoteReqBody(){}

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

	public Integer getNoteNumber() {
		return noteNumber;
	}

	public void setNoteNumber(Integer noteNumber) {
		this.noteNumber = noteNumber;
	}

	@Override
	public String toString() {
		return "UpdateNoteReqBody [note=" + note + ", notebookId=" + notebookId + ", noteNumber=" + noteNumber + "]";
	}
	
	
	
}
