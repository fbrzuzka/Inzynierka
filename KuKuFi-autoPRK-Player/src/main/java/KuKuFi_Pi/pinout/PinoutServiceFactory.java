package KuKuFi_Pi.pinout;

import org.apache.commons.lang3.SystemUtils;

public class PinoutServiceFactory {

    public PinoutService createService() {
        if (SystemUtils.IS_OS_LINUX
                && !SystemUtils.OS_ARCH.equals("amd64")) { // raspberry
            return new PinoutServicePi4J();
        }
        return new PinoutServiceDummy();
    }
}
