package com.vocab.shared.util;

import java.util.regex.Pattern;

public class PatternConst {

    public static final Pattern EMAIL_PATTERN =
            Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");

    private PatternConst() {
    }


}
