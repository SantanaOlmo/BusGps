package logica;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


public class GPSData {
    private String busId;
    private String timestamp;
    private double latitude;
    private double longitude;
    private double speed;


    public GPSData(String busId, String timestamp, double latitude, double longitude, double speed) {
        this.busId = busId;
        this.timestamp = timestamp;
        this.latitude = latitude;
        this.longitude = longitude;
        this.speed = speed;
    }


    public String getBusId() { return busId; }
    public String getTimestamp() { return timestamp; }
    public double getLatitude() { return latitude; }
    public double getLongitude() { return longitude; }
    public double getSpeed() { return speed; }

    @Override
    public String toString() {
        return String.format("%s | %s | %.6f | %.6f | %.2f km/h", busId, timestamp, latitude, longitude, speed);
    }
}