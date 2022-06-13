package com.pegasystems.vote.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Properties specific to Vote.
 * <p>
 * Properties are configured in the {@code application.yml} file.
 */
@ConfigurationProperties(prefix = "application", ignoreUnknownFields = false)
public class ApplicationProperties {}
