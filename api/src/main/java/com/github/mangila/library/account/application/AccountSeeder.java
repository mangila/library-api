package com.github.mangila.library.account.application;

import com.github.mangila.library.account.rest.model.SignupRequest;
import io.quarkus.logging.Log;
import io.quarkus.narayana.jta.QuarkusTransaction;
import io.quarkus.runtime.StartupEvent;
import jakarta.enterprise.context.Dependent;
import jakarta.enterprise.event.Observes;
import java.util.List;

@Dependent
public class AccountSeeder {

  private static void seedUser(
      String username,
      String password,
      List<String> roles,
      AccountFactory accountFactory,
      AccountService accountService) {
    final SignupRequest signupRequest = new SignupRequest(username, password);
    final Account account = accountFactory.from(signupRequest, roles);
    accountService.persist(account);
    Log.infof("User: %s seeded", username);
  }

  public void seed(
      @Observes StartupEvent event, AccountFactory accountFactory, AccountService accountService) {
    QuarkusTransaction.requiringNew()
        .run(
            () -> {
              seedUser("user12345", "user12345", List.of("USER"), accountFactory, accountService);
              seedUser(
                  "admin12345", "admin12345", List.of("ADMIN"), accountFactory, accountService);
            });
  }
}
