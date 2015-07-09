package com.hah.nyaungu.converter;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by htooaunghlaing on 6/23/15.
 */
public class Detector {

    public static int detector(String string) {
        //if (!string.matches("[\\u1000-\\u1097]")) {

        String isMyanmar = "[က-အ]+";
        Pattern isMyanmarPattern = Pattern.compile(isMyanmar);
        Matcher isMyanmarMatcher = isMyanmarPattern.matcher(string);
        if (!isMyanmarMatcher.find()) {
            return 0;
        }

        String uni = "[ဃငဆဇဈဉညတဋဌဍဎဏဒဓနဘရဝဟဠအ]်|ျ[က-အ]ါ|ျ[ါ-း]|[^\\1031]စ် |\\u103e|\\u103f|\\u1031[^\\u1000-\\u1021\\u103b\\u1040\\u106a\\u106b\\u107e-\\u1084\\u108f\\u1090]|\\u1031$|\\u100b\\u1039|\\u1031[က-အ]\\u1032|\\u1025\\u102f|\\u103c\\u103d[\\u1000-\\u1001]";
        Pattern pattern = Pattern.compile(uni);
        Matcher matcher = pattern.matcher(string);
        if (matcher.find()) {
            return 1;
        }
        return 2;
    }
}
