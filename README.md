# liferay-prototypes

Simple prototype for receiving and parsing analytics data.

####Pre-requisites:
1. Requires latest Blade (https://dev.liferay.com/develop/tutorials/-/knowledge_base/7-0/installing-blade-cli)
2. Once cloned and Blade installed:
     * blade initBundle
     * blade gw deploy

####Starting Elasticsearch
1. Download and Install Elasticsearch 5.3: https://artifacts.elastic.co/downloads/elasticsearch/elasticsearch-5.3.0.zip
2. Copy configurations from configs/common/elasticsearch/config to $ES_HOME/config
     * TCP port will be 9353-9453.
     * HTTP port will be 9253

####Starting Kibana
1. Download and Install Kibana 5.3: https://www.elastic.co/downloads/kibana (Platform specific)
2. Copy configurations from configs/common/kibana/config to $KIBANA_HOME/config
3. Start Kibana