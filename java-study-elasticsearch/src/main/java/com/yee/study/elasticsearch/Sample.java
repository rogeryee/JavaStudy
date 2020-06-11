package com.yee.study.elasticsearch;

import com.yee.study.elasticsearch.api.GetApi;
import com.yee.study.elasticsearch.api.IndexApi;
import com.yee.study.elasticsearch.api.SearchApi;
import com.yee.study.elasticsearch.api.UpdateApi;
import com.yee.study.util.json.JSON;
import org.apache.http.HttpHost;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.script.Script;
import org.elasticsearch.script.ScriptType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sun.misc.BASE64Encoder;
import sun.security.provider.MD5;

import java.security.MessageDigest;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Roger.Yi
 */
public class Sample {

    private static final Logger logger = LoggerFactory.getLogger(Sample.class);

    public static void main(String[] args) throws Exception {

        System.out.println(new Date(225561600000l));

        String appId = "appId-1";
        String appSecret = "appSecret-1";

        long timestamp = new Date().getTime();
        String text = appId + "#" + appSecret + "#" + timestamp;

        MessageDigest md = MessageDigest.getInstance("MD5");
        byte[] md5Bytes = md.digest(text.getBytes());

        BASE64Encoder encoder = new BASE64Encoder();
        String sign = encoder.encode(md5Bytes);
        System.out.println("sign=[" + sign + "]");

        md5Bytes = md.digest(text.getBytes());
        sign = encoder.encode(md5Bytes);
        System.out.println("sign=[" + sign + "]");
    }

    public static void main1(String[] args) throws Exception {

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
//        SearchApi.search(client);


        // Test UpdateDoc

        UpdateRequest request = new UpdateRequest();
        request.index("customer");
        request.type("_doc");
//        request.id("1");

//        request.script(removeTag());
        request.script(addTag());

        UpdateResponse updateResponse = client.update(request, RequestOptions.DEFAULT);
        logger.info("get index sync successfully. resp = " + JSON.getDefault().toJSONString(updateResponse));
    }

    private static Script removeTag() {
        Map<String, Object> parameters = new HashMap<String, Object>() {{
            put("tag_name", "after_80s");
        }};

        // remove has_car
        String idOrCode = "ctx._source.defined_tags1.removeIf(tag -> tag.tag_name == params.tag_name)";
        return new Script(ScriptType.INLINE, "painless", idOrCode, parameters);
    }

    private static Script addTag() {
        Map<String, Object> parameters = new HashMap<String, Object>() {{
            put("tag", new HashMap<String, Object>() {{
                put("tag_name", "after_10s");
                put("value", "10后");
            }});
        }};

        // remove has_car
        String idOrCode = "ctx._source.defined_tags1.add(params.tag.tag)";
        return new Script(ScriptType.INLINE, "painless", idOrCode, parameters);
    }
}
