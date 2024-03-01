/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.westminstershoppingmanager;

/**
 *
 * @author sheas
 */

import java.io.IOException;

public interface ShoppingManager {// Interface for the shopping manager

    void addProduct(Product product);

    void removeProduct(String productID);

    void printProducts();

    void saveToFile() throws IOException;

    void loadFile() throws IOException;

    
    
}