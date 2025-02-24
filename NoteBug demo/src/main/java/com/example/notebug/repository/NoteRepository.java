package com.example.notebug.repository;

//We need to import the model package to work with the class Note.java
import com.example.notebug.model.Note;
//We also need to import the interface from Spring Data JPA which allows us to access convenient Spring methods
import org.springframework.data.jpa.repository.JpaRepository;

//this interface acts as a gateway between the application and the database. It handles instances of Note
public interface NoteRepository extends JpaRepository<Note, Long> {
	//by default you already have extended the JpaRepository, meaning you have access to all of its methods
	//but you can add custom methods here for extra functionality
}
