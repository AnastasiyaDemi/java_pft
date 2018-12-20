package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;
import ru.stqa.pft.addressbook.model.GroupData;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactModificationTests extends TestBase {

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
    public void testContactModification() {
        Contacts before = app.contact().all();
        ContactData modifiedContact = before.iterator().next();
        ContactData contact = new ContactData(modifiedContact.getId(),
                "3first name",
                "3last name",
                "3nik",
                "3Address",
                "+379269998877",
                "+379031113366",
                "3email@mail.ru",
                "[none]");
        app.contact().modify(contact);
        Contacts after = app.contact().all();
        assertThat(after.size(), equalTo(before.size()));
        assertThat(after, equalTo(before.without(modifiedContact).withAdded(contact)));
    }


}
