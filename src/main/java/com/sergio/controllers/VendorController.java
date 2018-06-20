package com.sergio.controllers;

import org.reactivestreams.Publisher;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.sergio.domain.Category;
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
	public Flux<Vendor> list() {
		return vendorRepository.findAll();
	}

	@GetMapping("/api/v1/vendors/{id}")
	// Mono returns 0 or 1 result
	public Mono<Vendor> getById(@PathVariable String id) {
		return vendorRepository.findById(id);
	}

	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping("/api/v1/vendors")
	public Mono<Void> create(@RequestBody Publisher<Vendor> vendorStream) {
		return vendorRepository.saveAll(vendorStream).then();
	}

	@PutMapping("/api/v1/vendors/{id}")
	public Mono<Vendor> update(String id, @RequestBody Vendor vendor) {
		vendor.setId(id);
		return vendorRepository.save(vendor);
	}

	@PatchMapping("/api/v1/vendors/{id}")
	public Mono<Vendor> patch(@PathVariable String id, @RequestBody Vendor vendor) {
		
		Vendor foundVendor = vendorRepository.findById(id).block();
		
		// copy of vendor to save changes locally
		Mono<Vendor> savedVendorLocally = Mono.just(vendor);
		
		// set the id passed in the argument
		savedVendorLocally.block().setId(id);
		
		// check if there are differences
		if(!foundVendor.getFirstName().equals(vendor.getFirstName())) {
			savedVendorLocally.block().setFirstName(vendor.getFirstName());
		}
		if(!foundVendor.getLastName().equals(vendor.getLastName())) {
			savedVendorLocally.block().setLastName(vendor.getLastName());
		}
		
		// if the object passed is differnt from the object in the repository, then patch 
		// it with the locally saved Vendor
		if(!vendor.equals(foundVendor)){
		return vendorRepository.save(savedVendorLocally.block());
		}
		
		return Mono.just(foundVendor);
	}
}
