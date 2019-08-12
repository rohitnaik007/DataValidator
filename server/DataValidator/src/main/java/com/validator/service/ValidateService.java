package com.validator.service;

import java.util.List;

import com.validator.model.Result;

public interface ValidateService {

	public Result validate(List<String[]> fileData) throws Exception;
	
}
