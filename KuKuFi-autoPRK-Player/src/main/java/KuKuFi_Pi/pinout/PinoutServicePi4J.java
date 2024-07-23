package KuKuFi_Pi.pinout;

import com.pi4j.Pi4J;
import com.pi4j.context.Context;
import com.pi4j.io.gpio.digital.DigitalOutput;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class PinoutServicePi4J implements PinoutService {

    private final Context pi4j;

    public PinoutServicePi4J() {
        this.pi4j = Pi4J.newAutoContext();
    }


    @Override
    public void shutdown() {
        log.info("Shutdown PinoutService Pi4j");
        pi4j.shutdown();
    }

    @Override
    public Pin createPin(Integer pinNumber, String name) {
        DigitalOutput digitalOutput = pi4j.digitalOutput().create(pinNumber, name).high();
        return new PinPi4J(pinNumber, name, digitalOutput);
    }
}
