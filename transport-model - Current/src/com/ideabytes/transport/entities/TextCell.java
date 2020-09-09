package com.ideabytes.transport.entities;


import javax.persistence.*;
import java.util.Objects;

@Entity(name = "TextCell")
@Table(name="textcell")
public class TextCell {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="id")
    private Long id;

    @Column(name="templateName")
    private String templateName;

    @Column(name="name")
    private String name;

    @Column(name="x1")
    private Integer x1;

    @Column(name="y1")
    private Integer y1;

    @Column(name="x2")
    private Integer x2;

    @Column(name="y2")
    private Integer y2;

    @Column(name="width")
    private Integer width;

    @Column(name="height")
    private Integer height;

    @Column(name="fontsize")
    private Integer fontsize;

    @Column(name="image")
    private Boolean image;

    @Column(name="fontcolor")
    private String fontcolor;

    @Column(name="strike")
    private Boolean strike;

    @Column(name="bold")
    private Boolean bold;

    @Column(name="font")
    private String font;
    @Column(name="fontSize")
    private int fontSize;
    @Column(name="centeredText")
    private String centeredText;
    public TextCell() {
    }

    public TextCell(String templateName, String name, Integer x1, Integer y1, Integer x2, Integer y2, Integer width,
                    Integer height, Integer size, String color, Boolean strike,Boolean image,
                    String font, int fontSize, String centeredText) {
        this.templateName = templateName;
        this.name = name;
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
        this.width = width;
        this.height = height;
        this.fontsize = size;
        this.fontcolor = color;
        this.strike = strike;
        this.image = image;
        this.bold = Boolean.FALSE;
        this.font = font;
        this.fontSize = fontSize;
        this.centeredText = centeredText;

    }

    public String getCenteredText() {
        return centeredText;
    }

    public void setCenteredText(String centeredText) {
        this.centeredText = centeredText;
    }

    public int getFontSize() {
        return fontSize;
    }

    public void setFontSize(int fontSize) {
        this.fontSize = fontSize;
    }

    public String getFont() {
        return font;
    }

    public void setFont(String font) {
        this.font = font;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTemplateName() {
        return templateName;
    }

    public void setTemplateName(String templateName) {
        this.templateName = templateName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getX1() {
        return x1;
    }

    public void setX1(Integer x1) {
        this.x1 = x1;
    }

    public Integer getY1() {
        return y1;
    }

    public void setY1(Integer y1) {
        this.y1 = y1;
    }

    public Integer getX2() {
        return x2;
    }

    public void setX2(Integer x2) {
        this.x2 = x2;
    }

    public Integer getY2() {
        return y2;
    }

    public void setY2(Integer y2) {
        this.y2 = y2;
    }

    public Integer getWidth() {
        return width;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public Integer getFontsize() {
        return fontsize;
    }

    public void setFontsize(Integer fontsize) {
        this.fontsize = fontsize;
    }

    public String getFontcolor() {
        return fontcolor;
    }

    public void setFontcolor(String fontcolor) {
        this.fontcolor = fontcolor;
    }

    public Boolean getStrike() {
        return strike;
    }

    public void setStrike(Boolean strike) {
        this.strike = strike;
    }

    public Boolean getBold() {
        return bold;
    }

    public void setBold(Boolean bold) {
        this.bold = bold;
    }

    public boolean getImage() {
        return image;
    }
    public void setImage(Boolean image){
        this.image=image;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TextCell textCell = (TextCell) o;
        return fontSize == textCell.fontSize &&
                Objects.equals(id, textCell.id) &&
                Objects.equals(templateName, textCell.templateName) &&
                Objects.equals(name, textCell.name) &&
                Objects.equals(x1, textCell.x1) &&
                Objects.equals(y1, textCell.y1) &&
                Objects.equals(x2, textCell.x2) &&
                Objects.equals(y2, textCell.y2) &&
                Objects.equals(width, textCell.width) &&
                Objects.equals(height, textCell.height) &&
                Objects.equals(fontsize, textCell.fontsize) &&
                Objects.equals(image, textCell.image) &&
                Objects.equals(fontcolor, textCell.fontcolor) &&
                Objects.equals(strike, textCell.strike) &&
                Objects.equals(bold, textCell.bold) &&
                Objects.equals(font, textCell.font) &&
                Objects.equals(centeredText, textCell.centeredText);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, templateName, name, x1, y1, x2, y2, width, height, fontsize, image, fontcolor, strike, bold, font, fontSize, centeredText);
    }

    @Override
    public String toString() {
        return "TextCell{" +
                "id=" + id +
                ", templateName='" + templateName + '\'' +
                ", name='" + name + '\'' +
                ", x1=" + x1 +
                ", y1=" + y1 +
                ", x2=" + x2 +
                ", y2=" + y2 +
                ", width=" + width +
                ", height=" + height +
                ", fontsize=" + fontsize +
                ", image=" + image +
                ", fontcolor='" + fontcolor + '\'' +
                ", strike=" + strike +
                ", bold=" + bold +
                ", font='" + font + '\'' +
                ", fontSize=" + fontSize +
                ", centeredText='" + centeredText + '\'' +
                '}';
    }
}
