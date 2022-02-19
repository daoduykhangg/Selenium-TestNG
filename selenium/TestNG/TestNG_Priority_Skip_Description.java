package TestNG;

import org.testng.annotations.Test;

public class TestNG_Priority_Skip_Description {

    @Test(priority = 1)
    public void TC_01_Create_User() {

    }

    @Test(priority = 2, enabled = false)
    public void TC_02_Edit_User() {

    }

    @Test(priority = 3, description = "TC_03_View_User")
    public void TC_03_View_User() {

    }

    @Test(priority = 4)
    public void TC_04_Delete_User() {

    }
}
