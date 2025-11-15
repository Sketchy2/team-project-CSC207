package view;
import interface_adapters.RecommendOutfitController;
import interface_adapters.RecommendationViewModel;
import javax.swing.*;
import java.awt.*;


public class RecommendationPanel extends JPanel {
    private final JTextField tempField = new JTextField(5);
    private final JCheckBox rainCheck = new JCheckBox("Raining");
    private final JTextField windField = new JTextField(5);
    private final JButton recommendButton = new JButton(" Recommend");
    private final JTextArea resultArea = new JTextArea(8, 30);
    private final JLabel errorLabel = new JLabel(" ");
    private final RecommendOutfitController controller;
    private final RecommendationViewModel vm;

    public RecommendationPanel(RecommendOutfitController controller,
                               RecommendationViewModel vm) {

        this.controller = controller;
        this.vm = vm;

        setLayout(new BorderLayout(10, 10));
        JPanel inputPanel = new JPanel();
        inputPanel.add(new JLabel("Temperature (°C):"));
        inputPanel.add(tempField);
        inputPanel.add(rainCheck);
        inputPanel.add(new JLabel("Wind (km/h):"));
        inputPanel.add(windField);
        inputPanel.add(recommendButton);
        add(inputPanel, BorderLayout.NORTH);
        add(new JScrollPane(resultArea), BorderLayout.CENTER);
        add(errorLabel, BorderLayout.SOUTH);

        recommendButton.addActionListener(e -> {
            try {
                double temp = Double.parseDouble(tempField.getText());
                boolean raining = rainCheck.isSelected();
                double wind = Double.parseDouble(windField.getText());

                controller.recommend(temp, raining, wind);
                refreshFromViewModel();
            } catch (NumberFormatException ex) {
                errorLabel.setText("Please enter valid numbers.");
            }
        });
    }

    private void refreshFromViewModel() {
        if (vm.getError() != null) {
            errorLabel.setText(vm.getError());
            resultArea.setText("");
            return;
        }
        errorLabel.setText("");
        StringBuilder sb = new StringBuilder();
        sb.append(vm.getTitle()).append("\n\n");

        for (String item : vm.getItems()) {
            sb.append("- ").append(item).append("\n");
        }
        sb.append("\nReason: ").append(vm.getRationale());
        resultArea.setText(sb.toString());
    }
}
