package com.validator.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Component;

import com.validator.model.Result;
import com.validator.utils.AppConstants;

import info.debatty.java.stringsimilarity.Levenshtein;

@Component
public class ValidateServiceImpl implements ValidateService{

	private Levenshtein l = new Levenshtein();

	@Override
	public Result validate(List<String[]> fileData) throws Exception {

		List<String> duplicates = new ArrayList<String>();
		List<String> nonDuplicates = new ArrayList<String>();

		for (int i = 1; i < fileData.size() - 1; i++) {
			String[] arr = fileData.get(i);
			String[] arrNext = fileData.get(i + 1);

			if (checkDifference(arr, arrNext, 1) > AppConstants.FIRST_NAME
					|| checkDifference(arr, arrNext, 2) > AppConstants.LAST_NAME ||
					 checkDifference(arr,arrNext,3)>AppConstants.COMPANY ||
					checkDifference(arr, arrNext, 4) > AppConstants.EMAIL
					|| checkDifference(arr, arrNext, 5) > AppConstants.ADDRESS1
					|| checkDifference(arr, arrNext, 6) > AppConstants.ADDRESS2
					|| checkDifference(arr, arrNext, 7) > AppConstants.ZIP
					|| checkDifference(arr, arrNext, 8) > AppConstants.CITY
					|| checkDifference(arr, arrNext, 9) > AppConstants.STATE_LONG
					|| checkDifference(arr, arrNext, 10) > AppConstants.STATE
					|| checkDifference(arr,arrNext,11)>AppConstants.PHONE
			) {
				nonDuplicates.add(Arrays.toString(arr));
				continue;
			}

			duplicates.add(Arrays.toString(arr));
			duplicates.add(Arrays.toString(arrNext));

			i++;

		}
		
		if (!nonDuplicates.contains(fileData.get(fileData.size() - 1))) {
			nonDuplicates.add(Arrays.toString(fileData.get(fileData.size() - 1)));
		} else if (!duplicates.contains(fileData.get(fileData.size() - 1))) {
			duplicates.add(Arrays.toString(fileData.get(fileData.size() - 1)));
		}
		
		return new Result(duplicates, nonDuplicates);
	}

	private double checkDifference(String[] arr, String[] arrNext, int index) {
		if (!arr[index].trim().equals("") && !arrNext[index].trim().equals("")) {

			return l.distance(arr[index].trim(), arrNext[index].trim());
		}

		return Integer.MIN_VALUE;

	}
	
}
