import com.github.andrewgregersen.p0.backend.SHDriver;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class SHDriverTest {

    SHDriver driver;

    @Before
    public void init() {
        driver = new SHDriver();
    }

    @Test
    public void changeDirectoryTest1() throws IllegalArgumentException, IOException {
        String expected = Paths.get(System.getProperty("user.dir")).getParent().normalize().toString();
        driver.changeCwd(expected);
        String actual = Paths.get(System.getProperty("user.dir")).normalize().toString();
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void changeDirectoryTest2() throws IllegalArgumentException, IOException {
        String expected = Paths.get(System.getProperty("user.dir")).getParent().normalize().toString();
        driver.changeCwd("../");
        String actual = Paths.get(System.getProperty("user.dir")).normalize().toString();
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void changeDirectoryTest3() throws IllegalArgumentException, IOException {
        Path expected = Paths.get(System.getProperty("user.dir")).getParent().getParent().normalize();
        driver.changeCwd("../../");
        Path actual = Paths.get(System.getProperty("user.dir")).normalize();
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void changeDirectoryTest4() throws IllegalArgumentException, IOException {
        Path expected = Paths.get(System.getProperty("user.dir")).toAbsolutePath().normalize();
        driver.changeCwd("../p0/");
        Path actual = Paths.get(System.getProperty("user.dir")).toAbsolutePath().normalize();
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void changeDirectoryTest5() throws IllegalArgumentException, IOException {
        String expected = Paths.get(System.getProperty("user.home")).normalize().toString();
        driver.changeCwd("~");
        String actual = Paths.get(System.getProperty("user.home")).normalize().toString();
        Assert.assertEquals(expected, actual);

    }

    @Test
    public void changeDirectoryTest6() throws IOException {
        Path expected = Paths.get("A:\\").toAbsolutePath().normalize();
        driver.changeCwd("A:/");
        Path actual = Paths.get(System.getProperty("user.dir")).toAbsolutePath().normalize();
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void changeDirectoryTest7() throws IOException {
        Path expected = Paths.get("A:\\Testing").toAbsolutePath().normalize();
        driver.changeCwd("A:/Testing");
        Path actual = Paths.get(System.getProperty("user.dir")).toAbsolutePath().normalize();
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void changeDirectoryTest8() throws IOException {
        Path expected = Paths.get("A:\\Testing").toAbsolutePath().normalize();
        driver.changeCwd("A:/");
        driver.changeCwd("Testing");
        Path actual = Paths.get(System.getProperty("user.dir")).toAbsolutePath().normalize();
        Assert.assertEquals(expected, actual);
    }


}
