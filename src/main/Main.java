package main;

import component.MenuLayout;
import event.EventMenuSelected;
import form.Form_1;
import form.Form_2;
import form.MainForm;
import swing.WindowSnapshots;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import net.miginfocom.swing.MigLayout;
import org.jdesktop.animation.timing.Animator;
import org.jdesktop.animation.timing.TimingTarget;
import org.jdesktop.animation.timing.TimingTargetAdapter;

public class Main extends javax.swing.JFrame {

    private final MigLayout layout;
    private final MainForm main;
    private final MenuLayout menu;
    private final Animator animator;
    private final WindowSnapshots windowSnapshots;

    public Main() {
        initComponents();
        layout = new MigLayout("fill", "0[fill]0", "0[fill]0");
        main = new MainForm();
        menu = new MenuLayout();
        windowSnapshots = new WindowSnapshots(Main.this);
        menu.getMenu().initMoving(Main.this);
        main.initMoving(Main.this);
        mainPanel.setLayout(new BorderLayout());
        mainPanel.add(main);
        JPanel glassPanel = new JPanel(layout);
        glassPanel.setOpaque(false);
        glassPanel.add(menu, "pos -215 0 100% 100%");
        setGlassPane(glassPanel);
        TimingTarget target = new TimingTargetAdapter() {
            @Override
            public void timingEvent(float fraction) {
                float x = (fraction * 215);
                float alpha;
                if (menu.isShow()) {
                    x = -x;
                    alpha = 0.5f - (fraction / 2);
                } else {
                    x -= 215;
                    alpha = fraction / 2;
                }
                layout.setComponentConstraints(menu, "pos " + (int) x + " 0 100% 100%");
                if (alpha < 0) {
                    alpha = 0;
                }
                menu.setAlpha(alpha);
                menu.revalidate();
            }

            @Override
            public void begin() {
                getGlassPane().setVisible(true);
                windowSnapshots.createSnapshot();
                getContentPane().setVisible(false);
            }

            @Override
            public void end() {
                menu.setShow(!menu.isShow());
                if (!menu.isShow()) {
                    menu.setVisible(false);
                }
                windowSnapshots.removeSnapshot();
                getContentPane().setVisible(true);
            }

        };
        animator = new Animator(350, target);
        animator.setDeceleration(0.5f);
        animator.setAcceleration(0.5f);
        animator.setResolution(1);
        menu.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent me) {
                if (SwingUtilities.isLeftMouseButton(me)) {
                    if (!animator.isRunning()) {
                        if (menu.isShow()) {
                            animator.start();
                        }
                    }
                }
            }
        });
        main.addEventMenu(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                if (!animator.isRunning()) {
                    if (!menu.isShow()) {
                        menu.setVisible(true);
                        animator.start();
                    }
                }
            }
        });
        menu.getMenu().addEventMenuSelected(new EventMenuSelected() {
            @Override
            public void selected(int index) {
                if (index == 0) {
                    main.show(new Form_1());
                } else if (index == 1) {
                    main.show(new Form_2());
                } else if (index == 3) {
                    System.exit(0);
                }
            }
        });
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        mainPanel = new javax.swing.JLayeredPane();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);

        mainPanel.setBackground(new java.awt.Color(250, 250, 250));
        mainPanel.setOpaque(true);

        javax.swing.GroupLayout mainPanelLayout = new javax.swing.GroupLayout(mainPanel);
        mainPanel.setLayout(mainPanelLayout);
        mainPanelLayout.setHorizontalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1100, Short.MAX_VALUE)
        );
        mainPanelLayout.setVerticalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 645, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(mainPanel)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(mainPanel)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Main().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLayeredPane mainPanel;
    // End of variables declaration//GEN-END:variables
}
