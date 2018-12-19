package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.GroupData;
import java.util.Comparator;
import java.util.List;

public class ContactModificationTests extends TestBase {

    @BeforeMethod
    public void ensurePreconditions () {
        app.getNavigationHelper().gotoGroupPage();
        if (! app.getGroupHelper().isThereAGroup()) {
            app.getGroupHelper().createGroup(new GroupData("test1", "modif test2", null));
        }
        app.getNavigationHelper().gotoHomePage();
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
    }

    @Test
    public void testContactModification() {
        List<ContactData> before=app.getContactHelper().getContactList();
        int index = before.size()-1;
        ContactData contact = new ContactData(before.get(index).getId(),
                "3first name",
                "3last name",
                "3nik",
                "3Address",
                "+379269998877",
                "+379031113366",
                "3email@mail.ru",
                null);
        app.getContactHelper().modifyContact(before, index, contact);
        List<ContactData> after=app.getContactHelper().getContactList();
        Assert.assertEquals(after.size() ,before.size());
        before.remove(index);
        before.add(contact);
        Comparator<? super ContactData> byId = (c1,c2)-> Integer.compare(c1.getId(), c2.getId());
        before.sort(byId);
        after.sort(byId);
        Assert.assertEquals(before,after);
    }


}
