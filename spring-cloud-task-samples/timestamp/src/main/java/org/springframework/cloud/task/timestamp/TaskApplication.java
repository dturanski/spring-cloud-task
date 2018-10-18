/*
 * Copyright 2015 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.springframework.cloud.task.timestamp;

import java.util.OptionalLong;
import java.util.stream.Stream;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.task.configuration.EnableTask;
import org.springframework.context.annotation.Bean;
import org.springframework.util.StringUtils;

/**
 * Spring Boot Application that has tasks enabled.
 */
@SpringBootApplication
@EnableTask
@EnableConfigurationProperties({ TimestampTaskProperties.class })
public class TaskApplication {

	private static final Log logger = LogFactory.getLog(TaskApplication.class);

	@Bean
	public CommandLineRunner commandLineRunner() {
		return args -> {
			logger.info("Running with args:" + StringUtils.arrayToCommaDelimitedString(args));

			OptionalLong sleep = Stream.of(args)
				.filter(s -> s.startsWith("--sleep="))
				.mapToLong(s -> (Long.valueOf(s.split("=")[1])))
				.findFirst();

			if (sleep.isPresent()) {
				Thread.sleep(sleep.getAsLong());
			}
		};
	}

	public static void main(String[] args) {
		SpringApplication.run(TaskApplication.class, args);
	}
}
