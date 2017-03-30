package com.liferay.prototype.analytics.forms.internal.configuration;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

/**
 * @author Michael C. Han
 */
@ExtendedObjectClassDefinition(category = "prototype")
@Meta.OCD(
	id = "com.liferay.prototype.analytics.forms.internal.configuration.ElasticsearchAnalyticsMessageStorageConfiguration",
	name = "Elasticsearch Analytics Message Storage Configuration"
)
public interface ElasticsearchAnalyticsMessageStorageConfiguration {

	@Meta.AD(deflt = "AnalyticsPrototype", required = false)
	public String clusterName();

	@Meta.AD(deflt = "analytics-", required = false)
	public String indexNamePrefix();

	@Meta.AD(deflt = "localhost:9300", required = false)
	public String[] transportAddresses();

	@Meta.AD(deflt = "true", required = false)
	public boolean clientTransportSniff();

	@Meta.AD(deflt = "false", required = false)
	public boolean clientTransportIgnoreClusterName();

	@Meta.AD(deflt = "5s", required = false)
	public String clientTransportNodesSamplerInterval();

}