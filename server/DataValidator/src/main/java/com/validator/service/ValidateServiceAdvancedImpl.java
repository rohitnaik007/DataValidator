package com.validator.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.validator.model.Result;

@Component
@Qualifier("validateAdvancedService")
public class ValidateServiceAdvancedImpl implements ValidateService{

	@Override
	public Result validate(List<String[]> fileData) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

}
