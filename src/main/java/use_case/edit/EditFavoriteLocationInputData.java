package use_case.edit;

/**
 * Input data for renaming a favourite location.
 */
public class EditFavoriteLocationInputData {

    private final String oldName;
    private final String newName;

    public EditFavoriteLocationInputData(String oldName, String newName) {
        this.oldName = oldName;
        this.newName = newName;
    }

    public String getOldName() {
        return oldName;
    }

    public String getNewName() {
        return newName;
    }
}
