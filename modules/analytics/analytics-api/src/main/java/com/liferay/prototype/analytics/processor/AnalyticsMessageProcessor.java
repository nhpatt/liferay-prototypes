package com.liferay.prototype.analytics.processor;

/**
 * @author Michael C. Han
 */
public interface AnalyticsMessageProcessor<T> {

	public void processMessage(T t);

}