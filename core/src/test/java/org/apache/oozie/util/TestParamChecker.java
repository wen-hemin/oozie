/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.oozie.util;



import java.util.ArrayList;
import java.util.Arrays;

import org.junit.Test;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class TestParamChecker {

    @Test
    public void testNotNullElements() {
        ParamChecker.notEmptyElements(new ArrayList<String>(), "name");
        ParamChecker.notEmptyElements(Arrays.asList("a"), "name");
        try {
            ParamChecker.notEmptyElements(null, "name");
            fail();
        }
        catch (IllegalArgumentException | NullPointerException ex) {
            // nop
        }
        try {
            ParamChecker.notEmptyElements(Arrays.asList("a", null), "name");
            fail();
        }
        catch (IllegalArgumentException ex) {
            // nop
        }
    }

    @Test
    public void testNotEmpty() {
        ParamChecker.notEmpty("value", "name");
        try {
            ParamChecker.notEmpty(null, "name");
            fail();
        }
        catch (IllegalArgumentException ex) {
            // nop
        }
        try {
            ParamChecker.notEmpty("", "name");
            fail();
        }
        catch (IllegalArgumentException ex) {
            // nop
        }
    }

    @Test
    public void testNotEmptyElements() {
        ParamChecker.notEmptyElements(new ArrayList<String>(), "name");
        ParamChecker.notEmptyElements(Arrays.asList("a"), "name");
        try {
            ParamChecker.notEmptyElements(null, "name");
            fail();
        }
        catch (IllegalArgumentException | NullPointerException ex) {
            // nop
        }
        try {
            ParamChecker.notEmptyElements(Arrays.asList("a", null), "name");
            fail();
        }
        catch (IllegalArgumentException ex) {
            // nop
        }
    }

    @Test
    public void testValidToken() {
        ParamChecker.validateActionName("azAZ09_-");
        try {
            ParamChecker.validateActionName(null);
            fail();
        }
        catch (IllegalArgumentException ex) {
            // nop
        }
        try {
            ParamChecker.validateActionName("");
            fail();
        }
        catch (IllegalArgumentException ex) {
            // nop
        }
        try {
            ParamChecker.validateActionName("@");
            fail();
        }
        catch (IllegalArgumentException ex) {
            // nop
        }
    }

    @Test
    public void testValidIdentifier() {
        assertTrue(ParamChecker.isValidIdentifier("a"));
        assertTrue(ParamChecker.isValidIdentifier("a1"));
        assertTrue(ParamChecker.isValidIdentifier("a_"));
        assertTrue(ParamChecker.isValidIdentifier("_"));
        assertFalse(ParamChecker.isValidIdentifier("!"));
        assertFalse(ParamChecker.isValidIdentifier("1"));
    }

    @Test
    public void testCheckGTZero() {
        assertEquals(120, ParamChecker.checkGTZero(120, "test"));
        try {
            ParamChecker.checkGTZero(0, "test");
            fail();
        }
        catch (Exception ex) {
        }
        try {
            ParamChecker.checkGTZero(-1, "test");
            fail();
        }
        catch (Exception ex) {
        }
    }

    @Test
    public void testCheckGEZero() {
        assertEquals(120, ParamChecker.checkGEZero(120, "test"));
        assertEquals(0, ParamChecker.checkGEZero(0, "test"));
        try {
            ParamChecker.checkGEZero(-1, "test");
            fail();
        }
        catch (Exception ex) {
        }
    }

    @Test
    public void testCheckInteger() {
        assertEquals(120, ParamChecker.checkInteger("120", "test"));
        assertEquals(-12, ParamChecker.checkInteger("-12", "test"));
        try {
            ParamChecker.checkInteger("ABCD", "test");
            fail();
        }
        catch (Exception ex) {
        }
        try {
            ParamChecker.checkInteger("1.5", "test");
            fail();
        }
        catch (Exception ex) {
        }
    }

    @Test
    public void testCheckUTC() {
        ParamChecker.checkDateOozieTZ("2009-02-01T01:00Z", "test");
        try {
            ParamChecker.checkDateOozieTZ("2009-02-01T01:00", "test");
            fail();
        }
        catch (Exception ex) {
        }
        try {
            ParamChecker.checkDateOozieTZ("2009-02-01U01:00Z", "test");
            fail();
        }
        catch (Exception ex) {
        }
    }

    @Test
    public void testCheckTimeZone() {
        ParamChecker.checkTimeZone("UTC", "test");
        try {
            ParamChecker.checkTimeZone("UTZ", "test");
            fail();
        }
        catch (Exception ex) {
        }
        ParamChecker.checkTimeZone("America/Los_Angeles", "test");
        try {
            ParamChecker.checkTimeZone("America/Los_Angles", "test");
            fail();
        }
        catch (Exception ex) {
        }
    }

    @Test
    public void testIsMember() {
        String[] members = {"LIFO", "FIFO", "ONLYLAST"};
        ParamChecker.isMember("FIFO", members, "test");
        try {
            ParamChecker.isMember("FIF", members, "test");
            fail();
        }
        catch (Exception ex) {
        }

    }

    @Test
    public void testCheckFrequency() {
        ParamChecker.checkFrequency("10");

        String cron = "20,30 * * 10 *";
        ParamChecker.checkFrequency(cron);

        cron = "0/10 10-12 3 5 MON,FRI";
        ParamChecker.checkFrequency(cron);

        try {
            ParamChecker.checkFrequency("frequency");
            fail();
        }
        catch (IllegalArgumentException ex) {
        }

        try {
            ParamChecker.checkFrequency("10 * w e 1-4");
            fail();
        }
        catch (IllegalArgumentException ex) {
        }
    }

    @Test
    public void testValidateActionName() {
        String actionName = "actionName";
        ParamChecker.validateActionName(actionName);

        actionName = "actionName01";
        ParamChecker.validateActionName(actionName);

        actionName = "actionName01_02";
        ParamChecker.validateActionName(actionName);

        actionName = "actionName01_02-test";
        ParamChecker.validateActionName(actionName);

        // actionName with = 128 chars
        StringBuilder sb = new StringBuilder();
        sb.append("a");
        for (int i = 0; i < 127; i++) {
            sb.append(i % 10);
        }
        ParamChecker.validateActionName(sb.toString());

        try {
            actionName = "1actionName";
            ParamChecker.validateActionName(actionName);
            fail();
        } catch (IllegalArgumentException ex) {
        }

        try {
            actionName = "-actionName";
            ParamChecker.validateActionName(actionName);
            fail();
        } catch (IllegalArgumentException ex) {
        }

        try {
            // actionName with > 128 chars
            sb.setLength(0);
            sb.append("ab");
            for (int i = 0; i < 128; i++) {
                sb.append(i);
            }
            ParamChecker.validateActionName(sb.toString());
            fail();
        } catch (IllegalArgumentException ex) {
        }
    }

}
