package com.validator.service;

import java.io.Reader;
import java.util.List;

public interface FileReader {

	public List<String[]> readAll(Reader reader) throws Exception;
}
