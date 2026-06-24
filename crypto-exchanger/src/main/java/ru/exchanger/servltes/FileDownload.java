package ru.exchanger.servltes;

import ru.exchanger.entities.FileInfo;
import ru.exchanger.service.FilesService;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Optional;

@WebServlet("/uploaded/files")
public class FileDownload extends HttpServlet {

    private FilesService filesService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        this.filesService = (FilesService) config.getServletContext().getAttribute("fileService");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("ВХОД В СЕРВЛЕТ");
        String fileId = req.getParameter("id");
        // получили информацию о загруженном файле
        try {
            Optional<FileInfo> fileInfo = filesService.getFileInfo(Long.parseLong(fileId));

            if (fileInfo.isPresent()) {
                // в ответ указали тип данных
                resp.setContentType(fileInfo.get().getType());
                // в ответ указали размер данных
                resp.setContentLength(fileInfo.get().getSize().intValue());
                // в ответ указали оригинальное название файла
                resp.setHeader("Content-Disposition", "attachment; filename=\"" + fileInfo.get().getOriginalFileName() + "\"");
                // записали данные файла в ответ
                filesService.writeFileFromStorage(Long.parseLong(fileId), resp.getOutputStream());
                resp.flushBuffer();
            }

        } catch (SQLException e) {
            System.out.println("Ошибка получения file из БД");
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("Ошибка записи файла на диск пользователя");
            e.printStackTrace();
        }
        resp.sendRedirect("/home");
    }
}
