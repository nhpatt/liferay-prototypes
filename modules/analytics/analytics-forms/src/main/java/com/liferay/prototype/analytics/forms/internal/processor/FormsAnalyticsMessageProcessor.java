package com.liferay.prototype.analytics.forms.internal.processor;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.prototype.analytics.processor.AnalyticsMessageProcessor;
import com.liferay.prototype.analytics.storage.AnalyticsMessageStorage;

import java.util.ArrayList;
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
	implements AnalyticsMessageProcessor {

	@Override
	public void processMessage(JSONObject analyticsPayload) {
		JSONArray eventsJSONArray = analyticsPayload.getJSONArray("events");

		if (eventsJSONArray.length() == 0) {
			if (_log.isDebugEnabled()) {
				_log.debug("No events found");
			}

			return;
		}

		List<JSONObject> analyticsEvents = new ArrayList<>(
			eventsJSONArray.length());

		for (int i = 0; i < eventsJSONArray.length(); i++) {
			JSONObject indexableJSONObject = jsonFactory.createJSONObject();

			analyticsEvents.add(indexableJSONObject);

			JSONObject eventJSONObject = eventsJSONArray.getJSONObject(i);

			String timestamp = eventJSONObject.getString("timestamp");

			populateHeader(indexableJSONObject, analyticsPayload, timestamp);

			populateMessageBody(indexableJSONObject, eventJSONObject);
		}

		try {
			analyticsMessageStorage.store(analyticsEvents);
		}
		catch (PortalException pe) {
			if (_log.isWarnEnabled()) {
				_log.warn("Error storing events", pe);
			}
		}
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
		JSONObject indexableJSONObject, JSONObject eventJSONObject) {

		indexableJSONObject.put("event", eventJSONObject.get("event"));
	}

	@Reference
	protected AnalyticsMessageStorage analyticsMessageStorage;

	@Reference
	protected JSONFactory jsonFactory;

	private static final Log _log = LogFactoryUtil.getLog(
		FormsAnalyticsMessageProcessor.class);

}