package com.liferay.prototype.analytics.web.internal;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.prototype.analytics.data.binding.stubs.AnalyticsEvents;
import com.liferay.prototype.analytics.data.binding.stubs.Event;
import com.liferay.prototype.analytics.processor.AnalyticsMessageProcessor;
import com.liferay.prototype.analytics.storage.AnalyticsMessageStorage;
import com.liferay.prototype.analytics.storage.StoredAnalyticsEventFactory;
import com.liferay.prototype.analytics.storage.stubs.StoredAnalyticsEvent;

import java.util.Collection;
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
public class AnalyticsEventsAnalyticMessageProcessor
	implements AnalyticsMessageProcessor<AnalyticsEvents> {

	@Override
	public void processMessage(AnalyticsEvents analyticsEvents) {
		List<Event> events = analyticsEvents.getEvents();

		if (ListUtil.isEmpty(events)) {
			if (_log.isDebugEnabled()) {
				_log.debug("No events found");
			}

			return;
		}

		Collection<StoredAnalyticsEvent> storedAnalyticsEvents =
			storedAnalyticsEventFactory.create(analyticsEvents);

		try {
			analyticsMessageStorage.store(storedAnalyticsEvents);
		}
		catch (PortalException pe) {
			if (_log.isWarnEnabled()) {
				_log.warn("Error storing events", pe);
			}
		}
	}

	@Reference
	protected AnalyticsMessageStorage<StoredAnalyticsEvent>
		analyticsMessageStorage;

	@Reference
	protected StoredAnalyticsEventFactory storedAnalyticsEventFactory;

	private static final Log _log = LogFactoryUtil.getLog(
		AnalyticsEventsAnalyticMessageProcessor.class);

}