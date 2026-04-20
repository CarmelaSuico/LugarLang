package usc.edu.lugarlang.models;

public class Route {
    private String routeId;
    private String routeCode;
    private String startPoint;
    private String endPoint;
    private String distance;
    private String estimatedDuration;
    private String fare;
    private String status; // Active, Inactive
    private String assignedBus;

    // Empty constructor required for Firebase
    public Route() {}

    // Constructor with parameters
    public Route(String routeCode, String startPoint, String endPoint,
                 String distance, String estimatedDuration, String fare) {
        this.routeCode = routeCode;
        this.startPoint = startPoint;
        this.endPoint = endPoint;
        this.distance = distance;
        this.estimatedDuration = estimatedDuration;
        this.fare = fare;
        this.status = "Active";
    }

    // Getters and Setters
    public String getRouteId() {
        return routeId;
    }

    public void setRouteId(String routeId) {
        this.routeId = routeId;
    }

    public String getRouteCode() {
        return routeCode;
    }

    public void setRouteCode(String routeCode) {
        this.routeCode = routeCode;
    }

    public String getStartPoint() {
        return startPoint;
    }

    public void setStartPoint(String startPoint) {
        this.startPoint = startPoint;
    }

    public String getEndPoint() {
        return endPoint;
    }

    public void setEndPoint(String endPoint) {
        this.endPoint = endPoint;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public String getEstimatedDuration() {
        return estimatedDuration;
    }

    public void setEstimatedDuration(String estimatedDuration) {
        this.estimatedDuration = estimatedDuration;
    }

    public String getFare() {
        return fare;
    }

    public void setFare(String fare) {
        this.fare = fare;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAssignedBus() {
        return assignedBus;
    }

    public void setAssignedBus(String assignedBus) {
        this.assignedBus = assignedBus;
    }
}