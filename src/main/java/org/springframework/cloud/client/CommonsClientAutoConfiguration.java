/*
 * Copyright 2013-2015 the original author or authors.
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

package org.springframework.cloud.client;

import java.util.List;

import org.springframework.boot.actuate.health.HealthAggregator;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.discovery.DiscoveryClientHealthIndicator;
import org.springframework.cloud.client.discovery.DiscoveryCompositeHealthIndicator;
import org.springframework.cloud.client.discovery.DiscoveryHealthIndicator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

/**
 * @author Spencer Gibb
 */
@Configuration
@ConditionalOnClass(HealthIndicator.class)
@Order(0)
public class CommonsClientAutoConfiguration {

	@Configuration
	@ConditionalOnBean(DiscoveryClient.class)
	@ConditionalOnExpression("${spring.cloud.discovery.enabled:true}")
	protected static class HealthConfiguration {
		@Bean
		public DiscoveryClientHealthIndicator instancesHealthIndicator(
				DiscoveryClient discoveryClient) {
			return new DiscoveryClientHealthIndicator(discoveryClient);
		}

		@Bean
		public DiscoveryCompositeHealthIndicator discoveryHealthIndicator(
				HealthAggregator aggregator, List<DiscoveryHealthIndicator> indicators) {
			return new DiscoveryCompositeHealthIndicator(aggregator, indicators);
		}
	}
}
