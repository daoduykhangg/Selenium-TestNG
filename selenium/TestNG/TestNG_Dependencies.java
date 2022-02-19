package TestNG;

import listener.ExtendReport;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
@Listeners(ExtendReport.class)
public class TestNG_Dependencies {

    @Test
    public void TC_01(){
        Assert.assertTrue(false);
    }
    @Test(dependsOnMethods = "TC_01")
    public void TC_02(){

    }
    @Test()
    public void TC_03(){

    }
    @Test(dependsOnMethods = "TC_03")
    public void TC_04(){

    }
}
