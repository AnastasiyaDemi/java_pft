package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.GroupData;
import java.util.Comparator;
import java.util.List;

public class ContactCreationTests extends TestBase {
    
    @BeforeMethod
    public void ensurePreconditions () {
        app.goTo().groupPage();
        if (! app.group().isThereAGroup()) {
            app.group().create(new GroupData().withName("test1").withHeader("test2").withFooter("test3"));
        }
        app.goTo().HomePage();
    }

    @Test
    public void testContactCreation() {
        List<ContactData> before=app.contact().List();
        ContactData contact = new ContactData(
                "first name",
                "last name",
                "nik",
                "Address",
                "+79269998877",
                "+79031113366",
                "email@mail.ru",
                "test1");
        app.contact().createContact(contact);
        List<ContactData> after=app.contact().List();
        Assert.assertEquals(after.size() ,before.size()+1);
        contact.setId(after.stream().max((o1, o2) -> Integer.compare(o1.getId(), o2.getId())).get().getId());
        before.add(contact);
        Comparator<? super ContactData> byId = (c1, c2)-> Integer.compare(c1.getId(), c2.getId());
        before.sort(byId);
        after.sort(byId);
        Assert.assertEquals(before,after);
    }



}
