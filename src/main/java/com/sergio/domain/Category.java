package com.sergio.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Document // for MongoDB, the JPA entities are refered to as documents
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Category {

	@Id
	private String id;
	
	private String description;
}
