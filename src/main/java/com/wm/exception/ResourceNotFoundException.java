package com.wm.exception;


public class ResourceNotFoundException extends RuntimeException{

	private final String fieldName;
	private final String fieldTitle;
	private final String fieldInfo;

	public ResourceNotFoundException(String fieldName, String fieldTitle, String fieldInfo) {
		super(String.format("%s is not found with %s : %s", fieldName, fieldTitle, fieldInfo));
		this.fieldName = fieldName;
		this.fieldTitle = fieldTitle;
		this.fieldInfo = fieldInfo;
	}

	public ResourceNotFoundException(String msg) {
		super(String.format("%s", msg));
		this.fieldName = msg;
		this.fieldTitle = "";
		this.fieldInfo = "";
	}

	public String getFieldName() {
		return fieldName;
	}

	public String getFieldTitle() {
		return fieldTitle;
	}

	public String getFieldInfo() {
		return fieldInfo;
	}

}
