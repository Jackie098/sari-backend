package br.com.sari_backend.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import br.com.sari_backend.interceptors.AuthInterceptor;
import br.com.sari_backend.interceptors.RoleInterceptor;

@Configuration
// @EnableWebMvc
public class RequestInterceptor implements WebMvcConfigurer {

  @Autowired
  private AuthInterceptor authInterceptor;
  @Autowired
  private RoleInterceptor roleInterceptor;

  @Override
  public void addInterceptors(InterceptorRegistry registry) {
    registry.addInterceptor(authInterceptor).excludePathPatterns("/auth", "/api-docs", "/swagger-ui");
    registry.addInterceptor(roleInterceptor).excludePathPatterns("/auth", "/api-docs", "/swagger-ui");
  }
}
