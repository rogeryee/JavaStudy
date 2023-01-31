package com.yee.study.elasticsearch.api;

import com.yee.study.util.json.JSON;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.core.TermVectorsRequest;
import org.elasticsearch.client.core.TermVectorsResponse;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * 更新索引
 *
 * @author Roger.Yi
 */
public class SearchApi {

    private static final Logger logger = LoggerFactory.getLogger(SearchApi.class);

    /**
     * 更新索引（同步方式）
     *
     * @param client
     * @throws IOException
     */
    public static void search(RestHighLevelClient client) throws IOException {

        // Query Builder
//        MatchQueryBuilder matchQueryBuilder = new MatchQueryBuilder("customer_id", 10002);
        MatchQueryBuilder matchQueryBuilder = new MatchQueryBuilder("defined_tags1.tag_name", "after_00s");

        // Source Builder
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(matchQueryBuilder);

        String[] includeFields = new String[] {"agent_code", "customer_id", "gender", "defined_tags1.tag_name"};
        String[] excludeFields = new String[] {};
//        searchSourceBuilder.fetchSource(includeFields, excludeFields);

        // Search Request
        SearchRequest searchRequest = new SearchRequest("customer");
        searchRequest.source(searchSourceBuilder);

        SearchResponse response = client.search(searchRequest, RequestOptions.DEFAULT);
        logger.info("search index sync successfully. resp = " + JSON.getDefault().toPrettyJSONString(response));
    }
}
