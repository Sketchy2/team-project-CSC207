package interface_adapters;

import use_case.save.SaveOutfitOutputBoundary;
import use_case.save.SaveOutfitOutputData;

/**
 * Presenter converts raw Output Data → user-friendly ViewModel.
 */
public class SaveOutfitPresenter implements SaveOutfitOutputBoundary {

    private final SaveOutfitViewModel viewModel;

    public SaveOutfitPresenter(SaveOutfitViewModel vm) {
        this.viewModel = vm;
    }

    /**
     * Called by the Interactor when saving the outfit succeeds.
     *
     * @param data packaged output data containing success message
     *             and the updated list of saved outfits.
     */
    @Override
    public void prepareSuccessView(SaveOutfitOutputData data) {
        // Update the ViewModel with new data from the Interactor.
        viewModel.setOutfits(data.getOutfits());
        viewModel.setMessage(data.getMessage());

        // mark the ViewModel as changed so the UI will update.
    }

    /**
     * Called by the Interactor when the save operation fails.
     *
     * @param errorMessage description of what went wrong
     */
    @Override
    public void prepareFailView(String errorMessage) {
        viewModel.setError(errorMessage);
    }
}

