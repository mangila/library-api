# AccountRestResourceClient

All URIs are relative to *http://localhost*

| Method | HTTP request | Description |
|------------- | ------------- | -------------|
| [**apiV1AccountsLoginPost**](AccountRestResourceClient.md#apiv1accountsloginpost) | **POST** /api/v1/accounts/login | Login |
| [**apiV1AccountsMeGet**](AccountRestResourceClient.md#apiv1accountsmeget) | **GET** /api/v1/accounts/me | Me |
| [**apiV1AccountsRefreshPost**](AccountRestResourceClient.md#apiv1accountsrefreshpost) | **POST** /api/v1/accounts/refresh | Refresh |
| [**apiV1AccountsSignupPost**](AccountRestResourceClient.md#apiv1accountssignuppost) | **POST** /api/v1/accounts/signup | Signup |



## apiV1AccountsLoginPost

> TokenResponse apiV1AccountsLoginPost(loginRequest, userAgent)

Login

### Example

```ts
import {
  Configuration,
  AccountRestResourceClient,
} from '';
import type { ApiV1AccountsLoginPostRequest } from '';

async function example() {
  console.log("🚀 Testing  SDK...");
  const api = new AccountRestResourceClient();

  const body = {
    // LoginRequest
    loginRequest: ...,
    // string (optional)
    userAgent: userAgent_example,
  } satisfies ApiV1AccountsLoginPostRequest;

  try {
    const data = await api.apiV1AccountsLoginPost(body);
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
| **loginRequest** | [LoginRequest](LoginRequest.md) |  | |
| **userAgent** | `string` |  | [Optional] [Defaults to `undefined`] |

### Return type

[**TokenResponse**](TokenResponse.md)

### Authorization

No authorization required

### HTTP request headers

- **Content-Type**: `application/json`
- **Accept**: `application/json`


### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | OK |  -  |
| **400** | Bad Request |  -  |

[[Back to top]](#) [[Back to API list]](../README.md#api-endpoints) [[Back to Model list]](../README.md#models) [[Back to README]](../README.md)


## apiV1AccountsMeGet

> AccountMeResponse apiV1AccountsMeGet()

Me

### Example

```ts
import {
  Configuration,
  AccountRestResourceClient,
} from '';
import type { ApiV1AccountsMeGetRequest } from '';

async function example() {
  console.log("🚀 Testing  SDK...");
  const config = new Configuration({ 
    // Configure HTTP bearer authorization: SecurityScheme
    accessToken: "YOUR BEARER TOKEN",
  });
  const api = new AccountRestResourceClient(config);

  try {
    const data = await api.apiV1AccountsMeGet();
    console.log(data);
  } catch (error) {
    console.error(error);
  }
}

// Run the test
example().catch(console.error);
```

### Parameters

This endpoint does not need any parameter.

### Return type

[**AccountMeResponse**](AccountMeResponse.md)

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


## apiV1AccountsRefreshPost

> TokenResponse apiV1AccountsRefreshPost(userAgent, refreshToken)

Refresh

### Example

```ts
import {
  Configuration,
  AccountRestResourceClient,
} from '';
import type { ApiV1AccountsRefreshPostRequest } from '';

async function example() {
  console.log("🚀 Testing  SDK...");
  const api = new AccountRestResourceClient();

  const body = {
    // string (optional)
    userAgent: userAgent_example,
    // string (optional)
    refreshToken: refreshToken_example,
  } satisfies ApiV1AccountsRefreshPostRequest;

  try {
    const data = await api.apiV1AccountsRefreshPost(body);
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
| **userAgent** | `string` |  | [Optional] [Defaults to `undefined`] |
| **refreshToken** | `string` |  | [Optional] [Defaults to `undefined`] |

### Return type

[**TokenResponse**](TokenResponse.md)

### Authorization

No authorization required

### HTTP request headers

- **Content-Type**: Not defined
- **Accept**: `application/json`


### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | OK |  -  |

[[Back to top]](#) [[Back to API list]](../README.md#api-endpoints) [[Back to Model list]](../README.md#models) [[Back to README]](../README.md)


## apiV1AccountsSignupPost

> SignUpResponse apiV1AccountsSignupPost(signupRequest)

Signup

### Example

```ts
import {
  Configuration,
  AccountRestResourceClient,
} from '';
import type { ApiV1AccountsSignupPostRequest } from '';

async function example() {
  console.log("🚀 Testing  SDK...");
  const api = new AccountRestResourceClient();

  const body = {
    // SignupRequest
    signupRequest: ...,
  } satisfies ApiV1AccountsSignupPostRequest;

  try {
    const data = await api.apiV1AccountsSignupPost(body);
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
| **signupRequest** | [SignupRequest](SignupRequest.md) |  | |

### Return type

[**SignUpResponse**](SignUpResponse.md)

### Authorization

No authorization required

### HTTP request headers

- **Content-Type**: `application/json`
- **Accept**: `application/json`


### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | OK |  -  |
| **400** | Bad Request |  -  |

[[Back to top]](#) [[Back to API list]](../README.md#api-endpoints) [[Back to Model list]](../README.md#models) [[Back to README]](../README.md)

