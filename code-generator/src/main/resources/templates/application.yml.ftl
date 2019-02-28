spring:
  application:
    name: code-generator-output
  main:
    allow-bean-definition-overriding: true

mini:
  data:
    jdbc:
      connections:
        default:
          url: ${url}
          username: ${username}
          password: ${password}