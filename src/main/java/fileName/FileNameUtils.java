package fileName;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FileNameUtils {
    public static String removeBrackets(String str) {
        str = replaceConsts(str);
        return str;
    }

    private static String replaceConsts(String str) {
        return str
                .replaceAll("\\(Фран\\)", "")
                .replaceAll("\\(Fx\\)", "")
                .replaceAll("\\(Gs Remix\\)", "")
                .replaceAll("\\(Remix Gs\\)", "")
                .replaceAll("\\(Gs 2 Вар\\)", "")
                .replaceAll("\\(Gm2\\)", "")
                .replaceAll("\\(Es\\)", "")
                .replaceAll("\\(Sxg\\)", "")
                .replaceAll("\\(2 Вар\\)", "(2)")
                .replaceAll("\\(Вар 2\\)", "(2)")
                .replaceAll("\\[Kd]", "")
                .replaceAll("\\(Xg 2 Вар\\)", "(2)")
                .replaceAll("\\(2 Вар Xg\\)", "(2)")
                .replaceAll("К - Ф", "к.ф.")
                .replaceAll("\\(3 Вар\\)", "(3)")
                .replaceAll("\\(Вариант 2\\)", "(2)")
                .replaceAll("\\(2 Вер\\)", "")
                .replaceAll("\\(Gs\\)", "")
                .replaceAll("\\(Gm\\)", "(2)")
                .replaceAll("\\(Gm2\\)", "")
                .replaceAll("\\(Xg\\)", "")
                .replaceAll("\\s*Xg\\s*", "2")
                //.replaceAll("\\s\\.kar", " (2).kar")
                .trim();

    }


    private static String setSpace(String str) {
        int i = str.indexOf('(');
        if (i != -1 && i != 0) {
            if (str.charAt(i - 1) != ' ') {
                str = str.substring(0, i) + ' ' + str.substring(i, str.length());
            }
        }
        return str;
    }

    private static String deleteFirstAndLastSpaces(String str) {
        return str.trim();
    }

    private static String removeBracketsFromTheDigits(String str) {
        String substringInBrackets = str.replaceAll("[([^\\d])]", "");
        return str.replaceAll("\\(" + substringInBrackets + "\\)", substringInBrackets).trim().replaceAll("  +", "");
    }

    private static String removeBracketsWithLetters(String str) {
        return str.replaceAll("^\\(.*\\)", "").trim().replaceAll("  +", "");
    }

    private static String replaceDash(String str) {
        int firstOccurrence = str.indexOf('-');
        int lastOccurrence = str.lastIndexOf('-');
        if (firstOccurrence == -1 && lastOccurrence == -1) {
            return str;
        }
        if (firstOccurrence == lastOccurrence) {
            return str.replaceAll("-", "")
                    .replaceAll("  +", "")
                    .trim();
        }
        if (lastOccurrence != -1) {
            return str.substring(0, lastOccurrence - 1).trim().concat(".kar");
        }
        return str;
    }

    public static String getSubstringInBrackets(String str) {
        Pattern pattern = Pattern.compile("[(\\[{](.*?)[)\\]}]");
        Matcher matcher = pattern.matcher(str);
        List<String> lst = new ArrayList<>();
        while (matcher.find()) {
            lst.add(matcher.group(1));
        }
        if (!lst.isEmpty()) {
            return lst.get(0);
        } else return "";
    }
}

