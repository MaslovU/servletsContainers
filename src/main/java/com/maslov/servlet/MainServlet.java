package com.maslov.servlet;

import com.maslov.controller.PostController;
import com.maslov.repository.PostRepository;
import com.maslov.service.PostService;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class MainServlet extends HttpServlet {
    private PostController controller;
    private static final String API_POSTS_D_ENDPOINT = "/api/posts/\\d+";
    private static final String API_POSTS_ENDPOINT = "/api/posts";
    private static final String DEFAULT_ENDPOINT = "/";

    @Override
    public void init() {
        final PostRepository repository = new PostRepository();
        final PostService service = new PostService(repository);
        controller = new PostController(service);
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) {
        // если деплоились в root context, то достаточно этого
        try {
            final String path = req.getRequestURI();
            final String method = req.getMethod();
            final long id = Long.parseLong(path.substring(path.lastIndexOf("/")));
            // primitive routing
            if (method.equals("GET") && path.equals(API_POSTS_ENDPOINT)) {
                controller.all(resp);
                return;
            }
            if (method.equals("GET") && path.matches(API_POSTS_D_ENDPOINT)) {
                // easy way
                controller.getById(id, resp);
                return;
            }
            if (method.equals("GET") && path.equals(DEFAULT_ENDPOINT)) {
                controller.all(resp);
                return;
            }
            if (method.equals("POST") && path.equals(API_POSTS_ENDPOINT)) {
                controller.save(req.getReader(), resp);
                return;
            }
            if (method.equals("DELETE") && path.matches(API_POSTS_D_ENDPOINT)) {
                // easy way
                controller.removeById(id, resp);
                return;
            }
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
        } catch (Exception e) {
            e.printStackTrace();
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }
}
