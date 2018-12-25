package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.GroupData;

import java.util.Arrays;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactPhonesEmailsAddressTest extends TestBase {

    @BeforeMethod
    public void ensurePreconditions() {
        app.goTo().groupPage();
        if (app.group().all().size() == 0) {
            app.group().create(new GroupData().withName("nameCreate0"));
        }
        app.goTo().HomePage();
        if (!app.contact().isThereAContact()) {
            app.contact().createContact(new ContactData()
                    .withFirstName("Cr0 first name")
                    .withLastName("Cr0 first name")
                    .withNickName("Cr0 nik")
                    .withAddresse("Cr0 Address")
                    .withMobilePhone("+379269998877")
                    .withHomePhone("+379531113366")
                    .withEmail("Cr0email@mail.ru"));
        }
    }

    @Test
    public void testContactPhonesEmailsAddress() {
        ContactData contact = app.contact().all().iterator().next();
        ContactData contactInfoFromEditForm = app.contact().infoFromEditForm(contact);
        assertThat(contact.getAllPhones(), equalTo(mergePhones(contactInfoFromEditForm)));
        assertThat(contact.getAllEmails(), equalTo(mergeEmails(contactInfoFromEditForm)));
        assertThat(cleanedAddress(contact.getAddress()), equalTo(cleanedAddress(contactInfoFromEditForm.getAddress())));

    }


    private String mergePhones(ContactData contact) {
        return Arrays.asList(contact.getHomePhone(), contact.getMobilePhone(), contact.getWorkPhone())
                .stream().filter((s -> !s.equals(""))).map(ContactPhonesEmailsAddressTest::cleanedPhones).collect(Collectors.joining("\n"));
    }

    private String mergeEmails(ContactData contact) {
        return Arrays.asList(contact.getEmail(), contact.getEmail2(), contact.getEmail3())
                .stream().filter((s -> !s.equals(""))).map(ContactPhonesEmailsAddressTest::cleanedEmails).collect(Collectors.joining("\n"));
    }

    private static String cleanedPhones(String index) {
        return index.replaceAll("\\s", "").replaceAll("[-()]", "");
    }

    private static String cleanedAddress(String index) {
        return index.replaceAll("\\s", "");
    }

    private static String cleanedEmails(String index) {
        return index.replaceAll("\\s", "");
    }

}
