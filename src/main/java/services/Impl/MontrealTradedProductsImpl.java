package services.Impl;

import exceptions.ProductAlreadyRegisteredException;
import models.Future;
import models.Product;
import models.Stock;
import services.MontrealTradedProducts;
import services.ProductPricingService;

import java.util.HashMap;
import java.util.Map;


public class MontrealTradedProductsImpl implements MontrealTradedProducts {

    Map<Product, Integer> registeredProducts = new HashMap<>();
    ProductPricingService productPricingService;

    public MontrealTradedProductsImpl(ProductPricingService productPricingService) {
        this.productPricingService = productPricingService;
    }

    @Override
    public void addNewProduct(Product product) throws ProductAlreadyRegisteredException {
        if(registeredProducts.containsKey(product)) {
            throw new ProductAlreadyRegisteredException(String.format("Product with id %s is already registered!", product.getProductId()));
        }

        if(product instanceof Stock stockProduct) {
            product.setValue(productPricingService.price(
                    product.getExchange(),
                    stockProduct.getTicker()));
        }else if(product instanceof Future futureProduct){
            product.setValue(productPricingService.price(
                    product.getExchange(),
                    futureProduct.getContractCode(),
                    futureProduct.getContractMonth(),
                    futureProduct.getContractYear()));
        }

        registeredProducts.put(product, 0);

    }

    @Override
    public void trade(Product product, int quantity) {
            registeredProducts.put(product, registeredProducts.get(product) + quantity );
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
}
