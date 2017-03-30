package com.liferay.prototype.analytics.forms.internal.gogo;

import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.prototype.analytics.generator.AnalyticsEventsGenerator;
import com.liferay.prototype.analytics.processor.AnalyticsMessageProcessor;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import java.net.URI;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Michael C. Han
 */
@Component(
	immediate = true,
	property = {
		"osgi.command.function=generate", "osgi.command.function=load",
		"osgi.command.scope=analytics"
	},
	service = FormsAnalyticsGogoClient.class
)
public class FormsAnalyticsGogoClient {

	public void generate(int count) {
		for (int i = 0; i < count; i++) {
			JSONObject analyticsEvent =
				_analyticsEventsGenerator.generateEvents();

			_formsAnalyticsMessageProcessor.processMessage(analyticsEvent);
		}
	}

	public void load(String fileURI) {
		URI uri = URI.create(fileURI);

		try (InputStream in = uri.toURL().openStream()) {
			BufferedReader bufferedReader = new BufferedReader(
				new InputStreamReader(in));

			String jsonString = null;

			while ((jsonString = bufferedReader.readLine()) != null) {
				JSONObject jsonObject = _jsonFactory.createJSONObject(
					jsonString);

				_formsAnalyticsMessageProcessor.processMessage(jsonObject);
			}
		}
		catch (Exception e) {
			_log.error(e, e);
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		FormsAnalyticsGogoClient.class);

	@Reference(target = "(messageFormat=FORMS)")
	private AnalyticsEventsGenerator _analyticsEventsGenerator;

	@Reference(target = "(messageFormat=FORMS)")
	private AnalyticsMessageProcessor _formsAnalyticsMessageProcessor;

	@Reference
	private JSONFactory _jsonFactory;

}