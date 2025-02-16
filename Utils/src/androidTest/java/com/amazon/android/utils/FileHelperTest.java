/**
 * Copyright 2015-2016 Amazon.com, Inc. or its affiliates. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * A copy of the License is located at
 *
 *     http://aws.amazon.com/apache2.0/
 *
 * or in the "license" file accompanying this file. This file is distributed
 * on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
 * express or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */
package com.amazon.android.utils;

import org.junit.Test;

import android.content.Context;
import androidx.test.platform.app.InstrumentationRegistry;

import java.io.IOException;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * This class tests the {@link FileHelper} class.
 */
public class FileHelperTest {

    /**
     * Test the constructor.
     */
    @Test
    public void testConstructor() {

        assertNotNull(new FileHelper());
    }

    /**
     * Tests the {@link FileHelper#readFile(Context, String)} method with a test file.
     */
    @Test
    public void testReadFile() throws Exception {

        String result = FileHelper.readFile(InstrumentationRegistry.getContext(),
                "FileHelperTest1.txt");

        assertTrue(result.trim().equals("This is a test!"));

    }

    /**
     * Tests the {@link FileHelper#readFile(Context, String)} method with a fake test file to
     * produce an {@link IOException}.
     */
    @Test(expected = IOException.class)
    public void testReadFileWithBadFilePath() throws Exception {

        FileHelper.readFile(InstrumentationRegistry.getContext(), "FakeFile");
    }
}
