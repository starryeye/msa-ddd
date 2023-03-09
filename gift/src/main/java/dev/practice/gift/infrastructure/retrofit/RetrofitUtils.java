package dev.practice.gift.infrastructure.retrofit;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dev.practice.gift.common.response.CommonResponse;
import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import org.springframework.stereotype.Component;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.io.IOException;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Slf4j
@Component
public class RetrofitUtils {

    private static final HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor()
            .setLevel(HttpLoggingInterceptor.Level.BODY);

    /**
     * retrofit 생성을 위해 OKHttpClient 를 생성
     * retrofit 은 내부적으로 OKHttpClient 를 사용한다.
     */
    private static final OkHttpClient.Builder httpClient = new OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .connectTimeout(3, TimeUnit.SECONDS)
            .readTimeout(10, TimeUnit.SECONDS);

    /**
     * retrofit 생성을 위해 Gson 을 생성
     */
    private static final Gson gson = new GsonBuilder()
            .setLenient()
            .create();

    /**
     * retrofit 생성
     */
    public static Retrofit initRetrofit(String baseUrl) {
        return new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(httpClient.build())
                .build();
    }

    /**
     * call.execute() : 실제 요청 수행
     *
     * order server 의 body 타입은 CommonResponse 이며.. Response<T> 의 T 는 body 타입이므로
     * T 는 CommonResponse 타입이다.
     */
    public <T extends CommonResponse<?>> Optional<T> responseSync(Call<T> call) {
        try {
            Response<T> execute = call.execute();
            if(execute.isSuccessful()) {
                return Optional.ofNullable(execute.body());
            } else {
                log.error("requestSync errorBody={}", execute.errorBody());
                throw new RuntimeException("retrofit execute response error");
            }
        }catch (IOException e) {
            log.error("", e);
            throw new RuntimeException("retrofit execute IOException");
        }
    }

    public void responseVoid(Call<Void> call) {
        try {
            if(!call.execute().isSuccessful())
                throw new RuntimeException("retrofit execute response error");
        }catch (IOException e) {
            throw new RuntimeException("retrofit execute IOException");
        }
    }
}
