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

import org.testng.annotations.Test;

public class TestCMWithFailParticipant extends ZkIntegrationTestBase
{
  // ZkClient _zkClient;
  //
  // @BeforeClass ()
  // public void beforeClass() throws Exception
  // {
  // _zkClient = new ZkClient(ZK_ADDR);
  // _zkClient.setZkSerializer(new ZNRecordSerializer());
  // }
  //
  //
  // @AfterClass
  // public void afterClass()
  // {
  // _zkClient.close();
  // }

  @Test()
  public void testCMWithFailParticipant() throws Exception
  {
    int numResources = 1;
    int numPartitionsPerResource = 10;
    int numInstance = 5;
    int replica = 3;

    String uniqClusterName = "TestFail_" + "rg" + numResources + "_p" + numPartitionsPerResource
        + "_n" + numInstance + "_r" + replica;
    System.out.println("START " + uniqClusterName + " at " + new Date(System.currentTimeMillis()));

    TestDriver.setupCluster(uniqClusterName, ZK_ADDR, numResources, numPartitionsPerResource,
        numInstance, replica);

    for (int i = 0; i < numInstance; i++)
    {
      TestDriver.startDummyParticipant(uniqClusterName, i);
    }
    TestDriver.startController(uniqClusterName);

    TestDriver.stopDummyParticipant(uniqClusterName, 2000, 0);
    TestDriver.verifyCluster(uniqClusterName, 3000, 50 * 1000);
    TestDriver.stopCluster(uniqClusterName);

    System.out.println("END " + uniqClusterName + " at " + new Date(System.currentTimeMillis()));

  }
}
