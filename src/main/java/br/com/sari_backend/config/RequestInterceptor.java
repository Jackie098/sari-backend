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

  private static final String[] AUTH_WHITELIST = {
      "/auth",
      "/api-docs/**",
      "/swagger-ui/**",
      "/swagger-config/**"
  };

  @Autowired
  private AuthInterceptor authInterceptor;
  @Autowired
  private RoleInterceptor roleInterceptor;

  @Override
  public void addInterceptors(InterceptorRegistry registry) {
    registry.addInterceptor(authInterceptor).excludePathPatterns(AUTH_WHITELIST);
    registry.addInterceptor(roleInterceptor).excludePathPatterns(AUTH_WHITELIST);
  }
}
