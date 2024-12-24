package ru.itis.controller;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.itis.dto.TestDTO;
import ru.itis.service.test.general.api.GetTestService;
import ru.itis.service.test.general.impl.GetTestServiceImpl;

import java.io.IOException;
import java.util.UUID;

@WebServlet("/test")
public class TestPageServlet extends HttpServlet {

    private GetTestService getTestService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        getTestService = (GetTestService) config.getServletContext().getAttribute("getTestService");
        super.init();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String stringTestId = req.getParameter("id");
        UUID testId = UUID.fromString(stringTestId);

        TestDTO testDTO = getTestService.getTestById(testId);

        req.setAttribute("test", testDTO);
        req.setAttribute("testId", testId);
        req.getRequestDispatcher("/WEB-INF/view/pages/test.jsp").forward(req, resp);

    }
}
