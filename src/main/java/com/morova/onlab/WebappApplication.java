package com.morova.onlab;

import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class WebappApplication {

	public static void main(String[] args) {
		SpringApplication.run(WebappApplication.class, args);
	}

	@Bean
	ApplicationRunner runner(MeterRegistry meterRegistry, LoadSimulatorManager loadSimulatorManager) {
		return args -> {
			Gauge.builder("free.worker.threads", loadSimulatorManager, LoadSimulatorManager::getFreeWorkerThreadNum)
					.register(meterRegistry);
			Gauge.builder(
					"active.worker.threads",
					loadSimulatorManager,
					LoadSimulatorManager::getActiveWorkerThreadNum
			).register(meterRegistry);
		};
	}

}