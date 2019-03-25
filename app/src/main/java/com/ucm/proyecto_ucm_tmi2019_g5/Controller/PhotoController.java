package com.ucm.proyecto_ucm_tmi2019_g5.Controller;

import android.support.v4.view.AsyncLayoutInflater;

import com.ucm.proyecto_ucm_tmi2019_g5.Model.Restaurant;
import com.ucm.proyecto_ucm_tmi2019_g5.Util.Scraping;

public class PhotoController {

    public Restaurant restaurant;
    public static String menu = null;

    public PhotoController() {
    }

    public PhotoController(Restaurant restaurant, String menu) {
        this.restaurant = restaurant;
        this.menu = menu;
    }

    public Restaurant getRestaurantName(){
        //TODO metodo para usar Google vision API
        return restaurant;
    }

    public String getRestaurantMenu(String restaurante) {
        //TODO metodo para llamar metodo para el scraping

        System.out.println("restaurante in controller " + restaurante);
        /*Thread thread = new Thread(new Runnable() {

            @Override
            public void run() {

            }
        });
        thread.start();*/

        try  {
            //menu = Scraping.doScraping(restaurante);
            menu = new Scraping().execute(restaurante).get();
            System.out.println("menu in controller " + menu);
            Thread.sleep(1000);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return menu;
    }

}