package interface_adapters;
import java.util.ArrayList;

import java.util.List;

/**
 * View model for displaying outfit recommendations.
 * Stores the title, items, rationale, and any error messages.
 */
public class RecommendationViewModel {
    private String error;
    private String title;
    private List<String> items;
    private String rationale;
    private boolean changed;

    /**
     * @return the current error message, or null if none
     */
    public String getError() { return error; }

    /**
     * @return the recommendation title
     */
    public String getTitle() { return title; }

    /**
     * @return a copy of the recommended item list
     */
    public List<String> getItems() { return new ArrayList<>(items); }

    /**
     * @return the rationale for the recommendation
     */
    public String getRationale() { return rationale; }

    /**
     * @return true if the view model has been updated
     */
    public boolean isChanged() { return changed; }

    /**
     * Sets an error message.
     * @param error the error to display
     */
    public void setError(String error) { this.error = error; }

    /**
     * Sets the title of the recommendation.
     * @param title the title text
     */
    public void setTitle(String title) { this.title = title; }

    /**
     * Sets the list of recommended items.
     * @param items the list of items, or empty list if null
     */
    public void setItems(List<String> items) { this.items = (items == null) ? new ArrayList<>() : new ArrayList<>(items);}

    /**
     * Sets the explanation for the recommendation.
     * @param rationale the rationale text
     */
    public void setRationale(String rationale) { this.rationale = rationale; }

    /**
     * Marks whether the view model has been updated.
     * @param changed true if updated
     */
    public void setChanged(boolean changed) { this.changed = changed; }

    /**
     * Clears all fields and resets the view model.
     */
    public void clear() {
        error = null;
        title = null;
        items.clear();
        rationale = null;
        changed = false;
    }

}
