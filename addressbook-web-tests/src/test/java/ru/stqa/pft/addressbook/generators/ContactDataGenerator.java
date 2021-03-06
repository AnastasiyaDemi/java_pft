package ru.stqa.pft.addressbook.generators;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.ParameterException;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import ru.stqa.pft.addressbook.model.ContactData;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

public class ContactDataGenerator {

    private final int CATS_TOTAL = 5;

    @Parameter(names = "-c", description = "Contact count")
    public int count;

    @Parameter(names = "-f", description = "Target file")
    public String file;

    @Parameter(names = "-d", description = "Data format")
    public String format;

    public void run() throws IOException {
        List<ContactData> contacts = generateContacts(count);
        if (format.equals("json")) {
            saveAsJson(contacts, new File(file));
        } else {
            System.out.println("Unrecognised format: " + format);
        }

    }

    private void saveAsJson(List<ContactData> contacts, File file) throws IOException {
        Gson gson = new GsonBuilder().setPrettyPrinting().excludeFieldsWithoutExposeAnnotation().create();
        String json = gson.toJson(contacts);
        try (Writer writer = new FileWriter(file)) {
        writer.write(json);
        }
    }

    public static void main(String[] args) throws IOException {
        ContactDataGenerator generator = new ContactDataGenerator();
        JCommander jCommander = JCommander.newBuilder()
                .addObject(generator)
                .build();
        try {
            jCommander.parse(args);
        } catch (ParameterException ex) {
            jCommander.usage();
            return;
        }
        generator.run();

    }

    private List<ContactData> generateContacts(int count) {
        List<ContactData> contacts = new ArrayList<ContactData>();
        for (int i = 0; i < count; i++) {
            File photo = new File(String.format("src/test/resources/cat-%d.png", i % CATS_TOTAL));
            contacts.add(new ContactData()
                    .withFirstName(String.format("first name %d", i))
                    .withLastName(String.format("last name %d", i))
                    .withEmail(String.format("e-mail%d@mail.com", i))
                    .withHomePhone(String.format("+678689908224%d", i))
                    .withAddresse(String.format("test address %d", i))
                    .withPhotoPath(photo.getPath())
                    .withNickName(String.format("New nik %d", i))
                    .withMobilePhone(String.format("+379269998877%d", i))
                    .withWorkPhone(String.format("+379531113366%d", i)));
        }
        return contacts;
    }



}
