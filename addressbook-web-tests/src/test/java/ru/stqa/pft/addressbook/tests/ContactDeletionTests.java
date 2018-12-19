package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.GroupData;

import java.util.List;

public class ContactDeletionTests extends TestBase {
    @BeforeMethod
    public void ensurePreconditions () {
        app.goTo().groupPage();
        if (! app.group().isThereAGroup()) {
            app.group().create(new GroupData().withName("test1").withHeader("test2").withFooter("test3"));
        }
        app.goTo().HomePage();
        if (! app.contact().isThereAContact()) {
            app.contact().createContact(new ContactData(
                    "first name",
                    "last name",
                    "nik",
                    "Address",
                    "+79269998877",
                    "+79031113366",
                    "email@mail.ru",
                    "test1"));
        }
    }

    @Test
    public void testContactDeletion() {
        List<ContactData> before=app.contact().List();
        int index = before.size() -1;
        app.contact().delete(index);
        app.goTo().acceptAlert();
        app.contact().returnToHomePage();
        List<ContactData> after=app.contact().List();
        Assert.assertEquals(after.size() ,before.size()-1);
        before.remove(index);
        Assert.assertEquals(before, after);
    }

}
