package com.todo.client.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.todo.client.config.ApiUrl;
import com.todo.client.entity.ToDo;
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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class ToDoService {
    private final ObjectMapper mapper = new ObjectMapper();
    private final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
    private final PriorityService priorityService = new PriorityService();
    private final CategoryService categoryService = new CategoryService();

    public ToDo create(ToDo toDo) {

        String request = prepareRequest(toDo);
        String result;
        HttpPost httpPost = new HttpPost(ApiUrl.createTodo);
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
            toDo = mapper.readValue(result, ToDo.class);
        } catch (JsonProcessingException e) {
            return null;
        }
        return toDo;
    }

    private String prepareRequest(ToDo toDo) {
        String startDate = simpleDateFormat.format(toDo.getStartDate());
        String endDate = simpleDateFormat.format(toDo.getEndDate());
        return "{\"title\":\"" + toDo.getTitle()
                + "\",\"description\":\"" + toDo.getDescription()
                + "\",\"startDate\":\"" + startDate
                + "\",\"endDate\":\"" + endDate
                + "\",\"priority\":{\"id\":\"" + toDo.getPriority().getId()
                + "\"},\"category\":{\"id\":\"" + toDo.getCategory().getId()
                + "\"},\"favourite\":" + toDo.isFavourite() + "}";
    }

    public boolean update(Integer id, ToDo toDo) {
        HttpPut httpPut = new HttpPut(ApiUrl.updateToDo + id);
        String resultContent;
        String request = prepareRequest(toDo);
        httpPut.setEntity(new StringEntity(request, ContentType.APPLICATION_JSON));

        try (CloseableHttpClient httpclient = HttpClients.createDefault()) {
            try (CloseableHttpResponse response = httpclient.execute(httpPut)) {
                if (response.getCode() == 204)
                    return true;
                else {
                    HttpEntity entity = response.getEntity();
                    resultContent = EntityUtils.toString(entity);
                    FaildResponse faildResponse = mapper.readValue(resultContent, FaildResponse.class);
                    System.out.println(faildResponse.getMessage());
                    return false;
                }
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
        } catch (IOException e) {
            return false;
        }
    }

    public List<ToDo> selectAll() {
        String resultContent;
        List<ToDo> toDoList;
        HttpGet httpGet = new HttpGet(ApiUrl.selectAllToDo);
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
            toDoList = mapper.readValue(resultContent, new TypeReference<>() {
            });
        } catch (JsonProcessingException e) {
            return new ArrayList<>();
        }
        return toDoList;
    }

    public ToDo selectById(Integer id) {
        String resultContent;
        ToDo selectedToDo;
        HttpGet httpGet = new HttpGet(ApiUrl.selectToDoById + id);
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
            selectedToDo = mapper.readValue(resultContent, ToDo.class);
        } catch (JsonProcessingException e) {
            System.out.println(e.getMessage());
            return null;
        }
        return selectedToDo;
    }

    /**
     * @param mode represent searching mode 0 for startDate 1 for endDate
     * @param date String yyyy/MM/dd
     * @return List of matching items
     */
    public List<ToDo> selectByDate(int mode, String date) {
        String resultContent = null;
        List<ToDo> toDoList;
        HttpGet httpGet = new HttpGet(ApiUrl.selectToDoByDate + mode + "&date=" + date);
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
                throw new RuntimeException(e);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            toDoList = mapper.readValue(resultContent, new TypeReference<>() {
            });
        } catch (JsonProcessingException e) {
            return new ArrayList<>();
        }
        return toDoList;
    }

    public boolean deleteById(int id) {
        String resultContent;

        HttpDelete httpDelete = new HttpDelete(ApiUrl.deleteToDoById + id);
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

    public boolean deleteByTitle(String title) {
        String resultContent;

        HttpDelete httpDelete = new HttpDelete(ApiUrl.deleteToDoByTitle + title);
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

    public List<ToDo> selectByPriority(String priority) {
        String resultContent;
        List<ToDo> toDoList;
        HttpGet httpGet = new HttpGet(ApiUrl.selectToDoByPriority + priority);
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
            toDoList = mapper.readValue(resultContent, new TypeReference<>() {
            });
        } catch (JsonProcessingException e) {
            return new ArrayList<>();
        }
        return toDoList;
    }
}
