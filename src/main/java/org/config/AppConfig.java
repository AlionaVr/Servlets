package org.config;


import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableMBeanExport;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@ComponentScan(basePackages = "org")
@EnableAsync
@EnableMBeanExport(defaultDomain = "myApp")
@EnableCaching
public class AppConfig implements WebMvcConfigurer {
    @Bean
    public CacheManager cacheManager() {
        return new ConcurrentMapCacheManager("default");
    }
}
