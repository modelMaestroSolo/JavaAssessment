package models;

public abstract class Product {
    private final String productId;
    private final String exchange;

    public Product(String productId, String exchange) {
        this.productId = productId;
        this.exchange = exchange;
    }

    public abstract double getValue();


    public String getProductId() {
        return productId;
    }

    public String getExchange() {
        return exchange;
    }

}
