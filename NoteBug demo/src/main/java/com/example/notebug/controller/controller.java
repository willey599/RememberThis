package com.example.notebug.controller;

//needs to interact with Note
import com.example.notebug.model.Note;
//needs to interact with repository
import com.example.notebug.repository.NoteRepository;

//enables dependency injection (allows objects to be brought in from external sources) from spring 
//this one lets spring provide an instance of NoteRepository
import org.springframework.beans.factory.annotation.Autowired;

//allows for more control over HTTP responses like status codes
import org.springframework.http.ResponseEntity;

//REST annotations that allow us to use the RestController annotation
//also maps HTTP requests to specific handler methods
import org.springframework.web.bind.annotation.*;

import java.util.List;
//this is a decent import when you are handling values that might throw a NullPointerError
import java.util.Optional;

@RestController
//sets this as the base URL for all endpoints
//all methods in this class can be used when using this URL base
@RequestMapping("/api/notes")
public class controller {
	
}
