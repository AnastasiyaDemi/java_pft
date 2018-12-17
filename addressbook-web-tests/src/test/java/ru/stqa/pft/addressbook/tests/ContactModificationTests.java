package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.GroupData;

public class ContactModificationTests extends TestBase {
    @Test
    public void testContactModification() {
        app.getNavigationHelper().gotoGroupPage();
        if (! app.getGroupHelper().isThereAGroup()) {
            app.getGroupHelper().createGroup(new GroupData("test1", "modif test2", null));
        }
        app.getNavigationHelper().gotoHomePage();
        int before= app.getContactHelper().getContactCount();
        if (! app.getContactHelper().isThereAContact()) {
            app.getContactHelper().createContact(new ContactData(
                    "first name",
                    "last name",
                    "nik",
                    "Address",
                    "+79269998877",
                    "+79031113366",
                    "email@mail.ru",
                    "test1"));
        }
        app.getContactHelper().selectContact();
        app.getContactHelper().initContactModification();
        app.getContactHelper().fillContactForm(
                new ContactData(
                        "2first name",
                        "2last name",
                        "2nik",
                        "Address",
                        "+79269998877",
                        "+79031113366",
                        "email@mail.ru",
                        null),
                 false);
        app.getContactHelper().submitContactModification();
        app.getContactHelper().returnToHomePage();
        int after= app.getContactHelper().getContactCount();
        Assert.assertEquals(after, before );

    }
}
