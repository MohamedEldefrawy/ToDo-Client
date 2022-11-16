package com.todo.client.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.todo.client.config.ApiUrl;
import com.todo.client.entity.ToDo;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.HttpEntity;
import org.apache.hc.core5.http.ParseException;
import org.apache.hc.core5.http.io.entity.EntityUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ToDoService {
    private final ObjectMapper mapper = new ObjectMapper();

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
            System.out.println(e.getMessage());
            return null;
        }
        return selectedToDo;
    }
}
