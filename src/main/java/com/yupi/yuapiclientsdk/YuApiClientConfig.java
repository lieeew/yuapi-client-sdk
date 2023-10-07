package com.yupi.yuapiclientsdk;

import com.yupi.yuapiclientsdk.client.YupiApiClient;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 * @author leikooo
 * @create 2023-09-25 9:48
 * @Package com.yupi.yuapiclientsdk
 * @Description
 */
@Data
@Configuration
@ConfigurationProperties("yuapi.client")
@Component
public class YuApiClientConfig {

    private String accessKey;

    private String secretKey;

    private String interfaceName;

    @Bean
    public YupiApiClient yuApiClient() {
        return new YupiApiClient(secretKey, accessKey, interfaceName);
    }
}
