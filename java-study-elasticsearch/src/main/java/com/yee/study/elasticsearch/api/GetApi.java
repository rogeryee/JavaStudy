package com.yee.study.elasticsearch.api;

import com.yee.study.util.json.JSON;
import org.elasticsearch.action.ActionListener;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.Strings;
import org.elasticsearch.search.fetch.subphase.FetchSourceContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Map;

/**
 * 获取索引
 *
 * @author Roger.Yi
 */
public class GetApi {

    private static final Logger logger = LoggerFactory.getLogger(GetApi.class);

    /**
     * 获取索引（同步方式）
     *
     * @param client
     * @throws IOException
     */
    public static void getDocSync(RestHighLevelClient client) throws IOException {
        GetRequest getRequest = new GetRequest("posts", "post", "1");

        String[] includes = new String[]{"user"};
        String[] excludes = new String[]{"message"};
        FetchSourceContext fetchSourceContext =
                new FetchSourceContext(true, includes, excludes);
        getRequest.fetchSourceContext(fetchSourceContext);

        GetResponse getResponse = client.get(getRequest, RequestOptions.DEFAULT);
        logger.info("get index sync successfully. resp = " + JSON.getDefault().toJSONString(getResponse));
    }

    /**
     * 获取索引（异步方式）
     *
     * @param client
     * @throws IOException
     */
    public static void getDocAsync(RestHighLevelClient client) throws IOException {
        GetRequest getRequest = new GetRequest("posts", "post", "1");
        client.getAsync(getRequest, RequestOptions.DEFAULT, new ActionListener<GetResponse>() {
            @Override
            public void onResponse(GetResponse getResponse) {
                logger.info("get index async successfully. resp = " + JSON.getDefault().toJSONString(getResponse));
            }

            @Override
            public void onFailure(Exception e) {
                logger.info("get index sync meet error.");
            }
        });
    }
}
