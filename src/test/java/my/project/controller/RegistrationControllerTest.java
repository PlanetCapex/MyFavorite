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
public class RegistrationControllerTest {
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
    public void registerUser() throws Exception {
        User user = new User();
        user.setId(100L);
        user.setPassword("password");
        user.setUsername("User");
        user.setRoles(Collections.singleton(Role.USER));

        when(userRepository.save(user)).thenReturn(user);

        assertEquals(user, userService.save(user));
        User u_user = userRepository.findByUsername("User");
        assertNotNull(u_user.getId());
        assertNotNull(u_user.getActivationCode());
        assertTrue(u_user.getUsername().equals("USER"));
        assertTrue(CoreMatchers.is(u_user.getRoles()).matches(Collections.singleton(Role.USER)));
    }

    @Test
    public void activeUser() {
        User user = new User();
        user.setId(100L);
        user.setPassword("password");
        user.setUsername("User");
        user.setRoles(Collections.singleton(Role.USER));
        userService.sendMessage(user);
        userService.activateUser(user.getActivationCode());

        when(userRepository.save(user)).thenReturn(user);

        assertEquals(user, userService.save(user));
        assertNotNull(user.getId());
        assertTrue(user.isActive());
        assertTrue(user.getUsername().equals("USER"));
        assertTrue(CoreMatchers.is(user.getRoles()).matches(Collections.singleton(Role.USER)));
    }


}
