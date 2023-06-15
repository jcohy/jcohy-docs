package rsb.http.routes;

import org.springframework.web.reactive.function.server.RequestPredicate;
import org.springframework.web.reactive.function.server.ServerRequest;

/**
 * 描述: .
 * <p>
 * Copyright © 2022 <a href="https://www.jcohy.com" target= "_blank">https://www.jcohy.com</a>
 * </p>
 *
 * @author jiac
 * @version 2022.04.0 2023/5/18:12:17
 * @since 2022.04.0
 */
public class CaseInsensitiveRequestPredicate implements RequestPredicate {

    private final RequestPredicate target;

    public static RequestPredicate i(RequestPredicate rp) {
        return new CaseInsensitiveRequestPredicate(rp);
    }

    CaseInsensitiveRequestPredicate(RequestPredicate target) {
        this.target = target;
    }
    @Override
    public boolean test(ServerRequest request) {
        return this.target.test(new LowercaseUriServerRequestWrapper(request));
    }

    @Override
    public String toString() {
        return this.target.toString();
    }
}
