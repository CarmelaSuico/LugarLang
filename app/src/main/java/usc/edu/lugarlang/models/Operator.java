package usc.edu.lugarlang.models;

public class Operator {
    private String operatorId;
    private String name;
    private String email;
    private String phoneNumber;
    private String role;
    private String franchiseNumber;
    private String assignedRoutes;

    // Empty constructor required for Firebase
    public Operator() {}

    // Getters and Setters
    public String getOperatorId() {
        return operatorId;
    }

    public void setOperatorId(String operatorId) {
        this.operatorId = operatorId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getFranchiseNumber() {
        return franchiseNumber;
    }

    public void setFranchiseNumber(String franchiseNumber) {
        this.franchiseNumber = franchiseNumber;
    }

    public String getAssignedRoutes() {
        return assignedRoutes;
    }

    public void setAssignedRoutes(String assignedRoutes) {
        this.assignedRoutes = assignedRoutes;
    }
}