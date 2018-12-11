package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

public class ContactCreationTests extends TestBase {

    @Test
    public void testContactCreation() {
        app.getContactHelper().navigateToNewContact();
        app.getContactHelper().fillContactForm(
                new ContactData(
                        "first name",
                        "last name",
                        "nik",
                        "Address",
                        "+79269998877",
                        "+79031113366",
                        "email@mail.ru",
                        "test1"),
                true);
        app.getContactHelper().submitContactCreation();
        app.getContactHelper().returnToHomePage();
    }

}
