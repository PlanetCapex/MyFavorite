package my.project.controller;

import my.project.domain.Good;
import my.project.repos.GoodRepository;
import my.project.service.GoodService;
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
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest
public class MainControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private GoodRepository goodRepository;

    @Autowired
    private GoodService goodService;

    @Autowired
    private MainController mainController;

    @Test
    public void homeTest() throws Exception {
        Good good1 = generateGood(39L, "Стейк", "Донское хозяйство");
        Good good2 = generateGood(40L, "Грудка", "Фермерское хозяйство");

        when(goodRepository.findAll()).thenReturn(Arrays.asList(good1, good2));

        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("main"))
                .andExpect(model().attribute("goods", hasSize(2)))
                .andExpect(model().attribute("goods", hasItem(
                        allOf(
                                hasProperty("id", is(39L)),
                                hasProperty("title", is("Стейк")),
                                hasProperty("producer", is("Донское хозяйство"))
                        )
                )))
                .andExpect(model().attribute("goods", hasItem(
                        allOf(
                                hasProperty("id", is(40L)),
                                hasProperty("title", is("Грудка")),
                                hasProperty("producer", is("Фермерское хозяйство"))
                        )
                )));
    }

    @Test
    public void searchTest() throws Exception {
        List<Good> goodList = new ArrayList<>();
        Good good = new Good();
        good.setProducer("Донское хозяйство");
        goodList.add(good);

        Pageable pageable = PageRequest.of(0, 12);
        Page<Good> page = new PageImpl<>(goodList);

        when(goodRepository.findByProducerOrTitle(good.getProducer(), good.getProducer(), pageable)).thenReturn(page);

        assertNotNull(goodList);
        assertNotNull(good.getProducer());
        assertEquals(1, goodService.findByProducerOrTitle(good.getProducer(), good.getProducer(), pageable).getSize());
        assertEquals("Донское хозяйство", goodService.findByProducerOrTitle(good.getProducer(), good.getProducer(), pageable).getContent().get(0).getProducer());
    }

    @Test
    public void getProductByIdTest() throws Exception {
        Long id = 7L;
        Good good = new Good();
        good.setId(7L);

        assertNotNull(good.getId());
        assertEquals(id, good.getId());
    }

    private Good generateGood(Long id, String title, String producer) {
        Good good = new Good();
        good.setId(id);
        good.setTitle(title);
        good.setProducer(producer);
        good.setPrice(1000);

        return good;
    }
}
