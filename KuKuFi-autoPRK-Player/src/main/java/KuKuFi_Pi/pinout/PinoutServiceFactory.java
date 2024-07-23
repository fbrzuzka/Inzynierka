package KuKuFi_Pi.pinout;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.SystemUtils;

@Slf4j
public class PinoutServiceFactory {

    public PinoutService createService() {
        if (SystemUtils.IS_OS_LINUX
                && !SystemUtils.OS_ARCH.equals("amd64")) { // raspberry
            log.info("Create PinoutServicePi4J");
            return new PinoutServicePi4J();
        }
        log.info("Create PinoutServiceDummy");
        return new PinoutServiceDummy();
    }
}
