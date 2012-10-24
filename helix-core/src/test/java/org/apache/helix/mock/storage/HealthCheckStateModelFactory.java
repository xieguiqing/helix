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
package org.apache.helix.mock.storage;

import org.apache.helix.participant.statemachine.StateModel;
import org.apache.helix.participant.statemachine.StateModelFactory;
import org.apache.log4j.Logger;


public class HealthCheckStateModelFactory extends StateModelFactory
{
  private static Logger logger = Logger
      .getLogger(HealthCheckStateModelFactory.class);

  private StorageAdapter storageAdapter;

  // private ConsumerAdapter consumerAdapter;

  public HealthCheckStateModelFactory(StorageAdapter storage)
  {
    storageAdapter = storage;
  }

  HealthCheckStateModel getStateModelForPartition(Integer partition)
  {
    return null;
  }

  @Override
  public StateModel createNewStateModel(String stateUnitKey)
  {
    logger.info("HealthCheckStateModelFactory.getStateModel()");
    //TODO: fix these parameters
    return new HealthCheckStateModel(stateUnitKey, storageAdapter, null, 0, null, null);
  }

}
