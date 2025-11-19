package com.example.csci412assignment2

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.uiautomator.By
import androidx.test.uiautomator.UiDevice
import androidx.test.uiautomator.Until
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class UiAutomatorTest {
    private lateinit var device: UiDevice
    private val LAUNCH_TIMEOUT = 5000L

    @Before
    fun setUp() {
        device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation())
        // Start from the home screen
        device.pressHome()
    }

    @Test
    fun startAppFromLauncherAndCheckChallenge() {
        val instr = InstrumentationRegistry.getInstrumentation()
        val context = instr.context

        // Wait for launcher
        val launcherPkg = getLauncherPackageName(context)
        requireNotNull(launcherPkg) { "Unable to determine launcher package" }
        device.wait(Until.hasObject(By.pkg(launcherPkg).depth(0)), LAUNCH_TIMEOUT)

        // Click the app icon on the launcher by app name text
        val appName = context.resources.getString(R.string.app_name)
        val appIcon = device.findObject(By.text(appName))
        appIcon.click()

        // Wait for the app to appear
        val appPackage = InstrumentationRegistry.getInstrumentation().targetContext.packageName
        device.wait(Until.hasObject(By.pkg(appPackage).depth(0)), LAUNCH_TIMEOUT)

        // Click the "Start Activity Explicitly" button
        val startButton = device.findObject(By.text("Start Activity Explicitly"))
        startButton.click()

        // Now verify that one of the listed challenges appears in the SecondActivity
        val challenges = listOf(
            "Battery consumption",
            "Limited processing power",
            "Network variability",
            "UI responsiveness",
            "Data security"
        )

        var found = false
        for (c in challenges) {
            val obj = device.findObject(By.text(c))
            if (obj != null) {
                found = true
                break
            }
        }

        assertTrue("Expected one of the challenges to be visible in SecondActivity", found)
    }

    private fun getLauncherPackageName(context: Context): String? {
        val intent = Intent(Intent.ACTION_MAIN)
        intent.addCategory(Intent.CATEGORY_HOME)
        val pm = context.packageManager
        val resolveInfo = pm.resolveActivity(intent, PackageManager.MATCH_DEFAULT_ONLY)
        return resolveInfo?.activityInfo?.packageName
    }
}
