package interface_adapters;

import use_case.delete.DeleteOutfitInputBoundary;
import use_case.delete.DeleteOutfitInputData;

/**
 * Controller for deleting a saved outfit.
 */
public class DeleteOutfitController {

    private final DeleteOutfitInputBoundary interactor;

    /**
     * Creates a controller for the delete outfit use case.
     *
     * @param interactor the input boundary handling the delete request
     */
    public DeleteOutfitController(DeleteOutfitInputBoundary interactor) {
        this.interactor = interactor;
    }

    /**
     * Executes the delete outfit use case.
     *
     * @param name           the name of the outfit to delete
     * @param weatherProfile the associated weather profile
     * @param location       the associated location (may be null)
     */
    public void deleteOutfit(String name, String weatherProfile, String location) {
        DeleteOutfitInputData data =
                new DeleteOutfitInputData(name, weatherProfile, location);
        interactor.execute(data);
    }
}
