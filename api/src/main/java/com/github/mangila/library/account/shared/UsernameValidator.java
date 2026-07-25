package com.github.mangila.library.account.shared;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

public class UsernameValidator implements ConstraintValidator<Username, CharSequence> {

  private int min;
  private int max;
  private Pattern pattern;

  @Override
  public void initialize(Username constraintAnnotation) {
    this.min = constraintAnnotation.min();
    this.max = constraintAnnotation.max();
    if (!constraintAnnotation.regexp().isEmpty()) {
      this.pattern = Pattern.compile(constraintAnnotation.regexp());
    }
  }

  @Override
  public boolean isValid(CharSequence value, ConstraintValidatorContext context) {
    if (value == null) {
      return false;
    }
    int length = value.length();
    if (length < min || length > max) {
      return false;
    }
    if (pattern != null && !pattern.matcher(value).matches()) {
      return false;
    }
    return true;
  }
}
