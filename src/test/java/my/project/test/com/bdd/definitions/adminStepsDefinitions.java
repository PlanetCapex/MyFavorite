package com.bdd.definitions;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.spring.CucumberContextConfiguration;
import my.project.ServingWebContentApplication;
import my.project.domain.Good;
import my.project.domain.Order;
import my.project.domain.Role;
import my.project.domain.User;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.*;

public class adminStepsDefinitions {
    private static User user;
    private Good good;
    private Order order;
    private Good targetGood;
    private Order targetOrder;
    private List<Good> menu;
    private List<Order> orders;

    public adminStepsDefinitions() {
        this.menu = new ArrayList<Good>();
        this.orders = new ArrayList<Order>();
    }

    @Given("Авторизоваться пользователем с правами администратора")
    public void adminAuth() {
        Set<Role> adminRoleSet = new HashSet<Role>();
        adminRoleSet.add(Role.ADMIN);
        user = new User();
        user.setRoles(adminRoleSet);
    }

    @When("Сформировать новый товар с тестовыми данными")
    public void createNewGood() {
        good = new Good();
        good.setTitle("Тестовая грудка 1");
        good.setPrice(333);
        good.setVolume("4");
    }

    @And("Добавить товар в ассортимент")
    public void addGoodToMenu() {
        menu.add(good);
    }

    @And("Найти созданный товар в ассортименте")
    public void findCreatedGoodInMenu() {
        targetGood = menu.get(menu.indexOf(good));
    }

    @And("Создать новый заказ для теста")
    public void createNewTestOrder() {
        Set<Role> userRoleSet = new HashSet<Role>();
        userRoleSet.add(Role.USER);
        User testUser = new User();
        testUser.setRoles(userRoleSet);

        Good good = new Good();
        good.setTitle("Тестовое мясо 1");
        good.setVolume("1");
        good.setPrice(555);
        testUser.setGoodList(new ArrayList<Good>());
        testUser.getGoodList().add(good);

        order = new Order();
        order.setUser(testUser);
        order.setGoodList(testUser.getGoodList());
        order.setStatus("CREATED");

        int price = 0;
        List<Good> goodsArr = order.getGoodList();
        for (Good g : goodsArr) {
            price += g.getPrice();
        }
        order.setTotalPrice((double) price);
        this.orders.add(order);

    }

    @And("Найти созданный заказ")
    public void findCreatedOrder() {
        this.targetOrder = orders.get(orders.indexOf(this.order));
    }

    @Then("Проверить статус созданного заказа под админом")
    public void checkOrderStatus() {
        assert Objects.equals(order.getStatus(), "CREATED");
    }

    @And("Изменить статус заказа на {string}")
    public void setStatus(String targetStatus) {
        this.targetOrder.setStatus(targetStatus);
    }

    @Then("Проверить изменение статуса заказа {string}")
    public void checkStatus(String expectedStatus) {
        assert Objects.equals(this.targetOrder.getStatus(), expectedStatus);
    }

    @Then("Проверить название найденного товара {string}")
    public void checkGoodName(String name) {
        assert Objects.equals(targetGood.getTitle(), name);
    }

    @Then("Проверить стоимость найденного товара {string}")
    public void checkGoodPrice(String price) {
        assert Objects.equals(targetGood.getPrice(), new Integer(price));
    }

}
