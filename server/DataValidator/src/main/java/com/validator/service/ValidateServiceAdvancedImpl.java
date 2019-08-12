package com.validator.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.validator.model.Result;

import info.debatty.java.stringsimilarity.Levenshtein;

@Component
@Qualifier("validateAdvancedService")
public class ValidateServiceAdvancedImpl implements ValidateService{

	
	private Levenshtein l = new Levenshtein();
	
	@Override
	public Result validate(List<String[]> fileData) throws Exception {
		LinkedList<String[]> newList = new LinkedList<String[]>();

		for (String[] temp : fileData)
			newList.add(temp.clone());

		newList.addAll(fileData);

		List<String> duplicates = new ArrayList<String>();
		List<String> nonDuplicates = new ArrayList<String>();

		Set<String> hashSet = new HashSet<String>();
		// fileData.remove(0);

		// Preprocessing or cleaning the data before use

		LinkedList<String[]> cleanedData = dataCleaner(fileData);

		for (int i = 1; i < cleanedData.size(); i++) {

			String[] arr = cleanedData.get(i);

			for (int j = i + 1; j < cleanedData.size(); j++) {

				String[] arrNext = cleanedData.get(j);

			
			}
			
	}
		return null;
	}
	
	public LinkedList<String[]> dataCleaner(List<String[]> fileData) {
		LinkedList<String[]> newList = new LinkedList<String[]>();
		for (int i = 0; i < fileData.size() - 1; i++) {
			String[] arr = fileData.get(i);

			for (int j = 0; j < arr.length; j++) {
				arr[j] = arr[j].trim().toLowerCase();

				if (j == 4) {
					arr[j] = arr[j].split("@")[0];
				}

				if (j == 11) {
					arr[j] = arr[j].replace("-", "");

					if (arr[j].length() > 10) {
						arr[j] = arr[j].substring(arr[j].length() - 10);
					}
				}
			}

			newList.add(arr);
		}
		return newList;
	}
	
	public double getDataMatchScore(String[] first, String[] second) {
	return 0;
	}

}
