package com.dash_lat_net.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import java.util.List; // Import necessário

@Setter
@Getter
@Component
@ConfigurationProperties(prefix = "ping")
public class PingProperties {

    private long interval;
    private long maxRegistrosDia;
    private List<String> hosts;

}