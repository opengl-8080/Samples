package sample.spring.shell;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.util.StringUtils;

@SpringBootApplication
public class SampleApplication {

	public static void main(String[] args) {
		String[] disabledCommands = {"--spring.shell.command.clear.enabled=false"};
		String[] fullArgs = StringUtils.concatenateStringArrays(args, disabledCommands);
		SpringApplication.run(SampleApplication.class, fullArgs);
	}
}
