import domain.SHDriver;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.nio.file.Paths;

public class SHDriverTest {

    SHDriver driver;

    @Before
    public void init() {
        driver = new SHDriver();
    }

    @Test
    public void changeDirectoryTest1() throws IllegalArgumentException {
        String expected = Paths.get(System.getProperty("user.dir")).getParent().normalize().toString();
        driver.changeCwd(expected);
        String actual = Paths.get(System.getProperty("user.dir")).normalize().toString();
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void changeDirectoryTest2() throws IllegalArgumentException {
        String expected = Paths.get(System.getProperty("user.dir")).getParent().normalize().toString();
        driver.changeCwd("../");
        String actual = Paths.get(System.getProperty("user.dir")).normalize().toString();
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void changeDirectoryTest3() throws IllegalArgumentException {
        String expected = Paths.get(System.getProperty("user.dir")).getParent().normalize().toString();
        driver.changeCwd("../../");
        String actual = Paths.get(System.getProperty("user.dir")).normalize().toString();
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void changeDirectoryTest4() throws IllegalArgumentException {
        String expected = Paths.get(System.getProperty("user.dir")).getParent().normalize().toString();
        driver.changeCwd("../p0/");
        String actual = Paths.get(System.getProperty("user.dir")).normalize().toString();
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void changeDirectoryTest5() throws IllegalArgumentException {
        String expected = Paths.get(System.getProperty("user.home")).normalize().toString();
        driver.changeCwd("~");
        String actual = Paths.get(System.getProperty("user.home")).normalize().toString();
        Assert.assertEquals(expected, actual);

    }


}
