package fr.utbm.da50.freelyforms.core;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

@Component
public class StaticContentFilter implements Filter {

    private List<String> fileExtensions = Arrays.asList("html", "js", "json", "csv", "css", "png", "svg", "eot", "ttf",
            "woff", "appcache", "jpg", "jpeg", "gif", "ico");

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        doFilter((HttpServletRequest) request, (HttpServletResponse) response, chain);
    }

    private void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        String path = request.getServletPath();

        boolean isApi = path.startsWith("/api");
        boolean isResourceFile = !isApi && fileExtensions.stream().anyMatch(path::contains);

        if (isApi) {
            chain.doFilter(request, response);
        } else if (isResourceFile) {
            resourceToResponse("public" + path, response);
        } else {
            resourceToResponse("public/index.html", response);
        }
    }

    private void resourceToResponse(String resourcePath, HttpServletResponse response) throws IOException {
        InputStream inputStream = Thread.currentThread()
                .getContextClassLoader()
                .getResourceAsStream(resourcePath);

        if (inputStream == null) {
            response.sendError(HttpStatus.NOT_FOUND.value(), HttpStatus.NOT_FOUND.getReasonPhrase());
            return;
        }

        // headers
        if (resourcePath.endsWith(".html")) {
            response.setContentType("text/html");
        } else if (resourcePath.endsWith(".css")) {
            response.setContentType("text/css");
        } else if (resourcePath.endsWith(".js")) {
            response.setContentType("text/javascript");
        } else if (resourcePath.endsWith(".svg")) {
            response.setContentType("image/svg+xml");
        }

        inputStream.transferTo(response.getOutputStream());
    }
}