package com.keyur.E_Commerce.Services;

import com.keyur.E_Commerce.DTOs.OrderDTO;
import com.keyur.E_Commerce.Entities.Customer;
import com.keyur.E_Commerce.Entities.Order;
import com.keyur.E_Commerce.ExceptionObjects.LoginException;
import com.keyur.E_Commerce.ExceptionObjects.OrderException;

import java.time.LocalDate;
import java.util.List;

public interface OrderService {
    public Order saveOrder(OrderDTO orderDto) throws LoginException, OrderException;

    public Order getOrderByOrderId(Integer orderId) throws OrderException;

    public List<Order> getAllOrders() throws OrderException;

    public Order cancelOrderByOrderId(Integer orderId) throws OrderException;

    public Order updateOrderByOrder(OrderDTO order,Integer orderId) throws OrderException,LoginException;

    public Customer getCustomerByOrderid(Integer orderId) throws OrderException;
}
