package entities;
/**
 * Represents a single clothing item that can be recommended based on weather.
 * Warmth is an integer scale (0 = very light, 10 = very warm).
 * Category describes the type (e.g., "top", "bottom", "shoes", "outerwear").
 */

public class ClothingItem {
    private final String name;
    private final String category;
    private final boolean waterproof;
    private final int warmth;

    public ClothingItem(String name, String category, boolean waterproof, int warmth) {
        this.name = name;
        this.category = category;
        this.waterproof = waterproof;
        this.warmth = warmth;
    }

    public String getName(){
        return name;
    }

    public String getCategory(){
        return category;
    }

    public Boolean isWaterproof(){
        return waterproof;
    }

    public int getWarmth() {
        return warmth;
    }
}
