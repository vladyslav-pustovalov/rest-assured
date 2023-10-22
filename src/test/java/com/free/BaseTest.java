package com.free;

import org.testng.annotations.BeforeClass;

public class BaseTest {

    @BeforeClass
    public void baseSetup() {
        BaseSpecification.installSpec();
    }
}
