package KuKuFi_Pi.pinout;

public interface PinoutService {

    void shutdown();

    Pin createPin(Integer pinNumber, String name);
}
