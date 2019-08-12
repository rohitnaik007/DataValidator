package com.validator.controller;

import java.io.Reader;
import java.nio.file.Files;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;

import com.validator.model.Result;
import com.validator.service.FileReader;
import com.validator.service.ValidateService;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
@RequestMapping(value = "/api")
public class WebController {

	@Autowired
	FileReader fileReader;

	@Value("${normalFileName}")
	private String fileName;

	@Value("${normalFileName}")
	private String fileNameAdvanced;
	
	@Autowired
	@Qualifier("validateService")
	ValidateService validateService;
	
	@Qualifier("validateAdvancedService")
	@Autowired
	ValidateService validateAdvancedService;

	@GetMapping(value = "/data")
	public Result validateData() throws Exception {
		
		Reader reader = Files.newBufferedReader(new ClassPathResource(fileName).getFile().toPath());
		List<String[]> arr = fileReader.readAll(reader);
		return validateService.validate(arr);
	}
	
	@GetMapping(value = "/dataAdvanced")
	public Result readAllAdvanced() throws Exception {
		Reader reader = Files.newBufferedReader(new ClassPathResource(fileNameAdvanced).getFile().toPath());
		List<String[]> arr = fileReader.readAll(reader);
		return validateAdvancedService.validate(arr);
	}
}