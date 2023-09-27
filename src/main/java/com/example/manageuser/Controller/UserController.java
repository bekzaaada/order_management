package com.example.manageuser.Controller;

import com.example.manageuser.Entity.Order;
import com.example.manageuser.Entity.User;
import com.example.manageuser.Service.OrderService;
import com.example.manageuser.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(path = "/api/users")
public class UserController {
    @Autowired
    private final UserService userService;
    @Autowired
    private final OrderService orderService;

    @Autowired
    public UserController(UserService userService, OrderService orderService){
        this.userService = userService;
        this.orderService = orderService;
    }
    @GetMapping
    public List<User> getUsers(){
        return userService.getUsers();
    }
//
//    @GetMapping(path = "/order_get")
//    public List<Order> order_get(){
//        return orderService.order_get();
//    }
//    @GetMapping(path = "/order_add")
//    public void order_add(Order order){
//        orderService.order_add(order);
//    }


}
