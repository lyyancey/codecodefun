package org.zhonghe.http;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.config.RequestConfig;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.HttpEntity;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.apache.hc.core5.net.URIBuilder;
import org.apache.hc.core5.util.Timeout;
import org.zhonghe.pojo.DataItem;

import java.io.InputStream;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class HttpClient {
    private static final String URL =
            "http://10.23.15.219:8001/sap/zgrcc/zget_repeated";

    public void downloadReport(String yearMonth, Path outputPath) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        Files.createDirectories(outputPath.getParent());
        // 1. 构建客户端（默认带连接池）
        try (CloseableHttpClient client = HttpClients.createDefault()) {
            // 2.用 URIBuilder 构造带参数的 URL
            URI uri = new URIBuilder(URL)
                    .addParameter("year_month", yearMonth)
                    .build();

            // 3.设置请求配置
            RequestConfig requestConfig = RequestConfig.custom()
                    .setConnectionRequestTimeout(Timeout.ofMinutes(10))  // 连接超时
                    .setResponseTimeout(Timeout.ofMinutes(10)) // 响应超时
                    .build();

            // 4. 创建 GET 请求
            HttpGet get = new HttpGet(uri);
            get.setConfig(requestConfig);
            get.addHeader("Accept", "application/json");


            // 5. 执行
            try (CloseableHttpResponse resp = client.execute(get)) {
                if (resp.getCode() == 200) {
                    // ① 读响应 JSON 字符串
                    String body = EntityUtils.toString(resp.getEntity(),
                            StandardCharsets.UTF_8);

                    // ② 解析到树节点，定位 DATA 数组
                    JsonNode dataNode = mapper.readTree(body).path("DATA");

                    // ③ 直接反序列化成数组
                    DataItem[] items = mapper
                            .readerFor(DataItem[].class)
                            .readValue(dataNode);

                    System.out.println("拿到条数：" + items.length);
                    // Demo：打印第一条
                    if (items.length > 0) {
                        System.out.println(items[0].QMNUM + " / " + items[0].QMTXT);
                    }

                }
            }
        }
    }

}
