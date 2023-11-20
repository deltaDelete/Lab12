package ru.deltadelete.lab11;

import static org.junit.Assert.assertEquals;

import android.content.Context;

import androidx.room.Room;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import ru.deltadelete.lab11.database.AppDatabase;
import ru.deltadelete.lab11.database.entities.Country;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        assertEquals("ru.deltadelete.lab10", appContext.getPackageName());
    }

    @Test
    public void checkDbInsertAndDelete() {
        AppDatabase db = Room.databaseBuilder(InstrumentationRegistry.getInstrumentation().getTargetContext(),
                AppDatabase.class, "towns-db").build();

        Country c = new Country(0, "Россия", "RU");
        db.countryDao().insertAll(c);

        Country c1 = db.countryDao().all().get(0);
        try {
            Assert.assertEquals(c.getName(), c1.getName());
            Assert.assertEquals(c.getCode(), c1.getCode());
        }
        finally {
            db.countryDao().delete(c1);
        }
    }

    @Test
    public void checkDbGet() {
        AppDatabase db = Room.databaseBuilder(InstrumentationRegistry.getInstrumentation().getTargetContext(),
                AppDatabase.class, "towns-db").build();

        Country c = new Country(0, "Россия", "ru");
        Country c1 = db.countryDao().loadAllByIds(new int[] {1,2,3,4,5,6,7,8,9}).get(0);
        try {
            Assert.assertEquals(c.getName(), c1.getName());
            Assert.assertEquals(c.getCode(), c1.getCode());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}