# *Gift Certificates API* #

You can use these operations for gift certificates and tags

Certificates:
`http://localhost:8080/certificates`

Tags:
`http://localhost:8080/tags`

# Allowed operations #

### In all cases if there is any error with your request or response, it will return all types of exceptions with message  ###

### If you are creating or updating anything,you need to use JSON format for request body ###

## Certificate operations ##

### *Get all certificates* ###

GET `/certificates`

### *Get certificate by id* ###

GET `/certificates/{id}`

Example : `/certificates/1`

### *Get certificates by tag* ###

POST `/certificates/tag`

- name

```
{
    "name": "tag_name"
}
```

Example : `/certificates/tag`

```
{
    "name": "tag1"
}
```

### Get certificates by search filter ###

POST `/certificates/search`

These are required and accept String:

- searchValue
- searchType
- searchPlace

```
{
    "searchValue": "text",
    "searchType": "name or description",
    "searchPlace": "starts_with, contains or ends_with"
}
```

Example : `/certificates/search`

```
{
    "searchValue": "certificate",
    "searchType": "name",
    "searchPlace": "contains"
}
```

### Get certificates by sort filter ###

POST `/certificates/sort`

These are required and accept String:

- sortType
- sortOrder

```
{
    "sortType": "name or created_date or last_updated_date",
    "sortOrder": "asc or desc"
}
```

Example : `/certificates/sort`

```
{
    "sortType": "name",
    "sortOrder": "desc"
}
```

### Create certificate ###

POST `/certificates`

- name - String (*required*)
- description - String (*required*)
- price - Double (*required*)
- duration - Integer (*required*)
- tags - Array (*not required*)

```
{
    "name": "text",
    "description": "text",
    "price": "price",
    "duration": "number",
    "tags": []
}
```

Example : `/certificates`

```
{
    "name": "Certificate example",
    "description": "It is for certificate example",
    "price": "52.1",
    "duration": "120",
    "tags": []
}
```

### Edit certificate ###

PATCH `/certificates`

- id - Long (*required*)
- name - String (*not required*)
- description - String (*not required*)
- price - Double (*not required*)
- duration - Integer (*not required*)
- tags - Array (*not required*)

```
{
    "id" : "id",
    "name": "text",
    "description": "text",
    "price": "price (ex: 5.2)",
    "duration": "number (ex: 100)",
    "tags": [
        {
            "name": "text"
        }
    ]
}
```

### Delete certificate ###

DELETE `/certificates/{id}`

Example : `/certificates/1`

## Tag Operations ##

### Get all tags ###

GET `/tags`

### Get tag by id ###

GET `/tags/{id}`

Example : `/tags/1`

### Create tag ###

POST `/tags`

- name - String (required)

```
{
    "name": "text"
}
```

Example : `/tags`

```
{
    "name": "New Tag"
}
```

### Delete tag ###

DELETE `/tags/{id}`

Example : `/tags/1`

# Tables #

## Certificate tabel ##

| property       | data type                    |
|----------------|------------------------------|
| id             | Long                         |
| name           | String                       |
| description    | String                       |
| price          | Double                       |
| duration       | Integer                      |
| createDate     | String(format: ISO 8601 )    |
| lastUpdateDate | String(format: ISO 8601 )    |
| tags           | Array (may be null or empty) |

## Tag table ##

| property | data type |
|:---------|:----------|
| id       | Long      |
| name     | String    |