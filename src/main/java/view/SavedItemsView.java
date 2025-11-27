package view;

import entities.Outfit;
import interface_adapters.EditFavoriteLocationController;
import interface_adapters.SavedItemsController;
import interface_adapters.SavedItemsViewModel;
import interface_adapters.SaveOutfitController;
import interface_adapters.SaveOutfitViewModel;
import interface_adapters.DeleteOutfitController;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 * Swing UI for viewing and editing saved outfits and favourite locations.
 */
public class SavedItemsView extends JPanel implements PropertyChangeListener {

    private final DefaultListModel<String> favoritesModel = new DefaultListModel<>();
    private final DefaultListModel<Outfit> outfitsModel = new DefaultListModel<>();
    private final JTextArea output = new JTextArea(10, 50);

    private final SavedItemsViewModel savedItemsViewModel;

    public SavedItemsView(SavedItemsController controller,
                          EditFavoriteLocationController editLocationController,
                          SaveOutfitController saveOutfitController,
                          DeleteOutfitController deleteOutfitController,
                          SavedItemsViewModel savedItemsViewModel,
                          SaveOutfitViewModel saveOutfitViewModel) {

        this.savedItemsViewModel = savedItemsViewModel;
        this.savedItemsViewModel.addPropertyChangeListener(this);

        setLayout(new BorderLayout());

        // ---------- Favourite locations list + rename ----------

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

        JList<Outfit> outfitsList = new JList<>(outfitsModel);
        // Custom renderer to display outfit details
        outfitsList.setCellRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value instanceof Outfit) {
                    Outfit o = (Outfit) value;
                    setText(o.getName() + " | " + o.getWeatherProfile() + " | " + o.getLocation());
                }
                return this;
            }
        });

        JScrollPane outfitsScroll = new JScrollPane(outfitsList);

        JButton editOutfitBtn = new JButton("Edit selected outfit");
        editOutfitBtn.addActionListener(e -> {
            Outfit original = outfitsList.getSelectedValue();
            if (original == null) {
                JOptionPane.showMessageDialog(this,
                        "Please select an outfit to edit.",
                        "No selection",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }

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

            if (saveOutfitViewModel.getError() != null && !saveOutfitViewModel.getError().isEmpty()) {
                JOptionPane.showMessageDialog(this,
                        saveOutfitViewModel.getError(),
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            // If save succeeded, refresh saved items from UC6
            controller.loadSavedItems();

            JOptionPane.showMessageDialog(this,
                    "Outfit updated successfully.",
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE);
        });

        // --- NEW Delete button ---
        JButton deleteOutfitBtn = new JButton("Delete selected outfit");
        deleteOutfitBtn.addActionListener(e -> {
            Outfit toDelete = outfitsList.getSelectedValue();
            if (toDelete == null) {
                JOptionPane.showMessageDialog(this,
                        "Please select an outfit to delete.",
                        "No selection",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }

            int confirm = JOptionPane.showConfirmDialog(
                    this,
                    "Delete outfit: " + toDelete.getName() + "?",
                    "Confirm delete",
                    JOptionPane.OK_CANCEL_OPTION,
                    JOptionPane.WARNING_MESSAGE
            );
            if (confirm != JOptionPane.OK_OPTION) return;

            // call delete use case
            deleteOutfitController.deleteOutfit(
                    toDelete.getName(),
                    toDelete.getWeatherProfile(),
                    toDelete.getLocation()
            );

            if (!savedItemsViewModel.getError().isEmpty()) {
                JOptionPane.showMessageDialog(this,
                        savedItemsViewModel.getError(),
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            // refresh data after deletion
            controller.loadSavedItems();

            JOptionPane.showMessageDialog(this,
                    "Outfit deleted.",
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE);
        });


        JPanel outfitsPanel = new JPanel(new BorderLayout());
        outfitsPanel.add(new JLabel("Saved Outfits:"), BorderLayout.NORTH);
        outfitsPanel.add(outfitsScroll, BorderLayout.CENTER);
        JPanel outfitButtons = new JPanel();
        outfitButtons.add(editOutfitBtn);
        outfitButtons.add(deleteOutfitBtn);

        outfitsPanel.add(outfitButtons, BorderLayout.SOUTH);


        // ---------- Output text area for extra info ----------

        output.setEditable(false);
        JScrollPane outputScroll = new JScrollPane(output);

        // ---------- Top: refresh button ----------

        JButton refreshBtn = new JButton("Load saved items");
        refreshBtn.addActionListener(e -> {
            controller.loadSavedItems();
        });

        // ---------- Layout ----------

        JPanel leftPanel = new JPanel(new BorderLayout());
        leftPanel.add(favoritesPanel, BorderLayout.NORTH);
        leftPanel.add(outfitsPanel, BorderLayout.CENTER);

        add(refreshBtn, BorderLayout.NORTH);
        add(leftPanel, BorderLayout.WEST);
        add(outputScroll, BorderLayout.CENTER);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        // Whenever ViewModel updates (outfits, favorites, or message), refresh UI

        // 1. Update Text Area
        output.setText("");
        if (!savedItemsViewModel.getError().isEmpty()) {
            output.setText("Error: " + savedItemsViewModel.getError());
        } else {
            output.append(savedItemsViewModel.getMessage() + "\n");
        }

        // 2. Update Favorites List
        favoritesModel.clear();
        if (savedItemsViewModel.getFavoriteLocations() != null) {
            for (String city : savedItemsViewModel.getFavoriteLocations()) {
                favoritesModel.addElement(city);
            }
        }

        // 3. Update Outfits List
        outfitsModel.clear();
        if (savedItemsViewModel.getOutfits() != null) {
            for (Outfit o : savedItemsViewModel.getOutfits()) {
                outfitsModel.addElement(o); // Adding Entity directly
            }
        }
    }
}
