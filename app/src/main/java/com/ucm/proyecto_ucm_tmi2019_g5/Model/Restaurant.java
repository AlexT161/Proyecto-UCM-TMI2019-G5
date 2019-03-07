package com.ucm.proyecto_ucm_tmi2019_g5.Model;

import java.util.List;

public class Restaurant {

    private String name;
    private List<String> menu;

    public Restaurant(String name, List<String> menu) {

        this.name = name;
        this.menu = menu;

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getMenu() {
        return menu;
    }

    public void setMenu(List<String> menu) {
        this.menu = menu;
    }

    @Override
    public String toString() {
        return "Restaurant{" +
                "name='" + name + '\'' +
                ", menu=" + menu +
                '}';
    }
}
