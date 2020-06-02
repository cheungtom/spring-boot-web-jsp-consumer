package com.example.springBootAws.services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.example.springBootAws.models.S3Object;

/**
 * @author Greg Turnquist
 */
// tag::injection[]
@Component
public class Svc1Helper {

	@Value("${s3.svc.endpoint}")
	private String s3SvcEndpoint;
	
	private final RestTemplate restTemplate;

	@Autowired
	Svc1Helper(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}
// end::injection[]

	// tag::get-comments[]
//	@HystrixCommand(fallbackMethod = "defaultCustomers")
	public List<S3Object> getS3Objects(String folderName) {
		
		System.out.println("s3SvcEndpoint = " + s3SvcEndpoint);
		
		return new ArrayList<S3Object>(restTemplate.exchange(
				s3SvcEndpoint + "/v1/s3?folderName=" + folderName,
				HttpMethod.GET,
				null,
				new ParameterizedTypeReference<Collection<S3Object>>() {
				}).getBody());
	}
	// end::get-comments[]

	// tag::fallback[]
	public List<S3Object> defaultS3Objects() {
		return Collections.emptyList();
	}
	// end::fallback[]
}
