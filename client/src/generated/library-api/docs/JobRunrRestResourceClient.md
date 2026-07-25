# JobRunrRestResourceClient

All URIs are relative to *http://localhost*

| Method | HTTP request | Description |
|------------- | ------------- | -------------|
| [**apiV1JobrunrDownloadTypeGet**](JobRunrRestResourceClient.md#apiv1jobrunrdownloadtypeget) | **GET** /api/v1/jobrunr/download/{type} | Schedule File Download |
| [**apiV1JobrunrImportTypeGet**](JobRunrRestResourceClient.md#apiv1jobrunrimporttypeget) | **GET** /api/v1/jobrunr/import/{type} | Schedule File Import |
| [**apiV1JobrunrProcessTypeGet**](JobRunrRestResourceClient.md#apiv1jobrunrprocesstypeget) | **GET** /api/v1/jobrunr/process/{type} | Schedule Staging Processing |



## apiV1JobrunrDownloadTypeGet

> JobScheduledDto apiV1JobrunrDownloadTypeGet(type)

Schedule File Download

### Example

```ts
import {
  Configuration,
  JobRunrRestResourceClient,
} from '';
import type { ApiV1JobrunrDownloadTypeGetRequest } from '';

async function example() {
  console.log("🚀 Testing  SDK...");
  const config = new Configuration({ 
    // Configure HTTP bearer authorization: SecurityScheme
    accessToken: "YOUR BEARER TOKEN",
  });
  const api = new JobRunrRestResourceClient(config);

  const body = {
    // OpenLibraryType
    type: ...,
  } satisfies ApiV1JobrunrDownloadTypeGetRequest;

  try {
    const data = await api.apiV1JobrunrDownloadTypeGet(body);
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
| **type** | `OpenLibraryType` |  | [Defaults to `undefined`] [Enum: AUTHOR, WORK, EDITION] |

### Return type

[**JobScheduledDto**](JobScheduledDto.md)

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


## apiV1JobrunrImportTypeGet

> JobScheduledDto apiV1JobrunrImportTypeGet(type)

Schedule File Import

### Example

```ts
import {
  Configuration,
  JobRunrRestResourceClient,
} from '';
import type { ApiV1JobrunrImportTypeGetRequest } from '';

async function example() {
  console.log("🚀 Testing  SDK...");
  const config = new Configuration({ 
    // Configure HTTP bearer authorization: SecurityScheme
    accessToken: "YOUR BEARER TOKEN",
  });
  const api = new JobRunrRestResourceClient(config);

  const body = {
    // OpenLibraryType
    type: ...,
  } satisfies ApiV1JobrunrImportTypeGetRequest;

  try {
    const data = await api.apiV1JobrunrImportTypeGet(body);
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
| **type** | `OpenLibraryType` |  | [Defaults to `undefined`] [Enum: AUTHOR, WORK, EDITION] |

### Return type

[**JobScheduledDto**](JobScheduledDto.md)

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


## apiV1JobrunrProcessTypeGet

> JobScheduledDto apiV1JobrunrProcessTypeGet(type, limit)

Schedule Staging Processing

### Example

```ts
import {
  Configuration,
  JobRunrRestResourceClient,
} from '';
import type { ApiV1JobrunrProcessTypeGetRequest } from '';

async function example() {
  console.log("🚀 Testing  SDK...");
  const config = new Configuration({ 
    // Configure HTTP bearer authorization: SecurityScheme
    accessToken: "YOUR BEARER TOKEN",
  });
  const api = new JobRunrRestResourceClient(config);

  const body = {
    // OpenLibraryType
    type: ...,
    // number (optional)
    limit: 56,
  } satisfies ApiV1JobrunrProcessTypeGetRequest;

  try {
    const data = await api.apiV1JobrunrProcessTypeGet(body);
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
| **type** | `OpenLibraryType` |  | [Defaults to `undefined`] [Enum: AUTHOR, WORK, EDITION] |
| **limit** | `number` |  | [Optional] [Defaults to `undefined`] |

### Return type

[**JobScheduledDto**](JobScheduledDto.md)

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

