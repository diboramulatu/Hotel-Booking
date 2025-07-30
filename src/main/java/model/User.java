package model;

public abstract class User {
    public int userId;
    public String name;
    public String email;

    public abstract void showMenu();

    public void updateProfile() {
        System.out.println("Updating profile...");
    }

    // Getters and Setters
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
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
}
