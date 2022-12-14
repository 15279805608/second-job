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

        // 3.??????java HttpURLConnection
        String result = this.remoteInvoking(coffeeUri, "GET", MediaType.APPLICATION_JSON_VALUE, null);
        log.info("result: {}", result);
        String result2 = this.remoteInvoking(coffeeUri2, "GET", MediaType.APPLICATION_XML_VALUE, null);
        log.info("result2: {}", result2);

    }

    /**
     * ?????? http get??????
     *
     * @param url
     */
    public void get(String url) {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        try {
            // ??????httpget
            HttpGet httpget = new HttpGet(url);
            System.out.println("executing request " + httpget.getURI());
            // ??????get??????
            try (CloseableHttpResponse response = httpclient.execute(httpget)) {
                // ??????????????????
                HttpEntity entity = response.getEntity();
                // ??????????????????
                System.out.println(response.getStatusLine());
                if (entity != null) {
                    // ????????????????????????
                    System.out.println("Response content length: " + entity.getContentLength());
                    // ??????????????????
                    System.out.println("Response content: " + EntityUtils.toString(entity));
                }
            }
        } catch (ParseException | IOException e) {
            e.printStackTrace();
        } finally {
            // ????????????,????????????
            try {
                httpclient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * ??????java HttpURLConnection
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
             *?????????????????????post??????????????????doInput???doOutput???true
             * post????????????????????????
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
                //?????????????????????
                DataOutputStream out = new DataOutputStream(httpURLConnection.getOutputStream());
                out.write(data.getBytes(StandardCharsets.UTF_8));
                out.flush();
                out.close();
            }
            //???????????????????????????
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
