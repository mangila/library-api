# AuthorRestResourceClient

All URIs are relative to *http://localhost*

| Method | HTTP request | Description |
|------------- | ------------- | -------------|
| [**apiV1AuthorsIdGet**](AuthorRestResourceClient.md#apiv1authorsidget) | **GET** /api/v1/authors/{id} | Find By Id |



## apiV1AuthorsIdGet

> AuthorRestDto apiV1AuthorsIdGet(id)

Find By Id

### Example

```ts
import {
  Configuration,
  AuthorRestResourceClient,
} from '';
import type { ApiV1AuthorsIdGetRequest } from '';

async function example() {
  console.log("🚀 Testing  SDK...");
  const config = new Configuration({ 
    // Configure HTTP bearer authorization: SecurityScheme
    accessToken: "YOUR BEARER TOKEN",
  });
  const api = new AuthorRestResourceClient(config);

  const body = {
    // string
    id: id_example,
  } satisfies ApiV1AuthorsIdGetRequest;

  try {
    const data = await api.apiV1AuthorsIdGet(body);
    console.log(data);
  } catch (error) {
    console.error(error);
  }
}

// Run the test
example().catch(console.error);
```

### Parameters


| Name | Type | Description  | Notes |
|------------- | ------------- | ------------- | -------------|
| **id** | `string` |  | [Defaults to `undefined`] |

### Return type

[**AuthorRestDto**](AuthorRestDto.md)

### Authorization

[SecurityScheme](../README.md#SecurityScheme)

### HTTP request headers

- **Content-Type**: Not defined
- **Accept**: `application/json`


### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | OK |  -  |
| **401** | Not Authorized |  -  |
| **403** | Not Allowed |  -  |

[[Back to top]](#) [[Back to API list]](../README.md#api-endpoints) [[Back to Model list]](../README.md#models) [[Back to README]](../README.md)

