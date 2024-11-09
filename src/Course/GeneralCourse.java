package Course;

import java.io.*;
import java.util.*;

public class GeneralCourse implements Course {
    private String name = null;
    private final Map<String, Double> foodIngredient = new HashMap<>();
    private final Map<String, Double> spiceIngredient = new HashMap<>();
    private double calorie = 0.0;
    private final List<String> cookingSteps = new ArrayList<>();
    private String[] tags;

    public GeneralCourse(File file) {
        if (file.isFile()) {
            try {
                FileInputStream fis = new FileInputStream(file);
                InputStreamReader isr = new InputStreamReader(fis, "UTF-8"); // specify UTF-8 encoding
                BufferedReader br = new BufferedReader(isr);
                parseLine(br);
                br.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void parseLine(BufferedReader br) throws IOException {
        String line;
        while ((line = br.readLine()) != null) {
            String[] lineSplit = line.split(":");
            switch (lineSplit[0]) {
                case "name":
                    this.name = lineSplit[1];
                    break;
                case "FoodIngredient":
                    this.foodIngredient.put(lineSplit[1], Double.valueOf(lineSplit[2]));
                    break;
                case "SpiceIngredient":
                    this.spiceIngredient.put(lineSplit[1], Double.valueOf(lineSplit[2]));
                    break;
                case "Calorie":
                    this.calorie = Double.parseDouble(lineSplit[1]);
                    break;
                case "Tag":
                    this.tags = lineSplit[1].split(",");
                    break;
                case "CookingSteps":
                    while ((line = br.readLine()) != null) {
                        this.cookingSteps.add(line);
                    }
            }
        }
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public Double getCalorie() {
        return this.calorie;
    }

    @Override
    public String[] getTags() {
        return this.tags;
    }

    @Override
    public Map<String, Double> getAllIngredient() {
        Map<String, Double> result = new HashMap<>();
        result.putAll(this.foodIngredient);
        result.putAll(this.spiceIngredient);
        return result;
    }

    @Override
    public Map<String, Double> getFoodIngredient() {
        return this.foodIngredient;
    }

    @Override
    public Map<String, Double> getSpiceIngredient() {
        return this.spiceIngredient;
    }

    @Override
    public List<String> getCookingSteps() {
        return this.cookingSteps;
    }

    @Override
    public String toString() {
        return name;
    }
}
