package com.yee.study.elasticsearch;

import com.yee.study.elasticsearch.api.GetApi;
import com.yee.study.elasticsearch.api.SearchApi;
import com.yee.study.elasticsearch.api.UpdateApi;
import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Roger.Yi
 */
public class Sample {

    private static final Logger logger = LoggerFactory.getLogger(Sample.class);

    public static void main(String[] args) throws Exception {

        RestHighLevelClient client = new RestHighLevelClient(
                RestClient.builder(
                        new HttpHost("localhost", 9200, "http")));

        // create an index sync
//        IndexApi.indexSync(client);

        // create an index async
//        IndexApi.indexAsync(client);

        // Get Doc sync
//        GetApi.getDocSync(client);

        // Get Doc Async¬
//        GetApi.getDocAsync(client);

        // Update a Doc
//        UpdateApi.getDocSync(client);

//        UpdateApi.getDocByTermVectors(client);

        // Search Doc
        SearchApi.search(client);
    }
}
