package com.yee.study.elasticsearch.api;

import org.elasticsearch.action.ActionListener;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.common.xcontent.XContentType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * 创建索引
 *
 * @author Roger.Yi
 */
public class IndexApi {

    private static final Logger logger = LoggerFactory.getLogger(IndexApi.class);

    /**
     * 创建索引（同步方式）
     *
     * @param client
     * @throws IOException
     */
    public static void indexSync(RestHighLevelClient client) throws IOException {
        CreateIndexRequest req;
        IndexRequest request = new IndexRequest("posts");
        request.id("1");
        request.type("post");
        String jsonString = "{" +
                "\"user\":\"Roger\"," +
                "\"postDate\":\"2013-01-30\"," +
                "\"message\":\"trying out Elasticsearch\"" +
                "}";
        request
        request.source(jsonString, XContentType.JSON);
        client.index(request, RequestOptions.DEFAULT);
        logger.info("create index sync successfully.");
    }

    /**
     * 创建索引（异步方式）
     *
     * @param client
     * @throws IOException
     */
    public static void indexAsync(RestHighLevelClient client) throws IOException {
        IndexRequest request = new IndexRequest("posts");
        request.id("2");
        request.type("post");
        String jsonString = "{" +
                "\"user\":\"Phoebe\"," +
                "\"postDate\":\"2011-01-30\"," +
                "\"message\":\"trying out Elasticsearch too\"" +
                "}";
        request.source(jsonString, XContentType.JSON);
        client.indexAsync(request, RequestOptions.DEFAULT, new ActionListener<IndexResponse>() {
            @Override
            public void onResponse(IndexResponse indexResponse) {
                logger.info("create index async successfully.");
            }

            @Override
            public void onFailure(Exception e) {
                logger.error("create index async meet error.", e);
            }
        });
    }
}
