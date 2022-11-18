package geektime.spring.springbucks.customer;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.ParseException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.Banner;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

@SpringBootApplication
@Slf4j
public class CustomerServiceApplication implements ApplicationRunner {
    @Autowired
    private RestTemplate restTemplate;

    public static void main(String[] args) {
        new SpringApplicationBuilder()
                .sources(CustomerServiceApplication.class)
                .bannerMode(Banner.Mode.OFF)
                .web(WebApplicationType.NONE)
                .run(args);
    }

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder.build();
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        // 1.RestTemplate
        String coffeeUri = "http://localhost:8080/coffee/findInfoJson";
        String s = restTemplate.getForObject(coffeeUri, String.class);
        log.info("String: {}", s);

        String coffeeUri2 = "http://localhost:8080/coffee/findInfoXml";
        String s2 = restTemplate.getForObject(coffeeUri2, String.class);
        log.info("String2: {}", s2);

        // 2.HttpClient
        this.get(coffeeUri);
        this.get(coffeeUri2);

        // 3.原生java HttpURLConnection
        String result = this.remoteInvoking(coffeeUri, "GET", MediaType.APPLICATION_JSON_VALUE, null);
        log.info("result: {}", result);
        String result2 = this.remoteInvoking(coffeeUri2, "GET", MediaType.APPLICATION_XML_VALUE, null);
        log.info("result2: {}", result2);

    }

    /**
     * 发送 http get请求
     *
     * @param url
     */
    public void get(String url) {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        try {
            // 创建httpget
            HttpGet httpget = new HttpGet(url);
            System.out.println("executing request " + httpget.getURI());
            // 执行get请求
            try (CloseableHttpResponse response = httpclient.execute(httpget)) {
                // 获取响应实体
                HttpEntity entity = response.getEntity();
                // 打印响应状态
                System.out.println(response.getStatusLine());
                if (entity != null) {
                    // 打印响应内容长度
                    System.out.println("Response content length: " + entity.getContentLength());
                    // 打印响应内容
                    System.out.println("Response content: " + EntityUtils.toString(entity));
                }
            }
        } catch (ParseException | IOException e) {
            e.printStackTrace();
        } finally {
            // 关闭连接,释放资源
            try {
                httpclient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 原生java HttpURLConnection
     *
     * @param url
     * @param method
     * @param contentType
     * @param data
     * @return
     */
    public String remoteInvoking(String url, String method, String contentType, String data) {

        String result = "";

        try {
            URL url1 = new URL(url);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url1.openConnection();
            /**
             *设置一堆属性，post请求必须设定doInput和doOutput为true
             * post请求必须取消缓存
             */
            if ("POST".equals(method)) {
                httpURLConnection.setDoInput(true);
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setUseCaches(false);
            }
            httpURLConnection.setRequestMethod(method);
            httpURLConnection.setInstanceFollowRedirects(true);

            httpURLConnection.setRequestProperty("Content-Type", contentType);

            httpURLConnection.connect();
            if (!StringUtils.isEmpty(data)) {
                //通过输入流输入
                DataOutputStream out = new DataOutputStream(httpURLConnection.getOutputStream());
                out.write(data.getBytes(StandardCharsets.UTF_8));
                out.flush();
                out.close();
            }
            //通过输出流打印输出
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
            String b = "";
            StringBuilder sbu = new StringBuilder();
            while ((b = bufferedReader.readLine()) != null) {
                sbu.append(b);
            }
            result = sbu.toString();

            bufferedReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

}
