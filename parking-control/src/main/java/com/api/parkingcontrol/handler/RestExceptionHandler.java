package com.api.parkingcontrol.handler;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.api.parkingcontrol.exception.BadRequestException;
import com.api.parkingcontrol.exception.BadRequestExceptionDetails;
import com.api.parkingcontrol.exception.ExceptionDetails;
import com.api.parkingcontrol.exception.ValidationExceptionDetails;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler{
	
	@ExceptionHandler(BadRequestException.class)
	public ResponseEntity<BadRequestExceptionDetails> handleBadRequestException(BadRequestException bre){
		return new ResponseEntity<>(BadRequestExceptionDetails.builder()
				.timestamp(LocalDateTime.now())
				.status(HttpStatus.BAD_REQUEST.value())
				.title("Bad request exception, check the fields")
				.developerMessage(bre.getClass().getName())
				.build(), HttpStatus.BAD_REQUEST);
	}
	
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException exception, 
			HttpHeaders headers, HttpStatusCode statusCode, WebRequest request){
		
		List<FieldError> fieldErrors = exception.getBindingResult().getFieldErrors();
		
		String fields = fieldErrors.stream().map(FieldError::getField).collect(Collectors.joining(", "));
		String fieldMessage = fieldErrors.stream().map(FieldError::getDefaultMessage).collect(Collectors.joining(", "));
		
		return new ResponseEntity<>(ValidationExceptionDetails.builder()
				.timestamp(LocalDateTime.now())
				.status(HttpStatus.BAD_REQUEST.value())
				.title("Bad request exception, invalid fields")
				.details("Check the field(s) error")
				.developerMessage(exception.getClass().getName())
				.fields(fields)
				.fieldsMessage(fieldMessage)
				.build(), HttpStatus.BAD_REQUEST);
	}
	
	@Override
	protected ResponseEntity<Object> handleExceptionInternal(
			Exception ex, @Nullable Object body, HttpHeaders headers, HttpStatusCode statusCode, WebRequest request) {
		
		ExceptionDetails exceptionDetails = ExceptionDetails.builder()
		.timestamp(LocalDateTime.now())
		.status(statusCode.value())
		.title(ex.getCause().getMessage())
		.details(ex.getMessage())
		.developerMessage(ex.getClass().getName())
		.build();

		return new ResponseEntity<>(exceptionDetails, headers, statusCode);
	}
}
