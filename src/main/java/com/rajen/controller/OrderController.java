package com.rajen.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.rajen.dtos.apiresponse;
import com.rajen.dtos.pagableResponse;
import com.rajen.dtos.updatorderrequest;
import com.rajen.exception.badapirequest;
import com.rajen.service.OrderService;
import com.rajen.dtos.CreateOrderRequest;
import com.rajen.dtos.OrderDto;

@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    //create
    @PostMapping
    public ResponseEntity<OrderDto> createOrder(@Valid @RequestBody CreateOrderRequest request) throws badapirequest {
    	OrderDto order = orderService.createOrder(request);
        return new ResponseEntity<>(order, HttpStatus.CREATED);
    }

    @DeleteMapping("/{orderId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<apiresponse> removeOrder(@PathVariable String orderId) {
        orderService.removeOrder(orderId);
        apiresponse responseMessage = apiresponse.builder()
                .status(HttpStatus.OK)
                .message("order is removed !!")
                .sucess(true)
                .build();
        return new ResponseEntity<>(responseMessage, HttpStatus.OK);

    }

    //get orders of the user

    @GetMapping("/users/{userId}")
    public ResponseEntity<List<OrderDto>> getOrdersOfUser(@PathVariable String userId) {
        List<OrderDto> ordersOfUser = orderService.getOrdersOfUser(userId);
        return new ResponseEntity<>(ordersOfUser, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<pagableResponse<OrderDto>> getOrders(
            @RequestParam(value = "pageNumber", defaultValue = "0", required = false) int pageNumber,
            @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = "orderDate", required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = "desc", required = false) String sortDir
    ) {
        pagableResponse<OrderDto> orders = orderService.getOrders(pageNumber, pageSize, sortBy, sortDir);
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }
    
    @PutMapping("/{orderid}")
    public ResponseEntity<OrderDto> updateorder(@PathVariable("orderid") String orderid,@RequestBody updatorderrequest updatorderrequest){
	
    	  OrderDto orderDto = this.orderService.updateorder(orderid, updatorderrequest);
    	  
    	  return new ResponseEntity<OrderDto>(orderDto,HttpStatus.OK);
    	
    	
   
    }


}

