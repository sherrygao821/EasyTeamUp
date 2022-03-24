package com.example.easyteamup;

public class User {
    private int userId;
    private int gender;
    private int age;
    private boolean isStudent;
    private String userName;
    private String userPwd;
    private String firstName;
    private String lastName;

    public User(int userId, int gender, int age, boolean isStudent, String userName, String userPwd, String firstName, String lastName) {
        this.userId = userId;
        this.gender = gender;
        this.age = age;
        this.isStudent = isStudent;
        this.userName = userName;
        this.userPwd = userPwd;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    @Override
    public String toString() {
        return "Data{" +
                "userId=" + userId +
                ", gender=" + gender +
                ", age=" + age +
                ", isStudent=" + isStudent +
                ", userName='" + userName + '\'' +
                ", userPwd='" + userPwd + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                '}';
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public boolean isStudent() {
        return isStudent;
    }

    public void setStudent(boolean student) {
        isStudent = student;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPwd() {
        return userPwd;
    }

    public void setUserPwd(String userPwd) {
        this.userPwd = userPwd;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}



