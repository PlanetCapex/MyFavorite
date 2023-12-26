package com.bdd.definitions;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import my.project.domain.Good;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class filtersStepsDefinitions {
    private final List<Good> menu;
    private Good targetGood;
    private String expectedName;
    private String expectedBreed;
    public filtersStepsDefinitions() {
        this.menu = new ArrayList<Good>();
    }

    @When("Добавить тестовые товары")
    public void addTestGoods() {
        Good g1 = new Good();
        g1.setTitle("Тестовая говядина");
        g1.setBreed("Мраморная говядина");
        this.menu.add(g1);

        Good g2 = new Good();
        g2.setTitle("Тестовая грудка");
        g2.setBreed("Московский бройлер");
        this.menu.add(g2);
    }

    @And("Найти товар с названием {string}")
    public void getGoodByName(String name) {
        this.targetGood = new Good();
        this.expectedName = name;
        for (Good g : this.menu) {
            if (Objects.equals(g.getTitle(), name)) {
                this.targetGood = g;
            }
        }
    }

    @And("Найти товары с породой {string}")
    public void getGoodsByBreed(String breed) {
        this.targetGood = new Good();
        this.expectedBreed = breed;
        for (Good g : this.menu) {
            if (Objects.equals(g.getBreed(), breed)) {
                this.targetGood = g;
            }
        }
    }

    @Then("Проверить название полученного товара")
    public void checkGoodName() {
        assert Objects.equals(this.targetGood.getTitle(), this.expectedName);
    }

    @Then("Проверить породу полученных товаров")
    public void checkGoodBreed() {
        assert Objects.equals(this.targetGood.getBreed(), this.expectedBreed);
    }

    @Then("Проверить отсутствие товара")
    public void checkGoodNotFound() {
        assert this.targetGood.getTitle() == null;
        assert this.targetGood.getBreed() == null;
    }
}
