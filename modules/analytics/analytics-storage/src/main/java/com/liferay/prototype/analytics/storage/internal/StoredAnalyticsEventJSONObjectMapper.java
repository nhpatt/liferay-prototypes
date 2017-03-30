package com.liferay.prototype.analytics.storage.internal;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.liferay.prototype.analytics.data.binding.JSONObjectMapper;
import com.liferay.prototype.analytics.storage.stubs.StoredAnalyticsEvent;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;

import org.osgi.service.component.annotations.Component;

/**
 * @author Michael C. Han
 */
@Component(
	immediate = true,
	property = {
		"model=com.liferay.prototype.analytics.storage.stubs.StoredAnalyticsEvent"
	},
	service = JSONObjectMapper.class
)
public class StoredAnalyticsEventJSONObjectMapper
	implements JSONObjectMapper<StoredAnalyticsEvent> {

	@Override
	public StoredAnalyticsEvent convert(InputStream inputStream)
		throws IOException {

		ObjectMapper mapper = createObjectMapper();

		StoredAnalyticsEvent storedAnalyticsEvent = mapper.readValue(
			inputStream, StoredAnalyticsEvent.class);

		return storedAnalyticsEvent;
	}

	@Override
	public String convert(StoredAnalyticsEvent storedAnalyticsEvent)
		throws IOException {

		ObjectMapper mapper = createObjectMapper();

		try (StringWriter stringWriter = new StringWriter()) {
			mapper.writeValue(stringWriter, storedAnalyticsEvent);

			stringWriter.flush();

			return stringWriter.toString();
		}
	}

	public StoredAnalyticsEvent convert(String analyticsEventsJson)
		throws IOException {

		ObjectMapper mapper = createObjectMapper();

		StoredAnalyticsEvent storedAnalyticsEvent = mapper.readValue(
			analyticsEventsJson, StoredAnalyticsEvent.class);

		return storedAnalyticsEvent;
	}

	protected ObjectMapper createObjectMapper() {
		ObjectMapper objectMapper = new ObjectMapper();

		objectMapper.configure(
			MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, true);
		objectMapper.configure(
			DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

		return objectMapper;
	}

}