package com.github.mangila.library.author.graphql;

import io.smallrye.common.annotation.RunOnVirtualThread;
import jakarta.inject.Inject;
import org.eclipse.microprofile.graphql.GraphQLApi;
import org.eclipse.microprofile.graphql.Query;
import org.hibernate.validator.constraints.UUID;

@GraphQLApi
public class AuthorGraphqlResource {

  private final AuthorGraphqlService authorGraphQlService;

  @Inject
  public AuthorGraphqlResource(AuthorGraphqlService authorGraphQlService) {
    this.authorGraphQlService = authorGraphQlService;
  }

  @Query
  @RunOnVirtualThread
  public AuthorGraphqlDto findAuthorById(@UUID String id) {
    return authorGraphQlService.findById(id);
  }
}
