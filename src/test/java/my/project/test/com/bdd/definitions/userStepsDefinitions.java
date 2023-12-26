package com.bdd.definitions;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.spring.CucumberContextConfiguration;
import io.restassured.RestAssured;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import my.project.ServingWebContentApplication;
import my.project.domain.Good;
import my.project.domain.Order;
import my.project.domain.User;
import my.project.service.UserService;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static io.restassured.RestAssured.given;
//ВСТАВЛЯТЬ ЭТИ ДВЕ АННОТАЦИИ ТОЛЬКО ОДИН РАЗ В ОДНОМ ФАЙЛЕ ИНАЧЕ БУДЕТ ОЧКО
@CucumberContextConfiguration
@SpringBootTest(classes = ServingWebContentApplication.class)
public class userStepsDefinitions {

    private static User user;
    private List<Good> goods;
    private Order order;

    public userStepsDefinitions() {
        this.goods = new ArrayList<>();
    }

    @Given("Авторизоваться пользователем с обычными правами")
    public void authUser() {
        user = new User();
    }

    @When("Добавить первый товар в корзину")
    public void addFirstGoodToCart() {
        user.setGoodList(goods);
        Good good = new Good();
        good.setTitle("Тестовое мясо 1");
        good.setVolume("1");
        good.setPrice(555);
        user.getGoodList().add(good);
        //goods.add(good);    //сюда добавляем, чтобы потом сравнить с тем, что вернет user.getGoodsList()
    }

    @And("Добавить второй товар в корзину")
    public void addSecondGoodToCart() {
        Good good = new Good();
        good.setTitle("Тестовое мясо 2");
        good.setVolume("2");
        good.setPrice(666);
        user.getGoodList().add(good);
        //goods.add(good);    //сюда добавляем, чтобы потом сравнить с тем, что вернет user.getGoodsList()
    }

    @And("Удалить товар из корзины")
    public void removeGoodFromCart() {
        user.getGoodList().remove(user.getGoodList().size() - 1);
        //goods.remove(goods.size() - 1);
    }

    @And("Оформить новый заказ")
    public void createNewOrder() {
        this.order = new Order();
        order.setUser(user);
        order.setGoodList(user.getGoodList());
        order.setStatus("CREATED");

        int price = 0;
        List<Good> goodsArr = user.getGoodList();
        for (Good g : goodsArr) {
            price += g.getPrice();
        }

        order.setTotalPrice((double) price);

        goods = user.getGoodList();
    }

    @And("Добавить товар к существующему заказу")
    public void addItemToOrder() {
        Good good = new Good();
        good.setTitle("Тестовое мясо 2");
        good.setVolume("2");
        good.setPrice(666);
        this.order.getGoodList().add(good);
    }

    @And("Удалить товар из существующего заказа")
    public void removeItemFromOrder() {
        Good good = new Good();
        good.setTitle("Тестовое мясо 2");
        good.setVolume("2");
        good.setPrice(666);
        this.order.getGoodList().remove(good);
    }

    @Then("Проверить кол-во товаров в корзине {string}")
    public void checkCartSize(String expectedSize) {
        int actualSize = user.getGoodList().size();
        assert actualSize == new Integer(expectedSize);
    }

    @Then("Проверить стоимость товаров в корзине {string}")
    public void checkCartPrice(String expectedPrice) {
        int actualPrice = 0;
        List<Good> goodsArr = user.getGoodList();
        for (Good g : goodsArr) {
            actualPrice += g.getPrice();
        }

        assert actualPrice == new Integer(expectedPrice);
    }

    @Then("Проверить список товаров в заказе")
    public void checkOrderItems() {
        List<Good> actualItems = order.getGoodList();
        assert actualItems == goods;
    }

    @Then("Проверить стоимость заказа")
    public void checkOrderPrice() {
        int actualPrice = 0;
        List<Good> goodsArr = order.getGoodList();
        for (Good g : goodsArr) {
            actualPrice += g.getPrice();
        }

        int expectedPrice = 0;
        goodsArr = user.getGoodList();
        for (Good g : goodsArr) {
            expectedPrice += g.getPrice();
        }

        assert actualPrice == expectedPrice;
    }

    @Then("Проверить кол-во товаров в заказе {string}")
    public void checkItemsAmountInOrder(String num) {
        assert Objects.equals(order.getGoodList().size(), new Integer((num)));
    }

    @Then("Проверить стоимость товаров в заказе {string}")
    public void checkItemsPriceInOrder(String expectedPrice) {
        int actualPrice = 0;
        List<Good> goodsArr = order.getGoodList();
        for (Good g : goodsArr) {
            actualPrice += g.getPrice();
        }

        assert Objects.equals(actualPrice, new Integer(expectedPrice));
    }

    @Then("Проверить статус созданного заказа")
    public void checkOrderStatus() {
        assert Objects.equals(order.getStatus(), "CREATED");
    }
}
