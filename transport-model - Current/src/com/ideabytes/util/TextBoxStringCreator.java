package com.ideabytes.util;

import com.ideabytes.Constants;
import com.ideabytes.transport.entities.Consignee;
import com.ideabytes.transport.entities.ShipmentType;
import com.ideabytes.transport.entities.Transport;
import com.ideabytes.transport.entities.TransportType;

import java.util.Date;

public class TextBoxStringCreator {
    private Transport transport;
    private Consignee consignee;

    /**
     * Constructor
     * @param transport Transport object from db
     * @param consignee Consignee object from db
     */
    public TextBoxStringCreator(Transport transport, Consignee consignee) {
        this.transport = transport;
        this.consignee = consignee;
    }

    /**
     * Builds string for each box
     * @param cellName Name of box
     * @return Built string
     */
    public String getStringForBox(String cellName) {
        StringBuilder lSb = new StringBuilder();
        switch (cellName) {
            case Constants.SHIPPER: {
                lSb.append(transport.getShipper().getName() + '\n')
                        .append(transport.getShipper().getAddress1() + '\n')
                        .append(transport.getShipper().getPostalCode() + '\n')
                        .append(transport.getShipper().getCity() + '\n')
                        .append(transport.getShipper().getCountry());
                break;
            }
            case Constants.CONSIGNEE: {
                lSb.append(consignee.getName() + '\n')
                        .append(consignee.getAddress1() + '\n')
                        .append(consignee.getPostalCode() + '\n')
                        .append(consignee.getCity() + '\n')
                        .append(consignee.getCountry() + '\n')
                        .append(consignee.getPhone());
                break;
            }
            case Constants.REFERENCE: {
                lSb.append(transport.getShipper().getId());
                break;
            }
            case Constants.WAYBILL: {
                lSb.append("15432");
                break;
            }
            case Constants.AIRDEPARTUE: {
                lSb.append(transport.getDepartureAirport());
                break;
            }
            case Constants.AIRDESTINATION: {
                lSb.append(transport.getDestinationAirport());
                break;
            }
            case Constants.ADDITIONAL: {
                lSb.append(transport.getAdditionalhandling());
                break;
            }
                case Constants.NAMES: {
                    lSb.append(transport.getSignatory());
                    break;
                }
            case Constants.DATE: {
                lSb.append(new Date().toLocaleString());
                break;
            }
            case Constants.SIGNATURE: {
                lSb.append(transport.getSignature());
                break;
            }
            case Constants.PAGEOF:
            case Constants.PAGENUM : {
                return "";
            }
            default: {
                return null;
            }

        }
        return lSb.toString();
    }

    /**
     * If input data is a line, sends data
     * @param cellName
     * @return name of line
     */
    public boolean shouldStrikeThrough(String cellName) {
        switch (cellName) {
            case Constants.LINE1:
            case Constants.LINE2:
            case Constants.LINE3: {
                return transport.getTransportType().equals(TransportType.CARGOONLY);
            }
            case Constants.LINE4:
            case Constants.LINE5:
            case Constants.LINE6: {
                return transport.getTransportType().equals(TransportType.PASENGERANDCARGO);
            }
            case Constants.RADLINE: {
                return transport.getShipmentType().equals(ShipmentType.RADIOACTIVE);
            }
            case Constants.RADLINE1: {
                return transport.getShipmentType().equals(ShipmentType.NONRADIOACTIVE);
            }

            default: {

            }
            return false;
        }
    }
    public boolean shouldPrintImage(String cellName) {
        switch (cellName) {
            case Constants.LINE1:
            case Constants.LINE2:
            case Constants.LINE3: {
                return transport.getTransportType().equals(TransportType.CARGOONLY);
            }
            case Constants.LINE4:
            case Constants.LINE5:
            case Constants.LINE6: {
                return transport.getTransportType().equals(TransportType.PASENGERANDCARGO);
            }
            case Constants.RADLINE: {
                return transport.getShipmentType().equals(ShipmentType.RADIOACTIVE);
            }
            case Constants.RADLINE1: {
                return transport.getShipmentType().equals(ShipmentType.NONRADIOACTIVE);
            }

            default: {

            }
            return false;
        }
    }

    /**
     * Gets string for column
     * @param cellName name of cell
     * @param rowNum row number
     * @return String data
     */
    public String getStringForColumn(String cellName, int rowNum) {
        StringBuilder lSb = new StringBuilder();
        switch (cellName) {
            case Constants.UNID: {
                lSb.append(transport.getGoods().get(rowNum).getUnid());
                break;
            }
            case Constants.SHIPPINGNAME: {
                lSb.append(transport.getGoods().get(rowNum).getShippingName());
                break;
            }
            case Constants.CLASS: {
                return transport.getGoods().get(rowNum).getClassType();
            }
            case Constants.PACKINGGRP: {
                return transport.getGoods().get(rowNum).getPackagingGroup();
            }
            case Constants.QUANTITY: {
                lSb.append(transport.getGoods().get(rowNum).getPackingQuantity());
                break;
            }
            case Constants.PACKING: {
                return transport.getGoods().get(rowNum).getPackinginstructions();
            }
            case Constants.AUTHORIZATION: {
                lSb.append(transport.getGoods().get(rowNum).getAuthorization());
                break;
            }
            default: {
            }
        }
        return lSb.toString();
    }
}