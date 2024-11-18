package models;

import services.ProductPricingService;

public class Stock extends Product{
    private final ProductPricingService productPricingService;
    private final String ticker;


    public Stock(String productId, String exchange, String ticker,  ProductPricingService productPricingService) {
        super(productId, exchange);
        this.productPricingService = productPricingService;
        this.ticker =ticker;
    }

    public String getTicker() {
        return ticker;
    }

    @Override
    public double getValue() {
        return productPricingService.price(getExchange(), getTicker());
    }
}
