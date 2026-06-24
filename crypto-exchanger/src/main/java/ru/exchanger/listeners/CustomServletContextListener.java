package ru.exchanger.listeners;

import ru.exchanger.repository.*;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import ru.exchanger.repository.bankcurrenciesrepositoryimpl.BankCurrenciesRepositoryJDBCImpl;
import ru.exchanger.repository.bankrepositoryimpl.BanksRepositoryJDBCImpl;
import ru.exchanger.repository.currenciesrepositoryimpl.CurrenciesRepositoryJDBCImpl;
import ru.exchanger.repository.filerepositoryimpl.FilesRepositoryImpl;
import ru.exchanger.repository.ordersrepositoryimpl.OrdersRepositoryJDBCImpl;
import ru.exchanger.service.AdminService;
import ru.exchanger.service.FilesService;
import ru.exchanger.service.UserService;
import ru.exchanger.utils.PropertyLoader;
import ru.exchanger.repository.userskycrepositoryimpl.UsersKYCRepositoryJDBCImpl;
import ru.exchanger.repository.usersrepositoryimpl.UsersRepositoryJDBCImpl;
import ru.exchanger.repository.walletsrepositoryimpl.WalletsRepositoryJDBCImpl;
import ru.exchanger.service.CurrencyService;

@WebListener
public class CustomServletContextListener implements ServletContextListener {

    //TODO: перенести все скрипты в resources

    private static final String DB_USERNAME = "postgres";

    private static final String DB_PASSWORD = PropertyLoader.getProperty(DB_USERNAME);

    private static final String DB_URL = "jdbc:postgresql://localhost:5432/kriptodb";

    private static final String DB_DRIVER = "org.postgresql.Driver";

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        ServletContext servletContext = servletContextEvent.getServletContext();

        DriverManagerDataSource dataSource = new DriverManagerDataSource();

        dataSource.setDriverClassName(DB_DRIVER);
        dataSource.setUrl(DB_URL);
        dataSource.setUsername(DB_USERNAME);
        dataSource.setPassword(DB_PASSWORD);

        // инициализация всех репозиториев
        UsersRepository usersRepository = new UsersRepositoryJDBCImpl(dataSource);
        UsersKYCRepository usersKYCRepository = new UsersKYCRepositoryJDBCImpl(dataSource);
        WalletsRepository walletsRepository = new WalletsRepositoryJDBCImpl(dataSource);
        CurrenciesRepository currenciesRepository = new CurrenciesRepositoryJDBCImpl(dataSource);
        OrdersRepository ordersRepository = new OrdersRepositoryJDBCImpl(dataSource);
        BanksRepository banksRepository = new BanksRepositoryJDBCImpl(dataSource);
        BankCurrenciesRepository bankCurrenciesRepository = new BankCurrenciesRepositoryJDBCImpl(dataSource);
        FilesRepository filesRepository = new FilesRepositoryImpl(dataSource);

        CurrencyService currencyService = new CurrencyService(currenciesRepository);
        UserService userService = new UserService(usersRepository);
        AdminService adminService = new AdminService(currenciesRepository, usersRepository);
        FilesService filesService = new FilesService(filesRepository);

        servletContext.setAttribute("usersRepository", usersRepository);
        servletContext.setAttribute("userKYCRepository", usersKYCRepository);
        servletContext.setAttribute("walletsRepository", walletsRepository);
        servletContext.setAttribute("currenciesRepository", currenciesRepository);
        servletContext.setAttribute("ordersRepository", ordersRepository);
        servletContext.setAttribute("banksRepository", banksRepository);
        servletContext.setAttribute("fileRepository", filesRepository);
        servletContext.setAttribute("bankCurrenciesRepository", bankCurrenciesRepository);
        servletContext.setAttribute("currencyService", currencyService);
        servletContext.setAttribute("userService", userService);
        servletContext.setAttribute("adminService", adminService);
        servletContext.setAttribute("fileService", filesService);

    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
    }
}
