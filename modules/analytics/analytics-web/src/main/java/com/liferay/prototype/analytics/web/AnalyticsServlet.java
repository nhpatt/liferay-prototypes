package com.liferay.prototype.analytics.web;

import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.prototype.analytics.processor.AnalyticsMessageProcessor;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicy;
import org.osgi.service.component.annotations.ReferencePolicyOption;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Michael C. Han
 */
@Component(
	immediate = true,
	property = {
		"osgi.http.whiteboard.servlet.pattern=/analytics",
	},
	service = Servlet.class
)
public class AnalyticsServlet extends HttpServlet {

	@Override
	protected void service(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws ServletException, IOException {

		StringBundler sb = new StringBundler();

		String value = null;

		BufferedReader reader = httpServletRequest.getReader();

		while ((value = reader.readLine()) != null) {
			sb.append(value);
		}

		String analyticsMessage = sb.toString();
		try {
			JSONObject jsonObject = _jsonFactory.createJSONObject(
				analyticsMessage);

			String messageFormat = jsonObject.getString("messageFormat");

			if (Validator.isNull(messageFormat)) {
				if (_log.isWarnEnabled()) {
					_log.warn("No message format: " + analyticsMessage);
				}

				return;
			}

			AnalyticsMessageProcessor analyticsMessageProcessor =
				_analyticsMessageProcessors.get(messageFormat);

			if (analyticsMessageProcessor == null) {
				if (_log.isWarnEnabled()) {
					_log.warn("No message processor for : " + messageFormat);
				}

				return;
			}

			analyticsMessageProcessor.processMessage(jsonObject);
		}
		catch (JSONException e) {
			if (_log.isWarnEnabled()) {
				_log.warn("Could not parse: " + analyticsMessage, e);
			}
		}
	}

	@Reference(
		cardinality = ReferenceCardinality.AT_LEAST_ONE,
		policy = ReferencePolicy.DYNAMIC,
		policyOption = ReferencePolicyOption.GREEDY,
		unbind = "removeAnalyticsMessageProcessor"
	)
	protected void addAnalyticsMessageProcessor(
		AnalyticsMessageProcessor analyticsMessageProcessor,
		Map<String, Object> properties) {

		String messageFormat = GetterUtil.getString(
			properties.get("messageFormat"));

		_analyticsMessageProcessors.put(
			messageFormat, analyticsMessageProcessor);
	}

	protected void removeAnalyticsMessageProcessor(
		AnalyticsMessageProcessor analyticsMessageProcessor,
		Map<String, Object> properties) {

		String messageFormat = GetterUtil.getString(
			properties.get("messageFormat"));

		_analyticsMessageProcessors.remove(messageFormat);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		AnalyticsServlet.class);

	@Reference
	private JSONFactory _jsonFactory;

	private Map<String, AnalyticsMessageProcessor> _analyticsMessageProcessors =
		new ConcurrentHashMap<>();

}
