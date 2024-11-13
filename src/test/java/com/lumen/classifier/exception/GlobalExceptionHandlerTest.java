package com.lumen.classifier.exception;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.WebRequest;

class GlobalExceptionHandlerTest {

	private GlobalExceptionHandler globalExceptionHandler;

	@Mock
	private WebRequest webRequest;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
		globalExceptionHandler = new GlobalExceptionHandler();
	}

	@Test
	void handleIllegalArgumentExceptionShouldReturnNotFoundResponse() {

		IllegalArgumentException exception = new IllegalArgumentException("Invalid argument");
		ResponseEntity<ErrorResponse> response = globalExceptionHandler.handleIllegalArgumentException(exception);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
		assertThat(response.getBody()).isNotNull();
		assertThat(response.getBody().getError()).isEqualTo("Resource Not Found");
		assertThat(response.getBody().getMessage()).isEqualTo("Invalid argument");
	}

	@Test
	void classifierApiExceptionShouldReturnInternalServerErrorResponse() {

		RuntimeException exception = new RuntimeException("Internal server error");
		ResponseEntity<ErrorResponse> response = globalExceptionHandler.classifierApiException(exception, webRequest);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
		assertThat(response.getBody()).isNotNull();
		assertThat(response.getBody().getError()).isEqualTo("getClassifierList Failed");
		assertThat(response.getBody().getMessage()).isEqualTo("Internal server error");
		assertThat(response.getBody().getStatsuCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR.value());
	}

	@Test
	void handleGeneralExceptionShouldReturnInternalServerErrorResponse() {

		Exception exception = new Exception("Unexpected error");
		ResponseEntity<ErrorResponse> response = globalExceptionHandler.handleGeneralException(exception);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
		assertThat(response.getBody()).isNotNull();
		assertThat(response.getBody().getError()).isEqualTo("Internal Server Error");
		assertThat(response.getBody().getMessage()).isEqualTo("An unexpected error occurred");
	}
}
