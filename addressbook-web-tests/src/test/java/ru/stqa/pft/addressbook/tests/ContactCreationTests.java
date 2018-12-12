package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.GroupData;

public class ContactCreationTests extends TestBase {

    @Test
    public void testContactCreation() {
        app.getNavigationHelper().gotoGroupPage();
        if (! app.getGroupHelper().isThereAGroup()) {
            app.getGroupHelper().createGroup(new GroupData("test1", "modif test2", null));
        }
        app.getNavigationHelper().gotoHomePage();
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



}
