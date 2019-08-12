package com.validator.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.apache.commons.codec.language.Metaphone;
import org.apache.commons.lang3.StringUtils;
import com.validator.model.Result;

import info.debatty.java.stringsimilarity.Levenshtein;

//ValidateService implementation for normal.csv and advanced.csv both

@Component
@Qualifier("validateAdvancedService")
public class ValidateServiceAdvancedImpl implements ValidateService {

	private Levenshtein l = new Levenshtein();

	@Override
	public Result validate(List<String[]> fileData) throws Exception {

		LinkedList<String[]> newList = new LinkedList<String[]>();

		// cloning original list for add uncleaned data to final list
		for (String[] temp : fileData)
			newList.add(temp.clone());

		newList.addAll(fileData);

		// create two lists to store data
		List<String> duplicates = new ArrayList<String>();
		List<String> nonDuplicates = new ArrayList<String>();

		// set to store duplicates helping to skip unnecessary matching in iteration
		Set<String> hashSet = new HashSet<String>();

		// Preprocessing or cleaning the data before use

		LinkedList<String[]> cleanedData = dataCleaner(fileData);

		for (int i = 1; i < cleanedData.size(); i++) {

			String[] arr = cleanedData.get(i);

			for (int j = i + 1; j < cleanedData.size(); j++) {

				String[] arrNext = cleanedData.get(j);

				if (hashSet.contains(Arrays.toString(newList.get(i)))
						&& hashSet.contains(Arrays.toString(newList.get(j)))) {
					// Do Nothing if both records are in duplicate set
				} else {
					double finalScore = getDataMatchScore(arr, arrNext);

					// add to result list and set if score above threshold
					if (finalScore > 0.8) {

						if (!hashSet.contains(i + " " + Arrays.toString(newList.get(i)))) {
							hashSet.add(i + " " + Arrays.toString(newList.get(i)));
							duplicates.add(Arrays.toString(newList.get(i)));
						}

						if (!hashSet.contains(j + " " + Arrays.toString(newList.get(j)))) {
							hashSet.add(j + " " + Arrays.toString(newList.get(j)));
							duplicates.add(Arrays.toString(newList.get(j)));
						}
					}

				}
			}

			// add non duplicates
			if (!duplicates.contains(Arrays.toString(newList.get(i)))) {
				nonDuplicates.add(Arrays.toString(newList.get(i)));
			}

		}

		// logic for last record
		if (!duplicates.contains(Arrays.toString(newList.get(newList.size() - 1)))) {
			// hashSet.add(Arrays.toString(newList.get(j)));
			nonDuplicates.add(Arrays.toString(newList.get(newList.size() - 1)));
		}

		return new Result(duplicates, nonDuplicates);
	}

	public LinkedList<String[]> dataCleaner(List<String[]> fileData) {
		LinkedList<String[]> newList = new LinkedList<String[]>();
		for (int i = 0; i < fileData.size() - 1; i++) {
			String[] arr = fileData.get(i);

			for (int j = 0; j < arr.length; j++) {
				// trim and to lowercase each sub record
				arr[j] = arr[j].trim().toLowerCase();

				// remove domain name from email for better matching
				if (j == 4) {
					arr[j] = arr[j].split("@")[0];
				}

				if (j == 11) {
					// trim special characters for phone
					arr[j] = arr[j].replace("-", "");

					// make phone number 10 digit if country code present
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

		// score for matched sub-record
		double finalScore = 0;

		// calculates number of sub-records to be matched (skips empty ones)
		double validCheckPoints = 0;

		for (int i = 1; i < first.length; i++) {
			if (!first[i].equalsIgnoreCase("") && !second[i].equalsIgnoreCase("")) {
				validCheckPoints += 1;

				// checking if the string are equal
				if (first[i].equalsIgnoreCase(second[i])) {
					finalScore += 1;
				} else if (!StringUtils.isNumeric(first[i]) && !StringUtils.isNumeric(second[i])) {
					// checking if the strings MetaPhone are equal
					Metaphone metaPhone = new Metaphone();

					if (metaPhone.isMetaphoneEqual(first[i], second[i])) {
						finalScore += 1;
					}

				}

				// check levenstein distance (converted to smaller value as levenstein function
				// gives a large value)

				double maxLength = Math.max(first[i].length(), second[i].length());

				if ((maxLength - l.distance(first[i], second[i])) / maxLength > 0.8) {
					finalScore += 1;
				}

			}

		}

		return finalScore / validCheckPoints;
	}

}
