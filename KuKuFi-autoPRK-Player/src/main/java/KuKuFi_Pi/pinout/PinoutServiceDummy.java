package KuKuFi_Pi.pinout;

import com.pi4j.Pi4J;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class PinoutServiceDummy implements PinoutService{


    @Override
    public void shutdown() {
        log.info("Shutdown PinoutServiceDummy");
    }

    @Override
    public Pin createPin(Integer pinNumber, String name) {
        return new PinDummy(name);
    }
}
