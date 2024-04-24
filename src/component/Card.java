package component;

import model.ModelCard;
import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

public class Card extends javax.swing.JPanel {

    public Color getColor1() {
        return color1;
    }

    public void setColor1(Color color1) {
        this.color1 = color1;
    }

    public Color getColor2() {
        return color2;
    }

    public void setColor2(Color color2) {
        this.color2 = color2;
    }

    private Color color1;
    private Color color2;

    public Card() {
        initComponents();
        setOpaque(false);
        color1 = Color.BLACK;
        color2 = Color.WHITE;
    }

    public void setData(ModelCard data) {
        //lbIcon.setIcon(data.getIcon());
        lbTitle.setText(data.getTitle());
        lbValues.setText(data.getValues());
        lbValues1.setText(data.getValues1());
        lbValues2.setText(data.getValues2());
    }
    
    public void setValue(String nilai) {
        lbValues.setText(nilai);
    }
    
    public void setValue1(String nilai) {
        lbValues1.setText(nilai);
    }
    public void setValue2(String nilai) {
        lbValues2.setText(nilai);
    }
    
    public String getValue() {
        return lbValues.getText();
    }
    
    public String getValue1() {
        return lbValues1.getText();
    }
    public String getValue2() {
        return lbValues2.getText();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lbTitle = new javax.swing.JLabel();
        lbValues = new javax.swing.JLabel();
        lbValues1 = new javax.swing.JLabel();
        lbValues2 = new javax.swing.JLabel();

        lbTitle.setFont(new java.awt.Font("sansserif", 1, 16)); // NOI18N
        lbTitle.setForeground(new java.awt.Color(255, 255, 255));
        lbTitle.setText("Title");

        lbValues.setFont(new java.awt.Font("sansserif", 1, 11)); // NOI18N
        lbValues.setForeground(new java.awt.Color(255, 255, 255));
        lbValues.setText("Values");

        lbValues1.setFont(new java.awt.Font("sansserif", 1, 11)); // NOI18N
        lbValues1.setForeground(new java.awt.Color(255, 255, 255));
        lbValues1.setText("Values");

        lbValues2.setFont(new java.awt.Font("sansserif", 1, 11)); // NOI18N
        lbValues2.setForeground(new java.awt.Color(255, 255, 255));
        lbValues2.setText("Values");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lbValues2)
                    .addComponent(lbValues1)
                    .addComponent(lbValues)
                    .addComponent(lbTitle))
                .addContainerGap(206, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addComponent(lbTitle)
                .addGap(18, 18, 18)
                .addComponent(lbValues)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lbValues1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lbValues2)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    @Override
    protected void paintComponent(Graphics grphcs) {
        Graphics2D g2 = (Graphics2D) grphcs;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        GradientPaint g = new GradientPaint(0, 0, color1, 0, getHeight(), color2);
        g2.setPaint(g);
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), 15, 15);
        g2.setColor(new Color(255, 255, 255, 50));
        g2.fillOval(getWidth() - (getHeight() / 2), 10, getHeight(), getHeight());
        g2.fillOval(getWidth() - (getHeight() / 2) - 20, getHeight() / 2 + 20, getHeight(), getHeight());
        super.paintComponent(grphcs);
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel lbTitle;
    private javax.swing.JLabel lbValues;
    private javax.swing.JLabel lbValues1;
    private javax.swing.JLabel lbValues2;
    // End of variables declaration//GEN-END:variables
}
