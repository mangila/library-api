package com.github.mangila.library.author.rest;

import io.smallrye.common.annotation.RunOnVirtualThread;
import jakarta.inject.Inject;
import org.eclipse.microprofile.graphql.GraphQLApi;
import org.eclipse.microprofile.graphql.Query;
import org.hibernate.validator.constraints.UUID;

@GraphQLApi
public class AuthorGraphQLResource {

  private final AuthorWebService authorWebService;

  @Inject
  public AuthorGraphQLResource(AuthorWebService authorWebService) {
    this.authorWebService = authorWebService;
  }

  @Query
  @RunOnVirtualThread
  public AuthorWebDto findAuthorById(@UUID String id) {
    return authorWebService.findById(id);
  }
}
