package entities;
/**
 * A clothing item used when generating outfit recommendations.
 */

public class ClothingItem {
    private final String name;
    private final String category;
    private final boolean waterproof;
    private final int warmth;

    /**
     * Creates a ClothingItem entity.
     *
     * @param name       the name of the item
     * @param category   the category (e.g., "top", "shoes")
     * @param waterproof whether the item is waterproof
     * @param warmth     warmth rating from 0–10
     */

    public ClothingItem(String name, String category, boolean waterproof, int warmth) {
        this.name = name;
        this.category = category;
        this.waterproof = waterproof;
        this.warmth = warmth;
    }

    /**
     * @return the name of this clothing item
     */
    public String getName(){
        return name;
    }

    /**
     * @return the category of this clothing item
     */
    public String getCategory(){
        return category;
    }

    /**
     * @return true if the item is waterproof
     */
    public Boolean isWaterproof(){
        return waterproof;
    }

    /**
     * @return the warmth rating (0–10)
     */
    public int getWarmth() {
        return warmth;
    }
}
