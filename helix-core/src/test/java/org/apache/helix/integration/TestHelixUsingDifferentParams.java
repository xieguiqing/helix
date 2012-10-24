/**
 * Copyright (C) 2012 LinkedIn Inc <opensource@linkedin.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.helix.integration;

import java.util.Date;

import org.apache.log4j.Logger;
import org.testng.annotations.Test;

public class TestHelixUsingDifferentParams extends ZkIntegrationTestBase
{
  private static Logger LOG = Logger.getLogger(TestHelixUsingDifferentParams.class);

  @Test()
  public void testCMUsingDifferentParams() throws Exception
  {
    System.out.println("START " + getShortClassName() + " at "
        + new Date(System.currentTimeMillis()));

    int numResourceArray[] = new int[] { 1 }; // , 2}; // , 3, 6};
    int numPartitionsPerResourceArray[] = new int[] { 10 }; // , 20, 50, 100}; // ,
                                                        // 1000};
    int numInstances[] = new int[] { 5 }; // , 10}; // , 50, 100, 1000};
    int replicas[] = new int[] { 2 }; // , 3}; //, 4, 5};

    for (int numResources : numResourceArray)
    {
      for (int numPartitionsPerResource : numPartitionsPerResourceArray)
      {
        for (int numInstance : numInstances)
        {
          for (int replica : replicas)
          {
            String uniqClusterName = "TestDiffParam_" + "rg" + numResources + "_p"
                + numPartitionsPerResource + "_n" + numInstance + "_r" + replica;
            System.out.println("START " + uniqClusterName + " at "
                + new Date(System.currentTimeMillis()));

            TestDriver.setupCluster(uniqClusterName, ZK_ADDR, numResources,
                numPartitionsPerResource, numInstance, replica);

            for (int i = 0; i < numInstance; i++)
            {
              TestDriver.startDummyParticipant(uniqClusterName, i);
            }

            TestDriver.startController(uniqClusterName);
            TestDriver.verifyCluster(uniqClusterName, 1000, 50 * 1000);
            TestDriver.stopCluster(uniqClusterName);

            System.out.println("END " + uniqClusterName + " at "
                + new Date(System.currentTimeMillis()));
          }
        }
      }
    }

    System.out
        .println("END " + getShortClassName() + " at " + new Date(System.currentTimeMillis()));
  }
}
