package com.liferay.prototype.analytics.storage.internal;

import com.liferay.prototype.analytics.data.binding.stubs.AnalyticsEvents;
import com.liferay.prototype.analytics.storage.StoredAnalyticsEventFactory;
import com.liferay.prototype.analytics.storage.stubs.StoredAnalyticsEvent;

import java.util.Collection;

import org.osgi.service.component.annotations.Component;

/**
 * @author Michael C. Han
 */
@Component(immediate = true, service = StoredAnalyticsEventFactory.class)
public class DefaultStoredAnalyticsEventFactory
	implements StoredAnalyticsEventFactory {

	@Override
	public Collection<StoredAnalyticsEvent> create(
		AnalyticsEvents analyticsEvents) {

		return null;
	}

}