package com.liferay.prototype.analytics.generator;

import com.liferay.portal.kernel.json.JSONObject;

/**
 * @author Michael C. Han
 */
public interface AnalyticsEventsGenerator {

	public JSONObject generateEvents();

}