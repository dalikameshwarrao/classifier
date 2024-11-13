package com.lumen.classifier.exception;

public class ErrorResponse {

    private String error;
    private String message;
    private Integer statusCode;

    public ErrorResponse(String error, String message) {
        this.error = error;
        this.message = message;
    }
    
	public ErrorResponse(String error, String message, Integer statsuCode) {
		super();
		this.error = error;
		this.message = message;
		this.statusCode = statsuCode;
	}

	public Integer getStatsuCode() {
		return statusCode;
	}



	public void setStatsuCode(Integer statsuCode) {
		this.statusCode = statsuCode;
	}



	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	
}
