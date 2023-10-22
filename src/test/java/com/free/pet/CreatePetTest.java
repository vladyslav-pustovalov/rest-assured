package com.free.pet;

import com.free.pet.petmodels.*;
import org.testng.Assert;
import org.testng.annotations.Test;

public class CreatePetTest extends PetAPITest{

    @Test
    public void verifyThatValidPetIsCreated() {
        Pet validPet = testEntity.pet();

        facade.createPetWihBody(validPet);

        Assert.assertEquals(validPet, facade.getPetById(validPet.getId()));
    }

    @Test
    public void verifyThatInvalidPetIsNotCreated() {
        Pet invalidPet = testEntity.pet();
        invalidPet.setId(null);

        facade.createPetWihBody(invalidPet);
    }
}
