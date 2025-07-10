package com.keyur.E_Commerce.ExceptionHandler;

import java.time.LocalDateTime;

import com.keyur.E_Commerce.ExceptionDetails.ErrorDetails;
import com.keyur.E_Commerce.ExceptionObjects.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;


//global exception handler to handle exception form all controllers
@ControllerAdvice
public class GlobalExceptionHandler {
	@ExceptionHandler(ProductNotFoundException.class)
	public ResponseEntity<ErrorDetails> productNotFound(ProductNotFoundException pnf,WebRequest wr){
		ErrorDetails err = new ErrorDetails(LocalDateTime.now(),pnf.getMessage());
		return new ResponseEntity<>(err, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(CategoryNotFoundException.class)
	public ResponseEntity<ErrorDetails> categoryNotFound(CategoryNotFoundException cnf, WebRequest wr){
		ErrorDetails err = new ErrorDetails(LocalDateTime.now(),cnf.getMessage());

		return new ResponseEntity<ErrorDetails>(err,HttpStatus.NO_CONTENT);


	}

	@ExceptionHandler(SellerException.class)
	public ResponseEntity<ErrorDetails> sellerExceptionHandler(SellerException slre, WebRequest wr){
		ErrorDetails err = new ErrorDetails(LocalDateTime.now(), slre.getMessage());
		return new ResponseEntity<>(err, HttpStatus.BAD_REQUEST);
	}


	@ExceptionHandler(SellerNotFoundException.class)
	public ResponseEntity<ErrorDetails> sellerNotFoundExceptionHandler(SellerNotFoundException snfe, WebRequest wr){
		ErrorDetails err = new ErrorDetails(LocalDateTime.now(), snfe.getMessage());
		return new ResponseEntity<>(err, HttpStatus.NOT_FOUND);
	}


	@ExceptionHandler(CustomerNotFoundException.class)
	public ResponseEntity<ErrorDetails> customerNotFoundExceptionHandler(CustomerNotFoundException cnfe, WebRequest wr){
		ErrorDetails err = new ErrorDetails(LocalDateTime.now(), cnfe.getMessage());
		return new ResponseEntity<>(err, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(CustomerException.class)
	public ResponseEntity<ErrorDetails> customerExceptionHandler(CustomerException ce, WebRequest wr){
		ErrorDetails err = new ErrorDetails(LocalDateTime.now(), ce.getMessage());
		return new ResponseEntity<>(err, HttpStatus.FORBIDDEN);
	}



	@ExceptionHandler(LoginException.class)
	public ResponseEntity<ErrorDetails> loginExceptionHandler(LoginException le, WebRequest wr){
		ErrorDetails err = new ErrorDetails(LocalDateTime.now(), le.getMessage());
		return new ResponseEntity<>(err, HttpStatus.FORBIDDEN);
	}

	@ExceptionHandler(OrderException.class)
	public ResponseEntity<ErrorDetails> orderExceptionHandler(OrderException oe, WebRequest wr){
		ErrorDetails err = new ErrorDetails(LocalDateTime.now(), oe.getMessage());
		return new ResponseEntity<>(err, HttpStatus.FORBIDDEN);
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ErrorDetails> methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException manv, WebRequest wr){
		String message = manv.getBindingResult().getFieldError().getDefaultMessage();
		ErrorDetails err = new ErrorDetails(LocalDateTime.now(), message);
		return new ResponseEntity<ErrorDetails>(err, HttpStatus.FORBIDDEN);
	}



	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorDetails> exceptionHandler(Exception e, WebRequest wr){
		ErrorDetails err = new ErrorDetails(LocalDateTime.now(), e.getMessage());
		return new ResponseEntity<ErrorDetails>(err, HttpStatus.BAD_REQUEST);
	}
}
