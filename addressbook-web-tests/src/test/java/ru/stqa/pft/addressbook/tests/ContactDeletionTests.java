package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.GroupData;

public class ContactDeletionTests extends TestBase {
    @Test
    public void testContactDeletion() {
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
        app.getContactHelper().deleteSelectedContact();
        app.getNavigationHelper().acceptAlert();
        app.getContactHelper().returnToHomePage();
        int after= app.getContactHelper().getContactCount();
        Assert.assertEquals(after ,before-1);
    }
}
