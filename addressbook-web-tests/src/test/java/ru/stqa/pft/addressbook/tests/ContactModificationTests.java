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
        if (app.db().groups().size()==0) {
            app.goTo().groupPage();
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
                    .withHomePhone("+379531113366")
                    .withEmail("3email@mail.ru"));
        }
    }

    @Test
    public void testContactModification() {
        Contacts before = app.db().contacts();
        ContactData modifiedContact = before.iterator().next();
        ContactData contact = new ContactData()
                .withId(modifiedContact.getId())
                .withFirstName("Mod first name2")
                .withLastName("Mod last name")
                .withNickName("Mod nik")
                .withAddresse("Mod Address")
                .withMobilePhone("+379269998877")
                .withHomePhone("+379531113366")
                .withWorkPhone("+379531113360")
                .withEmail("3email@mail.ru")
                .withPhotoPath("src/test/resources/cat-4.png");
        app.contact().modify(contact);
        assertThat(app.contact().getContactCount(), equalTo(before.size()));
        Contacts after = app.db().contacts();
        assertThat(after, equalTo(before.without(modifiedContact).withAdded(contact)));
        verifyContactListInUI();
    }


}
