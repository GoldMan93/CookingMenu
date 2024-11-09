package Course;

import java.util.List;
import java.util.Map;

public interface Course {
    String getName();
    Double getCalorie();
    String[] getTags();
    Map<String, Double> getAllIngredient();
    Map<String, Double> getFoodIngredient();
    Map<String, Double> getSpiceIngredient();
    List<String>getCookingSteps();
    String toString();
}
