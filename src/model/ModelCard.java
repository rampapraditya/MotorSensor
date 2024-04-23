package model;

import javax.swing.Icon;

public class ModelCard {

    private Icon icon;
    private String title;
    private String values;
    private String values1;
    private String values2;
    private String description;
    
    public ModelCard() {
    }
    
    public ModelCard(Icon icon, String title, String v1, String v2, String v3, String description) {
        this.icon = icon;
        this.title = title;
        this.values = v1;
        this.values1 = v2;
        this.values2 = v3;
        this.description = description;
    }
    
    public Icon getIcon() {
        return icon;
    }

    public void setIcon(Icon icon) {
        this.icon = icon;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getValues() {
        return values;
    }

    public void setValues(String values) {
        this.values = values;
    }

    public String getValues1() {
        return values1;
    }

    public void setValues1(String values1) {
        this.values1 = values1;
    }

    public String getValues2() {
        return values2;
    }

    public void setValues2(String values2) {
        this.values2 = values2;
    }
    
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
