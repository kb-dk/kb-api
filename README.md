# KB API

REST API for internal & external use.

# Purpose

Primary entry point for access to digital material at the Royal Danish Library.

## Updating the API

It is easiest to use the [online Swagger editor](https://editor.swagger.io/) with the Swagger YAML from
`src/main/swagger/kb-api.yml`. It supports contextual parameter suggestions & code completion with `CTRL-space`
and warns on syntactical errors.

## How to build by Maven

Project uses Swagger Codegen with Maven plug-in for OpenAPI 3.0.2 <br>
```
<dependency>
    <groupId>org.openapitools</groupId>
    <artifactId>openapi-generator-maven-plugin</artifactId>
    <version>${openapi-generator-version}</version>
</dependency>
```
More information can be found here: <br>
https://openapi-generator.tech/docs/plugins <br>
https://github.com/OpenAPITools/openapi-generator/blob/master/modules/openapi-generator-maven-plugin/README.md <br>

To build, run the Maven command from the project directory: <br>
```mvn package```

## Deployment

The project can be deployed to Tomcat server (local installation required).<br>
It has been made clear that the project can be deployed to Jetty using Maven. But the challenge with multiple modules project has not been solved yet.<br>

kb-api.xml file must be corrected to point to logback and properties files where they are located on the server. 
For local tomcat installation, you can deploy kb-api.xml via symlink to tomcat/conf/Catalina/localhost <br>

The project is configured to run Jetty locally as well (local installation is not required). <br>
It is important to use jetty: run-war when starting jetty server. It wraps the application in a war file first and deploys to Jetty server afterwards. Jetty is watching the pom.xml file and re-deploying the application as soon as there are changes.


## Project structure

Project contains two modules: kb-api-swagger and kb-api-ws. <br>
Module kb-api-swagger generates server code and APIs specifikation via openapi plugin. <br>
Module kb-api-ws contains implementation of the interface and responsible for code presentation via Swagger UI.<br>
yml file used to generate a code must have the same name as project. The file is located src / main / swagger in the kb-api-swagger module.<br>

## Development

###Jetty
http://localhost:8080/kb-api-ws/swagger-ui/index.html <br>
http://localhost:8080/kb-api-ws/swagger-ui/openapi.json<br>
http://localhost:8080/kb-api-ws/api/hello<br>

###Tomcat
http://localhost:8080/kb-api/swagger-ui/index.html <br>
http://localhost:8080/kb-api/swagger-ui/openapi.json<br>
http://localhost:8080/kb-api/api/hello<br>



