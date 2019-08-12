package com.validator.service;

import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.opencsv.CSVReader;

@Component
public class CSVFileReader implements FileReader{

	public List<String[]> readAll(Reader reader) throws Exception {
	    CSVReader csvReader = new CSVReader(reader);
	    List<String[]> list = new ArrayList<>();
	    list = csvReader.readAll();
	    reader.close();
	    csvReader.close();
	    return list;
	}

	
}
