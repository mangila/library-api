
# AuthorRestDto


## Properties

Name | Type
------------ | -------------
`id` | string
`openLibraryKey` | string
`name` | string
`personalName` | string
`alternateNames` | Array&lt;string&gt;
`uris` | Array&lt;string&gt;
`bio` | string
`location` | string
`birthDate` | string
`deathDate` | string
`wikipedia` | string
`links` | Array&lt;string&gt;
`books` | Array&lt;string&gt;
`works` | Array&lt;string&gt;

## Example

```typescript
import type { AuthorRestDto } from ''

// TODO: Update the object below with actual values
const example = {
  "id": null,
  "openLibraryKey": null,
  "name": null,
  "personalName": null,
  "alternateNames": null,
  "uris": null,
  "bio": null,
  "location": null,
  "birthDate": null,
  "deathDate": null,
  "wikipedia": null,
  "links": null,
  "books": null,
  "works": null,
} satisfies AuthorRestDto

console.log(example)

// Convert the instance to a JSON string
const exampleJSON: string = JSON.stringify(example)
console.log(exampleJSON)

// Parse the JSON string back to an object
const exampleParsed = JSON.parse(exampleJSON) as AuthorRestDto
console.log(exampleParsed)
```

[[Back to top]](#) [[Back to API list]](../README.md#api-endpoints) [[Back to Model list]](../README.md#models) [[Back to README]](../README.md)


