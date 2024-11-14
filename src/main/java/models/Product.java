package models;

public abstract class Product {
    private final String productId;
    private double value;
    private final String exchange;

    public Product(String productId, String exchange) {
        this.productId = productId;
        this.exchange = exchange;
    }

    public double getValue() {
        return value;
    }


    public void setValue(double value) {
        this.value = value;
    }

    public String getProductId() {
        return productId;
    }

    public String getExchange() {
        return exchange;
    }

}
