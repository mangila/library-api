package com.github.mangila.library.account.shared;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Constraint(validatedBy = UsernameValidator.class)
@Target({
  ElementType.METHOD,
  ElementType.FIELD,
  ElementType.ANNOTATION_TYPE,
  ElementType.CONSTRUCTOR,
  ElementType.PARAMETER,
  ElementType.TYPE_USE,
  ElementType.RECORD_COMPONENT
})
@Retention(RetentionPolicy.RUNTIME)
public @interface Username {

  Class<?>[] groups() default {};

  int max() default 50;

  String message() default
      "username must be between 3 and 50 characters long and match the pattern ^[a-zA-Z0-9_.-]+$";

  int min() default 3;

  Class<? extends Payload>[] payload() default {};

  String regexp() default "^[a-zA-Z0-9_.-]+$";
}
