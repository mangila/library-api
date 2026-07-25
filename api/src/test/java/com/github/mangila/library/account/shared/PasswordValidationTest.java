package com.github.mangila.library.account.shared;

import static org.assertj.core.api.Assertions.assertThat;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import java.util.Set;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

class PasswordValidationTest {

  private static Validator validator;

  @BeforeAll
  static void setUp() {
    try (ValidatorFactory factory = Validation.buildDefaultValidatorFactory()) {
      validator = factory.getValidator();
    }
  }

  @ParameterizedTest
  @NullAndEmptySource
  @ValueSource(strings = {"1234567", "short", "pwd"})
  void should_fail_validation_for_invalid_passwords(String password) {
    PasswordDto dto = new PasswordDto(password);
    Set<ConstraintViolation<PasswordDto>> violations = validator.validate(dto);
    assertThat(violations).hasSize(1);
    assertThat(violations.iterator().next().getMessage())
        .isEqualTo("password must be at least 8 characters long but not more than 128 characters");
  }

  @ParameterizedTest
  @ValueSource(strings = {"password123", "secret@pass!", "VerySecure#2026", "12345678"})
  void should_pass_validation_for_valid_passwords(String password) {
    PasswordDto dto = new PasswordDto(password);
    Set<ConstraintViolation<PasswordDto>> violations = validator.validate(dto);
    assertThat(violations).isEmpty();
  }

  private record PasswordDto(@Password String password) {}
}
