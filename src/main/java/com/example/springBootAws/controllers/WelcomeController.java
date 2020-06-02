package com.example.springBootAws.controllers;

import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.springBootAws.ApplicationConfiguration;
import com.example.springBootAws.models.S3Object;
import com.example.springBootAws.services.Svc1Helper;

@Controller
public class WelcomeController {

	private Log log = LogFactory.getLog(getClass());
	
	private final ApplicationConfiguration applicationConfiguration;
	private final Svc1Helper svc1Helper;
	 
	@Autowired
	 public WelcomeController(ApplicationConfiguration applicationConfiguration, 
			 						Svc1Helper svc1Helper) { // <2>
	  this.applicationConfiguration = applicationConfiguration;
	  this.svc1Helper = svc1Helper;
	}
	
	// inject via application.properties
	@Value("${welcome.message:test}")
	private String message = "Hello World";

	@RequestMapping("/")
	public String welcome(@RequestParam("folderName") String folderName, Map<String, Object> model) {
		model.put("message", this.message);
		model.put("folderName", folderName);
		
		List<S3Object> s3ObjectList = this.svc1Helper.getS3Objects(folderName);
		System.out.println("s3ObjectList size = " + s3ObjectList != null ? s3ObjectList.size() : null);
		
		model.put("s3ObjectList", s3ObjectList);
		model.put("s3ObjectListSize", s3ObjectList == null? null:s3ObjectList.size());
		
		return "welcome";
	}

}