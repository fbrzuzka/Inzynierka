package KuKuFi_Pi.Models;

import KuKuFi_Pi.Controllers.Raspberry.KuPlayerController;
import KuKuFi_Pi.Controllers.Raspberry.PiExecutor;
import KuKuFi_Pi.Controllers.Raspberry.Pin_Pi;
import KuKuFi_Pi.MidiPlayer.SequencePlayer;
import KuKuFi_Pi.Models.Containers.ConnectConfiguration;
import KuKuFi_Pi.Models.Containers.DrumPartList;
import KuKuFi_Pi.Models.Containers.ProtocolListHashMap;
import KuKuFi_Pi.Models.Containers.TrackMap;
import KuKuFi_Pi.pinout.Pin;
import KuKuFi_Pi.pinout.PinoutService;
import KuKuFi_Pi.pinout.PinoutServiceFactory;
import lombok.Getter;

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
 * @author fbrzuzka
 * Pins are mapping from PIN_X = Y, where X is WiringPi number and Y is a BCM pin number
 */
public class Model {

    public static final String[] NOTE_NAMES
            = {"C", "C#", "D", "D#", "E", "F", "F#", "G", "G#", "A", "A#", "B"};
    private static final Integer PIN_0 = 17;
    private static final Integer PIN_1 = 18;
    private static final Integer PIN_2 = 27;
    private static final Integer PIN_3 = 22;
    private static final Integer PIN_4 = 23;
    private static final Integer PIN_5 = 24;
    private static final Integer PIN_6 = 25;
    private static final Integer PIN_7 = 4;
    private static final Integer PIN_8 = 2;
    private static final Integer PIN_9 = 3;
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
    public int ticksPerMetronomeClick = 0;

    public static TrackMap trackListOriginal;

    //    public static GPIOController controller;
    public static KuPlayerController kuPlayerController;
    public static PiExecutor piExecutor;

    public static String os;
    @Getter
    private final PinoutService pinoutService;
    private HashMap<String, Pin> outputPinMap = new HashMap<>();

    private Model() {
        this.pinoutService = new PinoutServiceFactory().createService();
        os = System.getProperty("os.name");
        System.out.println("os: " + os);
        if (os.toLowerCase().contains("windows")) {
            System.out.println("OS: " + os);
        } else {
            preparePinout();
        }
        //controller = new GPIOController();
        //piExecutor = new PiExecutor(4);

        kuPlayerController = new KuPlayerController();

        trackListOriginal = new TrackMap();
        trackListToPlayFromSpeakers = new TrackMap();
        connectConfig = new ConnectConfiguration();

        initDrumKitParts();
    }

    private static Model instance = null;

    public static Model getInstance() {
        if (instance == null) {
            instance = new Model();
        }
        return instance;
    }

    public Pin getOutputPin(String name) {
        return outputPinMap.get(name);
    }

    private void preparePinout() {
        outputPinMap.put("pin0", pinoutService.createPin(PIN_0, "pin0"));
        outputPinMap.put("pin1", pinoutService.createPin(PIN_1, "pin1"));
        outputPinMap.put("pin2", pinoutService.createPin(PIN_2, "pin2"));
        outputPinMap.put("pin3", pinoutService.createPin(PIN_3, "pin3"));
        outputPinMap.put("pin4", pinoutService.createPin(PIN_4, "pin4"));
        outputPinMap.put("pin5", pinoutService.createPin(PIN_5, "pin5"));
        outputPinMap.put("pin6", pinoutService.createPin(PIN_6, "pin6"));
        outputPinMap.put("pin7", pinoutService.createPin(PIN_7, "pin7"));
        outputPinMap.put("pin8", pinoutService.createPin(PIN_8, "pin8"));
        outputPinMap.put("pin9", pinoutService.createPin(PIN_9, "pin9"));
    }

    private void initDrumKitParts() {
        drumKitPartElements = new ArrayList<>();
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
}
