package interface_adapters;
import java.util.ArrayList;

import java.util.List;


public class RecommendationViewModel {
    private String error;
    private String title;
    private List<String> items;
    private String rationale;
    private boolean changed;

    public String getError() { return error; }
    public String getTitle() { return title; }
    public List<String> getItems() { return new ArrayList<>(items); }
    public String getRationale() { return rationale; }
    public boolean isChanged() { return changed; }

    public void setError(String error) { this.error = error; }
    public void setTitle(String title) { this.title = title; }
    public void setItems(List<String> items) { this.items = (items == null) ? new ArrayList<>() : new ArrayList<>(items);}
    public void setRationale(String rationale) { this.rationale = rationale; }
    public void setChanged(boolean changed) { this.changed = changed; }

    public void clear() {
        error = null;
        title = null;
        items.clear();
        rationale = null;
        changed = false;
    }

}
