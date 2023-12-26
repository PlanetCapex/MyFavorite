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
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest
public class MenuControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private GoodRepository goodRepository;

    @Autowired
    private GoodService goodService;

    @Autowired
    private MenuController menuController;

    @Test
    public void mainMenuTest() throws Exception {
        List<Good> goods = new ArrayList<>();
        Good good = new Good();
        goods.add(good);

        Pageable pageable = PageRequest.of(0, 12);
        Page<Good> page = new PageImpl<>(goods);

        when(goodRepository.findAll(pageable)).thenReturn(page);

        assertNotNull(goods);
        assertEquals(1, goodService.findAll(pageable).getSize());
    }

    @Test
    public void findByProducerTest() throws Exception {
        List<Good> goods = new ArrayList<>();
        Good good = new Good();
        good.setProducer("Донской завод");
        goods.add(good);

        Pageable pageable = PageRequest.of(0, 12);
        Page<Good> page = new PageImpl<>(goods);

        when(goodRepository.findByProducer(good.getProducer(), pageable)).thenReturn(page);

        assertNotNull(goods);
        assertEquals(1, goodService.findByProducer(good.getProducer(), pageable).getSize());
        assertEquals("Донской завод", goodService.findByProducer(good.getProducer(), pageable)
                .getContent().get(0).getProducer());
    }

    @Test
    public void findByBreedTest() throws Exception {
        List<Good> goods = new ArrayList<>();
        Good good = new Good();
        good.setBreed("ангуский");
        goods.add(good);

        Pageable pageable = PageRequest.of(0, 12);
        Page<Good> page = new PageImpl<>(goods);

        when(goodRepository.findByBreed(good.getBreed(), pageable)).thenReturn(page);

        assertNotNull(goods);
        assertEquals(1, goodService.findByBreed(good.getBreed(), pageable).getSize());
        assertEquals("ангуский", goodService.findByBreed(good.getBreed(), pageable)
                .getContent().get(0).getBreed());
    }

    @Test
    public void searchByParametersTest() throws Exception {
        List<Good> goods = new ArrayList<>();
        Good good = new Good();
        good.setBreed("ангуский");
        good.setProducer("Донской завод");
        good.setPrice(1000);
        goods.add(good);

        Pageable pageable = PageRequest.of(0, 12);
        Page<Good> page = new PageImpl<>(goods);

        when(goodRepository.findByPriceBetween(900, 1100, pageable)).thenReturn(page);
        when(goodRepository.findByProducerIn(Collections.singletonList(goods.get(0).getProducer()), pageable)).thenReturn(page);
        when(goodRepository.findByProducerInAndBreedIn(
                Collections.singletonList(goods.get(0).getProducer()),
                Collections.singletonList(goods.get(0).getBreed()),
                pageable)).thenReturn(page);

        assertNotNull(goods);
        assertEquals(1000, goodService.findByPriceBetween(900, 1100, pageable)
                .getContent().get(0).getPrice());
        assertEquals("Донской завод", goodService.findByProducerIn(Collections.singletonList(goods.get(0).getProducer()), pageable)
                .getContent().get(0).getProducer());
        assertEquals(1, goodService.findByProducerInAndBreedIn(
                Collections.singletonList(goods.get(0).getProducer()),
                Collections.singletonList(goods.get(0).getBreed()),
                pageable).getSize());
    }
}