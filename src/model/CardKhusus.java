package model;

import java.awt.Color;
import javax.swing.Icon;

/**
 *
 * @author RAMPA
 */
public class CardKhusus {
    
    private Icon icon;
    private String title;
    private Color warna;

    public CardKhusus() {
    }

    public CardKhusus(Icon icon, String title, Color warna) {
        this.icon = icon;
        this.title = title;
        this.warna = warna;
    }

    public Icon getIcon() {
        return icon;
    }

    public String getTitle() {
        return title;
    }

    public Color getWarna() {
        return warna;
    }
    
    
}
