package com.todo.client.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.todo.client.config.ApiUrl;
import com.todo.client.entity.Category;
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

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CategoryService {
    private final ObjectMapper mapper = new ObjectMapper();

    public Category create(Category category) {

        String request = "{\"name\":\"" + category.getName() + "}";
        String result;
        HttpPost httpPost = new HttpPost(ApiUrl.createCategory);
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
            category = mapper.readValue(result, Category.class);
        } catch (JsonProcessingException e) {
            return null;
        }
        return category;
    }

    public boolean update(int id, Category category) {

        String request = "{\"name\":\"" + category.getName() + "}";
        String result;
        HttpPut httpPut = new HttpPut(ApiUrl.updateCategoryById + id);
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

    public List<Category> selectAll() {
        String resultContent;
        List<Category> categories;
        HttpGet httpGet = new HttpGet(ApiUrl.selectAllCategories);
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
            categories = mapper.readValue(resultContent, new TypeReference<>() {
            });
        } catch (JsonProcessingException e) {
            return new ArrayList<>();
        }
        return categories;
    }

    public Category selectById(Integer id) {
        String resultContent;
        Category selectedCategory;
        HttpGet httpGet = new HttpGet(ApiUrl.selectCategoryById + id);
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
            selectedCategory = mapper.readValue(resultContent, Category.class);
        } catch (JsonProcessingException e) {
            return null;
        }
        return selectedCategory;
    }

    public Category selectByName(String name) {
        String resultContent = null;
        Category selectedCategory;
        HttpGet httpGet = new HttpGet(ApiUrl.selectToDoByTitle + name);
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
            selectedCategory = mapper.readValue(resultContent, Category.class);
        } catch (JsonProcessingException e) {
            System.out.println(e.getMessage());
            return null;
        }
        return selectedCategory;
    }

    public boolean deleteById(int id) {
        String resultContent;

        HttpDelete httpDelete = new HttpDelete(ApiUrl.deleteCategoryById + id);
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
