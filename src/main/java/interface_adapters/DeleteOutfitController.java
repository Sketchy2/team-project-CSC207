package interface_adapters;

import use_case.delete.DeleteOutfitInputBoundary;
import use_case.delete.DeleteOutfitInputData;

/**
 * Controller for deleting a saved outfit.
 */
public class DeleteOutfitController {

    private final DeleteOutfitInputBoundary interactor;

    public DeleteOutfitController(DeleteOutfitInputBoundary interactor) {
        this.interactor = interactor;
    }

    public void deleteOutfit(String name, String weatherProfile, String location) {
        DeleteOutfitInputData data =
                new DeleteOutfitInputData(name, weatherProfile, location);
        interactor.execute(data);
    }
}
