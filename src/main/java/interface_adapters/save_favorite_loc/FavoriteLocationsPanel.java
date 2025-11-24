package interface_adapters.save_favorite_loc;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;

/**
 * Panel for saving and deleting favorite locations.
 * This view:
 * - Reads data from FavoriteLocationsViewModel
 * - Sends user actions to FavoriteLocationsController (save&delete)
 */

public class FavoriteLocationsPanel extends JPanel implements PropertyChangeListener {

    private final FavoriteLocationsViewModel viewModel;
    private final FavoriteLocationsController controller;

    private final JTextField cityField = new JTextField(15);
    private final DefaultListModel<String> listModel = new DefaultListModel<>();
    private final JList<String> favoritesList = new JList<>(listModel);
    private final JLabel messageLabel = new JLabel(" ");

    public FavoriteLocationsPanel(FavoriteLocationsViewModel viewModel, FavoriteLocationsController controller) {
        this.viewModel = viewModel;
        this.controller = controller;

        this.viewModel.addPropertyChangeListener(this);

        buildUI();

        updateFavoritesList(viewModel.getFavorites());
        messageLabel.setText(viewModel.getMessage());
    }

    private void buildUI() {
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        //Top bar: input + save/delete
        JPanel topPanel = new JPanel();
        topPanel.add(new JLabel("City:"));
        topPanel.add(cityField);

        JButton saveButton = new JButton("Save");
        JButton deletButton = new JButton("Delete");
        topPanel.add(saveButton);
        topPanel.add(deletButton);

        add(topPanel, BorderLayout.NORTH);

        //Center: favorites list
        favoritesList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollPane = new JScrollPane(favoritesList);
        scrollPane.setPreferredSize(new Dimension(250, 150));
        add(scrollPane, BorderLayout.CENTER);

        //Bottom: message
        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.add(messageLabel, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);

        saveButton.addActionListener(e -> {
            String city = cityField.getText().trim();
            if (!city.isEmpty()) {
                controller.save(city);
            }
        });

        deletButton.addActionListener(e -> {
            String selected = favoritesList.getSelectedValue();
            if (selected == null || selected.isEmpty()) {
                selected = cityField.getText().trim();
            }
            if (selected != null && !selected.isEmpty()) {
                controller.delete(selected);
            }
        });
    }

        /**
         * Called when the ViewModel updates (favories list or message).
         */
        @Override
        public void propertyChange(PropertyChangeEvent evt) {
            String propertyName = evt.getPropertyName();

            if ("favorites".equals(propertyName)) {
                List<String> updated = viewModel.getFavorites();
                updateFavoritesList(updated);
            }

            if ("message".equals(propertyName)) {
                messageLabel.setText(viewModel.getMessage());
            }
        }

    /**
     * Helper to update the JList from ViewModel.
     */
    private void updateFavoritesList(List<String> favorites) {
        listModel.clear();
        if (favorites != null) {
            for (String city : favorites) {
                listModel.addElement(city);
            }
        }
    }
}