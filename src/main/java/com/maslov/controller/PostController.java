package com.maslov.controller;

import com.google.gson.Gson;
import com.maslov.model.Post;
import com.maslov.service.PostService;
import org.springframework.stereotype.Controller;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Reader;

@Controller
public class PostController {
    public static final String APPLICATION_JSON = "application/json";
    private final PostService service;

    public PostController(PostService service) {
        this.service = service;
    }

    public void all(HttpServletResponse response) throws IOException {
        response.setContentType(APPLICATION_JSON);
        final var data = service.all();
        final var gson = new Gson();
        response.getWriter().print(gson.toJson(data));
    }

    public void getById(int id, HttpServletResponse response) {
        // TODO: deserialize request & serialize response
        response.setContentType(APPLICATION_JSON);
        final var gson = new Gson();
        final var data = service.getById(id);
        try {
            response.getWriter().println(gson.toJson(data));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void save(Reader body, HttpServletResponse response) throws IOException {
        response.setContentType(APPLICATION_JSON);
        final var gson = new Gson();
        final var post = gson.fromJson(body, Post.class);
        final var data = service.save(post);
        response.getWriter().print(gson.toJson(data));
    }

    public void removeById(int id, HttpServletResponse response) {
        // TODO: deserialize request & serialize response
        response.setContentType(APPLICATION_JSON);
        service.removeById(id);
    }
}