package models;

import services.ProductPricingService;

public class Future extends Product{

    private final ProductPricingService productPricingService;
    private final String ContractCode;
    private final int contractMonth;
    private final int contractYear;

    public Future(String productId,  String exchange, String contractCode, int contractMonth, int contractYear, ProductPricingService productPricingService) {
        super(productId, exchange);
        this.productPricingService = productPricingService;
        this.ContractCode = contractCode;
        this.contractMonth = contractMonth;
        this.contractYear = contractYear;
    }

    public String getContractCode() {
        return ContractCode;
    }

    public int getContractMonth() {
        return contractMonth;
    }

    public int getContractYear() {
        return contractYear;
    }

    @Override
    public double getValue() {
        return productPricingService.price(getExchange(), getContractCode(), getContractMonth(), getContractYear());
    }
}
