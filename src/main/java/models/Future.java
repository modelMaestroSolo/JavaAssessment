package models;

public class Future extends Product{
    private final String ContractCode;
    private final int contractMonth;
    private final int contractYear;

    public Future(String productId, double value, String exchange, String contractCode, int contractMonth, int contractYear) {
        super(productId, exchange);
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
}
