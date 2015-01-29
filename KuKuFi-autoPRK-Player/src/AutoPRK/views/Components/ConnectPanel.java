/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AutoPRK.views.Components;

import AutoPRK.Controllers.PRKLogger;
import AutoPRK.Models.Containers.ConfPair;
import AutoPRK.Models.DrumPart;
import AutoPRK.Models.Model;
import static AutoPRK.views.mainWindow.window;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

/**
 *
 * @author fbrzuzka
 */
public class ConnectPanel extends JPanel implements ActionListener {

    private DrumTrackPanelBase drumTrackPanel;
    private DrumTrackPanelBase drumKitElementPanel;

    private HashMap<ConfPair, Graphics2D> graphics;

    public ConnectPanel() {
        super();
        graphics = new HashMap<>();

    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        JRadioButton radioTrack = null;
        JRadioButton radioElement = null;

        if (ae.getSource() instanceof JRadioButton) {
            JRadioButton radio = (JRadioButton) ae.getSource();
            if (radio.getParent() == drumTrackPanel) {
                radioTrack = radio;
                PRKLogger.instance().logToInfoArea("jestem na track panelu");
            } else if (radio.getParent() == drumKitElementPanel) {
                radioElement = radio;
                PRKLogger.instance().logToInfoArea("jestem na element panelu");
            }

            ConfPair pair = null;
            String a = "traalal";
            String b = "bumbumbum";
            JRadioButton r = null;
            boolean doIt = false;

            if ((radioElement != null) && (drumTrackPanel.getGroup().getSelection() != null)) {
                PRKLogger.instance().logToInfoArea("połaczyłem 1");

                a = radioElement.getText();

                Component[] components = drumTrackPanel.getComponents();
                for (Component c : components) {
                    if (c instanceof JRadioButton) {
                        r = (JRadioButton) c;
                        if (r.isSelected()) {
                            b = r.getText();

                            drumTrackPanel.moveRadioToUpOnPanel(r);
                            drumKitElementPanel.moveRadioToUpOnPanel(radio);
                            doIt = true;
                        }
                    }
                }
            } else if ((radioTrack != null) && (drumKitElementPanel.getGroup().getSelection() != null)) {
                PRKLogger.instance().logToInfoArea("połaczyłem 2");

                b = radioTrack.getText();

                Component[] components = drumKitElementPanel.getComponents();
                for (Component c : components) {
                    if (c instanceof JRadioButton) {
                        r = (JRadioButton) c;
                        if (r.isSelected()) {
                            a = r.getText();

                            drumTrackPanel.moveRadioToUpOnPanel(radio);
                            drumKitElementPanel.moveRadioToUpOnPanel(r);
                            doIt = true;
                        }
                    }
                }
            }
            if (doIt) {
                DrumPart dp = Model.protocolList.get(b).getDrumPart();
                pair = new ConfPair(a, dp);
                Model.connectConfig.add(pair);
                PRKLogger.instance().logToInfoArea(pair.toString());

                window.getConnectPanel().repaint();
                window.pack();

                drumTrackPanel.getGroup().clearSelection();
                drumKitElementPanel.getGroup().clearSelection();
            }
        }
    }

    @Override
    public void paintComponent(Graphics g) {

        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        g2d.setColor(Color.black);

        Component[] components = drumTrackPanel.getComponents();
        for (Component c : components) {
            if (c instanceof JRadioButton) {
                JRadioButton r = (JRadioButton) c;
                if (r.isSelected()) {

                }
            }
        }
        if (Model.connectConfig != null) {
            for (ConfPair pair : Model.connectConfig) {
                int x1 = drumTrackPanel.getWidth();
                String drumtrackname = pair.getDrumPart().getPartName();
                int y1 = drumTrackPanel.findByName(drumtrackname).getY() + drumTrackPanel.findByName(pair.getDrumPart().getPartName()).getHeight() / 2 + drumTrackPanel.getY();
                int x2 = drumKitElementPanel.getX();
                int y2 = drumKitElementPanel.findByName(pair.getDrumKitElement()).getY() + drumKitElementPanel.findByName(pair.getDrumKitElement()).getHeight() / 2 + drumKitElementPanel.getY();
                g2d.setStroke(new BasicStroke(8));
                g2d.drawLine(x1, y1, x2, y2);
                graphics.put(pair, g2d);

            }
        }

    }

    @Override
    public void setEnabled(boolean bln) {
        super.setEnabled(bln);
        drumKitElementPanel.setEnabled(bln);
        drumTrackPanel.setEnabled(bln);
    }

    public void clear() {
        drumKitElementPanel.clear();
        drumTrackPanel.clear();
    }

    public ActionListener getActionListener() {

        return this.getActionListener();
    }

    public DrumTrackPanelBase getDrumTrackPanel() {
        return drumTrackPanel;
    }

    public void setDrumTrackPanel(DrumTrackPanelBase drumTrackPanel) {
        this.drumTrackPanel = drumTrackPanel;
    }

    public DrumTrackPanelBase getDrumKitElementPanel() {
        return drumKitElementPanel;
    }

    public void setDrumElementPanel(DrumTrackPanelBase drumPartPanel) {
        this.drumKitElementPanel = drumPartPanel;
    }

}
