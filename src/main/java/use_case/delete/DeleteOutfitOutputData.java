package use_case.delete;
import entities.Outfit;
import java.util.List;

public class DeleteOutfitOutputData {
    private final List<Outfit> outfits;
    private final String message;

    public DeleteOutfitOutputData(List<Outfit> outfits, String message) {
        this.outfits = outfits;
        this.message = message;
    }

    public List<Outfit> getOutfits() { return outfits; }
    public String getMessage() { return message; }
}

