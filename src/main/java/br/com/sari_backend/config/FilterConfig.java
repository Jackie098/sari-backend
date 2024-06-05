package br.com.sari_backend.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import br.com.sari_backend.filter.JwtFilter;

@Configuration
public class FilterConfig {

  @Value("${jwt.secret}")
  private String secret;

  @Bean
  public FilterRegistrationBean<JwtFilter> jwtFilter() {
    FilterRegistrationBean<JwtFilter> filter = new FilterRegistrationBean<>();
    filter.setFilter(new JwtFilter(secret));
    filter.addUrlPatterns("/user/*");

    return filter;
  }
}
