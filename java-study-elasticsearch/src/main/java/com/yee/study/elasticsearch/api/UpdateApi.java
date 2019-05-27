package com.yee.study.elasticsearch.api;

import com.yee.study.util.json.JSON;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.core.TermVectorsRequest;
import org.elasticsearch.client.core.TermVectorsResponse;
import org.elasticsearch.common.xcontent.XContentType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * 更新索引
 *
 * @author Roger.Yi
 */
public class UpdateApi {

    private static final Logger logger = LoggerFactory.getLogger(UpdateApi.class);

    /**
     * 更新索引（同步方式）
     *
     * @param client
     * @throws IOException
     */
    public static void getDocSync(RestHighLevelClient client) throws IOException {
        UpdateRequest request = new UpdateRequest("posts", "post", "1");

        String jsonString = "{" +
                "\"updated\":\"2017-01-01\"," +
                "\"reason\":\"daily update\"" +
                "}";
        request.doc(jsonString, XContentType.JSON);

        UpdateResponse updateResponse = client.update(
                request, RequestOptions.DEFAULT);
        logger.info("get index sync successfully. resp = " + JSON.getDefault().toJSONString(updateResponse));
    }

    /**
     * 获取索引（同步方式）
     *
     * @param client
     * @throws IOException
     */
    public static void getDocByTermVectors(RestHighLevelClient client) throws IOException {
        TermVectorsRequest request = new TermVectorsRequest("posts", "post", "1");
        request.setFields("user");
        TermVectorsResponse response = client.termvectors(request, RequestOptions.DEFAULT);
        logger.info("get index sync successfully. resp = " + JSON.getDefault().toJSONString(response));
    }
}
