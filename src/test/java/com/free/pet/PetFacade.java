package com.free.pet;

import com.free.StatusCode;
import com.free.pet.petmodels.Pet;
import io.restassured.RestAssured;

public class PetFacade {

    public void createPetWihBody(Pet pet) {

        RestAssured
                .given()
                    .body(pet)
                .when()
                    .post()
                .then()
                    .statusCode(StatusCode.OK);
    }

    public void updatePetWithBody(Pet pet) {
        RestAssured
                .given()
                    .body(pet)
                .when()
                    .put()
                .then()
                    .statusCode(StatusCode.OK);
    }

    public Pet getPetById(int id) {
        return RestAssured
                .when()
                    .get("/" + id)
                .then()
                    .statusCode(StatusCode.OK)
                    .extract().body().as(Pet.class);
    }

    public void deletePetById(int id) {
        RestAssured
                .when()
                    .delete("/"+id)
                .then()
                    .statusCode(StatusCode.OK);
    }
}
