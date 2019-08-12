package com.validator.model;

import java.util.List;

public class Result {
	
	List<String> duplicates;
	List<String> nonDuplicates;
	
	
	public Result(List<String> duplicates,List<String> nonDuplicates)
	{
		this.duplicates = duplicates;
		this.nonDuplicates = nonDuplicates;
	}
	
	public List<String> getDuplicates() {
		return duplicates;
	}
	public void setDuplicates(List<String> duplicates) {
		this.duplicates = duplicates;
	}
	public List<String> getNonDuplicates() {
		return nonDuplicates;
	}
	public void setNonDuplicates(List<String> nonDuplicates) {
		this.nonDuplicates = nonDuplicates;
	}
	
	
}