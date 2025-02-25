//This is the class that controlls the functions of the program: CRUD and otherwise


package com.example.notebug.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
//REST annotations that allow us to use the RestController annotation
//also maps HTTP requests to specific handler methods
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//needs to interact with Note
import com.example.notebug.model.Note;
//needs to interact with repository
import com.example.notebug.repository.NoteRepository;

@RestController
//sets this as the base URL for all endpoints
//all methods in this class can be used when using this URL base
@RequestMapping("/api/notes")
public class controller {
	private NoteRepository noteRepository;
	
	//receives note, saves to DB, and returns note
	@PostMapping
	public Note createNote(@RequestBody Note note) {
		return noteRepository.save(note);
	}
	
	@GetMapping
	public List<Note> getAllNotes(){
		return noteRepository.findAll();
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Note> getNoteById(@PathVariable Long id) {
		return id.map(ResponseEntity::ok)
	}
	
}
