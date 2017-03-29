package com.liferay.prototype.analytics.processor;

import com.liferay.portal.kernel.json.JSONObject;

/**
 * @author Michael C. Han
 */
public interface AnalyticsMessageProcessor {

	public void processMessage(JSONObject jsonObject);

}
