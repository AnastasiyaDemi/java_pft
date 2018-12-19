package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.GroupData;
import java.util.HashSet;
import java.util.List;

import static org.testng.Assert.assertEquals;

public class GroupCreationTests extends TestBase {


    @BeforeMethod
    public void ensurePreconditions () {
        app.goTo().groupPage();
    }

        @Test
    public void testGroupCreation() {
        List<GroupData> before=app.group().list();
        GroupData group = new GroupData().withName("test1").withFooter("test3");
        app.group().create(group);
        List<GroupData> after=app.group().list();
        assertEquals(after.size() ,before.size()+1);

        group.withId(after.stream().max((o1, o2) -> Integer.compare(o1.getId(), o2.getId())).get().getId());
        before.add(group);
        Assert.assertEquals(new HashSet<Object>(before), new  HashSet<Object>(after));

    }

}
