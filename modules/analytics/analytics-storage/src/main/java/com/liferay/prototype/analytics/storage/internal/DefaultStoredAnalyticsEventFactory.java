package com.liferay.prototype.analytics.storage.internal;

import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.prototype.analytics.data.binding.stubs.AnalyticsEvents;
import com.liferay.prototype.analytics.data.binding.stubs.Event;
import com.liferay.prototype.analytics.storage.StoredAnalyticsEventFactory;
import com.liferay.prototype.analytics.storage.stubs.AdditionalInfo;
import com.liferay.prototype.analytics.storage.stubs.Context;
import com.liferay.prototype.analytics.storage.stubs.Location;
import com.liferay.prototype.analytics.storage.stubs.Properties;
import com.liferay.prototype.analytics.storage.stubs.StoredAnalyticsEvent;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

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

		List<Event> events = analyticsEvents.getEvents();

		if (ListUtil.isEmpty(events)) {
			return Collections.emptyList();
		}

		Collection<StoredAnalyticsEvent> storedAnalyticsEvents =
			new ArrayList<>(events.size());

		for (Event event : events) {
			StoredAnalyticsEvent storedAnalyticsEvent =
				new StoredAnalyticsEvent();

			storedAnalyticsEvent.setAdditionalInfo(
				convert(event.getAdditionalInfo()));

			storedAnalyticsEvent.setApplicationId(
				analyticsEvents.getApplicationId());

			storedAnalyticsEvent.setChannel(analyticsEvents.getChannel());

			storedAnalyticsEvent.setContext(
				convert(analyticsEvents.getContext()));

			storedAnalyticsEvent.setEvent(event.getEvent());
			storedAnalyticsEvent.setGroupId(event.getGroupId());
			storedAnalyticsEvent.setMessageFormat(
				analyticsEvents.getMessageFormat());

			storedAnalyticsEvent.setProperties(convert(event.getProperties()));
			storedAnalyticsEvent.setTimestamp(event.getTimestamp());
		}

		return storedAnalyticsEvents;
	}

	protected AdditionalInfo convert(
		com.liferay.prototype.analytics.data.binding.stubs.AdditionalInfo
			additionalInfo) {

		AdditionalInfo storageAdditionalInfo = new AdditionalInfo();

		storageAdditionalInfo.setTime(additionalInfo.getTime());

		Map<String, Object> additionalProperties =
			additionalInfo.getAdditionalProperties();

		additionalProperties.forEach(
			storageAdditionalInfo::setAdditionalProperty);

		return storageAdditionalInfo;
	}

	protected Context convert(
		com.liferay.prototype.analytics.data.binding.stubs.Context context) {

		Context storedContext = new Context();

		storedContext.setCompanyId(context.getCompanyId());
		storedContext.setDeviceId(context.getDeviceId());
		storedContext.setLanguageId(context.getLanguageId());
		storedContext.setSignedIn(context.getSignedIn());
		storedContext.setSessionId(context.getSessionId());
		storedContext.setUserId(context.getUserId());

		storedContext.setLocation(convert(context.getLocation()));

		Map<String, Object> additionalProperties =
			context.getAdditionalProperties();

		additionalProperties.forEach(storedContext::setAdditionalProperty);

		return storedContext;
	}

	protected Location convert(
		com.liferay.prototype.analytics.data.binding.stubs.Location location) {

		Location storedLocation = new Location();

		storedLocation.setLat(location.getLatitude());
		storedLocation.setLon(location.getLongitude());

		Map<String, Object> additionalProperties =
			location.getAdditionalProperties();

		additionalProperties.forEach(storedLocation::setAdditionalProperty);

		return storedLocation;
	}

	protected Properties convert(
		com.liferay.prototype.analytics.data.binding.stubs.Properties
			properties) {

		Properties storedProperties = new Properties();

		storedProperties.setElementId(properties.getElementId());
		storedProperties.setEntityType(properties.getEntityType());
		storedProperties.setReferrers(properties.getReferrers());

		Map<String, Object> additionalProperties =
			properties.getAdditionalProperties();

		additionalProperties.forEach(storedProperties::setAdditionalProperty);

		return storedProperties;
	}

}