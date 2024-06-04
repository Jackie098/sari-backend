package br.com.sari_backend.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import br.com.sari_backend.filter.JwtFilter;

@Configuration
public class FilterConfig {
  @Bean
  public FilterRegistrationBean jwtFilter() {
    FilterRegistrationBean filter = new FilterRegistrationBean();

    filter.setFilter(new JwtFilter());
    filter.addUrlPatterns("/user/*");

    return filter;
  }
}
