package com.liferay.prototype.analytics.storage;

import com.liferay.prototype.analytics.data.binding.stubs.AnalyticsEvents;
import com.liferay.prototype.analytics.storage.stubs.StoredAnalyticsEvent;

import java.util.Collection;

/**
 * @author Michael C. Han
 */
public interface StoredAnalyticsEventFactory {

	public Collection<StoredAnalyticsEvent> create(
		AnalyticsEvents analyticsEvents);

}