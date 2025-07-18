package ru.tgbot.audiobot.config.feign.feign;

import feign.Client;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.ssl.SSLContexts;
import org.apache.http.ssl.TrustStrategy;
import org.springframework.context.annotation.Bean;
import ru.tgbot.audiobot.service.feign.GigaChatAuthServiceImpl;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;

@Slf4j
@RequiredArgsConstructor
public class GigaChatFeignClientConfig {
    private final static String HEADER_VALUE_FIRST = "Bearer ";
    private final static String HEADER_KEY = "Authorization";

    private final GigaChatAuthServiceImpl service;


    @Bean
    public RequestInterceptor AuthRequestInterceptor() {
        return this::execute;
    }

    //TODO заменить на добавление сертификата от МинЦыфры в truststore. Учесть, что приложение будет собираться в docker image
    @Bean
    public Client feignClient() {
        return new Client.Default(getSSLSocketFactory(), getHostnameVerifier());
    }

    private void execute(RequestTemplate rt) {
        rt.header(HEADER_KEY, HEADER_VALUE_FIRST + service.getCurrentToken());
    }

    private SSLSocketFactory getSSLSocketFactory() {
        try {
            TrustStrategy acceptingTrustStrategy = (chain, authType) -> true;
            SSLContext sslContext = SSLContexts.custom()
                    .loadTrustMaterial(null, acceptingTrustStrategy)
                    .build();
            return sslContext.getSocketFactory();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private HostnameVerifier getHostnameVerifier() {
        return (hostname, session) -> true;
    }
}
