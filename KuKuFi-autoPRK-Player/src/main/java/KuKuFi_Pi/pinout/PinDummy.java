package KuKuFi_Pi.pinout;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Data
@AllArgsConstructor
public class PinDummy implements Pin {

    private final String name;

    @Override
    public void low() {
        log.info("PIN {} low", name);
    }

    @Override
    public void high() {
        log.info("PIN {} HIGH", name);
    }
}
