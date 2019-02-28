spring:
  application:
    name: code-generator-output
  main:
    allow-bean-definition-overriding: true

smsf:
  data:
    jdbc:
      connections:
        default:
          url: ${url}
          username: ${username}
          password: ${password}