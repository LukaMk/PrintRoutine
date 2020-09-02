package com.ideabytes.transport.entities;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity(name = "Transport")
@Table(name = "transport")

public class Transport {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="id")
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    private Shipper shipper;

    @Column(name="departure")
    private String departureAirport;
    @Enumerated(EnumType.STRING)
    @Column(name="transporttype", length = 16)
    private TransportType transportType;

    @Enumerated(EnumType.STRING)
    @Column(name="shipmenttype",length = 16)
    private ShipmentType shipmentType;

    @Column(name="destination")
    private String destinationAirport;

    @Column(name="additionalhandling")
    private String additionalhandling;

    @Column(name="signatory")
    private String signatory;

    @Column(name="place")
    private String place;

    @Column(name="date")
    private String date;

    @Column(name="signature")
    private String signature;

    @OneToMany(
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            mappedBy = "transport"
    )
    private List<TransportedGood> goods = new ArrayList<TransportedGood>();


    public Transport() {
    }

    public Transport(Shipper shipper, String departureAirport, TransportType transportType, ShipmentType shipmentType, String destinationAirport, String additionalhandling, String signatory, String place, String date, String signature) {
        this.shipper = shipper;
        this.departureAirport = departureAirport;
        this.transportType = transportType;
        this.shipmentType = shipmentType;
        this.destinationAirport = destinationAirport;
        this.additionalhandling = additionalhandling;
        this.signatory = signatory;
        this.place = place;
        this.date = date;
        this.signature = signature;
    }

    public Transport(Long id, Shipper shipper, String departureAirport, TransportType transportType, ShipmentType shipmentType, String destinationAirport, String additionalhandling, String signatory, String place, String date, String signature) {
        this.id = id;
        this.shipper = shipper;
        this.departureAirport = departureAirport;
        this.transportType = transportType;
        this.shipmentType = shipmentType;
        this.destinationAirport = destinationAirport;
        this.additionalhandling = additionalhandling;
        this.signatory = signatory;
        this.place = place;
        this.date = date;
        this.signature = signature;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Shipper getShipper() {
        return shipper;
    }

    public void setShipper(Shipper shipper) {
        this.shipper = shipper;
    }

    public String getDepartureAirport() {
        return departureAirport;
    }

    public void setDepartureAirport(String departureAirport) {
        this.departureAirport = departureAirport;
    }

    public TransportType getTransportType() {
        return transportType;
    }

    public void setTransportType(TransportType transportType) {
        this.transportType = transportType;
    }

    public ShipmentType getShipmentType() {
        return shipmentType;
    }

    public void setShipmentType(ShipmentType shipmentType) {
        this.shipmentType = shipmentType;
    }

    public String getDestinationAirport() {
        return destinationAirport;
    }

    public void setDestinationAirport(String destinationAirport) {
        this.destinationAirport = destinationAirport;
    }

    public String getAdditionalhandling() {
        return additionalhandling;
    }

    public void setAdditionalhandling(String additionalhandling) {
        this.additionalhandling = additionalhandling;
    }

    public String getSignatory() {
        return signatory;
    }

    public void setSignatory(String signatory) {
        this.signatory = signatory;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public List<TransportedGood> getGoods() {
        return goods;
    }

    public void setGoods(List<TransportedGood> goods) {
        this.goods = goods;
    }

    public void addGoods(TransportedGood good) {
        goods.add(good);
        good.setTransport(this);
    }

    public void removeGood(TransportedGood good) {
        goods.remove(good);
        good.setTransport(null);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Transport transport = (Transport) o;
        return Objects.equals(id, transport.id) &&
                Objects.equals(shipper, transport.shipper) &&
                Objects.equals(departureAirport, transport.departureAirport) &&
                transportType == transport.transportType &&
                shipmentType == transport.shipmentType &&
                Objects.equals(destinationAirport, transport.destinationAirport) &&
                Objects.equals(additionalhandling, transport.additionalhandling) &&
                Objects.equals(signatory, transport.signatory) &&
                Objects.equals(place, transport.place) &&
                Objects.equals(date, transport.date) &&
                Objects.equals(signature, transport.signature) &&
                Objects.equals(goods, transport.goods);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, shipper, departureAirport, transportType, shipmentType, destinationAirport, additionalhandling, signatory, place, date, signature, goods);
    }

    @Override
    public String toString() {
        return "Transport{" +
                "id=" + id +
                ", shipper=" + shipper +
                ", departureAirport='" + departureAirport + '\'' +
                ", transportType=" + transportType +
                ", shipmentType=" + shipmentType +
                ", destinationAirport='" + destinationAirport + '\'' +
                ", additionalhandling='" + additionalhandling + '\'' +
                ", signatory='" + signatory + '\'' +
                ", place='" + place + '\'' +
                ", date='" + date + '\'' +
                ", signature='" + signature + '\'' +
                ", goods=" + goods +
                '}';
    }
}

