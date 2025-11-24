package use_case.delete;
import data_access.OutfitsGateway;
import entities.Outfit;
import java.util.List;

/**
 * Interactor for the delete outfit use case.
 */
public class DeleteOutfitInteractor implements DeleteOutfitInputBoundary {

    private final OutfitsGateway gateway;
    private final DeleteOutfitOutputBoundary presenter;

    /**
     * Creates the interactor for deleting an outfit.
     *
     * @param gateway   the data access interface for outfits
     * @param presenter the output boundary for presenting results
     */
    public DeleteOutfitInteractor(OutfitsGateway gateway,
                                  DeleteOutfitOutputBoundary presenter) {
        this.gateway = gateway;
        this.presenter = presenter;
    }

    /**
     * Executes the delete outfit use case.
     *
     * @param inputData the data specifying which outfit to delete
     */
    @Override
    public void execute(DeleteOutfitInputData inputData) {
        if (!gateway.exists(inputData.getName(),
                inputData.getWeatherProfile(),
                inputData.getLocation())) {
            presenter.prepareFailView("Outfit not found.");
            return;
        }

        gateway.delete(inputData.getName(),
                inputData.getWeatherProfile(),
                inputData.getLocation());

        List<Outfit> updated = gateway.getAll();
        DeleteOutfitOutputData out =
                new DeleteOutfitOutputData(updated, "Outfit deleted.");
        presenter.prepareSuccessView(out);
    }
}
