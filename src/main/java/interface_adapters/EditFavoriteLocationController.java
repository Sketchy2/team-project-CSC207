package interface_adapters;

import use_case.edit.EditFavoriteLocationInputBoundary;
import use_case.edit.EditFavoriteLocationInputData;

/**
 * Controller for renaming a favourite location.
 */
public class EditFavoriteLocationController {

    private final EditFavoriteLocationInputBoundary interactor;

    public EditFavoriteLocationController(EditFavoriteLocationInputBoundary interactor) {
        this.interactor = interactor;
    }

    public void rename(String oldName, String newName) {
        EditFavoriteLocationInputData data =
                new EditFavoriteLocationInputData(oldName, newName);
        interactor.execute(data);
    }
}
