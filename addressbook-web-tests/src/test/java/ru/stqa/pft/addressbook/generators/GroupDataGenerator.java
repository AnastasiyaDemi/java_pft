package ru.stqa.pft.addressbook.generators;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.ParameterException;
import ru.stqa.pft.addressbook.model.GroupData;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

public class GroupDataGenerator {

    @Parameter(names = "-c", description = "Group count")
    public int count;

    @Parameter(names = "-f", description = "Target file")
    public String file;

    public void run() throws IOException {
        System.out.printf("%d %s\n", count, file);
        List<GroupData> groups = generateGroups(count);
        save(groups, new File(file));
    }

    public static void main(String[] args) throws IOException {//т.к.передавать исключение уже не кому, программа упадет и мы это увидим
        GroupDataGenerator generator = new GroupDataGenerator();
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

        // int count = Integer.parseInt(args[0]); //передается количество групп, но предварительно преобразовываем в число
        // File file = new File(args[1]); //передаем путь к файлу
    }

    private void save(List<GroupData> groups, File file) throws IOException { //если возникнет исключение, метод save передает методу main
        System.out.println(new File(".").getAbsolutePath()); //выводим абсолютный адрес корневой дириктории
        boolean b = file.getParentFile().getAbsoluteFile().mkdirs();
        System.out.println(b);
        Writer writer = new FileWriter(file.getAbsoluteFile());
        for (GroupData group : groups) {
            writer.write(String.format("%s;%s;%s\n", group.getName(), group.getHeader(), group.getFooter()));//\n- перевод на следующую строку
        }
        writer.close();
    }

    private List<GroupData> generateGroups(int count) {
        List<GroupData> groups = new ArrayList<GroupData>();
        for (int i = 0; i < count; i++) {
            groups.add(new GroupData().withName(String.format("test %s", i))
                    .withHeader(String.format("header %s", i))
                    .withFooter(String.format("footer %s", i)));
        }
        return groups;
    }
}
