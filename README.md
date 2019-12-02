# KB API

REST API for internal & external use.

# Purpose

Primary entry point for access to digital material at the Royal Danish Library.


## How to build

Adapted from
https://github.com/swagger-api/swagger-codegen/wiki/Server-stub-generator-HOWTO#java-jax-rs-apache-cxf-2--3
(execute from the `kb-api`-folder)

Build Swagger codegen:
```
pushd .. 
git clone https://github.com/swagger-api/swagger-codegen
cd swagger-codegen
git checkout 3.0.0
mvn package
popd
```

Use Swagger codegen:
```
java -jar ../swagger-codegen/modules/swagger-codegen-cli/target/swagger-codegen-cli.jar generate \
  -i src/main/swagger/kb-api.yml \
  -l jaxrs-cxf \
  -o swagger_gen

```

## Relevant links

https://github.com/swagger-api/swagger-samples/tree/samples-3.0/java/java-jersey2
