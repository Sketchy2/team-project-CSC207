package use_case.delete;
import entities.Outfit;
import java.util.List;

/**
 * Output data for the delete outfit use case.
 */
public class DeleteOutfitOutputData {

    private final List<Outfit> outfits;
    private final String message;

    /**
     * Creates output data containing the updated outfit list and a message.
     *
     * @param outfits updated list of saved outfits
     * @param message success message to present
     */
    public DeleteOutfitOutputData(List<Outfit> outfits, String message) {
        this.outfits = outfits;
        this.message = message;
    }

    /**
     * @return the updated list of outfits
     */
    public List<Outfit> getOutfits() { return outfits; }

    /**
     * @return the message describing the outcome
     */
    public String getMessage() { return message; }
}

