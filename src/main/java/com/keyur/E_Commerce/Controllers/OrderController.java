package com.keyur.E_Commerce.Controllers;

import com.keyur.E_Commerce.DTOs.OrderDTO;
import com.keyur.E_Commerce.Entities.Customer;
import com.keyur.E_Commerce.Entities.Order;
import com.keyur.E_Commerce.Services.OrderServiceImp;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RestController
public class OrderController {
    //fields
    OrderServiceImp oService;

    //dependency injection
    OrderController(OrderServiceImp oService) {
        this.oService = oService;
    }

    @PostMapping("/customer/order/place")
    public ResponseEntity<Order> addTheNewOrder(@Valid @RequestBody OrderDTO odto){
        Order savedorder = oService.saveOrder(odto);
        return new ResponseEntity<Order>(savedorder, HttpStatus.CREATED);

    }

    /*@GetMapping("/customer/orders")
    public List<Order> getAllOrders(){
        List<Order> listOfAllOrders = oService.getAllOrders();
        return listOfAllOrders;

    }*/

    @GetMapping("/customer/orders/{orderId}")
    public Order getOrdersByOrderId(@PathVariable("orderId") Integer orderId) {

        return oService.getOrderByOrderId(orderId);

    }

    @DeleteMapping("/customer/orders/{orderId}")
    public Order cancelTheOrderByOrderId(@PathVariable("orderId") Integer orderId){

        return oService.cancelOrderByOrderId(orderId);
    }

    @PutMapping("/customer/orders/{orderId}")
    public ResponseEntity<Order> updateOrderByOrder(@Valid @RequestBody OrderDTO orderdto, @PathVariable("orderId") Integer orderId){

        Order updatedOrder= oService.updateOrderByOrder(orderdto,orderId);

        return new ResponseEntity<Order>(updatedOrder,HttpStatus.ACCEPTED);

    }

    /*@GetMapping("/orders/by/date")
    public List<Order> getOrdersByDate(@RequestParam("date") String date){

        DateTimeFormatter dtf=DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate ld= LocalDate.parse(date,dtf);
        return oService.getAllOrdersByDate(ld);
    }*/

    @GetMapping("/customer/{orderId}")
    public Customer getCustomerDetailsByOrderId(@PathVariable("orderId") Integer orderId) {
        return oService.getCustomerByOrderid(orderId);
    }
}
