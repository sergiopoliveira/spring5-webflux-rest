package com.sergio.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.sergio.domain.Vendor;
import com.sergio.repository.VendorRepository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
public class VendorController {

	private final VendorRepository vendorRepository;

	public VendorController(VendorRepository vendorRepository) {
		this.vendorRepository = vendorRepository;
	}
	
	@GetMapping("/api/v1/vendors")
	// Flux returns 0 or many results
	Flux<Vendor> list(){
		return vendorRepository.findAll();
	}
	
	@GetMapping("/api/v1/vendors/{id}")
	// Mono returns 0 or 1 result
	Mono<Vendor> getById(@PathVariable String id) {
		return vendorRepository.findById(id);
	}

}
