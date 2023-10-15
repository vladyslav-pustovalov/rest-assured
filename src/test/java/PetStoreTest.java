import io.restassured.RestAssured;
import models.Category;
import models.Pet;
import models.Status;
import models.Tag;
import org.hamcrest.Matchers;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.Collections;
import java.util.List;

public class PetStoreTest {
    Integer id = 1144;
    Integer categoryId = 2255;
    Integer newCategoryId = 2256;
    String categoryName = "Cats";
    String newCategoryName = "NewCats";
    String name = "Sima";
    String newName = "Also Sima";
    String photoUrl = "https://media.gettyimages.com/id/1361767161/photo/cat-meowing-yawning-laughing-with-rose-gold-pink-background.jpg?s=2048x2048&w=gi&k=20&c=DX9NZLNkDEF9BZ5hevh0JIfV5YLzXU9bo81YG1PNwv0=";
    String newPhotoUrl = "https://github.com/vladyslav-superunlimited/test-files/blob/f7d65243bbbe7a9d00ccaa12598e45ed1433bacf/turbo.jpg";
    Integer tagId = 3366;
    Integer newTagId = 3367;
    String tagName = "someTag";
    String newTagName = "otherTag";
    String status = Status.AVAILABLE;
    String newStatus = Status.SOLD;

    Category cats = new Category(categoryId, categoryName);
    Category cats2 = new Category(newCategoryId, newCategoryName);
    Tag tag1 = new Tag(tagId, tagName);
    Tag tag2 = new Tag(newTagId, newTagName);
    Pet sima = new Pet(id, cats, name, Collections.singletonList(photoUrl), Collections.singletonList(tag1), status);
    Pet updatedSima = new Pet(id, cats2, newName, Collections.singletonList(newPhotoUrl), Collections.singletonList(tag2), newStatus);

    @BeforeClass
    public void setup() {

        Spec.installSpec(
                Spec.requestSpec("https://petstore.swagger.io", "/v2"),
                Spec.responseSpec()
        );
    }

    @Test(priority = 1)
    public void createPetByPost() {

        Pet pet = RestAssured
                .given()
                    .body(sima)
                .when()
                    .post("/pet")
                .then()
                    .assertThat()
                        .statusCode(200)
                    .extract().body().as(Pet.class);

        Assert.assertEquals(sima, pet);
    }

    @Test(priority = 2)
    public void uploadPetsImageByPost() {
        /*language=JSON*/
        String updatePetsImageBody = """
                {
                  "file": "%s"
                }
                """.formatted(photoUrl);

        RestAssured
                .given()
                    .accept("application/json")
                    .contentType("multipart/form-data")
                    .body(updatePetsImageBody)
                .when()
                    .post("/pet/"+id+"/uploadImage")
                .then()
                    .assertThat()
                        .statusCode(200)
                        .body("photoUrls", Matchers.equalTo("photoUrls[0]"));
    }

    @Test(priority = 3)
    public void updatePetsStatusByPost() {
        String changedStatus = Status.PENDING;

        /*language=JSON*/
        String updatePetsStatusBody = """
                {
                  "status": "%s"
                }
                """.formatted(changedStatus);

        RestAssured
                .given()
                    .contentType("application/x-www-form-urlencoded")
                    .body(updatePetsStatusBody)
                .when()
                    .post("/pet/"+id)
                .then()
                    .assertThat()
                        .statusCode(200);

        Pet pet = RestAssured
                .given()
                .when()
                    .get("/pet/"+id)
                .then()
                    .assertThat()
                        .statusCode(200)
                    .extract().body().as(Pet.class);

        Assert.assertEquals(pet.getStatus(), changedStatus);
    }

    @Test(priority = 4)
    public void updatePetByPut() {

        Pet pet = RestAssured
                .given()
                    .body(updatedSima)
                .when()
                    .put("/pet")
                .then()
                    .assertThat()
                        .statusCode(200)
                    .extract().body().as(Pet.class);

        Assert.assertEquals(pet, updatedSima);
    }

    @Test(priority = 5)
    public void getPetById() {

        Pet pet = RestAssured
                .given()
                .when()
                    .get("/pet/"+id)
                .then()
                    .assertThat()
                        .statusCode(200)
                    .extract().body().as(Pet.class);

        Assert.assertEquals(pet, updatedSima);
    }

    @Test(priority = 6)
    public void getPetsByStatus() {

        List<Pet> pets = RestAssured
                .given()
                .when()
                    .get("/pet/findByStatus?status="+newStatus)
                .then()
                    .assertThat()
                        .statusCode(200)
                        .extract().body().jsonPath().getList("", Pet.class);

        Assert.assertTrue(pets.contains(updatedSima), "Sima is not presented in this list");
    }

    @Test(priority = 7)
    public void deletePetById() {
        RestAssured
                .given()
                .when()
                    .delete("/pet/"+id)
                .then()
                    .assertThat()
                        .statusCode(200);

        RestAssured
                .given()
                .when()
                    .get("/pet/"+id)
                .then()
                    .assertThat()
                        .statusCode(404)
                        .body("message", Matchers.equalTo("Pet not found"));
    }
}
