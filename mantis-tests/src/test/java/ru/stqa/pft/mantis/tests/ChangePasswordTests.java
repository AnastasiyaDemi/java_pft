package ru.stqa.pft.mantis.tests;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.lanwen.verbalregex.VerbalExpression;
import ru.stqa.pft.mantis.model.MailMessage;
import ru.stqa.pft.mantis.model.MantisUser;

import javax.mail.MessagingException;
import java.io.IOException;
import java.util.List;

import static org.testng.Assert.assertTrue;

public class ChangePasswordTests extends TestBase {

    @BeforeMethod
    public void startMailServer() throws Exception {
        app.mail().start();
    }

    @Test
    public void testChangePassword() throws IOException, MessagingException {

        String user = "user1548284875005";

        long now = System.currentTimeMillis();
        String newPassword = String.format("password-%s", now);

        app.navigation().login(app.getProperty("web.adminLogin"), app.getProperty("web.adminPassword"));
        app.navigation().manage();
        app.navigation().manageUsers();

        MantisUser dbUser = app.db().users().stream()
                .filter(u -> u.getUsername().equals(user))
                .findFirst().get();

        app.navigation().editUser(dbUser.getId());
        app.navigation().resetPassword();

        List<MailMessage> mailMessages = app.mail().waitForMail(2, 10000);
        String confirmationLink = findConfirmationLink(mailMessages, dbUser.getEmail());
        app.navigation().changePassword(confirmationLink, newPassword);

        //  проверить, что пользователь может войти в систему с новым паролем
        assertTrue(app.newSession().login(user, newPassword));
        assertTrue(app.newSession().isLoggedInAs(user));
    }

    @AfterMethod(alwaysRun = true)
    public void stopMailServer() throws Exception {
        app.mail().stop();
    }


    private String findConfirmationLink(List<MailMessage> mailMessages, String email) {
        MailMessage mailMessage = mailMessages.stream().filter((m) -> m.to.equals(email)).findFirst().get();
        VerbalExpression regex = VerbalExpression.regex().find("http://").nonSpace().oneOrMore().build();
        return regex.getText(mailMessage.text);
    }
}
