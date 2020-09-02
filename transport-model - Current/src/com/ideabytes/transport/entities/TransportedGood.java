package com.ideabytes.transport.entities;

import javax.persistence.*;
import java.util.Objects;

@Entity(name = "transportgood")
@Table(name="transportgood")
public class TransportedGood {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="id")
    private int id;
    @Column(name="name")
    private String shippingName;

    @Column(name="class")
    private String classType;

    @Column(name="packinggroup")
    private String packagingGroup;

    @Column(name="quantity")
    private int packingQuantity;

    @Column(name="unid")
    private String unid;

    @Column(name="authorization")
    private String authorization;

    @Column(name="packinginstructions")
    private String packinginstructions;

    @ManyToOne(fetch = FetchType.LAZY)
    private Transport transport;

    public TransportedGood() {
    }

    public TransportedGood(String shippingName, String classType, String packagingGroup, int packingQuantity, String unid, String authorization, String packinginstructions) {
        this.shippingName = shippingName;
        this.classType = classType;
        this.packagingGroup = packagingGroup;
        this.packingQuantity = packingQuantity;
        this.unid = unid;
        this.authorization = authorization;
        this.packinginstructions = packinginstructions;
    }

    public TransportedGood(int id, String shippingName, String classType, String packagingGroup, int packingQuantity, Transport transport) {
        this.id = id;
        this.shippingName = shippingName;
        this.classType = classType;
        this.packagingGroup = packagingGroup;
        this.packingQuantity = packingQuantity;
        this.transport = transport;
    }

    public TransportedGood(String shippingName, String classType, String packagingGroup, int packingQuantity, String unid, String authorization, String packinginstructions, Transport transport) {
        this.shippingName = shippingName;
        this.classType = classType;
        this.packagingGroup = packagingGroup;
        this.packingQuantity = packingQuantity;
        this.unid = unid;
        this.authorization = authorization;
        this.packinginstructions = packinginstructions;
        this.transport = transport;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getShippingName() {
        return shippingName;
    }

    public void setShippingName(String shippingName) {
        this.shippingName = shippingName;
    }

    public String getClassType() {
        return classType;
    }

    public void setClassType(String classType) {
        this.classType = classType;
    }

    public String getPackagingGroup() {
        return packagingGroup;
    }

    public void setPackagingGroup(String packagingGroup) {
        this.packagingGroup = packagingGroup;
    }

    public int getPackingQuantity() {
        return packingQuantity;
    }

    public void setPackingQuantity(int packingQuantity) {
        this.packingQuantity = packingQuantity;
    }

    public Transport getTransport() {
        return transport;
    }

    public void setTransport(Transport transport) {
        this.transport = transport;
    }

    public String getUnid() {
        return unid;
    }

    public void setUnid(String unid) {
        this.unid = unid;
    }

    public String getAuthorization() {
        return authorization;
    }

    public void setAuthorization(String authorization) {
        this.authorization = authorization;
    }

    public String getPackinginstructions() {
        return packinginstructions;
    }

    public void setPackinginstructions(String packinginstructions) {
        this.packinginstructions = packinginstructions;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TransportedGood that = (TransportedGood) o;
        return id == that.id &&
                packingQuantity == that.packingQuantity &&
                Objects.equals(shippingName, that.shippingName) &&
                Objects.equals(classType, that.classType) &&
                Objects.equals(packagingGroup, that.packagingGroup) &&
                Objects.equals(unid, that.unid) &&
                Objects.equals(authorization, that.authorization) &&
                Objects.equals(packinginstructions, that.packinginstructions) &&
                Objects.equals(transport, that.transport);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, shippingName, classType, packagingGroup, packingQuantity, unid, authorization, packinginstructions, transport);
    }

    @Override
    public String toString() {
        return "TransportedGood{" +
                "id=" + id +
                ", shippingName='" + shippingName + '\'' +
                ", classType='" + classType + '\'' +
                ", packagingGroup='" + packagingGroup + '\'' +
                ", packingQuantity=" + packingQuantity +
                ", unid='" + unid + '\'' +
                ", authorization='" + authorization + '\'' +
                ", packinginstructions='" + packinginstructions +
                '}';
    }
}
