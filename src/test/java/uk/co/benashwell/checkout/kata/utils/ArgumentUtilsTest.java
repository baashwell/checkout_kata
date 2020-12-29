package uk.co.benashwell.checkout.kata.utils;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.Collections;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ArgumentUtilsTest {

    @Test
    @DisplayName("When no propertys are passed use default filename")
    void noProductFilePropertyPassed() {
        assertEquals("default_file", ArgumentUtils.getPropertyValue(new String[]{}, "default_file"));
    }


    @Test
    @DisplayName("When property is passed use that filename")
    void ProductFilePropertyPassed() {
        assertEquals("given_file", ArgumentUtils.getPropertyValue(new String[]{"given_file"}, "default_file"));
    }

}