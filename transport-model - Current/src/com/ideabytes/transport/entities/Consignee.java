package com.ideabytes.transport.entities;

import javax.persistence.*;
import java.util.Objects;

@Entity(name = "Consignee")
@Table(name="consignee")
public class Consignee {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private int id;
    @Column(name = "name")
    private String name;
    @Column(name = "address1")
    private String address1;
    @Column(name = "address2")
    private String address2;
    @Column(name = "city")
    private String city;
    @Column(name = "state")
    private String state;
    @Column(name = "postalcode")
    private String postalCode;
    @Column(name = "country")
    private String country;
    @Column(name = "phone")
    private String phone;
    @Column(name = "image")
    private String imageFile;

    public Consignee() {
    }

    public Consignee(String name, String address1, String address2, String city, String state, String postalCode, String country, String phone, String image) {
        this.name = name;
        this.address1 = address1;
        this.address2 = address2;
        this.city = city;
        this.state = state;
        this.postalCode = postalCode;
        this.country = country;
        this.phone = phone;
        this.imageFile = image;
    }

    public Consignee(int id, String name, String address1, String address2, String city, String state, String postalCode, String country, String phone, String image) {
        this.id = id;
        this.name = name;
        this.address1 = address1;
        this.address2 = address2;
        this.city = city;
        this.state = state;
        this.postalCode = postalCode;
        this.country = country;
        this.phone = phone;
        this.imageFile = image;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress1() {
        return address1;
    }

    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    public String getAddress2() {
        return address2;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getImageFile() {
        return imageFile;
    }

    public void setImageFile(String imageFile) {
        this.imageFile = imageFile;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Consignee consignee = (Consignee) o;
        return id == consignee.id &&
                Objects.equals(name, consignee.name) &&
                Objects.equals(address1, consignee.address1) &&
                Objects.equals(address2, consignee.address2) &&
                Objects.equals(city, consignee.city) &&
                Objects.equals(state, consignee.state) &&
                Objects.equals(postalCode, consignee.postalCode) &&
                Objects.equals(country, consignee.country) &&
                Objects.equals(phone, consignee.phone) &&
                Objects.equals(imageFile, consignee.imageFile);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, address1, address2, city, state, postalCode, country, phone, imageFile);
    }

    @Override
    public String toString() {
        return "Consignee{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", address1='" + address1 + '\'' +
                ", address2='" + address2 + '\'' +
                ", city='" + city + '\'' +
                ", state='" + state + '\'' +
                ", postalCode='" + postalCode + '\'' +
                ", country='" + country + '\'' +
                ", phone='" + phone + '\'' +
                ", imageFile='" + imageFile + '\'' +
                '}';
    }
}

