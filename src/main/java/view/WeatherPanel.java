package view;

import interface_adapters.weather.GetWeatherController;
import interface_adapters.weather.WeatherPresenter;
import interface_adapters.weather.WeatherViewModel;
import use_case.GetWeatherInputBoundary;
import use_case.GetWeatherInteractor;
import data_access.OpenMeteoWeatherDataGateway;
import external.OpenMeteoAPI;
import external.WeatherService;
import use_case.WeatherDataGateway;

import javax.swing.*;
import java.awt.*;

/**
 * UC1 GUI panel using Clean Architecture.
 * View -> Controller -> Interactor -> Presenter -> ViewModel -> View
 */
public class WeatherPanel extends JPanel {

    private final JTextField cityField = new JTextField(20);
    private final JButton getWeatherButton = new JButton("Get Weather");
    private final JTextArea outputArea = new JTextArea(8, 35);

    private final GetWeatherController controller;
    private final WeatherViewModel viewModel;

    public WeatherPanel(GetWeatherController controller, WeatherViewModel viewModel) {
        this.controller = controller;
        this.viewModel = viewModel;

        setLayout(new BorderLayout(10, 10));
        setPreferredSize(new Dimension(500, 400));

        JPanel inputPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        inputPanel.add(new JLabel("City:"));
        inputPanel.add(cityField);
        inputPanel.add(getWeatherButton);

        add(inputPanel, BorderLayout.NORTH);

        // --- Center: output area ---
        outputArea.setEditable(false);
        outputArea.setLineWrap(true);
        outputArea.setWrapStyleWord(true);
        JScrollPane scrollPane = new JScrollPane(outputArea);
        add(scrollPane, BorderLayout.CENTER);

        // Button behaviour
        getWeatherButton.addActionListener(e -> onGetWeather());
    }

    private void onGetWeather() {
        String city = cityField.getText().trim();
        if (city.isEmpty()) {
            JOptionPane.showMessageDialog(
                    this,
                    "Please enter a city name.",
                    "Input error",
                    JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        String countryCode = "CA";
        double lat = 0.0;
        double lon = 0.0;

        // 1) Call the use case through the controller
        controller.execute(city, countryCode, lat, lon);

        // 2) Read state set by the presenter in the ViewModel
        WeatherViewModel.State s = viewModel.getState();

        if (!s.errorMessage.isEmpty()) {
            JOptionPane.showMessageDialog(
                    this,
                    s.errorMessage,
                    "City not found",
                    JOptionPane.ERROR_MESSAGE
            );
            outputArea.setText("");
            return;
        }

        StringBuilder sb = new StringBuilder();
        sb.append("City: ").append(s.cityName).append("\n");
        sb.append("Temperature: ").append(s.temperatureText).append("\n");
        sb.append("Feels like: ").append(s.feelsLikeText).append("\n");
        sb.append("Humidity: ").append(s.humidityText).append("\n");
        sb.append("Wind speed: ").append(s.windSpeedText).append("\n");
        sb.append("Condition: ").append(s.descriptionText).append("\n");

        outputArea.setText(sb.toString());
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {

            WeatherService service = new OpenMeteoAPI();

            WeatherDataGateway gateway = new OpenMeteoWeatherDataGateway(service);

            // ViewModel
            WeatherViewModel viewModel = new WeatherViewModel();

            // Presenter
            WeatherPresenter presenter = new WeatherPresenter(viewModel);

            // Interactor (use case)
            GetWeatherInputBoundary interactor =
                    new GetWeatherInteractor(gateway, presenter);

            // Controller
            GetWeatherController controller =
                    new GetWeatherController(interactor);

            // --- Swing View ---
            JFrame frame = new JFrame("Weather2Wear - UC1 Demo");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setContentPane(new WeatherPanel(controller, viewModel));
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}

