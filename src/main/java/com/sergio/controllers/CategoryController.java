package com.sergio.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.sergio.domain.Category;
import com.sergio.repository.CategoryRepository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
public class CategoryController {

	private final CategoryRepository categoryRepository;

	public CategoryController(CategoryRepository categoryRepository) {
		this.categoryRepository = categoryRepository;
	}
	
	@GetMapping("/api/v1/categories")
	Flux<Category> list(){
		return categoryRepository.findAll();
	}
	
	@GetMapping("/api/v1/categories/{id}")
	Mono<Category> getById(@PathVariable String id) {
		return categoryRepository.findById(id);
	}

}
