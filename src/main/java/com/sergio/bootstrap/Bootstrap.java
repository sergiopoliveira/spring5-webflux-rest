package com.sergio.bootstrap;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.sergio.domain.Category;
import com.sergio.domain.Vendor;
import com.sergio.repository.CategoryRepository;
import com.sergio.repository.VendorRepository;

//CommandLineRunner is Spring Boot specific which allows to run
//code on startup
@Component
public class Bootstrap implements CommandLineRunner {

	private CategoryRepository categoryRepository;
	private VendorRepository vendorRepository;

	public Bootstrap(CategoryRepository categoryRepository, VendorRepository vendorRepository) {
		this.categoryRepository = categoryRepository;
		this.vendorRepository = vendorRepository;
	}

	@Override
	public void run(String... args) throws Exception {

		loadCategories();
		loadVendors();
	}

	private void loadCategories() {
   
		
	if(categoryRepository.count().block() == 0) {	
		//load data
        System.out.println("#### LOADING DATA ON BOOTSTRAP #####");

        categoryRepository.save(Category.builder()
                    .description("Fruits").build()).block();

        categoryRepository.save(Category.builder()
                .description("Nuts").build()).block();

        categoryRepository.save(Category.builder()
                .description("Breads").build()).block();

        categoryRepository.save(Category.builder()
                .description("Meats").build()).block();

        categoryRepository.save(Category.builder()
                .description("Eggs").build()).block();

        System.out.println("Loaded Categories: " + categoryRepository.count().block());
		}
	}

	private void loadVendors() {

		if(vendorRepository.count().block() == 0) {	
		   vendorRepository.save(Vendor.builder()
                   .firstName("Joe")
                   .lastName("Buck").build()).block();

       vendorRepository.save(Vendor.builder()
               .firstName("Micheal")
               .lastName("Weston").build()).block();

       vendorRepository.save(Vendor.builder()
               .firstName("Jessie")
               .lastName("Waters").build()).block();

       vendorRepository.save(Vendor.builder()
               .firstName("Bill")
               .lastName("Nershi").build()).block();

       vendorRepository.save(Vendor.builder()
               .firstName("Jimmy")
               .lastName("Buffett").build()).block();

       System.out.println("Loaded Vendors: " + vendorRepository.count().block());
	}
	}
}
