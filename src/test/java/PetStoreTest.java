import io.restassured.RestAssured;
import org.hamcrest.Matchers;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class PetStoreTest {
    Integer id = 1144;
    Integer categoryId = 2255;
    String categoryName = "Hitler's Party";
    String name = "Uncle Adolf";
    String newName = "Grand Uncle Adolf";
    String photoUrl = "https://github.com/vladyslav-pustovalov/HTML-Resume/blob/main/img/1.jpg";
    Integer tagId = 3366;
    Integer newTagId = 3367;
    String tagName = "someTag";
    String newTagName = "someOtherTag";
    String status = "available";
    String newStatus = "sold";

    @BeforeClass
    public void setup() {

        Spec.installSpec(
                Spec.requestSpec("https://petstore.swagger.io", "/v2"),
                Spec.responseSpec()
        );
    }

    @Test
    public void createPetByPost() {
        /*language=JSON*/
        String createNewPetBody = """
                {
                  "id": "%s",
                  "category": {
                    "id": "%s",
                    "name": "%s"
                  },
                  "name": "%s",
                  "tags": [
                      {
                        "id": "%s",
                        "name": "%s"
                      }
                    ],
                  "status": "%s"
                }
                """.formatted(id, categoryId, categoryName, name, tagId, tagName, status);

        RestAssured
                .given()
                .when()
                    .body(createNewPetBody)
                    .post("/pet")
                .then()
                    .assertThat()
                        .statusCode(200)
                        .body("id", Matchers.equalTo(id))
                        .body("category.id", Matchers.equalTo(categoryId))
                        .body("category.name", Matchers.equalTo(categoryName))
                        .body("name", Matchers.equalTo(name))
                        .body("tags[0].id", Matchers.equalTo(tagId))
                        .body("tags[0].name", Matchers.equalTo(tagName))
                        .body("status", Matchers.equalTo(status));
    }

//    @Test
//    public void uploadPetsImageByPost() {
//        /*language=JSON*/
//        String updatePetsImageBody = """
//                {
//                  "file": \"%s"
//                }
//                """.formatted(photoUrl);
//
//        RestAssured
//                .given()
//                .when()
//                .body(updatePetsImageBody)
//                .accept("application/json")
//                .contentType("multipart/form-data")
//                .post("/pet/"+id+"/uploadImage")
//                .then()
//                .assertThat()
//                .statusCode(200)
//                .body("id", Matchers.equalTo(id))
//                .body("category.id", Matchers.equalTo(categoryId))
//                .body("category.name", Matchers.equalTo(categoryName))
//                .body("name", Matchers.equalTo(name))
//                .body("tags[0].id", Matchers.equalTo(tagId))
//                .body("tags[0].name", Matchers.equalTo(tagName))
//                .body("status", Matchers.equalTo(status));
//    }

    @Test
    public void updatePetsStatusByPost() {
        /*language=JSON*/
        String updatePetsStatusBody = """
                {
                  "status": "%s"
                }
                """.formatted(newStatus);

        RestAssured
                .given()
                .when()
                .body(updatePetsStatusBody)
                .contentType("application/x-www-form-urlencoded")
                .post("/pet/"+id)
                .then()
                .assertThat()
                .statusCode(200);
    }

    @Test
    public void updatePetByPut() {
        /*language=JSON*/
        String updatePetBody = """
                {
                  "id": "%s",
                  "category": {
                    "id": "%s",
                    "name": "%s"
                  },
                  "name": "%s",
                  "photoUrls": "%s",
                  "tags": [
                      {
                        "id": "%s",
                        "name": "%s"
                      }
                    ],
                  "status": "%s"
                }
                """.formatted(id, categoryId, categoryName, newName, photoUrl, newTagId, newTagName, status);

        RestAssured
                .given()
                .when()
                    .body(updatePetBody)
                    .put("/pet")
                .then()
                    .assertThat()
                        .statusCode(200)
                        .body("id", Matchers.equalTo(id))
                        .body("category.id", Matchers.equalTo(categoryId))
                        .body("category.name", Matchers.equalTo(categoryName))
                        .body("name", Matchers.equalTo(newName))
                        .body("photoUrls", Matchers.equalTo(photoUrl))
                        .body("tags[0].id", Matchers.equalTo(tagId))
                        .body("tags[0].name", Matchers.equalTo(tagName))
                        .body("tags[1].id", Matchers.equalTo(newTagId))
                        .body("tags[1].name", Matchers.equalTo(tagName))
                        .body("status", Matchers.equalTo(newStatus));
    }

    @Test
    public void getPetById() {
        RestAssured
                .given()
                .when()
                    .get("/pet/"+id)
                .then()
                    .assertThat()
                        .statusCode(200)
                        .body("id", Matchers.equalTo(id))
                        .body("category.id", Matchers.equalTo(categoryId))
                        .body("category.name", Matchers.equalTo(categoryName))
                        .body("name", Matchers.equalTo(newName))
                        .body("photoUrls", Matchers.equalTo(photoUrl))
                        .body("tags[0].id", Matchers.equalTo(tagId))
                        .body("tags[0].name", Matchers.equalTo(tagName))
                        .body("tags[1].id", Matchers.equalTo(newTagId))
                        .body("tags[1].name", Matchers.equalTo(tagName))
                        .body("status", Matchers.equalTo(newStatus));
    }

    @Test
    public void getPetsByStatus() {
        RestAssured
                .given()
                .when()
                    .get("/pet/findByStatus?status="+newStatus)
                .then()
                    .assertThat()
                        .statusCode(200)
                        .body("[0].id", Matchers.equalTo(id))
                        .body("[0].status", Matchers.equalTo(newStatus));
    }

    @Test
    public void deletePetById() {
        RestAssured
                .given()
                .when()
                    .delete("/pet/"+id)
                .then()
                    .assertThat()
                        .statusCode(200);
    }
}
