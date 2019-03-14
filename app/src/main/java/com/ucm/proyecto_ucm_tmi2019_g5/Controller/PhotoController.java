package com.ucm.proyecto_ucm_tmi2019_g5.Controller;

import com.ucm.proyecto_ucm_tmi2019_g5.Model.Restaurant;

import java.util.List;

public class PhotoController {

    public Restaurant restaurant;
    public List<String> menu;

    public PhotoController(Restaurant restaurant, List<String> menu) {
        this.restaurant = restaurant;
        this.menu = menu;
    }

    public Restaurant getRestaurantName(){
        //TODO metodo para usar Google vision API
        return restaurant;
    }

    public List<String> getRestaurantMenu(){
        //TODO metodo para llamar metodo para el scraping
        return menu;
    }

}
