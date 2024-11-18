package services.Impl;

import exceptions.ProductAlreadyRegisteredException;
import models.Future;
import models.Product;
import models.Stock;
import org.assertj.core.data.Percentage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import services.ProductPricingService;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class MontrealTradedProductsImplTest {

    @Mock
    ProductPricingService productPricingService;

    @InjectMocks
    MontrealTradedProductsImpl montrealTradedProductsTest;

    Map<Product, Integer> registeredProducts;

    @BeforeEach
    void setUp(){
        MontrealTradedProductsImpl.getRegisteredProducts().clear();
        registeredProducts = MontrealTradedProductsImpl.getRegisteredProducts();
    }

    @Test
    void addNewProduct_whenProductAlreadyRegistered_throwsProductAlreadyRegisteredException() {
        Product product = new Stock("1", "ex1", "APPL", productPricingService);
        registeredProducts.put(product, 1);
        assertThatThrownBy(() -> montrealTradedProductsTest.addNewProduct(product))
                .isInstanceOf(ProductAlreadyRegisteredException.class)
                .hasMessage(String.format("Product with id %s is already registered!", product.getProductId()));
    }

    @Test
    void addNewProduct_whenProductNotRegistered_addsNewProductToSystem() {
        Product product = new Stock("1", "ex1", "APPL", productPricingService);
        montrealTradedProductsTest.addNewProduct(product);
        assertThat(registeredProducts).hasSize(1).containsEntry(product, 0);
    }

    @Test
    void trade_booksQuantityAgainstProductTraded() {
        Product product = new Stock("1", "ex1", "APPL", productPricingService);
        registeredProducts.put(product, 0);
        montrealTradedProductsTest.trade(product, 10);
        assertThat(registeredProducts).hasSize(1).containsValue(10);
    }

    @Test
    void totalTradeQuantityForDay() {
        Product product = new Stock("1", "ex1", "APPL", productPricingService);
        Product product1 = new Future("2", "ex2", "A2BD21E6", 8, 2024, productPricingService);

        registeredProducts.put(product, 12);
        registeredProducts.put(product1, 8);

        assertThat(montrealTradedProductsTest.totalTradeQuantityForDay()).isEqualTo(20);
    }

    @Test
    void totalValueOfDaysTradedProducts() {
        Product product = new Stock("1", "ex1", "APPL", productPricingService);
        Product product1 = new Future("2", "ex2", "A2BD21E6", 8, 2024, productPricingService);
        registeredProducts.put(product, 10);
        registeredProducts.put(product1, 15);

        when(productPricingService.price("ex1", "APPL")).thenReturn(5.0);
        when(productPricingService.price("ex2", "A2BD21E6", 8, 2024)).thenReturn(10.0);

        assertThat(montrealTradedProductsTest.totalValueOfDaysTradedProducts())
                .isCloseTo(200.0, Percentage.withPercentage(0.01));
    }
}