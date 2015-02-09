/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package KuKuFi.Controllers.MessageController;

import KuKuFi.Models.Message.AbstractMessage;
import KuKuFi.views.MainWindow;
import gnu.io.CommPortIdentifier;
import gnu.io.PortInUseException;
import gnu.io.SerialPort;
import gnu.io.SerialPortEvent;
import gnu.io.SerialPortEventListener;
import gnu.io.UnsupportedCommOperationException;
import java.awt.HeadlessException;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.TooManyListenersException;
import javax.swing.JOptionPane;

/**
 *
 * @author fbrzuzka
 */
public class SerialPortTransmiter implements SerialPortEventListener {

    SerialPort serialPort = null;

    private String appName;
    private BufferedReader input;
    private OutputStream output;
    private DataOutputStream dataOut;
    private InputStream inStream;

    private static final int TIME_OUT = 1000; // Port open timeout
    private static final int DATA_RATE = 9600; // Arduino serial port

    private byte[] readBuffer = new byte[400];

    private static final String PORT_NAMES[] = {
        //        "/dev/tty.usbmodem", // Mac OS X
        //        "/dev/usbdev", // Linux
        //        "/dev/tty", // Linux
        //        "/dev/serial", // Linux
        "COM1", // Windows
        "COM2", // Windows
        "COM3", // Windows
        "COM4", // Windows
        "COM5", // Windows
        "COM6", // Windows
        "COM7", // Windows
        "COM8", // Windows
        "COM9", // Windows
        "COM10", // Windows
        "COM11", // Windows
        "COM12", // Windows
        "COM13", // Windows
        "COM14", // Windows
        "COM15", // Windows
        "COM16", // Windows
        "COM17", // Windows
        "COM18", // Windows
        "COM19", // Windows
    };

    public SerialPortTransmiter() {
        initialize();
    }

    public boolean initialize() {
        try {
            CommPortIdentifier portId = null;
            Enumeration portEnum = CommPortIdentifier.getPortIdentifiers();

            System.out.println("Trying:");
            while (portId == null && portEnum.hasMoreElements()) {
                
                CommPortIdentifier currPortId = (CommPortIdentifier) portEnum.nextElement();
                System.out.println("   port" + currPortId.getName());
                for (String portName : PORT_NAMES) {
                    if (currPortId.getName().equals(portName) || currPortId.getName().startsWith(portName)) {
                        serialPort = (SerialPort) currPortId.open(appName, TIME_OUT);
                        portId = currPortId;
                        System.out.println("Connected on port" + currPortId.getName());
                        break;
                    }
                }
            }

            if (portId == null || serialPort == null) {
                JOptionPane.showMessageDialog(MainWindow.window,
                        "autoPRK is not connected to USB port or port is in use.\n Please unplug autoPRK from USB and then plug it again.",
                        "autoPRK warning",
                        JOptionPane.WARNING_MESSAGE);
                MainWindow.window.repaint();
                System.out.println("Oops... Could not connect to Arduino");
                return false;
            }

            // set port parameters
            serialPort.setSerialPortParams(DATA_RATE,
                    SerialPort.DATABITS_8,
                    SerialPort.STOPBITS_1,
                    SerialPort.PARITY_NONE);

            
            serialPort.addEventListener(this);
            serialPort.notifyOnDataAvailable(true);
            inStream = serialPort.getInputStream();

            // Give the Arduino some time
            try {
                Thread.sleep(2000);
            } catch (InterruptedException ie) {
            }

            return true;
        } catch (PortInUseException | HeadlessException | UnsupportedCommOperationException | TooManyListenersException | IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void sendMessage(AbstractMessage message) {
        try {
            output = serialPort.getOutputStream();
            output.write(message.toSend());
        } catch (Exception e) {
            System.err.println("Dear user, you didn't connect Arduino");
        }
    }

    @Override
    public void serialEvent(SerialPortEvent event) {
        switch (event.getEventType()) {
            case SerialPortEvent.DATA_AVAILABLE:
                readSerial();
                break;
        }
    }

    private void readSerial() {
        try {
            int availableBytes = inStream.available();
            if (availableBytes >= 7) {
                // Read the serial port
                inStream.read(readBuffer, 0, availableBytes);

                // Print it out
                System.out.println(
                        new String(readBuffer, 0, availableBytes));

                System.out.println("odebrane od biedrzego: ");
                for (byte p : readBuffer) {
                    System.out.print(p + " ");
                }
                System.out.println("");
                String foo = new String(readBuffer);
                MessageParser.parseString(foo);
            }
        } catch (IOException e) {
        }
    }

    
    public synchronized void close() {
        if (serialPort != null) {
            serialPort.removeEventListener();
            serialPort.close();
        }
        System.out.println("trying to close serial port transmiter");
    }

}
