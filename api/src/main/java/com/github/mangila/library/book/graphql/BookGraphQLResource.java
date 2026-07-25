package com.github.mangila.library.book.graphql;

import io.smallrye.common.annotation.RunOnVirtualThread;
import jakarta.inject.Inject;
import org.eclipse.microprofile.graphql.GraphQLApi;
import org.eclipse.microprofile.graphql.Query;
import org.hibernate.validator.constraints.UUID;

@GraphQLApi
public class BookGraphQLResource {

  private final BookGraphQLService bookGraphQLService;

  @Inject
  public BookGraphQLResource(BookGraphQLService bookGraphQLService) {
    this.bookGraphQLService = bookGraphQLService;
  }

  @Query
  @RunOnVirtualThread
  public BookGraphqlDto findBookById(@UUID String id) {
    return bookGraphQLService.findById(id);
  }
}
