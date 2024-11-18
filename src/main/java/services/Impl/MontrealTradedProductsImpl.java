package services.Impl;

import exceptions.ProductAlreadyRegisteredException;
import models.Product;
import services.MontrealTradedProducts;


import java.util.HashMap;
import java.util.Map;


public class MontrealTradedProductsImpl implements MontrealTradedProducts {

    private final static Map<Product, Integer> registeredProducts = new HashMap<>();

    @Override
    public void addNewProduct(Product product) throws ProductAlreadyRegisteredException {
        if(registeredProducts.containsKey(product)) {
            throw new ProductAlreadyRegisteredException(String.format("Product with id %s is already registered!", product.getProductId()));
        }
        registeredProducts.put(product, 0);
    }

    @Override
    public void trade(Product product, int quantity) {
        if (registeredProducts.containsKey(product)) {
            registeredProducts.put(product, registeredProducts.get(product) + quantity);
        }
    }

    @Override
    public int totalTradeQuantityForDay() {
        return  registeredProducts.values().stream()
                .reduce(0, Integer::sum);
    }

    @Override
    public double totalValueOfDaysTradedProducts() {
        return  registeredProducts.entrySet().stream()
                .mapToDouble(entry -> entry.getKey().getValue() * entry.getValue())
                .sum();
    }

    public static Map<Product, Integer> getRegisteredProducts(){
        return registeredProducts;
    }



}
