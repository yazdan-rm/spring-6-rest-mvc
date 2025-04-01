package guru.springframework.spring6restmvc.constant;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class RestConstant {

    public static final String BEER_URL = "/api/v1/beer";
    public static final String CUSTOMER_URL = "/api/v1/customer";
}
