package view;

import interface_adapters.RecommendOutfitController;
import interface_adapters.RecommendationViewModel;
import interface_adapters.SaveOutfitController;
import interface_adapters.SaveOutfitViewModel;
import interface_adapters.weather.WeatherViewModel;

import javax.swing.*;
import java.awt.*;

public class RecommendationPanel extends JPanel {

    private final JButton recommendButton = new JButton("Recommend Outfit");
    private final JButton saveButton = new JButton("Save Outfit");
    private final JTextArea resultArea = new JTextArea(10, 30);
    private final JLabel errorLabel = new JLabel(" ");

    private final RecommendOutfitController recommendController;
    private final RecommendationViewModel recommendationViewModel;
    private final WeatherViewModel weatherViewModel;
    private final SaveOutfitController saveOutfitController;
    private final SaveOutfitViewModel saveOutfitViewModel;

    public RecommendationPanel(RecommendOutfitController recommendController,
                               RecommendationViewModel recommendationViewModel,
                               WeatherViewModel weatherViewModel,
                               SaveOutfitController saveOutfitController,
                               SaveOutfitViewModel saveOutfitViewModel) {

        this.recommendController = recommendController;
        this.recommendationViewModel = recommendationViewModel;
        this.weatherViewModel = weatherViewModel;
        this.saveOutfitController = saveOutfitController;
        this.saveOutfitViewModel = saveOutfitViewModel;

        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createTitledBorder("Recommendation"));

        JPanel topPanel = new JPanel(new FlowLayout());
        topPanel.add(recommendButton);
        topPanel.add(saveButton);

        saveButton.setEnabled(false); // Disabled until recommendation exists

        add(topPanel, BorderLayout.NORTH);

        resultArea.setEditable(false);
        resultArea.setLineWrap(true);
        resultArea.setWrapStyleWord(true);
        add(new JScrollPane(resultArea), BorderLayout.CENTER);

        add(errorLabel, BorderLayout.SOUTH);

        recommendButton.addActionListener(e -> onRecommend());
        saveButton.addActionListener(e -> onSave());
    }

    private void onRecommend() {
        WeatherViewModel.State weatherState = weatherViewModel.getState();

        if (Double.isNaN(weatherState.temperature) || Double.isNaN(weatherState.windSpeed)) {
            errorLabel.setText("Please get weather data first.");
            return;
        }

        recommendController.recommend(
                weatherState.temperature,
                weatherState.isRaining,
                weatherState.windSpeed
        );

        refreshFromViewModel();
    }

    private void refreshFromViewModel() {
        if (recommendationViewModel.getError() != null) {
            errorLabel.setText(recommendationViewModel.getError());
            resultArea.setText("");
            saveButton.setEnabled(false);
            return;
        }

        errorLabel.setText("");
        StringBuilder sb = new StringBuilder();
        sb.append(recommendationViewModel.getTitle()).append("\n\n");

        for (String item : recommendationViewModel.getItems()) {
            sb.append("- ").append(item).append("\n");
        }
        sb.append("\nReason: ").append(recommendationViewModel.getRationale());
        resultArea.setText(sb.toString());

        saveButton.setEnabled(true);
    }

    private void onSave() {
        String name = JOptionPane.showInputDialog(this, "Enter outfit name:");
        if (name == null || name.trim().isEmpty()) {
            return;
        }

        String items = String.join(",", recommendationViewModel.getItems());
        String profile = recommendationViewModel.getRationale();
        String location = weatherViewModel.getState().cityName;

        saveOutfitController.save(name, items, profile, location, true);

        if (saveOutfitViewModel.getError() != null && !saveOutfitViewModel.getError().isEmpty()) {
            JOptionPane.showMessageDialog(this, saveOutfitViewModel.getError(), "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "Outfit saved!", "Success", JOptionPane.INFORMATION_MESSAGE);
        }
    }
}
