package use_case.save;

import entities.Outfit;
import java.util.List;

/**
 * DTO sent from Interactor → Presenter.
 */
public class SaveOutfitOutputData {

    private final List<Outfit> outfits;
    private final String message;

    public SaveOutfitOutputData(List<Outfit> outfits, String message) {
        this.outfits = outfits;
        this.message = message;
    }

    public List<Outfit> getOutfits() { return outfits; }
    public String getMessage() { return message; }
}
