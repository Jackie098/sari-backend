package br.com.sari_backend.interceptors;

import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import br.com.sari_backend.annotations.RoleAnnotation;
import br.com.sari_backend.models.enums.RoleEnum;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class RoleInterceptor implements HandlerInterceptor {

  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
    var userRole = request.getAttribute("role");

    if (handler instanceof HandlerMethod) {
      HandlerMethod method = (HandlerMethod) handler;
      RoleAnnotation roleRequest = method.getMethodAnnotation(RoleAnnotation.class);

      if (roleRequest == null) {
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        response.getWriter().write("User doesn't have permission to access this endpoint!");
        return false;
      }

      boolean hasPermission = false;
      for (RoleEnum role : roleRequest.roles()) {
        if (role.toString().equals(userRole)) {
          hasPermission = true;
          break;
        }
      }

      if (!hasPermission) {
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        response.getWriter().write("User doesn't have permission to access this endpoint!");
        return false;
      }
    }

    return true;
  }
}
