package ru.stqa.pft.addressbook.tests;

import org.hamcrest.MatcherAssert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.GroupData;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactPhoneTest extends TestBase {

    @BeforeMethod
    public void ensurePreconditions() {
        app.goTo().groupPage();
        if (app.group().all().size() == 0) {
            app.group().create(new GroupData().withName("nameCreate0"));
        }
        app.goTo().HomePage();
        if (!app.contact().isThereAContact()) {
            app.contact().createContact(new ContactData(
                    "first name",
                    "last name",
                    "nik",
                    "Address",
                    "+79269998877",
                    "+79031113366",
                    "email@mail.ru",
                    "[none]"));
        }
    }


    @Test
    public void testContactPhones () {
        ContactData contact = app.contact().all().iterator().next();
        ContactData contactInfoFromEditForm = app.contact().infoFromEditForm(contact);
        assertThat(contact.getHomePhone(),equalTo(cleaned(contactInfoFromEditForm.getHomePhone())));
        assertThat(contact.getMobilePhone(),equalTo(cleaned(contactInfoFromEditForm.getMobilePhone())));
        assertThat(contact.getWorkPhone(),equalTo(cleaned(contactInfoFromEditForm.getWorkPhone())));
    }

    public String cleaned (String phone) {
        return phone.replaceAll("\\s","").replaceAll("[-()]","");
    }
}
