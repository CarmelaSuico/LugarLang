package usc.edu.lugarlang.models;

public class Trip {
    private String tripId;
    private String startPoint;
    private String endPoint;
    private String plateNumber;
    private String assignedDriver;
    private String assignedConductor;
    private String contactInfo;
    private String vehicleAssignment;
    private String maintenanceStatus;
    private String tripStatus;
    private String departureTime;
    private String busCode;
    private String franchise;
    private String terminalOrigin;
    private String destination;
    private String estimatedArrivalTime;
    private String routeCode;

    // Empty constructor required for Firebase
    public Trip() {}

    // Constructor with required fields
    public Trip(String startPoint, String endPoint, String plateNumber,
                String assignedDriver, String assignedConductor, String contactInfo,
                String vehicleAssignment, String maintenanceStatus,
                String tripStatus, String departureTime) {
        this.startPoint = startPoint;
        this.endPoint = endPoint;
        this.plateNumber = plateNumber;
        this.assignedDriver = assignedDriver;
        this.assignedConductor = assignedConductor;
        this.contactInfo = contactInfo;
        this.vehicleAssignment = vehicleAssignment;
        this.maintenanceStatus = maintenanceStatus;
        this.tripStatus = tripStatus;
        this.departureTime = departureTime;
    }

    // Getters and Setters
    public String getTripId() {
        return tripId;
    }

    public void setTripId(String tripId) {
        this.tripId = tripId;
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

    public String getPlateNumber() {
        return plateNumber;
    }

    public void setPlateNumber(String plateNumber) {
        this.plateNumber = plateNumber;
    }

    public String getAssignedDriver() {
        return assignedDriver;
    }

    public void setAssignedDriver(String assignedDriver) {
        this.assignedDriver = assignedDriver;
    }

    public String getAssignedConductor() {
        return assignedConductor;
    }

    public void setAssignedConductor(String assignedConductor) {
        this.assignedConductor = assignedConductor;
    }

    public String getContactInfo() {
        return contactInfo;
    }

    public void setContactInfo(String contactInfo) {
        this.contactInfo = contactInfo;
    }

    public String getVehicleAssignment() {
        return vehicleAssignment;
    }

    public void setVehicleAssignment(String vehicleAssignment) {
        this.vehicleAssignment = vehicleAssignment;
    }

    public String getMaintenanceStatus() {
        return maintenanceStatus;
    }

    public void setMaintenanceStatus(String maintenanceStatus) {
        this.maintenanceStatus = maintenanceStatus;
    }

    public String getTripStatus() {
        return tripStatus;
    }

    public void setTripStatus(String tripStatus) {
        this.tripStatus = tripStatus;
    }

    public String getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(String departureTime) {
        this.departureTime = departureTime;
    }

    public String getBusCode() {
        return busCode;
    }

    public void setBusCode(String busCode) {
        this.busCode = busCode;
    }

    public String getFranchise() {
        return franchise;
    }

    public void setFranchise(String franchise) {
        this.franchise = franchise;
    }

    public String getTerminalOrigin() {
        return terminalOrigin;
    }

    public void setTerminalOrigin(String terminalOrigin) {
        this.terminalOrigin = terminalOrigin;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getEstimatedArrivalTime() {
        return estimatedArrivalTime;
    }

    public void setEstimatedArrivalTime(String estimatedArrivalTime) {
        this.estimatedArrivalTime = estimatedArrivalTime;
    }

    public String getRouteCode() {
        return routeCode;
    }

    public void setRouteCode(String routeCode) {
        this.routeCode = routeCode;
    }
}