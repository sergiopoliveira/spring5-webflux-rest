package com.sergio.controllers;

import org.junit.Before;
import org.junit.Test;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.test.web.reactive.server.WebTestClient;

import com.sergio.domain.Vendor;
import com.sergio.repository.VendorRepository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class VendorControllerTest {

	WebTestClient webTestClient;
	VendorRepository vendorRepository;
	VendorController vendorController;
	
	@Before
	public void setUp() throws Exception {
		vendorRepository = Mockito.mock(VendorRepository.class);
		vendorController = new VendorController(vendorRepository);
		webTestClient = WebTestClient.bindToController(vendorController).build();
	}
	
	@Test
	public void list() {
		BDDMockito.given(vendorRepository.findAll())
			.willReturn(Flux.just(Vendor.builder().firstName("FirstName1").lastName("LastName1").build(),
					Vendor.builder().lastName("LastName1").firstName("FirstName2").build()));
	
		webTestClient.get().uri("/api/v1/vendors/")
			.exchange()
			.expectBodyList(Vendor.class)
			.hasSize(2);
	}
	
	@Test
	public void getById() {
		BDDMockito.given(vendorRepository.findById("someid"))
				.willReturn(Mono.just(Vendor.builder().firstName("FirstName1").lastName("LastName1").build()));
	
		webTestClient.get().uri("/api/v1/vendors/someid")
		.exchange()
		.expectBody(Vendor.class);
	
	}
}
