package com.todo.client.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.todo.client.config.ApiUrl;
import com.todo.client.entity.ToDo;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.ContentType;
import org.apache.hc.core5.http.HttpEntity;
import org.apache.hc.core5.http.ParseException;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.apache.hc.core5.http.io.entity.StringEntity;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class ToDoService {
    private final ObjectMapper mapper = new ObjectMapper();

    public ToDo create(ToDo toDo) {
        ToDo todo = null;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String startDate = simpleDateFormat.format(toDo.getStartDate());
        String endDate = simpleDateFormat.format(toDo.getEndDate());
        String request = "{\"title\":\"" + toDo.getTitle()
                + "\",\"description\":\"" + toDo.getDescription()
                + "\",\"startDate\":\"" + startDate
                + "\",\"endDate\":\"" + endDate
                + "\",\"priority\":{\"id\":\"" + toDo.getPriority().getId()
                + "\"},\"category\":{\"id\":\"" + toDo.getCategory().getId()
                + "\"},\"favourite\":" + toDo.isFavourite() + "}";

        String result = null;
        HttpPost httpPost = new HttpPost(ApiUrl.createTodo);
        httpPost.setEntity(new StringEntity(request, ContentType.APPLICATION_JSON));

        try (CloseableHttpClient httpclient = HttpClients.createDefault()) {
            try (CloseableHttpResponse response = httpclient.execute(httpPost)) {
                result = EntityUtils.toString(response.getEntity());
            }
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
        try {
            toDo = mapper.readValue(result, ToDo.class);
        } catch (JsonProcessingException e) {
            return null;
        }

        return toDo;

    }

    public List<ToDo> selectAll() {
        String resultContent = null;
        List<ToDo> toDoList;
        HttpGet httpGet = new HttpGet(ApiUrl.selectAllToDo);
        try (CloseableHttpClient httpclient = HttpClients.createDefault()) {
            try (CloseableHttpResponse response = httpclient.execute(httpGet)) {
                HttpEntity entity = response.getEntity();
                resultContent = EntityUtils.toString(entity);
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            toDoList = mapper.readValue(resultContent, new TypeReference<>() {
            });
        } catch (JsonProcessingException e) {
            System.out.println(e.getMessage());
            return new ArrayList<>();
        }
        return toDoList;
    }

    public ToDo selectById(Integer id) {
        String resultContent = null;
        ToDo selectedToDo;
        HttpGet httpGet = new HttpGet(ApiUrl.selectToDoById + id);
        try (CloseableHttpClient httpclient = HttpClients.createDefault()) {
            try (CloseableHttpResponse response = httpclient.execute(httpGet)) {
                HttpEntity entity = response.getEntity();
                resultContent = EntityUtils.toString(entity);
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            selectedToDo = mapper.readValue(resultContent, ToDo.class);
        } catch (JsonProcessingException e) {
            return null;
        }
        return selectedToDo;
    }

    public ToDo selectByTitle(String title) {
        String resultContent = null;
        ToDo selectedToDo;
        HttpGet httpGet = new HttpGet(ApiUrl.selectToDoByTitle + title);
        try (CloseableHttpClient httpclient = HttpClients.createDefault()) {
            try (CloseableHttpResponse response = httpclient.execute(httpGet)) {
                HttpEntity entity = response.getEntity();
                resultContent = EntityUtils.toString(entity);
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            selectedToDo = mapper.readValue(resultContent, ToDo.class);
        } catch (JsonProcessingException e) {
            System.out.println(e.getMessage());
            return null;
        }
        return selectedToDo;
    }
}
