package logica;

public class Parada {
    private String busId;
    private String timestamp;
    private double latitude;
    private double longitude;

    public Parada(String busId, String timestamp, double latitude, double longitude) {
        this.busId = busId;
        this.timestamp = timestamp;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    // Getters
    public String getBusId() { return busId; }
    public String getTimestamp() { return timestamp; }
    public double getLatitude() { return latitude; }
    public double getLongitude() { return longitude; }

    @Override
    public String toString() {
        return String.format("Parada{busId=%s, timestamp=%s, lat=%.6f, lon=%.6f}", busId, timestamp, latitude, longitude);
    }
}