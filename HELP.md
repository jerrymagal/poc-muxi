# Getting Started

### Reference Documentation
For further reference, please consider the following sections:

* [Official Apache Maven documentation](https://maven.apache.org/guides/index.html)
* [Spring Boot Maven Plugin Reference Guide](https://docs.spring.io/spring-boot/docs/2.5.3/maven-plugin/reference/html/)
* [Create an OCI image](https://docs.spring.io/spring-boot/docs/2.5.3/maven-plugin/reference/html/#build-image)
* [Spring Boot DevTools](https://docs.spring.io/spring-boot/docs/2.5.3/reference/htmlsingle/#using-boot-devtools)
* [Spring Data JPA](https://docs.spring.io/spring-boot/docs/2.5.3/reference/htmlsingle/#boot-features-jpa-and-spring-data)
* [Spring Web](https://docs.spring.io/spring-boot/docs/2.5.3/reference/htmlsingle/#boot-features-developing-web-applications)

### Guides
The following guides illustrate how to use some features concretely:

* [Accessing Data with JPA](https://spring.io/guides/gs/accessing-data-jpa/)
* [Building a RESTful Web Service](https://spring.io/guides/gs/rest-service/)
* [Serving Web Content with Spring MVC](https://spring.io/guides/gs/serving-web-content/)
* [Building REST services with Spring](https://spring.io/guides/tutorials/bookmarks/)

### Criar Terminal

 * Content-Type -> text/html
 * POST -> (https://poc-muxi.herokuapp.com/muxi/api/v1/terminal)
 * Body -> 44332511;123;PWWIN;7;F04A2E4088B;4;8.00b3;0;16777216;PWWIN
 
### Consultar Terminal

  * GET -> (https://poc-muxi.herokuapp.com/muxi/api/v1/terminal/44332511)
  
### Atualizar Terminal

  * PUT -> (https://poc-muxi.herokuapp.com/muxi/api/v1/terminal)
  * Body -> {
    "logic": 44332511,
    "serial": "22569",
    "model": "PWWIN",
    "sam": 7,
    "ptid": "F04A2E4088B",
    "plat": 4,
    "version": "8.00b3",
    "mxr": 0,
    "mxf": 16777216,
    "verfm": "PWWIN"
}
