package br.com.sari_backend.config;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

// TODO: Add customized annotation - study generics and reflections
@Component
public class RoleInterceptor implements HandlerInterceptor {

  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
    var methodType = request.getMethod();
    var userRole = request.getAttribute("role");

    System.out.println("RoleInterceptor - preHandle - userRole -> " + userRole);

    return true;
  }

}
