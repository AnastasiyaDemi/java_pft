package ru.stqa.pft.addressbook.tests;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;
import ru.stqa.pft.addressbook.model.GroupData;
import ru.stqa.pft.addressbook.model.Groups;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactCreationTests extends TestBase {

    @BeforeMethod
    public void ensurePreconditions() {
        if (app.db().groups().size()==0) {
            app.goTo().groupPage();
            app.group().create(new GroupData().withName("nameCreate0"));
        }
        app.goTo().HomePage();
    }

    @DataProvider
    public Iterator<Object[]> validContactsAsJson() throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(new File("src/test/resources/contacts.json")))) {

            String json = "";
            String line = reader.readLine();
            while (line != null) {
                json += line;
                line = reader.readLine();
            }
            Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
            List<ContactData> contacts = gson.fromJson(json, new TypeToken<List<ContactData>>() {}.getType());

            return contacts.stream()
                    .map((g) -> new Object[]{g})
                    .collect(Collectors.toList()).iterator();
        }
    }

    @Test(dataProvider = "validContactsAsJson")
    public void testContactCreation(ContactData contact) {
        Contacts before = app.db().contacts();

        Groups groups = app.db().groups();
        GroupData group = groups.iterator().next();

        ContactData enhancedContact = contact.withAdditionalGroup(group);
        app.contact().createContact(enhancedContact);

        assertThat(app.contact().getContactCount(), equalTo(before.size() + 1));
        Contacts after = app.db().contacts();
        assertThat(after, equalTo(
                before.withAdded(contact.withId(after.stream().mapToInt((g) -> g.getId()).max().getAsInt()))));
        verifyContactListInUI();

    }
}
