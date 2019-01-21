package ru.stqa.pft.mantis.appmanager;

import org.openqa.selenium.WebDriver;

public class RegistrationHelper {
    private final ApplicationManager app;
    private WebDriver wd;

    public RegistrationHelper(ApplicationManager app) {
        this.app = app;
        wd = app.getDriver(); //"ленивая" инициализация - этот метод будет инициализировать драйвер в момент первого образщения
    }

    public void start(String username, String email) {
        wd.get(app.getProperty("web.baseUrl")+"/signup_page.php");
    }
}

