# KB API

REST API for internal & external use.

# Purpose

Primary entry point for access to digital material at the Royal Danish Library.

## Updating the API

It is easiest to use the [online Swagger editor](https://editor.swagger.io/) with the Swagger YAML from
`src/main/swagger/kb-api.yml`. It supports contextual parameter suggestions & code completion with `CTRL-space`
and warns on syntactical errors.

## How to build

Adapted from
https://github.com/swagger-api/swagger-codegen/wiki/Server-stub-generator-HOWTO#java-jax-rs-apache-cxf-2--3
(execute from the `kb-api`-folder)

 1: Build Swagger codegen:  
```
pushd .. 
git clone https://github.com/swagger-api/swagger-codegen
cd swagger-codegen
git checkout 3.0.0
mvn package
popd
```

2: Use Swagger codegen:
   
   
```
java -jar ../swagger-codegen/modules/swagger-codegen-cli/target/swagger-codegen-cli.jar generate \
  --api-package dk.kb.api \
  --model-package dk.kb.api.model \
  -i src/main/swagger/kb-api.yml \
  -l jaxrs-cxf
```

This will *not* overwrite the implementation classes! Only the `gen`-folder will be updated.

## Relevant links for future investigation

No command line, all maven:
https://github.com/swagger-api/swagger-samples/tree/samples-3.0/java/java-jersey2
