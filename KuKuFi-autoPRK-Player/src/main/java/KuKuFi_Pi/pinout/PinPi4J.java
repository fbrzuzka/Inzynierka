package KuKuFi_Pi.pinout;

import com.pi4j.io.gpio.digital.DigitalOutput;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Getter
@Slf4j
@AllArgsConstructor
public class PinPi4J implements Pin {

    private final int number;
    private final String name;
    private final DigitalOutput digitalOutput;

    @Override
    public void low() {
        digitalOutput.low();
    }

    @Override
    public void high() {
        digitalOutput.high();
    }
}
