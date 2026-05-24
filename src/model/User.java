package model;

public class User {
    private int id;
    private String fullName;
    private String employeeId;
    private String email;
    private boolean isAdmin;  // new

    public User(int id, String fullName, String employeeId, String email, boolean isAdmin) {
        this.id = id;
        this.fullName = fullName;
        this.employeeId = employeeId;
        this.email = email;
        this.isAdmin = isAdmin;
    }

    // Getters and setters
    public int getId() { return id; }
    public String getFullName() { return fullName; }
    public String getEmployeeId() { return employeeId; }
    public String getEmail() { return email; }
    public boolean isAdmin() { return isAdmin; }
    public void setFullName(String fullName) { this.fullName = fullName; }
    public void setEmail(String email) { this.email = email; }
    public void setAdmin(boolean admin) { isAdmin = admin; }
}