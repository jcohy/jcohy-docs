package rsb.http.filters;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.Set;

import static org.springframework.web.reactive.function.server.RouterFunctions.route;


/**
 * 描述: .
 * <p>
 * Copyright © 2022 <a href="https://www.jcohy.com" target= "_blank">https://www.jcohy.com</a>
 * </p>
 *
 * @author jiac
 * @version 2022.04.0 2023/5/18:15:23
 * @since 2022.04.0
 */
@Configuration
public class ErrorHandlingRouteConfiguration {

    @Bean
    RouterFunction<ServerResponse> errors() {
        var productIdPathVariable = "productId";
        return route()
                .GET("/products/{" + productIdPathVariable + "}",request -> {
                    var productId = request.pathVariable(productIdPathVariable);
                    if(!Set.of("1","2").contains(productId)) {
                        return ServerResponse.ok().syncBody(new Product(productId));
                    } else {
                        return Mono.error(new ProductNoFoundException(productId));
                    }
                })
                .filter((request, next) -> next.handle(request) // <1>
                        .onErrorResume(ProductNoFoundException.class,pnfe -> ServerResponse.notFound().build())) // <2>
                .build();
    }
}

record Product(String id) {}

class ProductNoFoundException extends RuntimeException {
    private final String productId;

    public ProductNoFoundException(String productId) {
        this.productId = productId;
    }

    public String getProductId() {
        return productId;
    }
}
