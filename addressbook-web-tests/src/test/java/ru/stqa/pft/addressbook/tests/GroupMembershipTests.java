package ru.stqa.pft.addressbook.tests;

import org.kohsuke.randname.RandomNameGenerator;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;
import ru.stqa.pft.addressbook.model.GroupData;
import ru.stqa.pft.addressbook.model.Groups;
import java.util.Optional;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;
import static org.testng.Assert.fail;

public class GroupMembershipTests extends TestBase {

    @BeforeMethod
    public void ensurePreconditions() {
        if (app.db().groups().size() == 0) {
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
    public void testContactGroupAddition() {
        Contacts before = app.db().contacts();
        Groups groups = app.db().groups();
        ContactData foundContact = null;
        GroupData foundGroup = null;
        for (ContactData c : before) {
            for (GroupData g : groups) {
                if (!c.getGroups().contains(g)) {
                    foundContact = c;
                    foundGroup = g;
                }
            }
        }
        if (foundContact == null) {
            app.goTo().groupPage();
            String groupName = new RandomNameGenerator(0).next();
            GroupData newGroup = new GroupData().withName(groupName);
            app.group().create(newGroup);
            app.goTo().HomePage();
            GroupData foundDbGroup = app.db().groups().stream()
                    .filter(g -> g.getName().equals(newGroup.getName()))
                    .findAny()
                    .get();
            foundGroup = foundDbGroup;
            foundContact = app.db().contacts().iterator().next();
        }

        app.contact().contactAddToGroup(foundContact, foundGroup);

        Contacts after = app.db().contacts();
        int foundContactId = foundContact.getId();
        Optional<ContactData> foundContactOptional = after.stream()
                .filter(c -> c.getId() == foundContactId)
                .findAny();
        if (foundContactOptional.isPresent()) {
            ContactData foundContact1 = foundContactOptional.get();
            assertTrue(foundContact1.getGroups().contains(foundGroup), "found contact does not contain necessary group");
        } else {
            fail("modified contact not found");
        }
    }

    @Test
    public void testContactGroupDeletion() {
        Contacts before = app.db().contacts();
        Groups groups = app.db().groups();
        ContactData foundContact = null;
        GroupData foundGroup = null;

        Optional<ContactData> uniqueContact = before.stream()
                .filter(contactData -> contactData.getGroups().stream().anyMatch(g -> groups.contains(g)))
                .findAny();

        if (uniqueContact.isPresent()) {
            foundContact = uniqueContact.get();
            foundGroup = foundContact.getGroups().stream().findAny().get();
        } else {
            ContactData foundDbContact = app.db().contacts().iterator().next();
            GroupData foundDbGroup = app.db().groups().iterator().next();
            app.contact().contactAddToGroup(foundDbContact, foundDbGroup);
            foundContact = foundDbContact;
            foundGroup = foundDbGroup;
        }

        app.contact().contactGroupDeletion(foundContact, foundGroup);

        Contacts after = app.db().contacts();
        int foundContactId = foundContact.getId();
        ContactData contactAfter = after.stream()
                .filter(c -> c.getId() == foundContactId)
                .findAny()
                .get();
        assertFalse(contactAfter.getGroups().contains(foundGroup), "found contact does not contain necessary group");

    }

}