package com.liferay.prototype.analytics.storage.elasticsearch.internal.storage;

import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.search.SearchException;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Props;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.prototype.analytics.storage.AnalyticsMessageStorage;
import com.liferay.prototype.analytics.storage.elasticsearch.internal.configuration.ElasticsearchAnalyticsMessageStorageConfiguration;

import java.net.InetAddress;

import java.text.SimpleDateFormat;

import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.Map;

import org.elasticsearch.action.admin.indices.create.CreateIndexRequestBuilder;
import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
import org.elasticsearch.action.admin.indices.exists.indices.IndicesExistsRequestBuilder;
import org.elasticsearch.action.admin.indices.exists.indices.IndicesExistsResponse;
import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexRequestBuilder;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.IndicesAdminClient;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.transport.client.PreBuiltTransportClient;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Michael C. Han
 */
@Component(
	configurationPid ="com.liferay.prototype.analytics.forms.internal.configuration.ElasticsearchAnalyticsMessageStorageConfiguration",
	immediate = true, service = AnalyticsMessageStorage.class
)
public class ElasticsearchAnalyticsMessageStorage
	implements AnalyticsMessageStorage {

	@Override
	public void store(Collection<JSONObject> jsonObjects)
		throws PortalException {

		try {
			BulkRequestBuilder bulkRequestBuilder =
				transportClient.prepareBulk();

			for (JSONObject jsonObject : jsonObjects) {
				IndexRequestBuilder indexRequestBuilder =
					transportClient.prepareIndex(getIndexName(), "analytics");

				indexRequestBuilder.setSource(
					jsonObject.toString(), XContentType.JSON);

				bulkRequestBuilder.add(indexRequestBuilder);
			}

			BulkResponse bulkResponse = bulkRequestBuilder.get();

			LogUtil.logActionResponse(_log, bulkResponse);
		}
		catch (Exception e) {
			throw new SearchException("Unable to insert data" + jsonObjects, e);
		}
	}

	@Override
	public void store(JSONObject jsonObject) throws PortalException {
		try {
			IndexRequestBuilder indexRequestBuilder =
				transportClient.prepareIndex(getIndexName(), "analytics");

			indexRequestBuilder.setSource(
				jsonObject.toString(), XContentType.JSON);

			IndexResponse indexResponse = indexRequestBuilder.get();

			LogUtil.logActionResponse(_log, indexResponse);
		}
		catch (Exception e) {
			throw new SearchException("Unable to insert data" + jsonObject, e);
		}
	}

	@Override
	public void store(JSONObject... jsonObjects) throws PortalException {
		store(Arrays.asList(jsonObjects));
	}

	@Activate
	@Modified
	protected void activate(Map<String, Object> properties) throws Exception {
		_elasticsearchAnalyticsMessageStorageConfiguration =
			ConfigurableUtil.createConfigurable(
				ElasticsearchAnalyticsMessageStorageConfiguration.class,
				properties);

		Settings.Builder settingsBuilder = Settings.builder();

		settingsBuilder.put(
			"client.transport.ignore_cluster_name",
			_elasticsearchAnalyticsMessageStorageConfiguration.
				clientTransportIgnoreClusterName());
		settingsBuilder.put(
			"client.transport.nodes_sampler_interval",
			_elasticsearchAnalyticsMessageStorageConfiguration.
				clientTransportNodesSamplerInterval());
		settingsBuilder.put(
			"client.transport.sniff",
			_elasticsearchAnalyticsMessageStorageConfiguration.
				clientTransportSniff());
		settingsBuilder.put(
			"cluster.name",
			_elasticsearchAnalyticsMessageStorageConfiguration.clusterName());
		settingsBuilder.put(
			"path.logs", props.get(PropsKeys.LIFERAY_HOME) + "/logs");

		transportClient = new PreBuiltTransportClient(settingsBuilder.build());

		String[] transportAddresses =
			_elasticsearchAnalyticsMessageStorageConfiguration.
				transportAddresses();

		for (String transportAddress : transportAddresses) {
			String[] transportAddressParts = StringUtil.split(
				transportAddress, StringPool.COLON);

			String host = transportAddressParts[0];

			int port = GetterUtil.getInteger(transportAddressParts[1]);

			InetAddress inetAddress = InetAddress.getByName(host);

			transportClient.addTransportAddress(
				new InetSocketTransportAddress(inetAddress, port));
		}

		checkIndices();
	}

	protected void checkIndices() throws Exception {
		IndicesAdminClient indicesAdminClient =
			transportClient.admin().indices();

		String indexName = getIndexName();

		if (hasIndex(indicesAdminClient, indexName)) {
			return;
		}

		createIndex(indexName, indicesAdminClient);
	}

	protected void createIndex(
			String indexName, IndicesAdminClient indicesAdminClient)
		throws Exception {

		CreateIndexRequestBuilder createIndexRequestBuilder =
			indicesAdminClient.prepareCreate(indexName);

		String mappings = ResourceUtil.getResourceAsString(
			getClass(), "/META-INF/mappings.json");

		createIndexRequestBuilder.addMapping(
			"analysis", mappings, XContentType.JSON);

		Settings.Builder builder = Settings.builder();

		String settings = ResourceUtil.getResourceAsString(
			getClass(), "/META-INF/settings.json");

		builder.loadFromSource(settings, XContentType.JSON);

		createIndexRequestBuilder.setSettings(builder);

		CreateIndexResponse createIndexResponse =
			createIndexRequestBuilder.get();

		LogUtil.logActionResponse(_log, createIndexResponse);
	}

	@Deactivate
	protected void deactivate() {
		transportClient.close();
	}

	protected String getIndexName() {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy.mm.dd");

		return
			_elasticsearchAnalyticsMessageStorageConfiguration.
				indexNamePrefix() + simpleDateFormat.format(new Date());
	}

	protected boolean hasIndex(
			IndicesAdminClient indicesAdminClient, String indexName)
		throws Exception {

		IndicesExistsRequestBuilder indicesExistsRequestBuilder =
			indicesAdminClient.prepareExists(indexName);

		IndicesExistsResponse indicesExistsResponse =
			indicesExistsRequestBuilder.get();

		return indicesExistsResponse.isExists();
	}

	@Reference
	protected Props props;

	protected TransportClient transportClient;

	private static final Log _log = LogFactoryUtil.getLog(
		ElasticsearchAnalyticsMessageStorage.class);

	private volatile ElasticsearchAnalyticsMessageStorageConfiguration
		_elasticsearchAnalyticsMessageStorageConfiguration;

}