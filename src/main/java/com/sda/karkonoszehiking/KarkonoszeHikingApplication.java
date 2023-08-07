package com.sda.karkonoszehiking;

import com.vaadin.flow.component.page.AppShellConfigurator;
import com.vaadin.flow.theme.Theme;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@Theme(value = "karkonoszehiking")
public class KarkonoszeHikingApplication implements AppShellConfigurator {

	public static void main(String[] args) {
		SpringApplication.run(KarkonoszeHikingApplication.class, args);
	}

}
