/*
 * Copyright (C) 2007 The Guava Authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.common.io;

import java.io.File;
import java.io.IOException;
import junit.framework.TestCase;

/**
 * Unit test for {@link Files#createTempDir}.
 *
 * @author Chris Nokleberg
 */

public class FilesCreateTempDirTest extends TestCase {

    public void testCreateTempDir() throws IOException {
        File temp = Files.createTempDir();
        try {
            assertTrue(temp.exists());
            assertTrue(temp.isDirectory());
            assertEquals(0, temp.listFiles().length);

            assertTrue(temp.canRead());
            assertTrue(temp.canWrite());
            assertTrue(temp.canExecute());
        } finally {
            assertTrue(temp.delete());
        }
    }
}