package com.sitionix.bffssox.it.infra.utils.comparator;


import lombok.experimental.UtilityClass;

import java.util.function.Predicate;

@UtilityClass
public class JsonMatcher {

    public static Predicate<String> jsonEqualsIgnore(final String expectedJson, final String... fieldsForIgnore) {
        return actualJson -> {
            try {
                JsonComparator.compareJson(expectedJson, actualJson, fieldsForIgnore);
                return true;
            } catch (Exception e) {
                return false;
            }
        };
    }
}
