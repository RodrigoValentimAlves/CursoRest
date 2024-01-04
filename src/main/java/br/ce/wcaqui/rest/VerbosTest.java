package br.ce.wcaqui.rest;

import org.junit.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;


public class VerbosTest {
    @Test
    public void deveSalvarUsuario(){
                given()
                        .log().all()
                        .contentType("application/json")
                        .body("{\"name\" : \"Rodrigo\", \"age\": 26}")
                .when()
                    .post("https://restapi.wcaquino.me/users")
                .then()
                        .log().all()
                        .statusCode(201)
                        .body("id", is(notNullValue()))
                        .body("name", is("Rodrigo"))
                        .body("age", is(26));
    }
    @Test
    public void naoDeveSalvarUsuarioSemNome(){
        given()
                .log().all()
                .contentType("application/json")
                .body("{\"age\": 26}")
            .when()
                .post("https://restapi.wcaquino.me/users")
            .then()
                .log().all()
                .statusCode(400)
                .body("id", is(nullValue()))
                .body("error", is("Name é um atributo obrigatório"))
                ;
    }
    @Test
    public void deveSalvarUsuarioViaXML(){
        given()
                .log().all()
//                .contentType(ContentType.XML)
                .contentType("application/XML")
                .body("<user><name>Rodrigo</name><age>30</age></user>")
            .when()
                .post("https://restapi.wcaquino.me/usersXML")
            .then()
                .log().all()
                .statusCode(201)
                .body("user.name",is("Rodrigo"))
                .body("user.age", is("30"))
        ;
    }

}