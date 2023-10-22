package com.free.pet;

import com.free.BaseTest;
import com.free.pet.petmodels.PetTestEntity;
import io.restassured.RestAssured;
import org.testng.annotations.BeforeClass;

public class PetAPITest extends BaseTest {
    PetFacade facade = new PetFacade();
    PetTestEntity testEntity = new PetTestEntity();

    @BeforeClass
    void PetAPIsetup() {
        RestAssured.requestSpecification.baseUri("https://petstore3.swagger.io");
        RestAssured.requestSpecification.basePath("/api/v3/pet");
    }
}
