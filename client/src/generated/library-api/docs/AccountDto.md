
# AccountDto


## Properties

Name | Type
------------ | -------------
`id` | string
`username` | string
`roles` | Array&lt;string&gt;
`active` | boolean
`createdAt` | Date
`updatedAt` | Date

## Example

```typescript
import type { AccountDto } from ''

// TODO: Update the object below with actual values
const example = {
  "id": null,
  "username": null,
  "roles": null,
  "active": null,
  "createdAt": null,
  "updatedAt": null,
} satisfies AccountDto

console.log(example)

// Convert the instance to a JSON string
const exampleJSON: string = JSON.stringify(example)
console.log(exampleJSON)

// Parse the JSON string back to an object
const exampleParsed = JSON.parse(exampleJSON) as AccountDto
console.log(exampleParsed)
```

[[Back to top]](#) [[Back to API list]](../README.md#api-endpoints) [[Back to Model list]](../README.md#models) [[Back to README]](../README.md)


