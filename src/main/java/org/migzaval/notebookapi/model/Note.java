package org.migzaval.notebookapi.model;

import java.time.Instant;
import java.util.ArrayList;

public class Note {

	private String title,body;
	private ArrayList<String> tags;
	private Instant instant;
	long created,modified;
	
	public Note(){
	}
	public Note(String title, String body, ArrayList<String> tags) {
		super();
		this.title = title;
		this.body = body;
		this.tags = tags;
		this.created = instant.toEpochMilli();
		this.modified =instant.toEpochMilli();
	}

	public Note(String title, String body, ArrayList<String> tags,long created,long modified) {
		super();
		this.title = title;
		this.body = body;
		this.tags = tags;
		this.created = created;
		this.modified = modified;
	}

	@Override
	public String toString() {
		return "{title:" + title + ", body:" + body + ", tags:" + tags + ", created:" + created + ", modified:"
				+ modified + "}";
	}

	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getBody() {
		return body;
	}
	public void setBody(String body) {
		this.body = body;
	}
	public ArrayList<String> getTags() {
		return tags;
	}
	public void setTags(ArrayList<String> tags) {
		this.tags = tags;
	}
	public Long getCreated() {
		return created;
	}
	public void setCreated(Long created) {
		this.created = created;
	}
	public Long getModified() {
		return modified;
	}
	public void setModified(Long modified) {
		this.modified = modified;
	}
	
	
}
