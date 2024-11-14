package models;

public class Stock extends Product{
    private final String ticker;


    public Stock(String productId, String exchange, String ticker) {
        super(productId, exchange);
        this.ticker =ticker;
    }

    public String getTicker() {
        return ticker;
    }
}
