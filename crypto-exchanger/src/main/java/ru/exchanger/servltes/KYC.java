package ru.exchanger.servltes;

import ru.exchanger.service.FilesService;
import ru.exchanger.service.UserService;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.IOException;
import java.util.UUID;

@WebServlet("/profile/KYC")
@MultipartConfig
public class KYC extends HttpServlet {

    private UserService userService;
    private FilesService filesService;

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/jsp/KYC.jsp").forward(req, resp);
    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        // Проверяем, есть ли файл (кнопка загрузки профиля)
        Part part = req.getPart("file");
        if (part != null && part.getSize() > 0) {

            String fileId = filesService.saveFileToStorage(
                    part.getInputStream(),
                    part.getSubmittedFileName(),
                    part.getContentType(),
                    part.getSize()
            );

            // сохраняем ID в сессию, чтобы показывать фото
            req.getSession().setAttribute("profile_img_id", fileId);

            resp.sendRedirect("/profile/KYC");
            return;
        }

        // TODO: в будющих версиях добавить обработку KYC
        resp.sendRedirect("/profile/KYC");
    }

    @Override
    public void init(ServletConfig config) throws ServletException {
        userService = (UserService) config.getServletContext().getAttribute("userService");
        filesService = (FilesService) config.getServletContext().getAttribute("fileService");
    }
}
