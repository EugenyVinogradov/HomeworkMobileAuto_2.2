package ru.netology.testing.uiautomator

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.uiautomator.By
import androidx.test.uiautomator.UiDevice
import androidx.test.uiautomator.UiSelector
import androidx.test.uiautomator.Until
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.util.concurrent.TimeUnit

@RunWith(AndroidJUnit4::class)
class MainActivityTest {

    private lateinit var device: UiDevice;
    private val textToSet = "Netology";
    private val emptyString = "";

    private fun waitForPackage(packageName: String) {
        val  context = ApplicationProvider.getApplicationContext<Context>();
        val intent = context.packageManager.getLaunchIntentForPackage(packageName);
        context.startActivity(intent);
        device.wait(Until.hasObject(By.pkg(packageName)), TIMEOUT);
    }

    @Before
    fun beforeEachTest() {
        device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());
        device.pressHome();

        val launcherPackage = device.launcherPackageName;
        device.wait(Until.hasObject(By.pkg(launcherPackage)), TIMEOUT)
    }

    @Test
    fun testChangeText() {
        val packageName = MODEL_PACKAGE;
        waitForPackage(packageName);

        device.findObject(By.res(packageName, "userInput")).text = textToSet;
        device.findObject(By.res(packageName, "buttonChange")).click();

        val result = device.findObject(By.res(packageName, "textToBeChanged")).text;
        assertEquals(result, textToSet);
    }

    @Test
    fun testEmptyString() {
        val packageName = MODEL_PACKAGE;
        waitForPackage(packageName);

        device.findObject(By.res(packageName, "userInput")).text = textToSet;
        device.findObject(By.res(packageName, "buttonChange")).click();
        device.findObject(By.res(packageName, "userInput")).text = emptyString;
        device.findObject(By.res(packageName, "buttonChange")).click();

        val result = device.findObject(By.res(packageName, "textToBeChanged")).text;
        assertEquals(result, textToSet);
    }

    @Test
    fun testChangeTextNewActivity() {
        val packageName = MODEL_PACKAGE;
        waitForPackage(packageName);

        device.findObject(By.res(packageName, "userInput")).text = textToSet;
        device.findObject(By.res(packageName, "buttonChange")).click();
        device.findObject(By.res(packageName, "buttonActivity")).click();
        Thread.sleep(500);
        val result = device.findObject(By.res(packageName, "text")).text;
        assertEquals(result, textToSet);
    }


}