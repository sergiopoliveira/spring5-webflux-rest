package com.sergio.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import com.sergio.domain.Category;

public interface CategoryRepository extends ReactiveMongoRepository<Category, String>{

}
