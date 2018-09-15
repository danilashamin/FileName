package fileName;

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
        try (Stream<Path> paths = Files.walk(Paths.get("/Users/danilashamin/Downloads/song-bank"))) {
            paths
                    .filter(Files::isRegularFile)
                    .filter(path -> path.getFileName().toString().endsWith(".kar"))
                    .forEach(path -> {
                        String name = FileNameUtils.getSubstringInBrackets(path.toFile().getName());
                        if(!name.equals("")){
                            allSubstringsWithBrackets.add(name);
                        }
                        //System.out.println("Name before renamed: " + path.toFile().getName());
                        //newName(path, FileNameUtils.removeBrackets(path.toFile().getName()));
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
        TreeMap<String, Integer> result = new TreeMap<>(comparator);
        result.putAll(map);
        return result;
    }

    static void newName(Path oldName, String newNameString) {
        try {
            Files.move(oldName, oldName.resolveSibling(newNameString));
            System.out.println("Succesfully renamed, new name: " + oldName.toFile().getName());
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Can't rename file to " + newNameString);
        }
        System.out.println("\n\n");
    }
}
