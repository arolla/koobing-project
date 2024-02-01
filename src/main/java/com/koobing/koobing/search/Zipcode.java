package com.koobing.koobing.search;

import java.util.regex.Pattern;

public record Zipcode(String value) {
    private final static Pattern ZIPCODE_PATTERN = Pattern.compile("^[0-9]{5}$");

    public Zipcode {
        if (!ZIPCODE_PATTERN.matcher(value).matches()) {
            throw new IllegalArgumentException("Invalid zipcode: " + value);
        }
    }
}
