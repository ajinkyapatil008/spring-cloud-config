// package com.example.config_client.config;

// import org.springframework.boot.context.properties.ConfigurationProperties;
// import org.springframework.cloud.context.config.annotation.RefreshScope;
// import org.springframework.stereotype.Component;

// @RefreshScope
// @ConfigurationProperties(prefix = "example")
// @Component
// public class ExampleProperties {
//     private String message;
//     public String getMessage() { return message; }
//     public void setMessage(String message) { this.message = message; }
// }

package com.example.config_client.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

@RefreshScope
@Component
@ConfigurationProperties(prefix = "example")
public class ExampleProperties {
    private String message;
    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
}
