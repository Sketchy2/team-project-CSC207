package interface_adapters;

import java.util.Arrays;
import java.util.List;

import use_case.save.SaveOutfitInputBoundary;
import use_case.save.SaveOutfitInputData;

/**
 * Converts UI input → Interactor data.
 */
public class SaveOutfitController {

    private final SaveOutfitInputBoundary interactor;

    public SaveOutfitController(SaveOutfitInputBoundary interactor) {
        this.interactor = interactor;
    }

    public void save(String name, String items, String profile, String location, boolean overwrite) {

        List<String> itemList = Arrays.stream(items.split(","))
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .toList();

        SaveOutfitInputData data = new SaveOutfitInputData(
                name, itemList, profile, location, overwrite
        );

        interactor.execute(data);
    }
}
