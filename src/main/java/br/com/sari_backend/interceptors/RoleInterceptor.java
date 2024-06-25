package br.com.sari_backend.interceptors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import br.com.sari_backend.annotations.RoleAnnotation;
import br.com.sari_backend.config.exceptions.DeniedPermissionException;
import br.com.sari_backend.models.enums.RoleEnum;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class RoleInterceptor implements HandlerInterceptor {

  @Value("${package.controllers.path}")
  private String packageControllersPath;

  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
    var userRole = request.getAttribute("role");

    if (handler instanceof HandlerMethod) {
      HandlerMethod handlerMethod = (HandlerMethod) handler;
      boolean isPathToControllerPackage = handlerMethod.getBeanType().getPackage().getName()
          .startsWith(packageControllersPath);

      if (isPathToControllerPackage) {
        RoleAnnotation roleRequest = handlerMethod.getMethodAnnotation(RoleAnnotation.class);

        if (roleRequest == null) {
          return true;
        }

        boolean hasPermission = false;
        for (RoleEnum role : roleRequest.roles()) {
          if (role.toString().equals(userRole)) {
            hasPermission = true;
            break;
          }
        }

        if (!hasPermission) {
          throw new DeniedPermissionException("User doesn't have permission to access this endpoint!");
        }
      }

    }

    return true;
  }
}
