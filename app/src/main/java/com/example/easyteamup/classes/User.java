package com.example.easyteamup.classes;


/**
 * Author: Andy Chen
 *
 * Instruction: User user = new User(firstname, lastname, email, age, isStudent, Email, password)
 *
 * NOTE: remember to call setUserId() with ID from DB after create new User and store in DB
 */
public class User {
    //Upload photo functionality not delivered
    private String photo;
    private int userId;
    private int age;
    private boolean isStudent;
    private String Email;
    private String userPwd;
    private String firstName;
    private String lastName;

    public User() {}

    public User(String email, String userPwd) {
        this.Email = email;
        this.userPwd = userPwd;
        this.firstName = "firstName";
        this.lastName = "lastName";
    }

    public User(int age, boolean isStudent, String Email, String userPwd, String firstName, String lastName) {
        this.age = age;
        this.isStudent = isStudent;
        this.Email = Email;
        this.userPwd = userPwd;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", age=" + age +
                ", isStudent=" + isStudent +
                ", Email='" + Email + '\'' +
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

    public int getAge() {
        return age;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public boolean getStudent() {
        return isStudent;
    }

    public void setStudent(boolean student) {
        isStudent = student;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String Email) {
        this.Email = Email;
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



