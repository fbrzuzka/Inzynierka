package KuKuFi_Pi.Models;

import KuKuFi_Pi.Controllers.Raspberry.GPIOController;
import KuKuFi_Pi.Controllers.Raspberry.KuPlayerController;
import KuKuFi_Pi.Controllers.Raspberry.PiExecutor;
import KuKuFi_Pi.MidiPlayer.SequencePlayer;
import KuKuFi_Pi.Models.Containers.ConnectConfiguration;
import KuKuFi_Pi.Models.Containers.DrumPartList;
import KuKuFi_Pi.Models.Containers.ProtocolListHashMap;
import KuKuFi_Pi.Models.Containers.TrackMap;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPinDigitalOutput;
import com.pi4j.io.gpio.PinState;
import com.pi4j.io.gpio.RaspiPin;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.sound.midi.Sequence;
import javax.sound.midi.Track;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author fbrzuzka
 */
public class Model {

    public static final int NOTE_ON = 0x90;
    public static final int NOTE_OFF = 0x80;
    public static final String[] NOTE_NAMES
            = {"C", "C#", "D", "D#", "E", "F", "F#", "G", "G#", "A", "A#", "B"};
    public static File midiFile;
    public double tickToMilisRatio;
    public static Sequence sequenceToPlayFromSpeakers;
    public static Sequence sequenceOriginal;
    public static Track trackSelectedToGenerate;
    public static DrumPartList drumTrackElements;
    public static List<String> drumKitPartElements;
    public static ProtocolListHashMap protocolList;
    public static ConnectConfiguration connectConfig;

    public static TrackMap trackListToPlayFromSpeakers;
    public static SequencePlayer sequencePlayer;

    public int numerator = 0;
    public double denominator = 0;
    public int ticksPerMetronomClick = 0;

    public static TrackMap trackListOriginal;

    public static GPIOController controller;
    public static KuPlayerController kuPlayerController;
    public static PiExecutor piExecutor;
    public static HashMap<String, GpioPinDigitalOutput> outputPinMap = new HashMap<>();

    public static String os;

    private Model() {
        os = System.getProperty("os.name");
        System.out.println("os: " + os);
        if (os.toLowerCase().contains("windows")) {

        } else {
            preparePinout();
        }
        //controller = new GPIOController();
        //piExecutor = new PiExecutor(4);

        kuPlayerController = new KuPlayerController();

        trackListOriginal = new TrackMap();
        trackListToPlayFromSpeakers = new TrackMap();
        connectConfig = new ConnectConfiguration();

        drumKitPartElements = new ArrayList<String>();
        drumKitPartElements.add("1");
        drumKitPartElements.add("2");
        drumKitPartElements.add("3");
        drumKitPartElements.add("4");
        drumKitPartElements.add("5");
        drumKitPartElements.add("6");
        drumKitPartElements.add("7");
        drumKitPartElements.add("8");
        drumKitPartElements.add("9");
        drumKitPartElements.add("10");

    }

    private static Model instance = null;

    public static Model instanceOf() {
        if (instance == null) {
            instance = new Model();
        }
        return instance;
    }

    private void preparePinout() {
        String pinName;
        pinName = "pin0";
        outputPinMap.put(pinName, GpioFactory.getInstance().provisionDigitalOutputPin(RaspiPin.GPIO_00, pinName, PinState.HIGH));
        pinName = "pin1";
        outputPinMap.put(pinName, GpioFactory.getInstance().provisionDigitalOutputPin(RaspiPin.GPIO_01, pinName, PinState.HIGH));
        pinName = "pin2";
        outputPinMap.put(pinName, GpioFactory.getInstance().provisionDigitalOutputPin(RaspiPin.GPIO_02, pinName, PinState.HIGH));
        pinName = "pin3";
        outputPinMap.put(pinName, GpioFactory.getInstance().provisionDigitalOutputPin(RaspiPin.GPIO_03, pinName, PinState.HIGH));
        pinName = "pin4";
        outputPinMap.put(pinName, GpioFactory.getInstance().provisionDigitalOutputPin(RaspiPin.GPIO_04, pinName, PinState.HIGH));
        pinName = "pin5";
        outputPinMap.put(pinName, GpioFactory.getInstance().provisionDigitalOutputPin(RaspiPin.GPIO_05, pinName, PinState.HIGH));
        pinName = "pin6";
        outputPinMap.put(pinName, GpioFactory.getInstance().provisionDigitalOutputPin(RaspiPin.GPIO_06, pinName, PinState.HIGH));
        pinName = "pin7";
        outputPinMap.put(pinName, GpioFactory.getInstance().provisionDigitalOutputPin(RaspiPin.GPIO_07, pinName, PinState.HIGH));
        pinName = "pin8";
        outputPinMap.put(pinName, GpioFactory.getInstance().provisionDigitalOutputPin(RaspiPin.GPIO_08, pinName, PinState.HIGH));
        pinName = "pin9";
        outputPinMap.put(pinName, GpioFactory.getInstance().provisionDigitalOutputPin(RaspiPin.GPIO_09, pinName, PinState.HIGH));
    }

}
