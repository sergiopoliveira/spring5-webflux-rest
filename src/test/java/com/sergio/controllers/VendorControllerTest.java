package com.sergio.controllers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.never;

import org.junit.Before;
import org.junit.Test;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.reactivestreams.Publisher;
import org.springframework.test.web.reactive.server.WebTestClient;

import com.sergio.domain.Vendor;
import com.sergio.repository.VendorRepository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class VendorControllerTest {

	private WebTestClient webTestClient;
	private VendorRepository vendorRepository;
	private VendorController vendorController;
	
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
	
	@Test
	public void testCreateVendor() {
		BDDMockito.given(vendorRepository.saveAll(any(Publisher.class)))
		 	.willReturn(Flux.just(Vendor.builder().firstName("firstName").lastName("lastName").build()));
	
		Mono<Vendor> vendorToSaveMono = Mono.just(Vendor.builder().firstName("firstName2").lastName("lastName2").build());
		
		webTestClient.post()
			.uri("/api/v1/vendors")
			.body(vendorToSaveMono, Vendor.class)
			.exchange()
			.expectStatus()
			.isCreated();
	}
	
	@Test
	public void updateVendor() {
		BDDMockito.given(vendorRepository.save(any(Vendor.class)))
		.willReturn(Mono.just(Vendor.builder().build()));
		
		Mono<Vendor> vendorToUpdateMono = Mono.just(Vendor.builder().firstName("firstName").lastName("lastName").build()); 
		
		webTestClient.put()
		.uri("/api/v1/vendors/asdas")
		.body(vendorToUpdateMono, Vendor.class)
		.exchange()
		.expectStatus()
		.isOk();
	}
	
	@Test
	public void patchVendorWithChanges() {
		BDDMockito.given(vendorRepository.findById(anyString()))
		.willReturn(Mono.just(Vendor.builder().id("id").firstName("firstName").lastName("lastName").build()));
		
		BDDMockito.given(vendorRepository.save(any(Vendor.class)))
		.willReturn(Mono.just(Vendor.builder().build()));
		
		Mono<Vendor> vendorToUpdateMono = Mono.just(Vendor.builder().id("idCHANGED").firstName("firstNameCHANGED").lastName("lastNameCHANGED").build()); 
		
		webTestClient.patch()
		.uri("/api/v1/vendors/asda")
		.body(vendorToUpdateMono, Vendor.class)
		.exchange()
		.expectStatus()
		.isOk();
		
		BDDMockito.verify(vendorRepository).save(any());
	}
	
	@Test
	public void patchVendorWithoutChanges() {
		BDDMockito.given(vendorRepository.findById(anyString()))
		.willReturn(Mono.just(Vendor.builder().id("id").firstName("firstName").lastName("lastName").build())); 
		
		BDDMockito.given(vendorRepository.save(any(Vendor.class)))
		.willReturn(Mono.just(Vendor.builder().build()));
		
		Mono<Vendor> vendorToUpdateMono = Mono.just(Vendor.builder().id("id").firstName("firstName").lastName("lastName").build()); 
		
		webTestClient.patch()
		.uri("/api/v1/vendors/id")
		.body(vendorToUpdateMono, Vendor.class)
		.exchange()
		.expectStatus()
		.isOk();
		
		BDDMockito.verify(vendorRepository, never()).save(any());
	}
}
