package com.sergio.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import com.sergio.domain.Vendor;

public interface VendorRepository extends ReactiveMongoRepository<Vendor, String>{

}
