package my.project.controller;

import my.project.domain.Good;
import my.project.domain.Role;
import my.project.domain.User;
import my.project.repos.GoodRepository;
import my.project.repos.UserRepository;
import my.project.service.GoodService;
import my.project.service.UserService;
import org.hamcrest.CoreMatchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest
public class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private GoodRepository goodRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private GoodService goodService;

    @Test
    public void getAllProductsTest() {
        List<Good> goodList = new ArrayList<>();
        goodList.add(new Good());

        Pageable pageable = PageRequest.of(0, 12);
        Page<Good> page = new PageImpl<>(goodList);

        when(goodRepository.findAll(pageable)).thenReturn(page);

        assertEquals(1, goodService.findAll(pageable).getSize());
    }

    @Test
    public void addProductTest() {
        Good good = new Good();
        good.setId(1L);
        good.setProducer("test");
        good.setTitle("test");

        when(goodRepository.save(good)).thenReturn(good);

        assertEquals(good, goodService.save(good));
        assertNotNull(good.getId());
    }

    @Test
    public void userListTest() {
        List<User> users = new ArrayList<>();
        users.add(new User());

        when(userRepository.findAll()).thenReturn(users);

        assertEquals(1, userService.findAll().size());
    }

    @Test
    public void userSaveTest() {
        User user = new User();
        user.setId(1L);
        user.setUsername("Oleg");
        user.setRoles(Collections.singleton(Role.ADMIN));

        when(userRepository.save(user)).thenReturn(user);

        assertEquals(user, userService.save(user));
        assertNotNull(user.getId());
        assertTrue(user.getUsername().equals("Oleg"));
        assertTrue(CoreMatchers.is(user.getRoles()).matches(Collections.singleton(Role.ADMIN)));
    }

    @Test
    public void updateProfileInfoTest() {
        User user = new User();
        user.setPassword("test");
        user.setEmail("test@test.com");

        when(userRepository.save(user)).thenReturn(user);

        assertEquals(user.getPassword(), "test");
        assertEquals(user.getEmail(), "test@test.com");
        assertEquals(user, userService.save(user));
    }
}