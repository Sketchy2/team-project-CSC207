package interface_adapters;

import use_case.view.ViewSavedItemsInputBoundary;
import use_case.view.ViewSavedItemsInputData;

/**
 * Controller for viewing saved outfits and favourite locations.
 */
public class SavedItemsController {

    private final ViewSavedItemsInputBoundary interactor;

    public SavedItemsController(ViewSavedItemsInputBoundary interactor) {
        this.interactor = interactor;
    }

    public void loadSavedItems() {
        interactor.execute(new ViewSavedItemsInputData());
    }
}
