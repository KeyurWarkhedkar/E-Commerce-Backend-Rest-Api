package com.keyur.E_Commerce.Services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.keyur.E_Commerce.DTOs.CustomerDTO;
import com.keyur.E_Commerce.DTOs.CustomerUpdateDTO;
import com.keyur.E_Commerce.Entities.*;
import com.keyur.E_Commerce.ExceptionObjects.CustomerException;
import com.keyur.E_Commerce.ExceptionObjects.CustomerNotFoundException;
import com.keyur.E_Commerce.ExceptionObjects.LoginException;
import com.keyur.E_Commerce.ExceptionObjects.SellerNotFoundException;
import com.keyur.E_Commerce.Repositories.CustomerDao;
import com.keyur.E_Commerce.Repositories.SellerDao;
import com.keyur.E_Commerce.Repositories.SessionDao;
import com.keyur.E_Commerce.Security.SecurityUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CustomerServiceImp implements CustomerService{
    //defining fields
    CustomerDao customerDao;
    SessionDao sessionDao;
    LoginLogoutServiceImp loginLogoutServiceImp;
    SellerDao sellerDao;
    ObjectMapper objectMapper;
    BCryptPasswordEncoder bCryptPasswordEncoder;
    SecurityUtils securityUtils;

    //injecting using constructor injection
    public CustomerServiceImp(CustomerDao customerDao, SessionDao sessionDao, LoginLogoutServiceImp loginLogoutServiceImp, SellerDao sellerDao, ObjectMapper objectMapper, BCryptPasswordEncoder bCryptPasswordEncoder, SecurityUtils securityUtils) {
        this.customerDao = customerDao;
        this.sessionDao = sessionDao;
        this.loginLogoutServiceImp = loginLogoutServiceImp;
        this.sellerDao = sellerDao;
        this.objectMapper = objectMapper;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.securityUtils = securityUtils;
    }

    //function to add a customer to the catalog/database
    @Override
    public Customer addCustomer(Customer customer) throws CustomerException {
        //check if the customer who wants to register, already exists in the database or not
        Optional<Customer> check = customerDao.findByMobileNo(customer.getMobileNo());

        //if the customer already exists, throw an error
        if(check.isPresent()) {
            throw new CustomerException("Customer is already registered!");
        }

        //set the remaining fields of the customer object
        if(customer.getCreditCard() == null) {
            customer.setCreditCard(new CreditCard());
        }
        customer.setCreatedOn(LocalDateTime.now());

        //set the Bcrypt password for the customer
        customer.setPassword(bCryptPasswordEncoder.encode(customer.getPassword()));

        //set the role of the customer for future authentication
        customer.setRole("ROLE_CUSTOMER");

        Cart cart = new Cart();
        cart.setCustomer(customer);
        customer.setCustomerCart(cart);
        customer.setOrders(new ArrayList<>());

        //save the customer to the database
        customerDao.save(customer);

        return customer;
    }

    //function to get the details of customer
    /*@Override
    public Customer getLoggedInCustomerDetails(String token) throws CustomerNotFoundException, LoginException {
        //checking if the token received is of customer or not
        if(!token.contains("customer")) {
            throw new LoginException("Invalid session for customer");
        }

        //retrieving the session using token received
        Optional<UserSession> check = sessionDao.findByToken(token);

        if(check.isEmpty()) {
            throw new LoginException("No session found for the given customer!");
        }

        //converting the Optional type to UserSession
        UserSession userSession = check.get();

        //check if the time of the session is over or not
        if(!loginLogoutServiceImp.checkSessionValidity(userSession)) {
            throw new LoginException("Session already expired");
        }

        //retrieve the customer details using the customer id from the session
        Optional<Customer> customerOptional = customerDao.findById(userSession.getUserId());

        //check if the customer with the given id is present or not
        if(customerOptional.isEmpty()) {
            throw new CustomerNotFoundException("No customer found with the given session!");
        }

        //return the customer details
        return customerOptional.get();
    }*/

    //function to get details of all customers. Only admin and seller can access
    //Need to add role functionality using JWT tokens and UserDetails
    @Override
    public List<Customer> getAllCustomers() throws CustomerNotFoundException, LoginException, SellerNotFoundException {
        //role based authentication and token validation will be performed spring
        //we only need to check if the token provided is owned by any seller in the database or not
        securityUtils.getCurrentSeller();

        List<Customer> customerList = customerDao.findAll();

        //if no customers exists, throw an error
        if(customerList.isEmpty()) {
            throw new CustomerNotFoundException("No customer records found!");
        }

        //return the list of customers
        return customerList;
    }

    //function to partially update a customer
    @Override
    public Customer updateCustomer(CustomerUpdateDTO customer) throws CustomerNotFoundException, LoginException {
        //fetch the customer from database
        Customer fromDatabase = securityUtils.getCurrentCustomer();

        //update the fields of the customer from database according to the dto
        if(customer.getFirstName() != null) {
            fromDatabase.setFirstName(customer.getFirstName());
        }

        if(customer.getLastName() != null) {
            fromDatabase.setLastName(customer.getLastName());
        }

        if(customer.getEmailId() != null) {
            fromDatabase.setEmailId(customer.getEmailId());
        }

        if(customer.getMobileNo() != null) {
            fromDatabase.setMobileNo(customer.getMobileNo());
        }

        if(customer.getPassword() != null) {
            fromDatabase.setPassword(customer.getPassword());
        }

        if(customer.getAddress() != null) {
            for(String type : customer.getAddress().keySet()) {
                fromDatabase.getAddress().put(type, customer.getAddress().get(type));
            }
        }

        //save the changes to the database as we are not annotating our methods with @Transactional
        customerDao.save(fromDatabase);

        return fromDatabase;
    }

    @Override
    public Customer updateCustomerMobileNoOrEmailId(CustomerUpdateDTO customerUpdateDTO) throws CustomerNotFoundException, LoginException {
        //fetch the customer from database using utility method
        Customer fromDatabase = securityUtils.getCurrentCustomer();

        //update the mobile and email of the managed entity
        if(customerUpdateDTO.getEmailId() != null) {
            fromDatabase.setEmailId(customerUpdateDTO.getEmailId());
        }
        if(customerUpdateDTO.getMobileNo() != null) {
            fromDatabase.setMobileNo(customerUpdateDTO.getMobileNo());
        }

        //save the updated value to database
        customerDao.save(fromDatabase);

        //return the updated values
        return fromDatabase;
    }

    @Override
    public Customer updateCreditCardDetails(CreditCard card) throws CustomerException, LoginException {
        //fetch the customer from database
        Customer fromDatabase = securityUtils.getCurrentCustomer();

        if(fromDatabase.getCreditCard() == null) {
            fromDatabase.setCreditCard(new CreditCard());
        }

        //get the card of the user
        CreditCard originalCard = fromDatabase.getCreditCard();

        //update the credit card for the customer
        if(card.getCardNumber() != null) {
            originalCard.setCardNumber(card.getCardNumber());
        }
        if(card.getCardValidity() != null) {
            originalCard.setCardValidity(card.getCardValidity());
        }
        if(card.getCardCVV() != null) {
            originalCard.setCardCVV(card.getCardCVV());
        }

        //set the updated card as new card of the customer
        fromDatabase.setCreditCard(originalCard);

        //save the updated values to the database
        customerDao.save(fromDatabase);

        //return the updated values
        return fromDatabase;
    }

    @Override
    public String updateCustomerPassword(CustomerDTO customerDTO) throws CustomerNotFoundException, LoginException, CustomerException {
        //fetch the current customer from database
        Customer fromDatabase = securityUtils.getCurrentCustomer();

        //verify mobile number and update the password of the customer
        if(customerDTO.getMobileId().equals(fromDatabase.getMobileNo())) {
            if(customerDTO.getPassword() != null) {
                fromDatabase.setPassword(bCryptPasswordEncoder.encode(customerDTO.getPassword()));

                //save the customer to the database with updated details
                customerDao.save(fromDatabase);

                loginLogoutServiceImp.logoutCustomer();
            } else {
                throw new CustomerException("Cannot update the password to blank!");
            }
        } else {
            throw new CustomerException("Verification failed. Could not update password!");
        }

        return "Changed password and logged out. Login again with new password!";
    }

    @Override
    public String deleteCustomer(CustomerDTO customerDTO) throws CustomerNotFoundException, LoginException, CustomerException {
        //fetch the customer from the database
        Customer fromDatabase = securityUtils.getCurrentCustomer();

        //validate the customer and delete
        if(customerDTO.getMobileId().equals(fromDatabase.getMobileNo()) && bCryptPasswordEncoder.matches(customerDTO.getPassword(), fromDatabase.getPassword())) {
            loginLogoutServiceImp.logoutCustomer();
            customerDao.delete(fromDatabase);

            return "Deleted details successfully!";
        } else {
            throw new CustomerException("Verification failed. Could not delete details!");
        }
    }

    @Override
    public Customer updateAddress(Address address, String type) throws CustomerException, LoginException {
        //fetch the customer from the database
        Customer fromDatabase = securityUtils.getCurrentCustomer();

        //update the key-value pair of the customer
        fromDatabase.getAddress().put(type, address);

        //save the customer with updated address to the database
        customerDao.save(fromDatabase);

        //return the updated customer
        return fromDatabase;
    }

    @Override
    public List<Order> getCustomerOrders() throws CustomerException, LoginException {
        //fetch the customer from the database
        Customer fromDatabase = securityUtils.getCurrentCustomer();

        //get the list of orders of the customer from the database
        List<Order> orders = fromDatabase.getOrders();

        //check if there are any orders or not
        if(orders.isEmpty()) {
            throw new CustomerException("No orders found for the given customer!");
        }

        return orders;
    }
}
