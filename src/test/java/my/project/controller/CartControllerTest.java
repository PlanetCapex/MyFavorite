package my.project.controller;

import my.project.domain.Good;
import my.project.domain.User;
import my.project.repos.UserRepository;
import my.project.service.Impl.MailSenderService;
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
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest
public class CartControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CartController cartController;

    @Autowired
    private UserService userService;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private MailSenderService mailSenderService;

    @MockBean
    private PasswordEncoder passwordEncoder;

    @Test
    public void getCartTest() throws Exception {
        List<Good> goods = new ArrayList<>();
        goods.add(new Good());
        goods.add(new Good());

        User user = new User();
        user.setGoodList(goods);

        assertNotNull(user.getGoodList());
        assertEquals(2, user.getGoodList().size());
    }

    @Test
    public void addToCartTest() throws Exception {
        List<Good> goods = new ArrayList<>();
        User user = new User();

        user.setGoodList(goods);
        user.getGoodList().add(new Good());

        userService.addUser(user);

        assertNotNull(user.getGoodList());
        assertEquals(1, user.getGoodList().size());

        Mockito.verify(userRepository, Mockito.times(1)).save(user);
    }

    @Test
    public void removeFromCartTest() throws Exception {
        List<Good> goods = new ArrayList<>();
        User user = new User();
        Good good = new Good();

        user.setGoodList(goods);
        user.getGoodList().add(good);

        userService.addUser(user);

        user.getGoodList().remove(good);

        userService.addUser(user);

        assertNotNull(user.getGoodList());
        assertEquals(0, user.getGoodList().size());

        Mockito.verify(userRepository, Mockito.times(2)).save(user);
    }
}
