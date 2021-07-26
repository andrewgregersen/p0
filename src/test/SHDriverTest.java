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

    public void getCWDTest() {
        String expected = System.getProperty("user.dir");
        String actual = driver.getCwd();
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void changeDirectoryTest1() throws ErrException {
        String expected = Paths.get(System.getProperty("user.dir")).getParent().normalize().toString();
        driver.setCwd(expected);
        String actual = Paths.get(System.getProperty("user.dir")).normalize().toString();
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void changeDirectoryTest2() throws ErrException {
        String expected = Paths.get(System.getProperty("user.dir")).getParent().normalize().toString();
        driver.setCwd("../");
        String actual = Paths.get(System.getProperty("user.dir")).normalize().toString();
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void changeDirectoryTest3() throws ErrException {
        String expected = Paths.get(System.getProperty("user.dir")).getParent().normalize().toString();
        driver.setCwd("../../");
        String actual = Paths.get(System.getProperty("user.dir")).normalize().toString();
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void changeDirectoryTest4() throws ErrException {
        String expected = Paths.get(System.getProperty("user.dir")).getParent().normalize().toString();
        driver.setCwd("../p0");
        String actual = Paths.get(System.getProperty("user.dir")).normalize().toString();
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void changeDirectoryTest5() {
        SHDriver driver = new SHDriver();

    }


}
