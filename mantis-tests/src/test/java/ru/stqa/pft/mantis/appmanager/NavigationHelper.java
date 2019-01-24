package ru.stqa.pft.mantis.appmanager;

import org.openqa.selenium.By;

public class NavigationHelper extends HelperBase {

    public NavigationHelper(ApplicationManager app) {
        super(app);
    }

    public void login(String username, String password) {

        wd.get(app.getProperty("web.baseUrl"));

        type(By.name("username"), username);
        type(By.name("password"), password);

        click(By.cssSelector("input[value='Login']"));
    }

    public void manage() {
        click(By.cssSelector("a[href='/manage_overview_page.php']"));
    }

    public void manageUsers() {
        click(By.cssSelector("a[href='/manage_user_page.php']"));
    }

    public void editUser(int id) {
        click(By.cssSelector("a[href='manage_user_edit_page.php?user_id=" + id + "']"));
    }

    public void resetPassword() {
        click(By.cssSelector("input[value='Reset Password']"));
    }

    public void changePassword(String confirmationLink, String password) {
        wd.get(confirmationLink);
        type(By.name("password"), password);
        type(By.name("password_confirm"), password);
        click(By.cssSelector("input[value='UpdateUser']"));
    }

}
