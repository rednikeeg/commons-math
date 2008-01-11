/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.commons.math.stat.descriptive;


import junit.framework.Test;
import junit.framework.TestSuite;

import org.apache.commons.math.stat.descriptive.moment.Mean;
/**
 * Test cases for the {@link SummaryStatistics} class.
 * When SummaryStatisticsImpl is removed in math 2.0, test cases from
 * SummaryStatisticsImplTest should be merged into this class.
 *
 * @version $Revision: 566833 $ $Date: 2007-08-16 13:36:33 -0700 (Thu, 16 Aug 2007) $
 */

public final class SummaryStatisticsTest extends SummaryStatisticsAbstractTest {

    public SummaryStatisticsTest(String name) {
        super(name);
    }
    
    public static Test suite() {
        TestSuite suite = new TestSuite(SummaryStatisticsTest.class);
        suite.setName("SummaryStatistics tests");
        return suite;
    }

    protected SummaryStatistics createSummaryStatistics() {
        return new SummaryStatistics();
    }
    
    public void testSetterInjection() throws Exception {
        SummaryStatistics u = createSummaryStatistics();
        u.setMeanImpl(new sumMean());
        u.addValue(1);
        u.addValue(3);
        assertEquals(4, u.getMean(), 1E-14);
        u.clear();
        u.addValue(1);
        u.addValue(2);
        assertEquals(3, u.getMean(), 1E-14);
        u.clear();
        u.setMeanImpl(new Mean()); // OK after clear
    }
    
    public void testSetterIllegalState() throws Exception {
        SummaryStatistics u = createSummaryStatistics();
        u.addValue(1);
        u.addValue(3);
        try {
            u.setMeanImpl(new sumMean());
            fail("Expecting IllegalStateException");
        } catch (IllegalStateException ex) {
            // expected
        }
    }
    
    /**
     * Bogus mean implementation to test setter injection.
     * Returns the sum instead of the mean.
     */
    class sumMean implements StorelessUnivariateStatistic {   
        private double sum = 0;
        private long n = 0;
        public double evaluate(double[] values, int begin, int length) {
            return 0;
        }
        public double evaluate(double[] values) {
            return 0;
        }
        public void clear() {
          sum = 0; 
          n = 0;
        }
        public long getN() {
            return n;
        }
        public double getResult() {
            return sum;
        }
        public void increment(double d) {
            sum += d;
            n++;
        }
        public void incrementAll(double[] values, int start, int length) {
        }
        public void incrementAll(double[] values) {
        }   
    }  
}
