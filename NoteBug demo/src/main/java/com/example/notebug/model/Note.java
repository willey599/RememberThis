package com.example.notebug.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity

public class Note {
		//primary key generation
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	//table titles 
	private String noteTitle;
	private String noteContent;
	private String noteCategory;
	
	//default constructor
	public Note() {}
	
	//parameterized constructor
	public Note(String noteTitle, String noteContent, String category) {
		this.noteTitle = noteTitle;
		this.noteContent = noteContent;
		this.noteCategory = noteCategory;
	}
	
	//getters + setters
	public long getId() {
		return id;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
	public String getNoteTitle() {
		return noteTitle;
	}
	
	public void setNoteTitle(String noteTitle) {
		this.noteTitle = noteTitle; 
	}
	
	public String getNoteContent() {
		return noteContent;
	}
	
	public void setNoteContent(String noteContent) {
		this.noteContent = noteContent;
	}
	
	public String getNoteCategory() {
		return noteCategory;
	}
	
	public void setNoteCategory(String noteCategory) {
		this.noteCategory = noteCategory;
	}
}
