package use_case;

import java.util.List;

/**
 * Output data for the outfit recommendation use case.
 */
public class RecommendOutfitOutputData {

    private final String title;
    private final List<String> items;
    private final String rationale;

    /**
     * Creates output data containing the recommendation results.
     *
     * @param title     the title of the recommendation
     * @param items     the list of recommended clothing items
     * @param rationale the explanation behind the recommendation
     */
    public RecommendOutfitOutputData(String title, List<String> items, String rationale) {
        this.title = title;
        this.items = items;
        this.rationale = rationale;
    }

    /**
     * @return the recommendation title
     */
    public String getTitle() { return title; }

    /**
     * @return the list of recommended clothing items
     */
    public List<String> getItems() { return items; }

    /**
     * @return the rationale for the recommendation
     */
    public String getRationale() { return rationale; }
}
