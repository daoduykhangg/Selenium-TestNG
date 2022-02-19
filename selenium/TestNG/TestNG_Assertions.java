package TestNG;

import org.testng.Assert;
import org.testng.annotations.Test;

public class TestNG_Assertions {
    @Test
    public void test(){
        int number = 3+6;
        Assert.assertTrue(true);
        Assert.assertFalse(false);
        Assert.assertEquals(number, 9);
    }
}
