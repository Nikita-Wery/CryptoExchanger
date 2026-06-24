package ru.exchanger.service;

import ru.exchanger.entities.FileInfo;
import ru.exchanger.repository.FilesRepository;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.Optional;
import java.util.UUID;

public class FilesService {

    // Абсолютный путь к папке хранения сервера
    private static final String STORAGE_DIR = "/home/wery/Desktop/all/2025/ORIS/KriptoProfileServer/";

    private final FilesRepository filesRepository;

    public FilesService(FilesRepository filesRepository) {
        this.filesRepository = filesRepository;
    }

    // Сохраняет файл на диск + запись в БД
    public String saveFileToStorage(InputStream file,
                                  String originalFileName,
                                  String contentType,
                                  Long size) {

        // Получаем расширение из mime типа (image/png → png)
        String extension = getExtension(contentType);

        // Генерируем uuid-имя файла
        String storageName = UUID.randomUUID().toString();

        FileInfo fileInfo = FileInfo.builder()
                .originalFileName(originalFileName)
                .storageFileName(storageName)
                .size(size)
                .type(contentType)
                .build();

        // Формируем путь
        String fullPath = STORAGE_DIR + storageName + "." + extension;

        try {
            // Создаём директорию при необходимости
            Files.createDirectories(Paths.get(STORAGE_DIR));

            // Копируем файл
            Files.copy(file, Paths.get(fullPath));

            // Сохраняем информацию в БД
            filesRepository.save(fileInfo);

        } catch (IOException e) {
            throw new IllegalStateException("Ошибка сохранения файла на диск", e);

        } catch (SQLException e) {
            System.out.println("Ошибка сохранения информации в БД");
            e.printStackTrace();
        }
        return storageName;
    }

    // Отдаёт файл в OutputStream (например, в сервлет)
    public void writeFileFromStorage(Long fileId, OutputStream outputStream) throws SQLException {
        Optional<FileInfo> fileInfoOpt = filesRepository.findById(fileId);

        if (!fileInfoOpt.isPresent()) {
            throw new IllegalStateException("Файл не найден в БД");
        }

        FileInfo fileInfo = fileInfoOpt.get();

        String extension = getExtension(fileInfo.getType());

        String fullPath = STORAGE_DIR + fileInfo.getStorageFileName() + "." + extension;

        File file = new File(fullPath);

        if (!file.exists()) {
            throw new IllegalStateException("Физический файл отсутствует по пути: " + fullPath);
        }

        try {
            Files.copy(file.toPath(), outputStream);
        } catch (IOException e) {
            throw new IllegalStateException("Ошибка записи файла в OutputStream", e);
        }
    }

    // Получение инфо о файле
    public Optional<FileInfo> getFileInfo(Long fileId) throws SQLException {
        return filesRepository.findById(fileId);
    }

    // Метод получения расширения из MIME-типа
    private String getExtension(String contentType) {
        if (contentType == null || !contentType.contains("/")) {
            return "bin";
        }
        return contentType.split("/")[1];
    }
}
