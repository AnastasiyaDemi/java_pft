package ru.stqa.pft.addressbook.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ContactHelper extends HelperBase {

    public ContactHelper(WebDriver wd) {
        super(wd);
    }

    public void navigateToNewContact() {
        click(By.linkText("add new"));
    }

    public void fillContactForm(ContactData contactData, boolean creation) {
        type(By.name("firstname"), contactData.getFirstName());
        type(By.name("lastname"), contactData.getLastName());
        type(By.name("nickname"), contactData.getNickName());
        type(By.name("address"), contactData.getAddress());
        type(By.name("home"), contactData.getHomePhone());
        type(By.name("mobile"), contactData.getMobilePhone());
        type(By.name("work"), contactData.getWorkPhone());
        type(By.name("email"), contactData.getEmail());
        attach(By.name("photo"), new File(contactData.getPhotoPath()));

        if (creation) {
            Select select = new Select(wd.findElement(By.name("new_group")));
            if (contactData.getGroup() == null){
                select.selectByIndex(1);
            } else {
                select.selectByVisibleText(contactData.getGroup());
            }
        } else {
            Assert.assertFalse(isElementPresent(By.name("new_group")));
        }
    }


    public void returnToHomePage() {
        click(By.linkText("home"));
    }

    public void submitContactCreation() {
        click(By.name("submit"));
    }

    public void selectContact(int index) {
        wd.findElements(By.name("selected[]")).get(index).click();
    }


    public void deleteSelectedContact() {
        click(By.xpath("//div[@id='content']/form[2]/div[2]/input"));
    }

    public void initContactModification(int id) {
        wd.findElement(By.cssSelector("a[href='edit.php?id=" + id + "']")).click();

    }

    public void submitContactModification() {
        click(By.name("update"));
    }

    public void createContact(ContactData contact) {
        navigateToNewContact();
        fillContactForm(contact, true);
        submitContactCreation();
        contactCache = null;
        returnToHomePage();

    }

    public void modify(ContactData contact) {
        selectContactById(contact.getId());
        initContactModification(contact.getId());
        fillContactForm(contact, false);
        submitContactModification();
        contactCache = null;
        returnToHomePage();
    }

    public void delete(int index) {
        selectContact(index);
        deleteSelectedContact();
    }

    public void delete(ContactData contact) {
        selectContactById(contact.getId());
        deleteSelectedContact();
        contactCache = null;
    }

    public void selectContactById(int id) {
        wd.findElement(By.cssSelector("input[id='" + id + "']")).click();
    }

    public boolean isThereAContact() {
        return isElementPresent(By.name("selected[]"));
    }

    public int getContactCount() {
        return wd.findElements(By.name("selected[]")).size();
    }

    /*public List<ContactData> List() {
        List<ContactData> contacts = new ArrayList<>();
        WebElement table = wd.findElement(By.cssSelector("table#maintable"));
        List<WebElement> rows = table.findElements(By.tagName("tr"));
        for (int r = 1; r < rows.size(); r++) {
            WebElement row = rows.get(r);
            List<WebElement> cells = row.findElements(By.tagName("td"));
            String firstName = cells.get(2).getText();
            String lastName = cells.get(1).getText();
            String address = cells.get(3).getText();
            String email = cells.get(4).getText();
            int id = Integer.parseInt(row.findElement(By.tagName("input")).getAttribute("value"));
            ContactData contact = new ContactData(id, firstName, lastName, null, address, null, null, email, null);
            contacts.add(contact);
        }
        return contacts;
    }*/


    private Contacts contactCache = null;


    public Contacts all() {
        if (contactCache != null) {
            return new Contacts(contactCache);
        }

        contactCache = new Contacts();
        WebElement table = wd.findElement(By.cssSelector("table#maintable"));
        List<WebElement> rows = table.findElements(By.tagName("tr"));
        for (int r = 1; r < rows.size(); r++) {
            WebElement row = rows.get(r);
            List<WebElement> cells = row.findElements(By.tagName("td"));
            String firstName = cells.get(2).getText();
            String lastName = cells.get(1).getText();
            String address = cells.get(3).getText();
            String allEmails = cells.get(4).getText();
            String allPhones = cells.get(5).getText();
            int id = Integer.parseInt(row.findElement(By.tagName("input")).getAttribute("value"));
            ContactData contact = new ContactData()
                    .withId(id)
                    .withFirstName(firstName)
                    .withLastName(lastName)
                    .withAddresse(address)
                    .withAllEmails(allEmails)
                    .withAllPhones(allPhones);
            contactCache.add(contact);
        }
        return new Contacts(contactCache);
    }


    public ContactData infoFromEditForm(ContactData contact) {
        initContactModificationByID(contact.getId());
        String firstname = wd.findElement(By.name("firstname")).getAttribute("value");
        String lastname = wd.findElement(By.name("lastname")).getAttribute("value");
        String home = wd.findElement(By.name("home")).getAttribute("value");
        String mobile = wd.findElement(By.name("mobile")).getAttribute("value");
        String work = wd.findElement(By.name("work")).getAttribute("value");
        String address = wd.findElement(By.name("address")).getAttribute("value");
        String email = wd.findElement(By.name("email")).getAttribute("value");
        String email2 = wd.findElement(By.name("email2")).getAttribute("value");
        String email3 = wd.findElement(By.name("email3")).getAttribute("value");

        wd.navigate().back(); //????
        return new ContactData ()
                .withId(contact.getId())
                .withFirstName(firstname)
                .withLastName(lastname)
                .withHomePhone(home)
                .withMobilePhone(mobile)
                .withWorkPhone(work)
                .withAddresse(address)
                .withEmail(email)
                .withEmail2(email2)
                .withEmail3(email3);
    }

    private void initContactModificationByID(int id) {
        wd.findElement(By.cssSelector(String.format("a[href='edit.php?id=%s']", id))).click();
    }
}
