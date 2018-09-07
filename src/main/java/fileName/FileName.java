package fileName;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Stream;

public class FileName {
    public static void main(String[] arg) {
        List<String> allSubstringsWithBrackets = new ArrayList<>();
        try (Stream<Path> paths = Files.walk(Paths.get("/Users/danilashamin/Library/Group Containers/6N38VWS5BX.ru.keepcoder.Telegram/account-15095284205556603934/postbox/media/all-songs 2"))) {
            paths
                    .filter(Files::isRegularFile)
                    .filter(path -> path.getFileName().toString().endsWith(".kar"))
                    .forEach(path -> {
                        String name = FileNameUtils.getSubstringInBrackets(path.toFile().getName());
                        if(!name.equals("")){
                            allSubstringsWithBrackets.add(name);
                        }
                        //System.out.println("Name before renamed: " + path.toFile().getName());
                        //renameFile(path.toFile(), FileNameUtils.removeBrackets(path.toFile().getName()));
                    });
        } catch (IOException e) {
            e.printStackTrace();
        }
        Map<String, Integer> map = countWords(allSubstringsWithBrackets);

        for (Map.Entry<String, Integer> pair : map.entrySet()) {
            System.out.println("(" + pair.getKey() + ") - " + String.valueOf(pair.getValue()));
        }
    }


    public static Map<String, Integer> countWords(List<String> list) {
        HashMap<String, Integer> result = new HashMap<>();
        for (String word : list) {
            result.put(word, result.containsKey(word) ? result.get(word) + 1 : 1);
        }
        TreeMap<String, Integer> sortedMap = sortMapByValue(result);
        return sortedMap;
    }

    public static TreeMap<String, Integer> sortMapByValue(HashMap<String, Integer> map){
        Comparator<String> comparator = new ValueComparator(map);
        //TreeMap is a map sorted by its keys.
        //The comparator is used to sort the TreeMap by keys.
        TreeMap<String, Integer> result = new TreeMap<String, Integer>(comparator);
        result.putAll(map);
        return result;
    }
    public static void renameFile(File toBeRenamed, String new_name) {
        //need to be in the same path
        File fileWithNewName = new File(toBeRenamed, new_name);

        String newFile = fileWithNewName.getName();
        // Rename file (or directory)
        boolean success = toBeRenamed.renameTo(fileWithNewName);
        if (!success) {
            System.out.println("Succesfully renamed, new name: " + toBeRenamed.getName());
        } else {
            System.out.println("Can't rename file to " + new_name);
        }
        System.out.println("\n\n");
    }
}
