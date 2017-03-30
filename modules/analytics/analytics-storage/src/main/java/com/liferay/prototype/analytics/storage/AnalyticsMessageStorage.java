package com.liferay.prototype.analytics.storage;

import com.liferay.portal.kernel.exception.PortalException;

import java.util.Collection;

/**
 * @author Michael C. Han
 */
public interface AnalyticsMessageStorage<T> {

	public void store(Collection<T> analyticsMessages) throws PortalException;

	public void store(T analyticsMessage) throws PortalException;

	public void store(T... analyticsMessages) throws PortalException;

}