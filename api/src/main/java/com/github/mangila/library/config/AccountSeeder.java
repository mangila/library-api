package com.github.mangila.library.config;

import com.github.mangila.library.account.application.Account;
import com.github.mangila.library.account.application.AccountFactory;
import com.github.mangila.library.account.application.AccountService;
import com.github.mangila.library.account.rest.model.SignupRequest;
import io.quarkus.logging.Log;
import io.quarkus.narayana.jta.QuarkusTransaction;
import io.quarkus.runtime.StartupEvent;
import jakarta.enterprise.context.Dependent;
import jakarta.enterprise.event.Observes;
import java.util.List;

@Dependent
public class AccountSeeder {

  public void seed(
      @Observes StartupEvent event,
      AdminConfig adminConfig,
      AccountFactory accountFactory,
      AccountService accountService) {
    QuarkusTransaction.requiringNew()
        .run(
            () -> {
              final boolean adminSeed = adminConfig.seed();
              Log.infof("Admin seed: %s", adminSeed);
              if (adminSeed) {
                final String username = adminConfig.username();
                final String password = adminConfig.password();
                final List<String> roles = List.of("ADMIN");
                final SignupRequest signupRequest = new SignupRequest(username, password);
                final Account account = accountFactory.from(signupRequest, roles);
                accountService.persist(account);
                Log.infof("User: %s seeded", username);
              }
            });
  }
}
