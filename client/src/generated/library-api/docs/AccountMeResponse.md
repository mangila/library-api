
# AccountMeResponse


## Properties

Name | Type
------------ | -------------
`jwt` | [JsonWebToken](JsonWebToken.md)
`account` | [AccountDto](AccountDto.md)

## Example

```typescript
import type { AccountMeResponse } from ''

// TODO: Update the object below with actual values
const example = {
  "jwt": null,
  "account": null,
} satisfies AccountMeResponse

console.log(example)

// Convert the instance to a JSON string
const exampleJSON: string = JSON.stringify(example)
console.log(exampleJSON)

// Parse the JSON string back to an object
const exampleParsed = JSON.parse(exampleJSON) as AccountMeResponse
console.log(exampleParsed)
```

[[Back to top]](#) [[Back to API list]](../README.md#api-endpoints) [[Back to Model list]](../README.md#models) [[Back to README]](../README.md)


