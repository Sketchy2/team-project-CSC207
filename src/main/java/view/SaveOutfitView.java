package view;

import entities.Outfit;
import interface_adapters.SaveOutfitController;
import interface_adapters.SaveOutfitViewModel;

import javax.swing.*;
import java.awt.*;

/**
 * Swing UI for saving outfits.
 */
public class SaveOutfitView extends JPanel {

    public SaveOutfitView(SaveOutfitController controller,
                          SaveOutfitViewModel viewModel) {

        setLayout(new BorderLayout());

        JTextField name = new JTextField(15);
        JTextField items = new JTextField(25);
        JTextField weather = new JTextField(15);
        JTextField location = new JTextField(15);
        JCheckBox overwrite = new JCheckBox("Overwrite existing");

        JTextArea output = new JTextArea(10, 40);
        output.setEditable(false);

        JButton saveBtn = new JButton("Save");
        saveBtn.addActionListener(e -> {
            controller.save(
                    name.getText(),
                    items.getText(),
                    weather.getText(),
                    location.getText(),
                    overwrite.isSelected()
            );

            // Refresh UI
            output.setText("");
            if (!viewModel.getError().isEmpty()) {
                output.setText("Error: " + viewModel.getError());
                return;
            }

            output.append(viewModel.getMessage() + "\n\n");
            for (Outfit o : viewModel.getOutfits()) {
                output.append(o.getName() + " | " +
                        o.getWeatherProfile() + " | " +
                        o.getLocation() + " | " +
                        o.getItems() + "\n");
            }
        });

        JPanel form = new JPanel(new GridLayout(5, 2));
        form.add(new JLabel("Name:")); form.add(name);
        form.add(new JLabel("Items:")); form.add(items);
        form.add(new JLabel("Weather:")); form.add(weather);
        form.add(new JLabel("Location:")); form.add(location);
        form.add(new JLabel("")); form.add(overwrite);

        add(form, BorderLayout.NORTH);
        add(saveBtn, BorderLayout.CENTER);
        add(new JScrollPane(output), BorderLayout.SOUTH);
    }
}
