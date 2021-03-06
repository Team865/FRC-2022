package ca.warp7.frc2022.lib.control;

/*
 * MIT License
 *
 * Copyright (c) 2018 Team 254
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

/**
 * This boolean enforces a minimum time for the value to be true.  It captures a rising edge and enforces
 * based on timestamp.
 */
public class MinTimeBoolean {
    private LatchedBoolean mLatchedBoolean;
    private double mMinTime;
    private double mRisingEdgeTime;

    public MinTimeBoolean(double minTime) {
        mLatchedBoolean = new LatchedBoolean();
        mMinTime = minTime;
        mRisingEdgeTime = Double.NaN;
    }

    public boolean update(boolean value, double timestamp) {
        if (mLatchedBoolean.update(value)) {
            mRisingEdgeTime = timestamp;
        }

        if (!value && !Double.isNaN(mRisingEdgeTime)
                && (timestamp - mRisingEdgeTime < mMinTime)) {
            return true;
        }
        return value;
    }
}