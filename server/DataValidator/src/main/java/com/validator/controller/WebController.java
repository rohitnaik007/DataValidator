package com.validator.controller;

import java.io.Reader;
import java.nio.file.Files;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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

	@Autowired
	ValidateService validateService;

	@GetMapping(value = "/data")
	public Result readAll() throws Exception {
		// Reader reader = Files.newBufferedReader(Paths.get(
		// ClassLoader.getSystemResource(fileName).toURI()));
		// System.out.println(new ClassPathResource(fileName).getFile());
		Reader reader = Files.newBufferedReader(new ClassPathResource(fileName).getFile().toPath());
		List<String[]> arr = fileReader.readAll(reader);
		//System.out.println(arr);
		return validateService.validate(arr);
	}
}