## Starter for spring with token security setup

login endpoing: /api/login
test admin endpoint: /admin/me
test admin user: admin/admin

to obtain token:
```$xslt
curl -X POST \
  http://localhost:8080/api/login \
  -H 'cache-control: no-cache' \
  -H 'content-type: application/json' \
  -H 'postman-token: 8f2deb88-51ea-e41e-2a95-0a730331f6c5' \
  -d '{
  "username": "admin",
  "password": "admin"
}'
```

| Endpoint      | Authority     |
| ------------- | ------------- |
| /api/pub      |  anonymous    |
| /api/user/me  |  ROLE_USER    |
| /admin/me     |  ROLE_ADMIN   |


