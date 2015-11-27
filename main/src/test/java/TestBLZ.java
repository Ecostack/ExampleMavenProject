import de.techstack.example.Checker;
import de.techstack.example.Environment;
import org.junit.Assert;
import org.junit.Test;

import java.sql.SQLException;

/**
 * Created by basti on 27.11.15.
 */
public class TestBLZ {

    @Test
    public void testBLZ1() throws SQLException {
        String lcBLZ = "40060000";
        Environment.instance.getChecker().checkNumberAndWriteResultInDatabase(lcBLZ);
        String lcName = Environment.instance.getDbManager().readBLZNameEntry(lcBLZ);
        Assert.assertNotNull(lcName);
        Assert.assertTrue(lcName.length() > 0);
    }

    @Test
    public void testBLZ2() throws SQLException {
        String lcBLZ = "49050102";
        Environment.instance.getChecker().checkNumberAndWriteResultInDatabase(lcBLZ);
        String lcName = Environment.instance.getDbManager().readBLZNameEntry(lcBLZ);
        Assert.assertNull(lcName);
    }
}
