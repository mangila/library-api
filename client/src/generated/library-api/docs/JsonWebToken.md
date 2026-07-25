
# JsonWebToken


## Properties

Name | Type
------------ | -------------
`name` | string
`rawToken` | string
`issuer` | string
`audience` | Set&lt;string&gt;
`subject` | string
`tokenID` | string
`expirationTime` | number
`issuedAtTime` | number
`groups` | Set&lt;string&gt;
`claimNames` | Set&lt;string&gt;

## Example

```typescript
import type { JsonWebToken } from ''

// TODO: Update the object below with actual values
const example = {
  "name": null,
  "rawToken": null,
  "issuer": null,
  "audience": null,
  "subject": null,
  "tokenID": null,
  "expirationTime": null,
  "issuedAtTime": null,
  "groups": null,
  "claimNames": null,
} satisfies JsonWebToken

console.log(example)

// Convert the instance to a JSON string
const exampleJSON: string = JSON.stringify(example)
console.log(exampleJSON)

// Parse the JSON string back to an object
const exampleParsed = JSON.parse(exampleJSON) as JsonWebToken
console.log(exampleParsed)
```

[[Back to top]](#) [[Back to API list]](../README.md#api-endpoints) [[Back to Model list]](../README.md#models) [[Back to README]](../README.md)


