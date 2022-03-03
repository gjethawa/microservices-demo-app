package com.zensar.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value=HttpStatus.NOT_FOUND) //what shud be http status when then error is thrown
public class ResourceNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;
		private String resourceName;
		private String fieldName;
		private long fieldValue;
		
		
		
		public ResourceNotFoundException(String resourceName, String fieldName, long fieldValue) {
			super(resourceName+ " Not found with  : "+fieldName+" : Value : "+fieldValue);
			this.resourceName = resourceName;
			this.fieldName = fieldName;
			this.fieldValue = fieldValue;
		}
		public String getResourceName() {
			return resourceName;
		}
		public String getFieldName() {
			return fieldName;
		}
		public long getFieldValue() {
			return fieldValue;
		}
		
		
}
