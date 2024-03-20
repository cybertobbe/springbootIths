package com.example.springbootprojektiths.repository;

import com.example.springbootprojektiths.entity.User;
import org.springframework.data.repository.ListCrudRepository;

public interface UserRepository extends ListCrudRepository<User, Long>{

}
