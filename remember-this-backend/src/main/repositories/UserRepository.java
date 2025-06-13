package com.github.willeylee.remember_this;

import org.springframework.data.repository.CrudRepository;
import src.main.entities.User.java;

public interface UserRepository extends CrudRepository<User, Integer> {

}
