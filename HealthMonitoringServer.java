package hm;

import fi.iki.elonen.NanoHTTPD;
import java.io.IOException;

public class HealthMonitoringServer extends NanoHTTPD {

    // Server port
    private static final int PORT = 8080;

    // Constructor
    public HealthMonitoringServer() throws IOException {
        super(PORT);
        start(SOCKET_READ_TIMEOUT, false);
        System.out.println("Server started on port " + PORT);
    }

    // Main method to start the server
    public static void main(String[] args) {
        try {
            new HealthMonitoringServer();
        } catch (IOException e) {
            System.err.println("Could not start server: " + e);
        }
    }

    // Handle HTTP requests	
    @Override
    public Response serve(IHTTPSession session) {
        if (Method.GET.equals(session.getMethod())) {
            // Placeholder values for demonstration
            float temperature = 25.0f;
            float humidity = 60.0f;
            float BPM = 70.0f;
            float SpO2 = 98.0f;
            float bodyTemperature = 36.5f;
            return newFixedLengthResponse(Response.Status.OK, "text/html", sendHTML(temperature, humidity, BPM, SpO2, bodyTemperature));
        }
        return newFixedLengthResponse(Response.Status.NOT_FOUND, "text/plain", "Not found");
    }

    // Generate HTML content
    private String sendHTML(float temperature, float humidity, float BPM, float SpO2, float bodyTemperature) {
    
        // Build the HTML response
        return "<!DOCTYPE html>" +
               "<html lang='en'>" +
               "<head>" +
               "<meta charset='UTF-8'>" +
               "<meta name='viewport' content='width=device-width, initial-scale=1.0'>" +
               "<title>ESP32 Patient Health Monitoring</title>" +
               "<link href='https://fonts.googleapis.com/css?family=Open+Sans:300,400,600' rel='stylesheet'>" +
               "<style>" +
               "body { font-family: 'Open Sans', sans-serif; margin: 0; padding: 0; background-color: #f8f9fa; text-align: center; }" +
               "h1 { margin: 20px; color: #333; }" +
               ".container { display: flex; flex-wrap: wrap; justify-content: center; padding: 20px; }" +
               ".data { background: #ffffff; border-radius: 8px; box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2); margin: 10px; padding: 20px; width: 220px; text-align: center; }" +
               ".data .icon { margin-bottom: 10px; }" +
               ".data .icon svg { width: 50px; height: 50px; }" +
               ".label { font-size: 18px; font-weight: bold; color: #555; margin-bottom: 10px; }" +
               ".value { font-size: 36px; font-weight: 300; color: #333; }" +
               ".value .unit { font-size: 24px; vertical-align: super; }" +
               "</style>" +
               "</head>" +
               "<body>" +
               "<h1>ESP32 Patient Health Monitoring</h1>" +
               "<div class='container'>" +

               "<div class='data'>" +
               "<div class='icon'>" +
               "<svg xmlns='http://www.w3.org/2000/svg' viewBox='0 0 24 24' fill='none'><path d='M13 2H11V8H13V2ZM13 16H11V14H13V16ZM13 12H11V10H13V12Z' fill='#F29C1F'/></svg>" +
               "</div>" +
               "<div class='label'>Temperature</div>" +
               "<div class='value'>" + formatValue(temperature, "<span class='unit'>°C</span>") + "</div>" +
               "</div>" +

               "<div class='data'>" +
               "<div class='icon'>" +
               "<svg xmlns='http://www.w3.org/2000/svg' viewBox='0 0 24 24' fill='none'><path d='M2 12H22V13H2V12ZM2 10H22V11H2V10ZM2 8H22V9H2V8ZM2 6H22V7H2V6Z' fill='#3B97D3'/></svg>" +
               "</div>" +
               "<div class='label'>Humidity</div>" +
               "<div class='value'>" + formatValue(humidity, "<span class='unit'>%</span>") + "</div>" +
               "</div>" +

               "<div class='data'>" +
               "<div class='icon'>" +
               "<svg xmlns='http://www.w3.org/2000/svg' viewBox='0 0 24 24' fill='none'><path d='M12 4V20M4 12L12 4L20 12' stroke='#FF0000' stroke-width='2' stroke-linecap='round' stroke-linejoin='round'/></svg>" +
               "</div>" +
               "<div class='label'>BPM</div>" +
               "<div class='value'>" + formatValue(BPM, "") + "</div>" +
               "</div>" +

               "<div class='data'>" +
               "<div class='icon'>" +
               "<svg xmlns='http://www.w3.org/2000/svg' viewBox='0 0 24 24' fill='none'><path d='M6 12H18M6 16H18M6 8H18M6 4H18M2 12H4M2 16H4M2 8H4M2 4H4M20 12H22M20 16H22M20 8H22M20 4H22' stroke='#955BA5' stroke-width='2' stroke-linecap='round' stroke-linejoin='round'/></svg>" +
               "</div>" +
               "<div class='label'>SpO2</div>" +
               "<div class='value'>" + formatValue(SpO2, "<span class='unit'>%</span>") + "</div>" +
               "</div>" +

               "<div class='data'>" +
               "<div class='icon'>" +
               "<svg xmlns='http://www.w3.org/2000/svg' viewBox='0 0 24 24' fill='none'><path d='M12 5C9.79 5 8 6.79 8 9C8 11.21 9.79 13 12 13C14.21 13 16 11.21 16 9C16 6.79 14.21 5 12 5ZM12 15C9.39 15 6.98 16.58 6.05 18.84L6 20H18L17.95 18.84C17.02 16.58 14.61 15 12 15Z' fill='#F29C1F'/></svg>" +
               "</div>" +
               "<div class='label'>Body Temperature</div>" +
               "<div class='value'>" + formatValue(bodyTemperature, "<span class='unit'>°C</span>") + "</div>" +
               "</div>" +

               "</div>" +
               "</body>" +
               "</html>";
    }
    private String formatValue(float value, String unit) {
        if (Float.isNaN(value)) {
            return "Error";
        }
        return String.format("%.1f%s", value, unit);
    }

}
