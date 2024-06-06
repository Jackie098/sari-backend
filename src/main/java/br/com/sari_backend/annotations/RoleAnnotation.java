package br.com.sari_backend.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import br.com.sari_backend.models.enums.RoleEnum;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface RoleAnnotation {
  // RoleEnum role() default RoleEnum.ALUNO;
  String role() default "ALUNO";
}
