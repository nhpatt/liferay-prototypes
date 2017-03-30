package com.liferay.prototype.analytics.storage;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONObject;

import java.util.Collection;

/**
 * @author Michael C. Han
 */
public interface AnalyticsMessageStorage {

	public void store(Collection<JSONObject> jsonObjects)
		throws PortalException;

	public void store(JSONObject jsonObject) throws PortalException;

	public void store(JSONObject... jsonObjects) throws PortalException;

}