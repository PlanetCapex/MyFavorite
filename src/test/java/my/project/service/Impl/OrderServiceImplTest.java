package my.project.service.Impl;

import my.project.controller.OrderController;
import my.project.domain.Good;
import my.project.domain.Order;
import my.project.domain.Role;
import my.project.domain.User;
import my.project.repos.OrderRepository;
import my.project.repos.UserRepository;
import my.project.service.OrderService;
import my.project.service.UserService;
import org.hamcrest.CoreMatchers;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

public class OrderServiceImplTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserService userService;

    @Autowired
    private OrderServiceImpl orderServiceImpl;

    @Autowired
    private OrderController orderController;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private OrderRepository orderRepository;

    @MockBean
    private MailSenderService mailSenderService;

    @MockBean
    private PasswordEncoder passwordEncoder;

    @Test
    public void changeStatus() throws Exception {
        List<Good> goods = new ArrayList<>();
        User user = new User();
        user.setEmail("fhgdf@gmail.com");
        Good good = new Good();

        user.setGoodList(goods);
        user.getGoodList().add(good);

        userService.addUser(user);

        Order order = new Order(user);
        order.setId(10L);
        order.setFirstName("Пользователь");
        order.setGoodList(user.getGoodList());

        when(orderServiceImpl.save(order)).thenReturn(order);

        assertEquals(user, orderServiceImpl.save(order));
        assertNotNull(order.getTotalPrice());
        assertTrue(order.getStatus().equals("На рассмотрение"));

        orderServiceImpl.updateStatus(order,"Заказ в пути");

        assertTrue(order.getStatus().equals("Заказ в пути"));
    }

    @Test
    public void findOrderByUserTest() throws Exception {
        List<Good> goods = new ArrayList<>();
        User user = new User();
        Good good = new Good();

        user.setGoodList(goods);
        user.getGoodList().add(good);

        userService.addUser (user) ;

        Order order = new Order(user);
        order.setId(10L);
        order.setFirstName("Пользователь");
        order.setGoodList(user.getGoodList());

        List<Order> finded_order = orderServiceImpl.findOrderByUser(user);
        assertTrue(finded_order.get(0).equals(order));
        Order next_order = new Order(user);
        finded_order = orderServiceImpl.findOrderByUser(user);
        assertTrue(finded_order.get(0).equals(order));
        assertTrue(finded_order.get(1).equals(next_order));


    }
}
