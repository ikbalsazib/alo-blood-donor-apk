package com.aloblooddonor.bd;

public class UserInfo {
    public String email, name, phone, blood, age, weight, address;

    public UserInfo() {
    }

    public UserInfo(String email, String name, String phone, String blood, String age, String weight, String address) {
        this.email = email;
        this.name = name;
        this.phone = phone;
        this.blood = blood;
        this.age = age;
        this.weight = weight;
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }


    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getBlood() {
        return blood;
    }

    public void setBlood(String blood) {
        this.blood = blood;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
