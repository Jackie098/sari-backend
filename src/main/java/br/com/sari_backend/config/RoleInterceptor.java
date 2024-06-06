package br.com.sari_backend.config;

import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import br.com.sari_backend.annotations.RoleAnnotation;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class RoleInterceptor implements HandlerInterceptor {

  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
    var userRole = request.getAttribute("role");

    // System.out.println("RoleInterceptor - preHandle - userRole -> " + userRole);
    // System.out.println("RoleInterceptor - preHandle - userRole - type -> " +
    // userRole.getClass());

    if (handler instanceof HandlerMethod) {
      HandlerMethod method = (HandlerMethod) handler;
      RoleAnnotation roleRequest = method.getMethodAnnotation(RoleAnnotation.class);

      if (roleRequest == null) {
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        response.getWriter().write("User doesn't have permission to access this endpoint!");
        return false;
      }
      // System.out.println("RoleInterceptor - preHandle - roleRequest -> " +
      // roleRequest.role());
      // System.out.println("RoleInterceptor - preHandle - roleRequest - type -> " +
      // roleRequest.role().getClass());
      // System.out.println("userRole == roleRequest.role() - " + userRole ==
      // roleRequest.role());

      if (!(userRole.equals(roleRequest.role()))) {
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        response.getWriter().write("User doesn't have permission to access this endpoint!");
        return false;
      }
    }

    return true;
  }
}
