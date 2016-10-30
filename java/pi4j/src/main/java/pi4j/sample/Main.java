package pi4j.sample;

import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPinDigitalInput;
import com.pi4j.io.gpio.GpioPinDigitalOutput;
import com.pi4j.io.gpio.PinPullResistance;
import com.pi4j.io.gpio.PinState;
import com.pi4j.io.gpio.RaspiPin;
import com.pi4j.io.gpio.event.GpioPinListenerDigital;

public class Main {
    public static void main(String[] args) throws Exception {
        GpioController controller = GpioFactory.getInstance();

        GpioPinDigitalInput input = controller.provisionDigitalInputPin(RaspiPin.GPIO_01, "button", PinPullResistance.PULL_DOWN);
        input.setShutdownOptions(true, PinState.LOW, PinPullResistance.OFF);

        GpioPinDigitalOutput output = controller.provisionDigitalOutputPin(RaspiPin.GPIO_07, "led", PinState.HIGH);
        output.setShutdownOptions(true, PinState.LOW, PinPullResistance.OFF);

        GpioPinListenerDigital listener = e -> {
            if (e.getState() == PinState.LOW) {
                output.toggle();
            }
        };

        input.addListener(listener);

        try {
            System.out.println("running...");
            while (true) {
                Thread.sleep(1000);
            }
        } finally {
            controller.shutdown();
        }
    }
}
