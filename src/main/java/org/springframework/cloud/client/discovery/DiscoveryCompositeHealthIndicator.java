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

package org.springframework.cloud.client.discovery;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.health.CompositeHealthIndicator;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthAggregator;
import org.springframework.boot.actuate.health.HealthIndicator;

/**
 * @author Spencer Gibb
 */
public class DiscoveryCompositeHealthIndicator extends CompositeHealthIndicator {

	@Autowired
	public DiscoveryCompositeHealthIndicator(HealthAggregator healthAggregator,
			List<DiscoveryHealthIndicator> indicators) {
		super(healthAggregator);
		for (DiscoveryHealthIndicator indicator : indicators) {
			addHealthIndicator(indicator.getName(), new Holder(indicator));
		}
	}

	public static class Holder implements HealthIndicator {
		DiscoveryHealthIndicator delegate;

		public Holder(DiscoveryHealthIndicator delegate) {
			this.delegate = delegate;
		}

		@Override
		public Health health() {
			return this.delegate.health();
		}
	}
}
