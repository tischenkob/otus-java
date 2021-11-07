package ru.otus.servlet;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import ru.otus.crm.model.Client;
import ru.otus.crm.service.DBServiceClient;
import ru.otus.services.TemplateProcessor;

@RequiredArgsConstructor
public class ClientsServlet extends HttpServlet {

    private static final String CLIENTS_PAGE = "clients.html";

    private final DBServiceClient clientService;
    private final TemplateProcessor templateProcessor;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse response) throws IOException {
        Map<String, Object> parameters = Map.of("clients", clientService.findAll());
        final String pageContent = templateProcessor.getPage(CLIENTS_PAGE, parameters);
        response.setContentType("text/html");
        response.getWriter().println(pageContent);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse response) throws IOException {
        var clientInfo = req.getParameterMap();
        var client = new Client(clientInfo.get("name")[0]);
        clientService.saveClient(client);
        response.sendRedirect("/clients");
    }

}
