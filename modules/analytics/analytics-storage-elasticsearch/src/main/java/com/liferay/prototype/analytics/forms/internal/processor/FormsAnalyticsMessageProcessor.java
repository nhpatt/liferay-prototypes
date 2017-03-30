package com.liferay.prototype.analytics.forms.internal.processor;

import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.prototype.analytics.data.binding.stubs.AnalyticsEvents;
import com.liferay.prototype.analytics.data.binding.stubs.Event;
import com.liferay.prototype.analytics.processor.AnalyticsMessageProcessor;
import com.liferay.prototype.analytics.storage.AnalyticsMessageStorage;

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Michael C. Han
 */
@Component(
	immediate = true, property = {"messageFormat=FORMS"},
	service = AnalyticsMessageProcessor.class
)
public class FormsAnalyticsMessageProcessor
	implements AnalyticsMessageProcessor<AnalyticsEvents> {

	@Override
	public void processMessage(AnalyticsEvents analyticsEvents) {
		List<Event> events = analyticsEvents.getEvents();

		if (ListUtil.isEmpty(events)) {
			if (_log.isDebugEnabled()) {
				_log.debug("No events found");
			}

			return;
		}
/*
		try {
			for (Event event : events) {
				JSONObject indexableJSONObject = jsonFactory.createJSONObject();

				analyticsEvents.add(indexableJSONObject);

				JSONObject eventJSONObject = eventsJSONArray.getJSONObject(i);

				String timestamp = eventJSONObject.getString("timestamp");

				populateHeader(
					indexableJSONObject, analyticsPayload, timestamp);

				populateMessageBody(indexableJSONObject, eventJSONObject);
			}

			analyticsMessageStorage.store(analyticsEvents);
		}
		catch (PortalException pe) {
			if (_log.isWarnEnabled()) {
				_log.warn("Error storing events", pe);
			}
		}*/
	}

	protected void populateHeader(
		JSONObject eventJSONObject, JSONObject analyticsPayload,
		String timestamp) {

		eventJSONObject.put(
			"applicationId", analyticsPayload.getString("applicationId"));
		eventJSONObject.put("channel", analyticsPayload.getString("channel"));
		eventJSONObject.put(
			"context", analyticsPayload.getJSONObject("context"));
		eventJSONObject.put(
			"messageFormat", analyticsPayload.getString("messageFormat"));
		eventJSONObject.put("timestamp", timestamp);
	}

	protected void populateMessageBody(
			JSONObject indexableJSONObject, JSONObject eventJSONObject)
		throws JSONException {

		String additionalInfoString = eventJSONObject.getString(
			"additionalInfo");

		JSONObject additionalInfo = jsonFactory.createJSONObject(
			additionalInfoString);

		indexableJSONObject.put("additionalInfo", additionalInfo);

		indexableJSONObject.put("event", eventJSONObject.getString("event"));
		indexableJSONObject.put(
			"groupId", eventJSONObject.getString("groupId"));
		indexableJSONObject.put(
			"properties", eventJSONObject.getJSONObject("properties"));
	}

	@Reference
	protected AnalyticsMessageStorage analyticsMessageStorage;

	@Reference
	protected JSONFactory jsonFactory;

	private static final Log _log = LogFactoryUtil.getLog(
		FormsAnalyticsMessageProcessor.class);

}