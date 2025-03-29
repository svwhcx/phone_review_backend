package com.svwh.phonereview.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.method.HandlerTypePredicate;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @description 跨域配置
 * @Author cxk
 * @Date 2022/4/30 16:24
 */
@Configuration
@ConfigurationProperties
public class CorsConfig implements WebMvcConfigurer {

    @Value("${application.url.prefix}")
    private String urlPrefix;

    @Value("${application.file.path}")
    private String filePath;

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOriginPatterns("*")
                .allowedMethods("GET", "HEAD", "POST", "PUT", "DELETE", "OPTIONS")
                .allowCredentials(true)
                .maxAge(3600)
                .allowedHeaders("*");
    }

    @Override
    public void configurePathMatch(PathMatchConfigurer configurer) {
        // 添加一个prefix前缀
        configurer.addPathPrefix("/api", HandlerTypePredicate.forAnnotation(RestController.class));
    }



    /**
     * 配置资源映射<p>
     * 通过将http请求映射到资源目录<p>
     * 访问:http: //localhost:8083/images/xxx.jpg<p>
     * 会被映射到: file:{filePath}/xxx.jpg
     *
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {

        registry.addResourceHandler("/images/"+"/**")
                .addResourceLocations("file:"+filePath);
    }

}
