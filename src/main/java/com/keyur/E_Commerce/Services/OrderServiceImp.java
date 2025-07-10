package com.keyur.E_Commerce.Services;

import com.keyur.E_Commerce.DTOs.CartDTO;
import com.keyur.E_Commerce.DTOs.OrderDTO;
import com.keyur.E_Commerce.Entities.*;
import com.keyur.E_Commerce.Enums.OrderStatusValues;
import com.keyur.E_Commerce.Enums.ProductStatus;
import com.keyur.E_Commerce.ExceptionObjects.CustomerNotFoundException;
import com.keyur.E_Commerce.ExceptionObjects.LoginException;
import com.keyur.E_Commerce.ExceptionObjects.OrderException;
import com.keyur.E_Commerce.Repositories.OrderDao;
import com.keyur.E_Commerce.Repositories.ProductDao;
import com.keyur.E_Commerce.Security.SecurityUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class OrderServiceImp implements OrderService {
    //fields
    LoginLogoutServiceImp loginLogoutServiceImp;
    CustomerServiceImp customerServiceImp;
    OrderDao orderDao;
    CartServiceImp cartServiceImp;
    ProductDao productDao;
    SecurityUtils securityUtils;

    //injecting dependencies using constructor injection
    OrderServiceImp(LoginLogoutServiceImp loginLogoutServiceImp, CustomerServiceImp customerServiceImp, OrderDao orderDao, CartServiceImp cartServiceImp, ProductDao productDao, SecurityUtils securityUtils) {
        this.loginLogoutServiceImp = loginLogoutServiceImp;
        this.customerServiceImp = customerServiceImp;
        this.orderDao = orderDao;
        this.cartServiceImp = cartServiceImp;
        this.productDao = productDao;
        this.securityUtils = securityUtils;
    }

    @Override
    public Order saveOrder(OrderDTO orderDTO) throws LoginException, OrderException {
        //get the customer details and check session validity
        Customer customer = securityUtils.getCurrentCustomer();

        //check if all the items in the cart are present in the database in the required quantity or not
        for(CartItem cartItem : customer.getCustomerCart().getCartItems()) {
            int remainingQuantity = cartItem.getCartProduct().getQuantity() - cartItem.getCartItemQuantity();
            if(remainingQuantity < 0) {
                throw new OrderException("Only " + (cartItem.getCartItemQuantity() + remainingQuantity) + cartItem.getCartProduct().getProductName() + " remaining");
            }
        }

        //check the card details and then proceed for placing order
        if(orderDTO.getCreditCard().getCardNumber().equals(customer.getCreditCard().getCardNumber()) &&
           orderDTO.getCreditCard().getCardValidity().equals(customer.getCreditCard().getCardValidity()) &&
           orderDTO.getCreditCard().getCardCVV().equals(customer.getCreditCard().getCardCVV())){

            //place a new order
            Order newOrder = new Order();

            //create a list of OrderItems
            List<OrderItems> orderItems = new ArrayList<>();

            //if the all the items are available in the required amount(which we checked above), update the quantities and
            for(CartItem cartItem : customer.getCustomerCart().getCartItems()) {
                int remainingQuantity = cartItem.getCartProduct().getQuantity() - cartItem.getCartItemQuantity();
                cartItem.getCartProduct().setQuantity(remainingQuantity);

                //add the CartItems as OrderItems to the order
                OrderItems newOrderItem = new OrderItems();
                newOrderItem.setOrder(newOrder);
                newOrderItem.setProduct(cartItem.getCartProduct());
                newOrderItem.setQuantity(cartItem.getCartItemQuantity());
                orderItems.add(newOrderItem);

                //if 0 items remain of any product, we need to update its status as OUT OF STOCK
                if(remainingQuantity == 0) {
                    cartItem.getCartProduct().setStatus(ProductStatus.OUTOFSTOCK);
                }
            }

            //set the required fields of the new order
            newOrder.setOrderItems(orderItems);
            newOrder.setDate(LocalDate.now());
            newOrder.setTotal(customer.getCustomerCart().getCartTotal());
            newOrder.setCustomer(customer);
            newOrder.setAddress(customer.getAddress().get(orderDTO.getAddressType()));
            newOrder.setOrderStatus(OrderStatusValues.SUCCESS);
            newOrder.setCardNumber(orderDTO.getCreditCard().getCardNumber());

            //save the order to the database
            orderDao.save(newOrder);

            //clear the cart of the customer
            cartServiceImp.clearCart();

            return newOrder;
        } else {
            throw new OrderException("Invalid card details!");
        }
    }

    @Override
    public Order getOrderByOrderId(Integer orderId) throws OrderException {
        Optional<Order> optionalOrder = orderDao.findById(orderId);

        if(optionalOrder.isEmpty()) {
            throw new OrderException("No order found!");
        }

        return optionalOrder.get();
    }

    @Override
    public List<Order> getAllOrders() throws OrderException {
        List<Order> optionalOrders = orderDao.findAll();

        if(optionalOrders.isEmpty()) {
            throw new OrderException("No orders found!");
        } else {
            return optionalOrders;
        }
    }

    @Override
    public Order cancelOrderByOrderId(Integer orderId) throws OrderException, LoginException {
        //check if the order to be cancelled exists or not
        Optional<Order> optionalOrder = orderDao.findById(orderId);

        //if there is no such order, throw an exception
        if(optionalOrder.isEmpty()) {
            throw new OrderException("No order found!");
        }

        //convert the Optional value to Order
        Order order = optionalOrder.get();

        //get the details of the customer who is currently logged in
        Customer customer = securityUtils.getCurrentCustomer();

        //check if the customer who is logged is deleting his order only or not
        if(!order.getCustomer().getCustomerId().equals(customer.getCustomerId())) {
            throw new LoginException("Invalid login!");
        }

        //add the products back to catalog
        for(OrderItems orderItem : order.getOrderItems()) {
            Product product = orderItem.getProduct();
            orderItem.getProduct().setQuantity(product.getQuantity() + orderItem.getQuantity());
            orderItem.getProduct().setStatus(ProductStatus.AVAILABLE);
            productDao.save(product);
        }

        //delete the order from database
        orderDao.delete(order);

        return order;
    }

    @Override
    public Order updateOrderByOrder(OrderDTO orderDTO, Integer orderId) throws OrderException, LoginException {
        //check if any order with the given id exists in the database or not
        Optional<Order> optionalOrder = orderDao.findById(orderId);

        if(optionalOrder.isEmpty()) {
            throw new OrderException("No order found!");
        }

        //convert the Optional value to Order value
        Order existingOrder = optionalOrder.get();

        Customer customer = existingOrder.getCustomer();

        //check if all the items in the cart are present in the database in the required quantity or not
        for(CartItem cartItem : existingOrder.getCustomer().getCustomerCart().getCartItems()) {
            int remainingQuantity = cartItem.getCartProduct().getQuantity() - cartItem.getCartItemQuantity();
            if(remainingQuantity < 0) {
                throw new OrderException("Only " + (cartItem.getCartItemQuantity() + remainingQuantity) + cartItem.getCartProduct().getProductName() + " remaining");
            }
        }

        //check the card details and then proceed for placing order
        if(orderDTO.getCreditCard().getCardNumber().equals(customer.getCreditCard().getCardNumber()) &&
                orderDTO.getCreditCard().getCardValidity().equals(customer.getCreditCard().getCardValidity()) &&
                orderDTO.getCreditCard().getCardCVV().equals(customer.getCreditCard().getCardCVV())){


            //create a list of OrderItems
            List<OrderItems> orderItems = new ArrayList<>();

            //if the all the items are available in the required amount(which we checked above), update the quantities and
            for(CartItem cartItem : customer.getCustomerCart().getCartItems()) {
                int remainingQuantity = cartItem.getCartProduct().getQuantity() - cartItem.getCartItemQuantity();
                cartItem.getCartProduct().setQuantity(remainingQuantity);

                //add the CartItems as OrderItems to the order
                OrderItems newOrderItem = new OrderItems();
                newOrderItem.setOrder(existingOrder);
                newOrderItem.setProduct(cartItem.getCartProduct());
                newOrderItem.setQuantity(cartItem.getCartItemQuantity());
                orderItems.add(newOrderItem);

                //if 0 items remain of any product, we need to update its status as OUT OF STOCK
                if(remainingQuantity == 0) {
                    cartItem.getCartProduct().setStatus(ProductStatus.OUTOFSTOCK);
                }
            }

            //set the required fields of the new order
            existingOrder.getOrderItems().clear();
            existingOrder.getOrderItems().addAll(orderItems);
            existingOrder.setAddress(customer.getAddress().get(orderDTO.getAddressType()));
            existingOrder.setCardNumber(orderDTO.getCreditCard().getCardNumber());

            //save the order to the database
            orderDao.save(existingOrder);

            //clear the cart of the customer
            cartServiceImp.clearCart();

            return existingOrder;
        } else {
            throw new OrderException("Invalid card details!");
        }
    }

    @Override
    public Customer getCustomerByOrderid(Integer orderId) throws OrderException {
        Optional<Order> optionalOrder = orderDao.findById(orderId);

        if(optionalOrder.isPresent()) {
            return optionalOrder.get().getCustomer();
        } else {
            throw new OrderException("No order found!");
        }
    }
}
