package CalcTest;

import data.Subscriprions.SubsLauncher;
import org.junit.Assert;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestFactory;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;

class SubsLauncherTest extends SubsLauncher {

    @DisplayName("Test for threads subscription senders")
    @TestFactory
    Collection<DynamicTest> dynamicTestsWithCollection() {
        return Arrays.asList(
                DynamicTest.dynamicTest("Morning check", () ->
                        assertEquals(50*1000*60, getTime(6, 10,
                        7))),
                DynamicTest.dynamicTest("Evening check", () ->
                        assertEquals(50*1000*60, getTime(16, 10,
                                17))),
                DynamicTest.dynamicTest("Midnight check (00:00)", () ->
                        assertEquals(7*60*1000*60, getTime(0, 0,
                                7))),
                DynamicTest.dynamicTest("Midnight check (24:00)", () ->
                        assertEquals(7*60*1000*60, getTime(24, 0,
                                7))));

    }


}