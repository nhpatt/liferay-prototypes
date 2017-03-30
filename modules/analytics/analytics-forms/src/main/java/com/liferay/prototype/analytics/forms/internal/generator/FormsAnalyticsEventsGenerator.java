package com.liferay.prototype.analytics.forms.internal.generator;

import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.prototype.analytics.generator.AnalyticsEventsGenerator;

import org.osgi.service.component.annotations.Component;

/**
 * @author Michael C. Han
 */
@Component(
	immediate = true, property = {"messageFormat=FORMS"},
	service = AnalyticsEventsGenerator.class
)
public class FormsAnalyticsEventsGenerator implements AnalyticsEventsGenerator {

	@Override
	public JSONObject generateEvents() {
		return null;
	}

}