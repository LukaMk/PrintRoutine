package com.ideabytes.transport.entities;

import javax.persistence.*;
import java.util.Objects;

@Entity(name = "TemplateDataRow")
@Table(name="templatedatarow")
public class TemplateDataRow {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="id")
    private int id;
    @Column(name="name")
    private String name;
    @Column(name="multiline")
    private boolean multiline;
    @Column(name="numlines")
    private int numlines;
    @Column(name="strikeThrough")
    private boolean strikeThrough;
    @Column(name="fontsize")
    private int fontsize;
    @Column(name="bold")
    private boolean bold;
    @Column(name="color")
    private String color;
    @Column(name="textData")
    private String textData;
    @Column(name="templateName")
    private String templateName;
    @Column(name="multirow")
    private boolean multirow;
    @Column(name="rowNum")
    private int rowNum;




    public TemplateDataRow() {
    }

    public TemplateDataRow(String name, String templateName) {
        this.name = name;
        this.multiline = false;
        this.numlines = 1;
        this.fontsize = 10;
        this.bold = false;
        this.color = "BLACK";
        this.textData = "";
        this.templateName = templateName;
        this.multirow = false;
        this.rowNum = 0;
        this.strikeThrough = true;
    }

    public TemplateDataRow(String name, boolean multiline, int numlines, int fontsize, boolean bold, String color, String textData, String templateName, boolean multiRow, int rowNum) {
        this.name = name;
        this.multiline = multiline;
        this.numlines = numlines;
        this.fontsize = fontsize;
        this.bold = bold;
        this.color = color;
        this.textData = textData;
        this.templateName = templateName;
        this.multirow = multiRow;
        this.rowNum = rowNum;
        this.strikeThrough = false;
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

    public boolean isMultiline() {
        return multiline;
    }

    public void setMultiline(boolean multiline) {
        this.multiline = multiline;
    }

    public int getNumlines() {
        return numlines;
    }

    public void setNumlines(int numlines) {
        this.numlines = numlines;
    }

    public int getFontsize() {
        return fontsize;
    }

    public void setFontsize(int fontsize) {
        this.fontsize = fontsize;
    }

    public boolean isBold() {
        return bold;
    }

    public void setBold(boolean bold) {
        this.bold = bold;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getTextData() {
        return textData;
    }

    public void setTextData(String textData) {
        this.textData = textData;
    }

    public String getTemplateName() {
        return templateName;
    }

    public void setTemplateName(String templateName) {
        this.templateName = templateName;
    }

    public boolean isStrikeThrough() {
        return strikeThrough;
    }

    public void setStrikeThrough(boolean strikeThrough) {
        this.strikeThrough = strikeThrough;
    }

    public boolean isMultirow() {
        return multirow;
    }

    public void setMultirow(boolean multirow) {
        this.multirow = multirow;
    }

    public int getRowNum() {
        return rowNum;
    }

    public void setRowNum(int rowNum) {
        this.rowNum = rowNum;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TemplateDataRow that = (TemplateDataRow) o;
        return id == that.id &&
                multiline == that.multiline &&
                numlines == that.numlines &&
                strikeThrough == that.strikeThrough &&
                fontsize == that.fontsize &&
                bold == that.bold &&
                Objects.equals(name, that.name) &&
                Objects.equals(color, that.color) &&
                Objects.equals(textData, that.textData) &&
                Objects.equals(templateName, that.templateName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, multiline, numlines, strikeThrough, fontsize, bold, color, textData, templateName);
    }

    @Override
    public String toString() {
        return "TemplateDataRow{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", multiline=" + multiline +
                ", numlines=" + numlines +
                ", strikeThrough=" + strikeThrough +
                ", fontsize=" + fontsize +
                ", bold=" + bold +
                ", color='" + color + '\'' +
                ", textData='" + textData + '\'' +
                ", templateName='" + templateName + '\'' +
                '}';
    }
}
