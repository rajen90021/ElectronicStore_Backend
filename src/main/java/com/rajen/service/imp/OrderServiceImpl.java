package com.rajen.service.imp;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.rajen.dtos.CreateOrderRequest;
import com.rajen.dtos.OrderDto;
import com.rajen.dtos.pagableResponse;
import com.rajen.dtos.updatorderrequest;
import com.rajen.entity.Cart;
import com.rajen.entity.CartItem;
import com.rajen.entity.Order;
import com.rajen.entity.OrderItem;
import com.rajen.entity.Users;
import com.rajen.exception.badapirequest;
import com.rajen.exception.resourcenotfoundexception;
import com.rajen.repositories.CartRepository;
import com.rajen.repositories.OrderRepository;
import com.rajen.repositories.UserRepository;
import com.rajen.service.OrderService;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private CartRepository cartRepository;


    @Override
    public OrderDto createOrder(CreateOrderRequest orderDto) throws badapirequest {

        String userId = orderDto.getUserid();
        String cartId = orderDto.getCartid();
        //fetch user
        Users user = userRepository.findById(userId).orElseThrow(() -> new resourcenotfoundexception("User not found with given id !!"));

        //fetch cart
        Cart cart = cartRepository.findById(cartId).orElseThrow(() -> new resourcenotfoundexception("Cart with given id not found on server !!"));

        List<CartItem> cartItems = cart.getItems();

        if (cartItems.size() <= 0) {
            throw new badapirequest("Invalid number of items in cart !!");

        }

        //other checks

        Order order = Order.builder()
                .billingName(orderDto.getBillingName())
                .billingphone(orderDto.getBillingphone())
                .billingaddress(orderDto.getBillingaddress())
                .orderDate(new Date())
                .deliverddate(null)
                .paymentstatus(orderDto.getOrderstatus())
                .orderstatus(orderDto.getOrderstatus())
                .orderid(UUID.randomUUID().toString())
                .users(user)
                .build();

//        orderItems,amount

        AtomicReference<Integer> orderAmount = new AtomicReference<>(0);
        List<OrderItem> orderItems = cartItems.stream().map(cartItem -> {
//            CartItem->OrderItem
            OrderItem orderItem = OrderItem.builder()
                    .quantity(cartItem.getQuantity())
                    .product(cartItem.getProduct())
                    .totalprice(cartItem.getQuantity() * cartItem.getProduct().getDiscount())
                    .order(order)
                    .build();

            orderAmount.set(orderAmount.get() + orderItem.getTotalprice());
            return orderItem;
        }).collect(Collectors.toList());

        order.setOrderitems(orderItems);
        order.setOrderamount(orderAmount.get());

        //
        cart.getItems().clear();
        cartRepository.save(cart);
        Order savedOrder = orderRepository.save(order);
        return modelMapper.map(savedOrder, OrderDto.class);
    }

    @Override
    public void removeOrder(String orderId) {

        Order order = orderRepository.findById(orderId).orElseThrow(() -> new resourcenotfoundexception("order is not found !!"));
        orderRepository.delete(order);

    }

    @Override
    public List<OrderDto> getOrdersOfUser(String userId) {
        Users user = userRepository.findById(userId).orElseThrow(() -> new resourcenotfoundexception("User not found !!"));
        List<Order> orders = orderRepository.findByUsers(user);
        List<OrderDto> orderDtos = orders.stream().map(order -> modelMapper.map(order, OrderDto.class)).collect(Collectors.toList());
        return orderDtos;
    }


    public pagableResponse<OrderDto> getOrders(int pageNumber, int pageSize, String sortBy, String sortDir) {
        Sort sort = Sort.by(sortBy);

        if (sortDir.equalsIgnoreCase("desc")) {
            sort = sort.descending();
        } else {
            sort = sort.ascending();
        }

        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize, sort);

        Page<Order> pageOfOrder = this.orderRepository.findAll(pageRequest);

        List<Order> listOfContent = pageOfOrder.getContent();

        List<OrderDto> orderDtoList = listOfContent.stream()
                .map(order -> modelMapper.map(order, OrderDto.class))
                .collect(Collectors.toList());

        pagableResponse<OrderDto> response = new pagableResponse<>();

        response.setContent(orderDtoList);

        response.setPagenumber(pageOfOrder.getNumber());
        response.setPageseize(pageOfOrder.getSize());
        response.setTotalelement(pageOfOrder.getTotalElements());
        response.setTotalpages(pageOfOrder.getTotalPages());
        response.setLastpage(pageOfOrder.isLast());

        return response;
    }

	@Override
	public OrderDto updateorder(String orderid, updatorderrequest request) {
	
		 Order order = orderRepository.findById(orderid).orElseThrow(() -> new resourcenotfoundexception("Order ID not found"));

		    order.setBillingaddress(request.getBillingAddress());
		    order.setBillingName(request.getBillingName());
		    order.setBillingphone(request.getBillingPhone());
		    order.setDeliverddate(request.getDeliveredDate());
		    order.setOrderstatus(request.getOrderStatus());
		    order.setPaymentstatus(request.getPaymentStatus());

		    Order updatedOrder = orderRepository.save(order);

		    return modelMapper.map(updatedOrder, OrderDto.class);
		    
		  
	}

}











