package com.todo.client.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.todo.client.config.ApiUrlConfig;
import com.todo.client.config.ApplicationContextConfig;
import com.todo.client.entity.Priority;
import com.todo.client.response.FaildResponse;
import org.apache.hc.client5.http.classic.methods.HttpDelete;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.classic.methods.HttpPut;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.ContentType;
import org.apache.hc.core5.http.HttpEntity;
import org.apache.hc.core5.http.ParseException;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.apache.hc.core5.http.io.entity.StringEntity;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PriorityService {
    private final ObjectMapper mapper = new ObjectMapper();
    AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(ApplicationContextConfig.class);
    ApiUrlConfig apiUrlConfig = context.getBean(ApiUrlConfig.class);

    public Priority create(Priority priority) {

        String request = "{\"name\":\"" + priority.getName() + "}";
        String result;
        HttpPost httpPost = new HttpPost(apiUrlConfig.createPriority);
        httpPost.setEntity(new StringEntity(request, ContentType.APPLICATION_JSON));

        try (CloseableHttpClient httpclient = HttpClients.createDefault()) {
            try (CloseableHttpResponse response = httpclient.execute(httpPost)) {
                result = EntityUtils.toString(response.getEntity());
                if (response.getCode() != 201) {
                    FaildResponse faildResponse = mapper.readValue(result, FaildResponse.class);
                    System.out.println(faildResponse.getMessage());
                    return null;
                }
            }
        } catch (IOException | ParseException e) {
            e.printStackTrace();
            return null;
        }
        try {
            priority = mapper.readValue(result, Priority.class);
        } catch (JsonProcessingException e) {
            return null;
        }
        return priority;
    }

    public boolean update(int id, Priority priority) {

        String request = "{\"name\":\"" + priority.getName() + "}";
        String result;
        HttpPut httpPut = new HttpPut(apiUrlConfig.updatePriorityById + id);
        httpPut.setEntity(new StringEntity(request, ContentType.APPLICATION_JSON));

        try (CloseableHttpClient httpclient = HttpClients.createDefault()) {
            try (CloseableHttpResponse response = httpclient.execute(httpPut)) {
                result = EntityUtils.toString(response.getEntity());
                if (response.getCode() != 204) {
                    FaildResponse faildResponse = mapper.readValue(result, FaildResponse.class);
                    System.out.println(faildResponse.getMessage());
                    return false;
                } else
                    return true;
            }
        } catch (IOException | ParseException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Priority> selectAll() {
        String resultContent;
        List<Priority> priorities;
        HttpGet httpGet = new HttpGet(apiUrlConfig.selectAllPriorities);
        try (CloseableHttpClient httpclient = HttpClients.createDefault()) {
            try (CloseableHttpResponse response = httpclient.execute(httpGet)) {
                HttpEntity entity = response.getEntity();
                resultContent = EntityUtils.toString(entity);
                if (response.getCode() != 200) {
                    FaildResponse faildResponse = mapper.readValue(resultContent, FaildResponse.class);
                    System.out.println(faildResponse.getMessage());
                    return new ArrayList<>();
                }
            } catch (ParseException e) {
                return new ArrayList<>();
            }
        } catch (IOException e) {
            return new ArrayList<>();
        }
        try {
            priorities = mapper.readValue(resultContent, new TypeReference<>() {
            });
        } catch (JsonProcessingException e) {
            return new ArrayList<>();
        }
        return priorities;
    }

    public Priority selectById(Integer id) {
        String resultContent;
        Priority selectedPriority;
        HttpGet httpGet = new HttpGet(apiUrlConfig.selectPriorityById + id);
        try (CloseableHttpClient httpclient = HttpClients.createDefault()) {
            try (CloseableHttpResponse response = httpclient.execute(httpGet)) {
                HttpEntity entity = response.getEntity();
                resultContent = EntityUtils.toString(entity);
                if (response.getCode() != 200) {
                    FaildResponse faildResponse = mapper.readValue(resultContent, FaildResponse.class);
                    System.out.println(faildResponse.getMessage());
                    return null;
                }
            } catch (ParseException e) {
                return null;
            }
        } catch (IOException e) {
            return null;
        }
        try {
            selectedPriority = mapper.readValue(resultContent, Priority.class);
        } catch (JsonProcessingException e) {
            return null;
        }
        return selectedPriority;
    }

    public Priority selectByName(String name) {
        String resultContent = null;
        Priority selectedPriority;
        HttpGet httpGet = new HttpGet(apiUrlConfig.selectPriorityByName + name);
        try (CloseableHttpClient httpclient = HttpClients.createDefault()) {
            try (CloseableHttpResponse response = httpclient.execute(httpGet)) {
                HttpEntity entity = response.getEntity();
                resultContent = EntityUtils.toString(entity);
                if (response.getCode() != 200) {
                    FaildResponse faildResponse = mapper.readValue(resultContent, FaildResponse.class);
                    System.out.println(faildResponse.getMessage());
                    return null;
                }
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            selectedPriority = mapper.readValue(resultContent, Priority.class);
        } catch (JsonProcessingException e) {
            System.out.println(e.getMessage());
            return null;
        }
        return selectedPriority;
    }

    public boolean deleteById(int id) {
        String resultContent;

        HttpDelete httpDelete = new HttpDelete(apiUrlConfig.deletePriorityById + id);
        try (CloseableHttpClient httpclient = HttpClients.createDefault()) {
            try (CloseableHttpResponse response = httpclient.execute(httpDelete)) {
                HttpEntity entity = response.getEntity();
                resultContent = EntityUtils.toString(entity);
                if (response.getCode() != 204) {
                    FaildResponse faildResponse = mapper.readValue(resultContent, FaildResponse.class);
                    System.out.println(faildResponse.getMessage());
                    return false;
                }
                return true;
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
        } catch (IOException e) {
            return false;
        }
    }
}
