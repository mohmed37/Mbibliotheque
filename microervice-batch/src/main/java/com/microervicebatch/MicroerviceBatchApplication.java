package com.microervicebatch;

import brave.sampler.Sampler;
import com.microervicebatch.web.controleur.EnvoiMail;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;


@SpringBootApplication
@EnableConfigurationProperties
@EnableDiscoveryClient
public class MicroerviceBatchApplication {

	public MicroerviceBatchApplication() throws SchedulerException {
	}

	public static void main(String[] args) {
		SpringApplication.run(MicroerviceBatchApplication.class, args);


	}
	@Bean
	public Sampler defaultSampler(){
		return Sampler.ALWAYS_SAMPLE;
	}

	/*@Bean
	public static void mail() throws Exception {
		JobDetail job = JobBuilder.newJob(EnvoiMail.class).
				withIdentity("myJob", "group1").build();

		Trigger trigger = TriggerBuilder
				.newTrigger()
				.withIdentity("myTrigger", "group1")
				.withSchedule(
						SimpleScheduleBuilder.simpleSchedule()
								.withIntervalInMinutes(1).repeatForever())
				.build();

		// schedule it
		Scheduler scheduler = new StdSchedulerFactory().getScheduler();
		scheduler.start();
		scheduler.scheduleJob(job, trigger);

	}*/

}
