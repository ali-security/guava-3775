/*
 * Copyright (C) 2017 The Guava Authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */

package com.google.common.primitives;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

import com.google.common.annotations.GwtCompatible;
import com.google.common.base.Preconditions;
import java.io.Serializable;
import java.util.AbstractList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.RandomAccess;
import javax.annotation.CheckForNull;

@GwtCompatible
public final class ImmutableLongArray implements Serializable {
    private static final ImmutableLongArray EMPTY = new ImmutableLongArray(new long[0]);

    public static Builder builder() {
        return new Builder(10);
    }

    public static final class Builder {
        private long[] array;
        private int count = 0;

        Builder(int initialCapacity) {
            array = new long[initialCapacity];
        }

        public Builder add(long value) {
            ensureRoomFor(1);
            array[count] = value;
            count += 1;
            return this;
        }

        private void ensureRoomFor(int numberToAdd) {
            int newCount = count + numberToAdd;
            if (newCount > array.length) {
                array = Arrays.copyOf(array, expandedCapacity(array.length, newCount));
            }
        }

        private static int expandedCapacity(int oldCapacity, int minCapacity) {
            if (minCapacity < 0) {
                throw new AssertionError("cannot store more than MAX_VALUE elements");
            }
            int newCapacity = oldCapacity + (oldCapacity >> 1) + 1;
            if (newCapacity < minCapacity) {
                newCapacity = Integer.highestOneBit(minCapacity - 1) << 1;
            }
            if (newCapacity < 0) {
                newCapacity = Integer.MAX_VALUE;
            }
            return newCapacity;
        }

        public ImmutableLongArray build() {
            return count == 0 ? EMPTY : new ImmutableLongArray(array, 0, count);
        }
    }

    @SuppressWarnings("Immutable")
    private final long[] array;

    private final transient int start;
    private final int end;

    private ImmutableLongArray(long[] array) {
        this(array, 0, array.length);
    }

    private ImmutableLongArray(long[] array, int start, int end) {
        this.array = array;
        this.start = start;
        this.end = end;
    }

    /** Returns the number of values in this array. */
    public int length() {
        return end - start;
    }

    public long[] toArray() {
        return Arrays.copyOfRange(array, start, end);
    }
}