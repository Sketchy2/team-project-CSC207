package use_case;
import java.util.List;

public class RecommendOutfitOutputData {
    private final String title;
    private final List<String> items;
    private final String rationale;


    public RecommendOutfitOutputData(String title, List<String> items, String rationale) {
        this.title = title;
        this.items = items;
        this.rationale = rationale;
    }

    public String getTitle() { return title; }
    public List<String> getItems() { return items; }
    public String getRationale() { return rationale; }

}
