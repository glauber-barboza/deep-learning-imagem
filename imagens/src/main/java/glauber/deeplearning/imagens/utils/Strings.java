package glauber.deeplearning.imagens.utils;

import org.apache.commons.lang3.StringUtils;

public class Strings {

    public static String formatSafe(String key, Object... args) {
        if (StringUtils.isBlank(key) || args == null || args.length == 0) {
            return key;
        }
        try {
            return String.format(key, args);
        } catch (Exception e) {
            return key;
        }
    }
}