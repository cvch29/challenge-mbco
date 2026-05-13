package com.example.challengembco

import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4

import android.util.Log

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*

@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {

    @Test
    fun useAppContext() {

        val username = "admin"
        val password = "SuperSecretPassword123"

        Log.d("CREDENTIALS", "$username:$password")

        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("com.example.challengembco", appContext.packageName)
    }
}