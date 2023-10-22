package com.free.pet.petmodels;

import com.github.javafaker.Faker;

import java.util.*;

public class PetTestEntity {
    private Faker faker = new Faker();

    public Integer setFakeId() {
        return faker.idNumber().hashCode();
    }

    public String setFakeName() {
        return faker.name().name();
    }

    public String setFakeImageUrl() {
        return "https://image.com/"+faker.idNumber().hashCode()+".jpg";
    }

    public Category category() {
        return new Category(setFakeId(), setFakeName());
    }

    public Tag tag() {
        return new Tag(setFakeId(), setFakeName());
    }

    public Pet pet() {
        return new Pet(
                setFakeId(),
                category(),
                setFakeName(),
                Collections.singletonList(setFakeImageUrl()),
                Collections.singletonList(tag()),
                Status.AVAILABLE.status
        );
    }
}
