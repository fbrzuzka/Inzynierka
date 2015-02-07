/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package KuKuFi.Controllers.MessageController;

import KuKuFi.Controllers.WindowController;
import KuKuFi.Models.Message.AbstractMessage;
import KuKuFi.views.MainWindow;
import gnu.io.CommPortIdentifier;
import gnu.io.SerialPort;
import gnu.io.SerialPortEvent;
import gnu.io.SerialPortEventListener;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.PipedOutputStream;
import java.util.Enumeration;
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
        boolean initialize = initialize();
    }

    public boolean initialize() {
        try {
            CommPortIdentifier portId = null;
            Enumeration portEnum = CommPortIdentifier.getPortIdentifiers();

            // Enumerate system ports and try connecting to Arduino over each
            //
            System.out.println("Trying:");
            while (portId == null && portEnum.hasMoreElements()) {
                // Iterate through your host computer's serial port IDs
                //
                CommPortIdentifier currPortId = (CommPortIdentifier) portEnum.nextElement();
                System.out.println("   port" + currPortId.getName());
                for (String portName : PORT_NAMES) {
                    if (currPortId.getName().equals(portName)
                            || currPortId.getName().startsWith(portName)) {

                        // Try to connect to the Arduino on this port
                        //
                        // Open serial port
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

            // add event listeners
            serialPort.addEventListener(this);
            serialPort.notifyOnDataAvailable(true);
            inStream = serialPort.getInputStream();

            // Give the Arduino some time
            try {
                Thread.sleep(2000);
            } catch (InterruptedException ie) {
            }

            return true;
        } catch (Exception e) {
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

//    @Override
//    public synchronized void serialEvent(SerialPortEvent oEvent) {
//        try {
//            switch (oEvent.getEventType()) {
//                case SerialPortEvent.DATA_AVAILABLE:
//                    if (input == null) {
//                        input = new BufferedReader(
//                                new InputStreamReader(
//                                        serialPort.getInputStream()));
//                    }
//                    char odebrane[] = new char[10];
//                    input.read(odebrane);
//
//                    System.out.println("odebrane od biedrzego: ");
//                    for (char p : odebrane) {
//                        System.out.print((byte) p + " ");
//                    }
//                    System.out.println("");
//                    String foo = new String(odebrane);
//                    MessageParser.parseString(foo);
//                    break;
//
//                default:
//                    break;
//            }
//        } catch (Exception e) {
//            System.err.println(e.toString());
//        }
//    }
    //
    // This should be called when you stop using the port
    //
    public synchronized void close() {
        if (serialPort != null) {
            serialPort.removeEventListener();
            serialPort.close();
        }
        System.out.println("trying to close serial port transmiter");
    }

}
