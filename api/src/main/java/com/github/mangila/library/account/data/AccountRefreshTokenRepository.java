package com.github.mangila.library.account.data;

import com.github.mangila.library.account.data.model.AccountRefreshTokenEntity;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;
import java.util.UUID;

@ApplicationScoped
public class AccountRefreshTokenRepository
    implements PanacheRepositoryBase<AccountRefreshTokenEntity, UUID> {}
