package com.github.mangila.library.book.data;

import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;
import java.util.UUID;

@ApplicationScoped
public class BookRepository implements PanacheRepositoryBase<BookEntity, UUID> {}
