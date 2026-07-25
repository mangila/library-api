package com.github.mangila.library.author.data;

import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;
import java.util.UUID;

@ApplicationScoped
public class AuthorRepository implements PanacheRepositoryBase<AuthorEntity, UUID> {}
