package com.ideabytes.util;

import com.ideabytes.Constants;
import com.ideabytes.transport.entities.*;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import javax.persistence.Query;
import java.util.List;

public class DBHandler {

    private static SessionFactory sessionFactory;

    public static final String DEFAULT_TEMPLATE = Constants.DEFAULT_TEMPLATE;

    public DBHandler() {
        createSessionFactory();
    }

    // A SessionFactory is set up once for an application

    public  void createSessionFactory() {

        try {

            Configuration configuration = new Configuration();
            configuration.addAnnotatedClass(Shipper.class);
            configuration.addAnnotatedClass(Transport.class);
            configuration.addAnnotatedClass(TransportedGood.class);
            configuration.addAnnotatedClass(Consignee.class);
            configuration.addAnnotatedClass(TextCell.class);
            configuration.addAnnotatedClass(TemplateDataRow.class);

            configuration.configure();
            sessionFactory = configuration.buildSessionFactory();

            createTestShipments();
            createTextCellPlacements();
            getShipmentData();
            createShipperDB();
        } catch (
                HibernateException exception) {
            System.out.println("Problem creating session factory");
            exception.printStackTrace();
        }

    }

    public  void createTestShipments() {

        TransportedGood g1 = new TransportedGood("Litium Ion Battery", "9", "11", 200, "UN234", "", "Y341") ;
        TransportedGood g2 = new TransportedGood("Paint", "5", "23", 11, "ID800", "", "Y500");
        TransportedGood g3 = new TransportedGood("Cleaner", "7", "222", 155, "UN800", "", "Y333");
        TransportedGood g4 = new TransportedGood("Washer", "7", "22", 10, "ID800", "", "Y888");
        TransportedGood g5 = new TransportedGood("Explosive", "3", "11", 10, "ID800", "", "Y500");

        TransportedGood g6 = new TransportedGood("Sanitizer", "9", "11", 200, "UN234", "", "Y341") ;
        TransportedGood g7 = new TransportedGood("Potion", "5", "23", 11, "ID800", "", "Y500");
        TransportedGood g8 = new TransportedGood("Ethanol", "7", "222", 155, "UN800", "", "Y333");
        TransportedGood g9 = new TransportedGood("Peroxide", "7", "22", 10, "ID800", "", "Y888");
        TransportedGood g10 = new TransportedGood("Desinfectant", "3", "11", 10, "ID800", "", "Y500");
        TransportedGood g11 = new TransportedGood("Fertilizer", "3", "11", 10, "ID800", "", "Y500");




        Shipper sh1 = new Shipper("George K", "11 Street1", null, "Bridgestone", "New State", "123456", "Country1", "123-4546-7890");
        Shipper sh2 = new Shipper("Luka Mac", "22 Street2", null, "Chicago", "New State2", "123456", "Country2", "098-765-4321");


        Transport t1 = new Transport(sh1, "Ottawa", TransportType.PASENGERANDCARGO, ShipmentType.RADIOACTIVE, "Kuala Lumpur", "Handle with care", "George", "Nepean", "July 22 2020", Constants.SIGNITURE_PATH);
        t1.addGoods(g1);
        t1.addGoods(g2);
        t1.addGoods(g3);

        t1.addGoods(g6);
        t1.addGoods(g7);
        t1.addGoods(g8);
        t1.addGoods(g9);
        t1.addGoods(g10);
        t1.addGoods(g11);
        t1.addGoods(g4);
        t1.addGoods(g5);


        Transport t2 = new Transport(sh2, "Hyderabad", TransportType.CARGOONLY, ShipmentType.NONRADIOACTIVE, "Ottawa", "Handle with Care", "Luka", "Hyderabad", "September 20 2020", "Luka M.");


        Consignee cons = new Consignee("IdeaBytes", "142 GolfLinks", null, "Nepean", "New State", "123456", "Country1", "123-4546-7890", "IdeaBytesLogo.jpg");

        try {
            Session session = sessionFactory.openSession();
            session.beginTransaction();
            session.save(cons);
            session.save(g1);
            session.save(g2);
            session.save(g3);
            session.save(g4);
            session.save(g5);

            session.save(g6);
            session.save(g7);
            session.save(g8);
            session.save(g9);
            session.save(g10);
            session.save(g11);


            session.save(t1);
            session.save(t2);
            session.save(sh1);
            session.save(sh2);

            session.getTransaction().commit();
            session.close();

        } catch (Exception e) {
            System.out.println("Problem creating test data");
            e.printStackTrace();
        }
    }

    // Test method to demonstrate read from DB and getting the entities
    public void getShipmentData() {
        try {
            Session session = sessionFactory.openSession();
            session.beginTransaction();

            Query lQuery = session.createQuery("SELECT trans FROM Transport trans", Transport.class);
            List lResult = lQuery.getResultList();
            int count = 0;
            if (lResult != null && !lResult.isEmpty()) {
                for (Object lR : lResult) {
                    Transport transport = (Transport) lR;
                    System.out.println("Transport " + count + " : " + transport.toString());
                    System.out.println("Transport shipper " + transport.getShipper());
                    System.out.println("Transport goods " + transport.getGoods().toString());
                    count++;
                }
            }
            session.close();
        } catch (Exception e) {
            System.out.println("Problem getShipmentData");
            e.printStackTrace();
        }

    }


    // Test method to demonstrate read from DB and getting the entities
    public Transport getTransport(String departure) {
        try {
            Session session = sessionFactory.openSession();
            session.beginTransaction();

            Query lQuery = session.createQuery("SELECT trans FROM Transport trans", Transport.class);
            List lResult = lQuery.getResultList();
            if (lResult != null && !lResult.isEmpty()) {
                for (Object lR : lResult) {
                    Transport transport = (Transport) lR;
                    System.out.println("Transport shipper " + transport.getShipper());
                    System.out.println("Transport goods " + transport.getGoods().toString());
                    session.close();
                    return transport;
                }
            }
            session.close();
        } catch (Exception e) {
            System.out.println("Problem getShipmentData");
            e.printStackTrace();
        }
        return null;

    }

    public Consignee getConsignee(String name) {
        try {
            Session session = sessionFactory.openSession();
            session.beginTransaction();

            Query lQuery = session.createQuery("SELECT cons FROM Consignee cons", Consignee.class);
            List lResult = lQuery.getResultList();
            if (lResult != null && !lResult.isEmpty()) {
                for (Object lR : lResult) {
                    Consignee consignee = (Consignee) lR;
                    session.close();
                    return consignee;
                }
            }
            session.close();
        } catch (Exception e) {
            System.out.println("Problem getShipmentData");
            e.printStackTrace();
        }
        return null;
    }

    // Test code to populate DB with the positions of text for each cell - only needed to be run once
    public void createTextCellPlacements() {
        String font = Constants.FONT_ARIEL;
        int fontSize = 10;
        TextCell c1 = new TextCell(DEFAULT_TEMPLATE, Constants.SHIPPER, 41, 852 -44, 298, 852 -100, 200, 100, 10, Constants.TEST_COLOUR, Boolean.FALSE, Boolean.FALSE,font,fontSize, Constants.NOT_CENTERED_TEXT);
        TextCell c2 = new TextCell(DEFAULT_TEMPLATE, Constants.CONSIGNEE, 41, 852 -117, 298, 852 -171, 200, 100, 10, Constants.BLACK, Boolean.FALSE, Boolean.FALSE,font,fontSize, Constants.NOT_CENTERED_TEXT);
        TextCell c3 = new TextCell(DEFAULT_TEMPLATE, Constants.LINE1, 43, 852 -258, 101, 852 -248, 200, 100, 10, Constants.BLACK, Boolean.TRUE, Boolean.FALSE,font,fontSize, Constants.NOT_CENTERED_TEXT);
        TextCell c4 = new TextCell(DEFAULT_TEMPLATE, Constants.LINE2, 43, 852 -269, 100, 852 -255, 10, 200, 100, Constants.BLACK, Boolean.TRUE, Boolean.FALSE,font,fontSize, Constants.NOT_CENTERED_TEXT);
        TextCell c5 = new TextCell(DEFAULT_TEMPLATE, Constants.LINE3, 43, 852 -278, 97, 852 -268, 200, 100,10, Constants.BLACK, Boolean.TRUE, Boolean.FALSE,font,fontSize, Constants.NOT_CENTERED_TEXT);
        TextCell c6 = new TextCell(DEFAULT_TEMPLATE, Constants.LINE4, 112, 852 -258, 147, 852 -248, 200, 100,10, Constants.BLACK, Boolean.TRUE, Boolean.FALSE,font,fontSize, Constants.NOT_CENTERED_TEXT);
        TextCell c7 = new TextCell(DEFAULT_TEMPLATE, Constants.LINE5, 112, 852 -269, 159, 852 -257, 200, 100,10, Constants.BLACK, Boolean.TRUE, Boolean.FALSE,font,fontSize, Constants.NOT_CENTERED_TEXT);
        TextCell c8 = new TextCell(DEFAULT_TEMPLATE, Constants.LINE6, 112, 852 -278, 140, 852 -268, 200, 100,10, Constants.BLACK, Boolean.TRUE, Boolean.FALSE,font,fontSize, Constants.NOT_CENTERED_TEXT);
        TextCell c9 = new TextCell(DEFAULT_TEMPLATE, Constants.AIRDEPARTUE, 170, 852 -223, 300, 852 -273, 200, 100,10, Constants.BLACK, Boolean.FALSE, Boolean.FALSE,font,fontSize, Constants.NOT_CENTERED_TEXT);
        c9.setBold(Boolean.TRUE);
        TextCell c10 = new TextCell(DEFAULT_TEMPLATE, Constants.AIRDESTINATION, 37, 852 -292, 298, 852 -305, 200, 100,10, Constants.BLACK, Boolean.FALSE, Boolean.FALSE,font,fontSize, Constants.NOT_CENTERED_TEXT);
        c10.setBold(Boolean.TRUE);
        TextCell c11 = new TextCell(DEFAULT_TEMPLATE, Constants.WAYBILL, 380, 852 -35, 582, 852 -130, 200, 100,10, Constants.RED, Boolean.FALSE, Boolean.FALSE,font,fontSize, Constants.NOT_CENTERED_TEXT);
        TextCell c12 = new TextCell(DEFAULT_TEMPLATE, Constants.PAGENUM, 323, 852 -58, 344, 852 -64, 200, 100,10, Constants.BLACK, Boolean.FALSE, Boolean.FALSE,font,fontSize, Constants.NOT_CENTERED_TEXT);
        TextCell c13 = new TextCell(DEFAULT_TEMPLATE, Constants.PAGEOF, 344, 852 -58, 365, 852 -64, 200, 100,10, Constants.BLACK, Boolean.FALSE, Boolean.FALSE,font,fontSize, Constants.NOT_CENTERED_TEXT);
        TextCell c14 = new TextCell(DEFAULT_TEMPLATE, Constants.REFERENCE, 420, 852 -83, 583, 852 -92, 200, 100,10, Constants.BLACK, Boolean.FALSE, Boolean.FALSE,font,fontSize, Constants.NOT_CENTERED_TEXT);
        TextCell c15 = new TextCell(DEFAULT_TEMPLATE, Constants.RADLINE, 312, 852 -311, 400, 852 -301, 200, 100,10, Constants.BLACK, Boolean.TRUE, Boolean.FALSE,font,fontSize, Constants.NOT_CENTERED_TEXT);
        TextCell c16 = new TextCell(DEFAULT_TEMPLATE, Constants.RADLINE1, 414, 852 -311, 474, 852 -304, 200, 100,10, Constants.BLACK, Boolean.TRUE, Boolean.FALSE,font,fontSize, Constants.NOT_CENTERED_TEXT);
        TextCell c17 = new TextCell(DEFAULT_TEMPLATE, Constants.UNID, 34, 852 -379, 77, 852 -429, 200, 20,10, Constants.BLACK, Boolean.FALSE, Boolean.FALSE,font,fontSize,Constants.NOT_CENTERED_TEXT);
        TextCell c18 = new TextCell(DEFAULT_TEMPLATE, Constants.SHIPPINGNAME, 84, 852 -379, 243, 852 -429, 200, 20,10, Constants.BLACK, Boolean.FALSE, Boolean.FALSE,font,fontSize, Constants.NOT_CENTERED_TEXT);
        TextCell c19 = new TextCell(DEFAULT_TEMPLATE, Constants.CLASS, 260, 852 -379, 500, 852 -429, 200, 20,10, Constants.BLACK, Boolean.FALSE, Boolean.FALSE,font,fontSize, Constants.CENTERED_TEXT);
        TextCell c20 = new TextCell(DEFAULT_TEMPLATE, Constants.PACKINGGRP, 305, 852 -379, 500, 852 -429, 200, 20,10, Constants.BLACK, Boolean.FALSE, Boolean.FALSE,font,fontSize, Constants.CENTERED_TEXT);
        TextCell c21 = new TextCell(DEFAULT_TEMPLATE, Constants.QUANTITY, 345, 852 -379, 470, 852 -429, 200, 20,10, Constants.BLACK, Boolean.FALSE, Boolean.FALSE,font,fontSize, Constants.NOT_CENTERED_TEXT);
        TextCell c22 = new TextCell(DEFAULT_TEMPLATE, Constants.PACKING, 470, 852 -379, 600, 852 -429, 200, 20,10, Constants.BLACK, Boolean.FALSE, Boolean.FALSE,font,fontSize, Constants.CENTERED_TEXT);
        TextCell c23 = new TextCell(DEFAULT_TEMPLATE, Constants.AUTHORIZATION, 524, 852 -379, 584, 852 -429, 200, 20,10, Constants.BLACK, Boolean.FALSE, Boolean.FALSE,font,fontSize, Constants.NOT_CENTERED_TEXT);
        TextCell c24 = new TextCell(DEFAULT_TEMPLATE, Constants.ADDITIONAL, 39, 852 -653, 583, 852 -510, 200, 100,10, Constants.BLACK, Boolean.FALSE, Boolean.FALSE,font,fontSize, Constants.NOT_CENTERED_TEXT);
        TextCell c25 = new TextCell(DEFAULT_TEMPLATE, Constants.NAMES, 385, 852 -727, 585, 852 -553, 200, 100,10, Constants.BLACK, Boolean.FALSE, Boolean.FALSE,font,fontSize, Constants.NOT_CENTERED_TEXT);
        TextCell c26 = new TextCell(DEFAULT_TEMPLATE, Constants.DATE, 385, 852 -753, 585, 852 -583, 200, 100,10, Constants.BLACK, Boolean.FALSE, Boolean.FALSE,font,fontSize, Constants.NOT_CENTERED_TEXT);
        TextCell c27 = new TextCell(DEFAULT_TEMPLATE, Constants.SIGNATURE, 434, 852 -790, 585, 852 -595, 40, 14,10, Constants.BLACK, Boolean.FALSE, Boolean.TRUE,font,fontSize, Constants.NOT_CENTERED_TEXT);
        TextCell c28 = new TextCell(DEFAULT_TEMPLATE, Constants.IMAGE, 300, 664, 582, 650, 300, 80,10, Constants.BLACK, Boolean.FALSE, Boolean.TRUE,font,fontSize, Constants.NOT_CENTERED_TEXT);


        try {
            Session session = sessionFactory.openSession();
            session.beginTransaction();
            session.save(c1);
            session.save(c2);
            session.save(c3);
            session.save(c4);
            session.save(c5);
            session.save(c6);
            session.save(c7);
            session.save(c8);
            session.save(c9);
            session.save(c10);
            session.save(c11);
            session.save(c12);
            session.save(c13);
            session.save(c14);
            session.save(c15);
            session.save(c16);
            session.save(c17);
            session.save(c18);
            session.save(c19);
            session.save(c20);
            session.save(c21);
            session.save(c22);
            session.save(c23);
            session.save(c24);
            session.save(c25);
            session.save(c26);
            session.save(c27);
            session.save(c28);

            session.getTransaction().commit();
            session.close();

        } catch (Exception e) {
            System.out.println("Problem creating test data");
            e.printStackTrace();
        }
    }

    public List<TextCell> getTextCells() {
        try {
            Session session = sessionFactory.openSession();
            session.beginTransaction();

            Query lQuery = session.createQuery("SELECT tc FROM TextCell tc", TextCell.class);
            List lResult = lQuery.getResultList();
            session.close();
            return lResult;
        } catch (Exception e) {
            System.out.println("Problem getShipmentData");
            e.printStackTrace();
        }
        return null;
    }


    public void createShipperDB() {
        Transport transportData = getTransport("Ottawa");
        Consignee consignee = getConsignee("IdeaBytes");
        List<TextCell> lTextCells = getTextCells();
        TextBoxStringCreator lTbString = new TextBoxStringCreator(transportData, consignee);

        if (lTextCells != null && !lTextCells.isEmpty()) {
            for (TextCell cell : lTextCells) {
                if (cell.getStrike()) {
                    System.out.println("Cell name for strike trhough " + cell.getName());
                    boolean strike = lTbString.shouldStrikeThrough(cell.getName());
                    dumpStrikeThrough(cell.getName(), DEFAULT_TEMPLATE, strike);
                }
                else if ( cell.getImage()) {
                    String imagepath = "";
                    if (cell.getName().equals(Constants.IMAGE)) {
                        imagepath = Constants.IMAGE_PATH;
                    }
                    else if (cell.getName().equals(Constants.SIGNATURE)) {
                        imagepath = transportData.getSignature();
                    }
                    dumpImageName(cell.getName(), imagepath, DEFAULT_TEMPLATE);
                }
                else {
                    String lBoxString = lTbString.getStringForBox(cell.getName());
                    if (lBoxString != null) {
                        int countLines = lBoxString.split("\\\n",-1).length-1;
                        System.out.println("split = " + countLines);
                        dumpString(cell.getName(), countLines, lBoxString, DEFAULT_TEMPLATE);
                    }
                    else {
                        // we hit the data that is in Transport Goods
                        // every 5 entries go to another page
                        List<TransportedGood> allGoods = transportData.getGoods();

                        for (int rowNum = 0; rowNum < allGoods.size(); rowNum++) {
                            String lRowString = lTbString.getStringForColumn(cell.getName(), rowNum);
                            dumpRowString(cell.getName(), lRowString, rowNum, DEFAULT_TEMPLATE );
                        }
                    }
                }
            }
        }

    }

    private void dumpImageName(String name, String imagePath, String defaultTemplate) {
        TemplateDataRow row = new TemplateDataRow(name,
                false,
                1,
                imagePath,
                defaultTemplate,
                false,
                1);
        createRow(row);

    }

    private void dumpRowString(String name, String lRowString, int rowNum, String defaultTemplate) {

        TemplateDataRow row = new TemplateDataRow(name,
                false,
                1,
                lRowString,
                defaultTemplate,
                true,
                rowNum);
        createRow(row);

    }

    private void dumpString(String name, int countLines, String lBoxString, String defaultTemplate) {

        TemplateDataRow row = new TemplateDataRow(name,
                countLines > 0 ? true : false,
                countLines,
                lBoxString,
                defaultTemplate,
                false,
                0);
        createRow(row);
    }

    public void dumpStrikeThrough(String name, String templateName, boolean strike) {
        TemplateDataRow row = new TemplateDataRow(name, templateName);
        row.setStrikeThrough(strike);
        createRow(row);
    }


    public void createRow(TemplateDataRow rowData) {
        try {
            Session session = sessionFactory.openSession();
            session.beginTransaction();
            session.save(rowData);
            session.getTransaction().commit();
            session.close();
        } catch (Exception e) {
            System.out.println("dumpString Problem creating test data");
            e.printStackTrace();
        }
    }

    public List<TemplateDataRow> getTemplateDataRows() {
        try {
            Session session = sessionFactory.openSession();
            session.beginTransaction();

            Query lQuery = session.createQuery("SELECT tr FROM TemplateDataRow tr", TemplateDataRow.class);
            List lResult = lQuery.getResultList();
            session.close();
            return lResult;
        } catch (Exception e) {
            System.out.println("Problem getTemplateDataRows");
            e.printStackTrace();
        }
        return null;
    }
}


