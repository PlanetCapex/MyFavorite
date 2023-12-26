package my.project.controller;

import my.project.domain.Good;
import my.project.domain.Order;
import my.project.domain.User;
import my.project.repos.OrderRepository;
import my.project.repos.UserRepository;
import my.project.service.Impl.MailSenderService;
import my.project.service.OrderService;
import my.project.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest
public class OrderControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserService userService;

    @Autowired
    private OrderService orderService;

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
    public void getOrderTest() throws Exception {
        List<Good> goods = new ArrayList<>();
        User user = new User();
        Good good = new Good();

        user.setGoodList(goods);
        user.getGoodList().add(good);

        assertNotNull(user.getGoodList());
        assertEquals(1, user.getGoodList().size());
    }

    @Test
    public void postOrderTest() throws Exception {
        List<Good> goods = new ArrayList<>();
        User user = new User();
        Good good = new Good();

        user.setGoodList(goods);
        user.getGoodList().add(good);

        userService.save(user);

        Order order = new Order(user);
        order.setId(1L);
        order.setFirstName("John");
        order.setGoodList(user.getGoodList());

        orderService.save(order);

        assertNotNull(user);
        assertNotNull(user.getGoodList());
        assertEquals(1, user.getGoodList().size());
        assertNotNull(order);
        assertEquals(1L, order.getId());
        assertEquals("John", order.getFirstName());
        assertEquals(1, order.getGoodList().size());

        Mockito.verify(userRepository, Mockito.times(1)).save(user);
        Mockito.verify(orderRepository, Mockito.times(1)).save(order);
    }

    @Test
    public void finalizeOrderTest() throws Exception {
        List<Good> goods = new ArrayList<>();
        User user = new User();
        Good good = new Good();
        Order order = new Order(user);

        user.setGoodList(goods);
        user.getGoodList().add(good);
        order.setGoodList(user.getGoodList());

        when(orderService.findAll()).thenReturn(Collections.singletonList(order));

        assertNotNull(user);
        assertNotNull(user.getGoodList());
        assertEquals(1, user.getGoodList().size());
        assertNotNull(order);
        assertEquals(1, order.getGoodList().size());
    }
}
