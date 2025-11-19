package use_case.delete;
import data_access.OutfitsGateway;
import entities.Outfit;
import java.util.List;

public class DeleteOutfitInteractor implements DeleteOutfitInputBoundary {

    private final OutfitsGateway gateway;
    private final DeleteOutfitOutputBoundary presenter;

    public DeleteOutfitInteractor(OutfitsGateway gateway,
                                  DeleteOutfitOutputBoundary presenter) {
        this.gateway = gateway;
        this.presenter = presenter;
    }

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

