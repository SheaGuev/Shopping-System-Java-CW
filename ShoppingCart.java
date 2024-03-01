/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.westminstershoppingmanager;

/**
 *
 * @author sheas
 */

import java.util.ArrayList;
import java.util.List;

public class ShoppingCart {
    private List<Product> products;

    public ShoppingCart() {
        this.products = new ArrayList<>();
    }

    public void addProduct(Product product) {
        // add product to the cart
        products.add(product);
    }

    public void removeProduct(Product product) {
        // remove product from the cart
        products.remove(product);
    }

    public double calculateTotalCost() {
        // logic to calculate the total cost of all products in the cart
        double totalCost = 0;
        for (Product product : products) {
            totalCost += product.getPrice();
        }
        return totalCost;
    }

    
}

