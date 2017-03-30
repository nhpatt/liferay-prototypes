package com.liferay.prototype.analytics.data.binding.internal;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.liferay.prototype.analytics.data.binding.JSONObjectMapper;
import com.liferay.prototype.analytics.data.binding.stubs.AnalyticsEvents;

import java.io.IOException;
import java.io.StringWriter;

import org.osgi.service.component.annotations.Component;

/**
 * @author Michael C. Han
 */
@Component(
	immediate = true,
	property = {
		"model=com.liferay.prototype.analytics.data.binding.stubs.AnalyticsEvents"
	},
	service = JSONObjectMapper.class
)
public class AnalyticsEventsJSONObjectMapper
	implements JSONObjectMapper<AnalyticsEvents> {

	@Override
	public String convert(AnalyticsEvents analyticsEvents) throws IOException {
		ObjectMapper mapper = createObjectMapper();

		try (StringWriter stringWriter = new StringWriter()) {
			mapper.writeValue(stringWriter, analyticsEvents);

			stringWriter.flush();

			return stringWriter.toString();
		}
	}

	public AnalyticsEvents convert(String analyticsEventsJson)
		throws IOException {

		ObjectMapper mapper = createObjectMapper();

		AnalyticsEvents analyticsEvents = mapper.readValue(
			analyticsEventsJson, AnalyticsEvents.class);

		return analyticsEvents;
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