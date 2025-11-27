package interface_adapters.save_favorite_loc;

import entities.Location;
import interface_adapters.weather.GetWeatherController;
import interface_adapters.weather.WeatherViewModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

/**
 * Panel for saving and deleting favorite locations.
 * This view:
 * - Reads data from FavoriteLocationsViewModel
 * - Sends user actions to FavoriteLocationsController (save&delete)
 */

public class FavoriteLocationsPanel extends JPanel implements PropertyChangeListener {

    private final FavoriteLocationsViewModel viewModel;
    private final FavoriteLocationsController controller;
    private final GetWeatherController weatherController;
    private final WeatherViewModel weatherViewModel;

    private final JTextField cityField = new JTextField(10);
    private final JTextField countryField = new JTextField(4);
    private final JTextField latField = new JTextField(8);
    private final JTextField lonField = new JTextField(8);

    private final DefaultListModel<String> listModel = new DefaultListModel<>();
    private final JList<String> favoritesList = new JList<>(listModel);
    private final JLabel messageLabel = new JLabel(" ");

    private final Map<String, Location> locationByName = new HashMap<>();

    public FavoriteLocationsPanel(
            FavoriteLocationsViewModel viewModel,
            FavoriteLocationsController controller,
            GetWeatherController weatherController,
            WeatherViewModel weatherViewModel) {
        this.viewModel = viewModel;
        this.controller = controller;
        this.weatherController = weatherController;
        this.weatherViewModel = weatherViewModel;

        this.viewModel.addPropertyChangeListener(this);

        buildUI();

        updateFavoritesList(viewModel.getFavorites());
        messageLabel.setText(viewModel.getMessage());
    }

    private void buildUI() {
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        //Top bar: input + save/delete
        JPanel inputPanel = new JPanel();
        inputPanel.add(new JLabel("City:"));
        inputPanel.add(cityField);

        JButton saveButton = new JButton("Save");
        JButton deleteButton = new JButton("Delete");

        JPanel buttonRow = new JPanel();
        buttonRow.add(saveButton);
        buttonRow.add(deleteButton);

        JPanel northPanel = new JPanel(new BorderLayout());
        northPanel.add(inputPanel, BorderLayout.CENTER);
        northPanel.add(buttonRow, BorderLayout.SOUTH);
        add(northPanel, BorderLayout.NORTH);


        //Center: favorites list
        favoritesList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollPane = new JScrollPane(favoritesList);
        scrollPane.setPreferredSize(new Dimension(300, 150));
        add(scrollPane, BorderLayout.CENTER);

        //Bottom: message
        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.add(messageLabel, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);

        //double-click a city to get weather
        favoritesList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    String selected = favoritesList.getSelectedValue();
                    if (selected == null || selected.isEmpty()) {
                        return;
                    }

                    Location loc = locationByName.get(selected);

                    if (loc != null) {
                        weatherController.execute(
                                loc.getName(),
                                loc.getCountryCode(),
                                loc.getLatitude(),
                                loc.getLongitude()
                        );
                    } else {
                        messageLabel.setText("Using city name only for " + selected);
                        weatherController.execute(
                                selected,
                                "CA",
                                0.0,
                                0.0
                        );
                    }

                    WeatherViewModel.State s = weatherViewModel.getState();

                    if (!s.errorMessage.isEmpty()) {
                        JOptionPane.showMessageDialog(
                                FavoriteLocationsPanel.this,
                                s.errorMessage,
                                "City not found",
                                JOptionPane.ERROR_MESSAGE
                        );
                        return;
                    }

                    StringBuilder sb = new StringBuilder();
                    sb.append("City: ").append(s.cityName).append("\n");
                    sb.append("Temperature: ").append(s.temperatureText).append("\n");
                    sb.append("Feels like: ").append(s.feelsLikeText).append("\n");
                    sb.append("Humidity: ").append(s.humidityText).append("\n");
                    sb.append("Wind speed: ").append(s.windSpeedText).append("\n");
                    sb.append("Condition: ").append(s.descriptionText).append("\n");

                    JOptionPane.showMessageDialog(
                            FavoriteLocationsPanel.this,
                            sb.toString(),
                            "Weather",
                            JOptionPane.INFORMATION_MESSAGE
                    );
                }
            }
        });

        //Save button
        saveButton.addActionListener(e -> {
            String city = cityField.getText().trim();

            if (city.isEmpty()) {
                messageLabel.setText("Please enter a city name.");
                return;
            }

            String defaultCountry = "CA";
            double lat = 0.0;
            double lon = 0.0;

            controller.save(city, defaultCountry, lat, lon);
        });

        //Delete button
        deleteButton.addActionListener(e -> {
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