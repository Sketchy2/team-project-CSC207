package view;

import entities.Outfit;
import interface_adapters.EditFavoriteLocationController;
import interface_adapters.SavedItemsController;
import interface_adapters.SavedItemsViewModel;
import interface_adapters.SaveOutfitController;
import interface_adapters.SaveOutfitViewModel;

import javax.swing.*;
import java.awt.*;

/**
 * Swing UI for viewing and editing saved outfits and favourite locations.
 */
public class SavedItemsView extends JPanel {

    public SavedItemsView(SavedItemsController controller,
                          EditFavoriteLocationController editLocationController,
                          SaveOutfitController saveOutfitController,
                          SavedItemsViewModel savedItemsViewModel,
                          SaveOutfitViewModel saveOutfitViewModel) {

        setLayout(new BorderLayout());

        // ---------- Favourite locations list + rename ----------

        DefaultListModel<String> favoritesModel = new DefaultListModel<>();
        JList<String> favoritesList = new JList<>(favoritesModel);
        JScrollPane favoritesScroll = new JScrollPane(favoritesList);

        JButton renameBtn = new JButton("Rename selected city");
        renameBtn.addActionListener(e -> {
            String selected = favoritesList.getSelectedValue();
            if (selected == null) {
                JOptionPane.showMessageDialog(this,
                        "Please select a city to rename.",
                        "No selection",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }

            String newName = JOptionPane.showInputDialog(
                    this,
                    "Enter new name for " + selected + ":",
                    selected
            );
            if (newName == null) {
                // user cancelled
                return;
            }

            editLocationController.rename(selected, newName);

            if (!savedItemsViewModel.getError().isEmpty()) {
                JOptionPane.showMessageDialog(this,
                        savedItemsViewModel.getError(),
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            // refresh favourites list from view model
            favoritesModel.clear();
            for (String city : savedItemsViewModel.getFavoriteLocations()) {
                favoritesModel.addElement(city);
            }

            JOptionPane.showMessageDialog(this,
                    savedItemsViewModel.getMessage(),
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE);
        });

        JPanel favoritesPanel = new JPanel(new BorderLayout());
        favoritesPanel.add(new JLabel("Favourite Locations:"), BorderLayout.NORTH);
        favoritesPanel.add(favoritesScroll, BorderLayout.CENTER);
        favoritesPanel.add(renameBtn, BorderLayout.SOUTH);

        // ---------- Outfits list + edit ----------

        DefaultListModel<String> outfitsModel = new DefaultListModel<>();
        JList<String> outfitsList = new JList<>(outfitsModel);
        JScrollPane outfitsScroll = new JScrollPane(outfitsList);

        JButton editOutfitBtn = new JButton("Edit selected outfit");
        editOutfitBtn.addActionListener(e -> {
            int index = outfitsList.getSelectedIndex();
            if (index < 0) {
                JOptionPane.showMessageDialog(this,
                        "Please select an outfit to edit.",
                        "No selection",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }

            // Get the actual Outfit from the view model
            Outfit original = savedItemsViewModel.getOutfits().get(index);

            // Show a simple edit dialog with text fields
            JTextField nameField = new JTextField(original.getName(), 20);
            JTextField itemsField = new JTextField(String.join(", ", original.getItems()), 30);
            JTextField weatherField = new JTextField(original.getWeatherProfile(), 20);
            JTextField locationField = new JTextField(original.getLocation(), 20);

            JPanel panel = new JPanel(new GridLayout(4, 2));
            panel.add(new JLabel("Name:"));
            panel.add(nameField);
            panel.add(new JLabel("Items (comma-separated):"));
            panel.add(itemsField);
            panel.add(new JLabel("Weather profile:"));
            panel.add(weatherField);
            panel.add(new JLabel("Location:"));
            panel.add(locationField);

            int result = JOptionPane.showConfirmDialog(
                    this,
                    panel,
                    "Edit Outfit",
                    JOptionPane.OK_CANCEL_OPTION,
                    JOptionPane.PLAIN_MESSAGE
            );

            if (result != JOptionPane.OK_OPTION) {
                return; // user cancelled
            }

            // Call existing SaveOutfit use case with overwrite = true
            saveOutfitController.save(
                    nameField.getText(),
                    itemsField.getText(),
                    weatherField.getText(),
                    locationField.getText(),
                    true  // overwrite existing
            );

            // Check SaveOutfitViewModel for any error
            if (!saveOutfitViewModel.getError().isEmpty()) {
                JOptionPane.showMessageDialog(this,
                        saveOutfitViewModel.getError(),
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            // If save succeeded, refresh saved items from UC6
            controller.loadSavedItems();

            // Update lists from SavedItemsViewModel
            outfitsModel.clear();
            for (Outfit o : savedItemsViewModel.getOutfits()) {
                outfitsModel.addElement(
                        o.getName() + " | " +
                        o.getWeatherProfile() + " | " +
                        o.getLocation()
                );
            }

            favoritesModel.clear();
            for (String city : savedItemsViewModel.getFavoriteLocations()) {
                favoritesModel.addElement(city);
            }

            JOptionPane.showMessageDialog(this,
                    "Outfit updated successfully.",
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE);
        });

        JPanel outfitsPanel = new JPanel(new BorderLayout());
        outfitsPanel.add(new JLabel("Saved Outfits:"), BorderLayout.NORTH);
        outfitsPanel.add(outfitsScroll, BorderLayout.CENTER);
        outfitsPanel.add(editOutfitBtn, BorderLayout.SOUTH);

        // ---------- Output text area for extra info ----------

        JTextArea output = new JTextArea(10, 50);
        output.setEditable(false);
        JScrollPane outputScroll = new JScrollPane(output);

        // ---------- Top: refresh button ----------

        JButton refreshBtn = new JButton("Load saved items");
        refreshBtn.addActionListener(e -> {
            controller.loadSavedItems();

            output.setText("");
            if (!savedItemsViewModel.getError().isEmpty()) {
                output.setText("Error: " + savedItemsViewModel.getError());
                return;
            }

            // update favourites list
            favoritesModel.clear();
            for (String city : savedItemsViewModel.getFavoriteLocations()) {
                favoritesModel.addElement(city);
            }

            // update outfits list
            outfitsModel.clear();
            for (Outfit o : savedItemsViewModel.getOutfits()) {
                outfitsModel.addElement(
                        o.getName() + " | " +
                        o.getWeatherProfile() + " | " +
                        o.getLocation()
                );
            }

            output.append(savedItemsViewModel.getMessage() + "\n");
        });

        // ---------- Layout ----------

        JPanel leftPanel = new JPanel(new BorderLayout());
        leftPanel.add(favoritesPanel, BorderLayout.NORTH);
        leftPanel.add(outfitsPanel, BorderLayout.CENTER);

        add(refreshBtn, BorderLayout.NORTH);
        add(leftPanel, BorderLayout.WEST);
        add(outputScroll, BorderLayout.CENTER);
    }
}
