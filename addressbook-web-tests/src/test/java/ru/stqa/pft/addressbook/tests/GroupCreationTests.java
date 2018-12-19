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
        app.getNavigationHelper().gotoGroupPage();
    }

        @Test
    public void testGroupCreation() {
        List<GroupData> before=app.getGroupHelper().getGroupList();
        GroupData group = new GroupData("test1", null, "test3");
        app.getGroupHelper().createGroup(group);
        List<GroupData> after=app.getGroupHelper().getGroupList();
        assertEquals(after.size() ,before.size()+1);

        group.setId(after.stream().max((o1, o2) -> Integer.compare(o1.getId(), o2.getId())).get().getId());
        before.add(group);
        Assert.assertEquals(new HashSet<Object>(before), new  HashSet<Object>(after));

    }

}
