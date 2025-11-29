package demo;

import data_access.FileOutfitsGateway;
import data_access.OutfitsGateway;

import interface_adapters.SaveOutfitController;
import interface_adapters.SaveOutfitPresenter;
import interface_adapters.SaveOutfitViewModel;

import use_case.save.SaveOutfitInputBoundary;
import use_case.save.SaveOutfitInteractor;
import use_case.save.SaveOutfitOutputBoundary;

import view.SaveOutfitView;
import javax.swing.*;

/**
 * Simple demo frame for Use Case 5: Save Outfit.
 *
 * This is similar in spirit to FavoriteLocationsPanelDemo:
 * it wires the gateway, view model, presenter, interactor, controller,
 * and then shows the SaveOutfitView in a Swing frame.
 */
public class SaveOutfitDemo {

    public static void main(String[] args) {
        // 1. Data access (gateway)
        //    Stores outfits in a simple file (like favorite locations demo).
        //    If your FileOutfitsGateway constructor is different, adjust this line.
        OutfitsGateway gateway = new FileOutfitsGateway("outfits_demo.txt");

        // 2. ViewModel (UI-facing state)
        SaveOutfitViewModel viewModel = new SaveOutfitViewModel();

        // 3. Presenter (Output boundary implementation)
        SaveOutfitOutputBoundary presenter = new SaveOutfitPresenter(viewModel);

        // 4. Interactor (Use Case 5 business logic)
        SaveOutfitInputBoundary interactor = new SaveOutfitInteractor(gateway, presenter);

        // 5. Controller (adapts UI input → interactor)
        SaveOutfitController controller = new SaveOutfitController(interactor);

        // 6. View (Swing panel for saving outfits)
        SaveOutfitView panel = new SaveOutfitView(controller, viewModel);

        // 7. Show in a simple frame
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Save Outfit Demo (UC5)");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setContentPane(panel);
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}
