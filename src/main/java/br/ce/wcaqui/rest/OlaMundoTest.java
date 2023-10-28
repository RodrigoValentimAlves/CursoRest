package br.ce.wcaqui.rest;

import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static io.restassured.RestAssured.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;


public class OlaMundoTest {
    @Test
    public void testOlaMundo() {
        Response response = request(Method.GET,"http://restapi.wcaquino.me/ola");
        Assert.assertTrue (response.getBody().asString().equals("Ola Mundo!"));
        Assert.assertTrue(response.statusCode() == 200);
        Assert.assertTrue("O status code deveria ser 200",response.statusCode() == 200);
        Assert.assertEquals(200,response.statusCode());

        ValidatableResponse validacao = response.then();
        validacao.statusCode(200);
    }

    @Test
    public void conhecendoOutrasFormasRestAssured() {
        Response response = request(Method.GET,"http://restapi.wcaquino.me/ola");
        ValidatableResponse validacao = response.then();
        validacao.statusCode(200);

        get("http://restapi.wcaquino.me/ola").then().statusCode(200);

        given() //Pré condição
                .when().get("http://restapi.wcaquino.me/ola")//Ação
            .then()
                .statusCode(200);//Resultado esperado
    }

    @Test
    public void devoConhecerOsMatcherComHamcrest(){
        assertThat("Maria", Matchers.is("Maria"));
        assertThat(140, Matchers.is(140));
        assertThat(130, Matchers.isA(Integer.class));
        assertThat(130d, Matchers.isA(Double.class));
        assertThat(130d, Matchers.greaterThan(120D));
        assertThat(130d, Matchers.lessThan(140D));

        List<Integer> impares = Arrays.asList(1,3,5,7,9);
        assertThat(impares,contains(1,3,5,7,9));
        assertThat(impares,containsInAnyOrder(1,9,5,7,3));
        assertThat(impares,hasItem(3));
        assertThat(impares,hasItems(3,7));
        assertThat(impares,Matchers.hasSize(5));

        assertThat("Maria", is(not("João")));
        assertThat("Maria", not("João"));
        assertThat("Maria", anyOf(is("João"), is("Maria")));
        assertThat("Maria", allOf(startsWith("Ma"), endsWith("ia"), containsString("ar")));
    }

    @Test
    public void devoValidarBody() {
        given()
            .when()
                .get("http://restapi.wcaquino.me/ola")
            .then()
                .statusCode(200)
                .body(is("Ola Mundo!"))
                .body(containsString("Mundo"))
                .body(is(not(nullValue())));
    }

}
