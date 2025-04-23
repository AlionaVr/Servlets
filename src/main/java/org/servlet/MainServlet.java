package org.servlet;


import org.config.AppConfig;
import org.controller.PostController;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class MainServlet extends HttpServlet {
    private PostController controller;
    private AnnotationConfigApplicationContext context;

    @Override
    public void init() {
        context = new AnnotationConfigApplicationContext(AppConfig.class);
        controller = context.getBean(PostController.class);
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) {
        // если деплоились в root context, то достаточно этого
        try {
            String path = req.getRequestURI();
            String method = req.getMethod();

            if (path.equals("/api/posts")) {
                handlePosts(method, req, resp);
            } else if (path.matches("/api/posts/\\d+")) {
                long id = Long.parseLong(path.substring(path.lastIndexOf("/")).replace("/", ""));
                handlePostById(method, id, req, resp);
            } else {
                resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    private void handlePosts(String method, HttpServletRequest req, HttpServletResponse resp) throws IOException {
        switch (method) {
            case "POST" -> controller.save(req.getReader(), resp);
            case "GET" -> controller.all(resp);
            default -> resp.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
        }
    }

    private void handlePostById(String method, long id, HttpServletRequest req, HttpServletResponse resp) throws Exception {
        switch (method) {
            case "GET" -> controller.getById(id, resp);
            case "DELETE" -> controller.removeById(id, resp);
            default -> resp.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
        }
    }
}

