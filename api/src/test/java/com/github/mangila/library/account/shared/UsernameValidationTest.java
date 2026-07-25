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

class UsernameValidationTest {

  private static Validator validator;

  @BeforeAll
  static void setUp() {
    try (ValidatorFactory factory = Validation.buildDefaultValidatorFactory()) {
      validator = factory.getValidator();
    }
  }

  @ParameterizedTest
  @NullAndEmptySource
  @ValueSource(strings = {"ab", "a", "user@invalid!", "invalid username", "user#1"})
  void should_fail_validation_for_invalid_usernames(String username) {
    UserDto dto = new UserDto(username);
    Set<ConstraintViolation<UserDto>> violations = validator.validate(dto);
    assertThat(violations).hasSize(1);
    assertThat(violations.iterator().next().getMessage())
        .isEqualTo(
            "username must be between 3 and 50 characters long and match the pattern"
                + " ^[a-zA-Z0-9_.-]+$");
  }

  @ParameterizedTest
  @ValueSource(strings = {"user1", "john_doe", "jane-doe", "user.name", "a123"})
  void should_pass_validation_for_valid_usernames(String username) {
    UserDto dto = new UserDto(username);
    Set<ConstraintViolation<UserDto>> violations = validator.validate(dto);
    assertThat(violations).isEmpty();
  }

  private record UserDto(@Username String username) {}
}
