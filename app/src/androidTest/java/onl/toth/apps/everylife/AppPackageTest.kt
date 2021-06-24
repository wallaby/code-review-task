package onl.toth.apps.everylife

import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class AppPackageTest {
    @Test
    fun testPackageName() {
        // Context of the app under test.
        val appContext = ApplicationProvider.getApplicationContext<TaskListApp>()
        Assert.assertEquals("onl.toth.apps.everylife", appContext.packageName)
    }
}