package com.liferay.prototype.analytics.internal.generator;

import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.prototype.analytics.data.binding.stubs.AdditionalInfo;
import com.liferay.prototype.analytics.data.binding.stubs.AnalyticsEvents;
import com.liferay.prototype.analytics.data.binding.stubs.Context;
import com.liferay.prototype.analytics.data.binding.stubs.Event;
import com.liferay.prototype.analytics.data.binding.stubs.Location;
import com.liferay.prototype.analytics.generator.AnalyticsEventsGenerator;
import com.liferay.prototype.analytics.internal.generator.configuration.AnalyticsEventsGeneratorConfiguration;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.OptionalDouble;
import java.util.OptionalInt;
import java.util.OptionalLong;
import java.util.Random;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;

/**
 * @author Michael C. Han
 */
@Component(
	immediate = true,
	property = {
		"model=com.liferay.prototype.analytics.data.binding.stubs.AnalyticsEvents"
	},
	service = AnalyticsEventsGenerator.class
)
public class DefaultAnalyticsEventsGenerator
	implements AnalyticsEventsGenerator<AnalyticsEvents> {

	@Override
	public AnalyticsEvents generateEvents() {
		AnalyticsEvents analyticsEvents = new AnalyticsEvents();

		Random random = new Random();

		analyticsEvents.setApplicationId(
			_analyticsEventsGeneratorConfiguration.applicationId());

		analyticsEvents.setChannel(
			_analyticsEventsGeneratorConfiguration.channel());

		analyticsEvents.setContext(createContext(random));

		List<Event> events = createEvents(random);

		analyticsEvents.setEvents(events);

		analyticsEvents.setMessageFormat(
			_analyticsEventsGeneratorConfiguration.messageFormat());

		return analyticsEvents;
	}

	@Activate
	@Modified
	protected void activate(Map<String, Object> properties)
		throws Exception {
		_analyticsEventsGeneratorConfiguration =
			ConfigurableUtil.createConfigurable(
				AnalyticsEventsGeneratorConfiguration.class, properties);

		DateFormat format = new SimpleDateFormat(_DATE_FORMAT_STRING);

		_timestampStart = format.parse(
			_analyticsEventsGeneratorConfiguration.timestampStart());

		_timestampEnd = format.parse(
			_analyticsEventsGeneratorConfiguration.timestampEnd());
	}

	protected Context createContext(Random random) {
		Context context = new Context();

		context.setCompanyId(
			_analyticsEventsGeneratorConfiguration.companyId());
		context.setDeviceId(StringUtil.randomId());
		context.setDeviceType(randomDeviceType(random));
		context.setLanguageId("en_US");
		context.setLocation(randomLocation(random));
		context.setSignedIn(true);
		context.setUserId(randomUserId(random));

		return context;
	}

	protected List<Event> createEvents(Random random) {
		OptionalInt optionalInt = random.ints(1, 5, 20).findAny();

		int numEvents = optionalInt.orElse(5);

		List<Event> events = new ArrayList<>(numEvents);

		long timestampStart = randomTimestampStart(random);

		DateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");

		for (int i = 0; i < numEvents; i++) {
			Event event = new Event();

			event.setGroupId(_analyticsEventsGeneratorConfiguration.groupId());

			long timestamp =
				timestampStart * Math.round(random.nextDouble() * 10000);

			Date date = new Date(timestamp);

			event.setTimestamp(format.format(date));

			AdditionalInfo additionalInfo = new AdditionalInfo();
			additionalInfo.setTime(random.nextInt());

			event.setAdditionalInfo(additionalInfo);

			events.add(event);
		}

		return events;
	}

	protected long randomTimestampStart(Random random) {
		OptionalLong optionalLong = random.longs(
			1, _timestampStart.getTime(), _timestampEnd.getTime()).findAny();

		return optionalLong.orElse(System.currentTimeMillis());
	}

	protected String randomDeviceType(Random random) {
		String[] deviceTypes =
			_analyticsEventsGeneratorConfiguration.deviceTypes();

		int index = random.nextInt(deviceTypes.length);

		return deviceTypes[index];
	}

	protected Location randomLocation(Random random) {
		Location location = new Location();

		OptionalDouble lon = random.doubles(1, 30.0, 45.0).findAny();

		lon.ifPresent(location::setLongitude);

		OptionalDouble lat = random.doubles(1, 70, 125.0).findAny();

		lat.ifPresent(location::setLatitude);

		return location;
	}

	protected int randomUserId(Random random) {
		return random.nextInt(5000);
	}

	private static final String _DATE_FORMAT_STRING =
		"yyyy-MM-dd'T'HH:mm:ss.SSSZ";

	private volatile AnalyticsEventsGeneratorConfiguration
		_analyticsEventsGeneratorConfiguration;

	private Date _timestampStart;
	private Date _timestampEnd;

}