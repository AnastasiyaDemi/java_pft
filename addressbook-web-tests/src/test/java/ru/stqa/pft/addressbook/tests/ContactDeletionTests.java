package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;
import ru.stqa.pft.addressbook.model.GroupData;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactDeletionTests extends TestBase {
    @BeforeMethod
    public void ensurePreconditions() {
        app.goTo().groupPage();
        if (app.group().all().size() == 0) {
            app.group().create(new GroupData().withName("nameCreate0"));
        }
        app.goTo().HomePage();
        if (!app.contact().isThereAContact()) {
            app.contact().createContact(new ContactData()
                    .withFirstName("Mod first name")
                    .withLastName("Mod first name")
                    .withNickName("Mod nik")
                    .withAddresse("Mod Address")
                    .withMobilePhone("+379269998877")
                    .withHomePhone("+379031113366")
                    .withEmail("3email@mail.ru"));
        }
    }

    @Test
    public void testContactDeletion() {
        Contacts before = app.contact().all();
        ContactData deletedContact = before.iterator().next();
        app.contact().delete(deletedContact);
        app.goTo().acceptAlert();
        app.contact().returnToHomePage();
        assertThat(app.contact().getContactCount(), equalTo(before.size() - 1));
        Contacts after = app.contact().all();
        assertThat(after, equalTo(before.without(deletedContact)));
    }

}
