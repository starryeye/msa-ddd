package dev.practice.gift.infrastructure.retrofit;

import dev.practice.gift.infrastructure.gift.order.RetrofitOrderApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RetrofitServiceRegistry {

    @Value("${example.order.base-url}")
    private String baseUrl;

    /**
     * Retrofit API 인터페이스 를 구현
     * - RetrofitUtils 에서 Retrofit 을 생성한다.
     * - 생성된 Retrofit 으로 인터페이스를 구현
     */
    @Bean
    public RetrofitOrderApi retrofitOrderApi() {

        //retrofit create
        var retrofit = RetrofitUtils.initRetrofit(baseUrl);

        //RetrofitOrderApi implementation Bean register
        return retrofit.create(RetrofitOrderApi.class);
    }
}
