package com.validator.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.validator.model.Result;
import com.validator.utils.AppConstants;

import info.debatty.java.stringsimilarity.Levenshtein;

//ValidateService implementation for normal.csv

@Component
@Qualifier("validateService")
public class ValidateServiceImpl implements ValidateService{

	private Levenshtein l = new Levenshtein();
	
	@Override
	public Result validate(List<String[]> fileData) throws Exception {

		//create two lists to store data
		List<String> duplicates = new ArrayList<String>();
		List<String> nonDuplicates = new ArrayList<String>();

		//loop through csv data
		for (int i = 1; i < fileData.size() - 1; i++) {
			String[] arr = fileData.get(i);
			String[] arrNext = fileData.get(i + 1);

			//Check if any condition is above limit set in AppConstants, if above limit then its not matching
			if (checkDifference(arr, arrNext, 1) > AppConstants.FIRST_NAME
					|| checkDifference(arr, arrNext, 2) > AppConstants.LAST_NAME ||
					checkDifference(arr, arrNext, 4) > AppConstants.EMAIL
					|| checkDifference(arr, arrNext, 5) > AppConstants.ADDRESS1
					|| checkDifference(arr, arrNext, 6) > AppConstants.ADDRESS2
					|| checkDifference(arr, arrNext, 7) > AppConstants.ZIP
					|| checkDifference(arr, arrNext, 8) > AppConstants.CITY
					|| checkDifference(arr, arrNext, 9) > AppConstants.STATE_LONG
					|| checkDifference(arr, arrNext, 10) > AppConstants.STATE
			) {
				//add current record to nonduplicates list
				nonDuplicates.add(Arrays.toString(arr));
				continue;
			}

			//add current record and next record to duplicates list as they match
			duplicates.add(Arrays.toString(arr));
			duplicates.add(Arrays.toString(arrNext));

			i++;

		}
		
		//check for last record only
		if (!nonDuplicates.contains(fileData.get(fileData.size() - 1))) {
			nonDuplicates.add(Arrays.toString(fileData.get(fileData.size() - 1)));
		} else if (!duplicates.contains(fileData.get(fileData.size() - 1))) {
			duplicates.add(Arrays.toString(fileData.get(fileData.size() - 1)));
		}
		
		return new Result(duplicates, nonDuplicates);
	}

	//Find the levenshtein distance
	private double checkDifference(String[] arr, String[] arrNext, int index) {
		if (!arr[index].trim().equals("") && !arrNext[index].trim().equals("")) {

			return l.distance(arr[index].trim(), arrNext[index].trim());
		}
		
		//If one string empty then return low value to make statement true
		return Integer.MIN_VALUE;

	}
	
}
