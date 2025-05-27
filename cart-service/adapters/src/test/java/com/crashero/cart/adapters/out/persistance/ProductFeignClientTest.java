package com.crashero.cart.adapters.out.persistance;//package com.crashero.cart.adapters.out.persistance;
//
//import com.crashero.model.Product;
//import com.crashero.model.ProductType;
//import com.github.tomakehurst.wiremock.WireMockServer;
//import org.junit.jupiter.api.AfterEach;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
//import org.springframework.test.context.ActiveProfiles;
//
//import static com.github.tomakehurst.wiremock.client.WireMock.*;
//import static org.assertj.core.api.Assertions.assertThat;
//
//@AutoConfigureWireMock(port = 8888)
//@SpringBootTest
//@ActiveProfiles("test")
//public class ProductFeignClientTest {
//    @Autowired
//    private WireMockServer wireMockServer;
//
//    @Autowired
//    private ProductFeignClient productFeignClient;
//
//    @BeforeEach
//    public void setUp() {
//        wireMockServer.start();
//    }
//
//    @AfterEach
//    public void tearDown() {
//        wireMockServer.stop();
//    }
//
//    @Test
//    void shouldFetchProductById() {
//        stubFor(get(urlEqualTo("/products/1"))
//                .willReturn(aResponse()
//                        .withHeader("Content-Type", "application/json")
//                        .withBody("""
//                            {
//                                "id": 1,
//                                "productName": "Test Product",
//                                "price": 99.99,
//                                "type": "ELECTRONICS"
//                            }
//                            """)));
//
//        Product product = productFeignClient.findById(1L);
//
//        assertThat(product).isNotNull();
//        assertThat(product.getId()).isEqualTo(1L);
//        assertThat(product.getProductName()).isEqualTo("Test Product");
//        assertThat(product.getPrice()).isEqualTo(99.99);
//        assertThat(product.getType()).isEqualTo(ProductType.ELECTRONICS);
//    }
//
//}
