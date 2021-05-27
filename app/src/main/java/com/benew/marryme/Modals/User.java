package com.benew.marryme.Modals;

public class User {

    private String mail, name, adress, city, birthday, country, birthplace, gender, userID, profile_picture, work_answer, work_place, bio;

    public User() {}

    public User(String mail, String name, String adress, String city, String birthday, String country, String birthplace, String gender, String userID, String profile_picture, String work_answer, String work_place, String bio) {
        this.mail = mail;
        this.name = name;
        this.adress = adress;
        this.city = city;
        this.birthday = birthday;
        this.country = country;
        this.birthplace = birthplace;
        this.gender = gender;
        this.userID = userID;
        this.profile_picture = profile_picture;
        this.work_answer = work_answer;
        this.work_place = work_place;
        this.bio = bio;
    }

    public String getWork_answer() {
        return work_answer;
    }

    public void setWork_answer(String work_answer) {
        this.work_answer = work_answer;
    }

    public String getWork_place() {
        return work_place;
    }

    public void setWork_place(String work_place) {
        this.work_place = work_place;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getProfile_picture() {
        return profile_picture;
    }

    public void setProfile_picture(String profile_picture) {
        this.profile_picture = profile_picture;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getBirthplace() {
        return birthplace;
    }

    public void setBirthplace(String birthplace) {
        this.birthplace = birthplace;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}
