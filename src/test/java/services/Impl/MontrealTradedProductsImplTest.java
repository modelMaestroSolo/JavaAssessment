package services.Impl;

import exceptions.ProductAlreadyRegisteredException;
import models.Product;
import models.Stock;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import services.ProductPricingService;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MontrealTradedProductsImplTest {

    @Mock
    ProductPricingService productPricingService;

    @Mock
    Map<Product, Integer> registeredProducts;

    @InjectMocks
    MontrealTradedProductsImpl montrealTradedProductsTest;

    @Test
    void addNewProduct_whenProductAlreadyRegistered_throwsProductAlreadyRegisteredException() {
        Product product = new Stock("1", "ex1", "APPL");
        when(registeredProducts.containsKey(product)).thenReturn(true);
        assertThatThrownBy(() -> montrealTradedProductsTest.addNewProduct(product))
                .isInstanceOf(ProductAlreadyRegisteredException.class)
                .hasMessage(String.format("Product with id %s is already registered!", product.getProductId()));
    }

    @Test
    void addNewProduct_whenProductNotRegistered_addsNewProductToSystem() {
        Product product = new Stock("1", "ex1", "APPL");
        montrealTradedProductsTest.addNewProduct(product);
        Stock stockProduct = (Stock) product;
        verify(productPricingService).price(stockProduct.getExchange(), stockProduct.getTicker());
        verify(registeredProducts).put(product, 0);
    }

    @Test
    void trade_booksQuantityAgainstProductTraded() {
        Product product = new Stock("1", "ex1", "APPL");
        when(registeredProducts.get(product)).thenReturn(0);
        montrealTradedProductsTest.trade(product, 10);
        verify(registeredProducts).put(product, 10);
        verify(registeredProducts).get(product);
    }

//    @Test
//    void totalTradeQuantityForDay() {
//    }
//
//    @Test
//    void totalValueOfDaysTradedProducts() {
//    }
}