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
package org.apache.helix.participant;

import java.util.List;

import org.apache.helix.HelixManager;
import org.apache.helix.NotificationContext;
import org.apache.helix.HelixConstants.ChangeType;
import org.apache.helix.model.Message;
import org.apache.helix.participant.statemachine.StateModel;
import org.apache.helix.participant.statemachine.StateModelInfo;
import org.apache.helix.participant.statemachine.Transition;
import org.apache.log4j.Logger;


@StateModelInfo(initialState = "OFFLINE", states = { "LEADER", "STANDBY" })
public class GenericLeaderStandbyModel extends StateModel
{
  private static Logger LOG = Logger.getLogger(GenericLeaderStandbyModel.class);

  private final CustomCodeInvoker _particHolder;
  private final List<ChangeType> _notificationTypes;

  public GenericLeaderStandbyModel(CustomCodeCallbackHandler callback, 
                                     List<ChangeType> notificationTypes,
                                     String partitionKey)
  {
    _particHolder = new CustomCodeInvoker(callback, partitionKey);
    _notificationTypes = notificationTypes;
  }

  @Transition(to="STANDBY",from="OFFLINE")
  public void onBecomeStandbyFromOffline(Message message, NotificationContext context)
  {
    LOG.info("Become STANDBY from OFFLINE");
  }

  @Transition(to="LEADER",from="STANDBY")
  public void onBecomeLeaderFromStandby(Message message, NotificationContext context)
      throws Exception
  {
    LOG.info("Become LEADER from STANDBY");
    HelixManager manager = context.getManager();
    if (manager == null)
    {
      throw new IllegalArgumentException("Require HelixManager in notification conext");
    }
    for (ChangeType notificationType : _notificationTypes)
    {
      if (notificationType == ChangeType.LIVE_INSTANCE)
      {
        manager.addLiveInstanceChangeListener(_particHolder);
      }
      else if (notificationType == ChangeType.CONFIG)
      {
        manager.addConfigChangeListener(_particHolder);
      }
      else if (notificationType == ChangeType.EXTERNAL_VIEW)
      {
        manager.addExternalViewChangeListener(_particHolder);
      }
      else
      {
        LOG.error("Unsupport notificationType:" + notificationType.toString());
      }
    }
  }

  @Transition(to="STANDBY",from="LEADER")
  public void onBecomeStandbyFromLeader(Message message, NotificationContext context)
  {
    LOG.info("Become STANDBY from LEADER");
    HelixManager manager = context.getManager();
    manager.removeListener(_particHolder);    
  }

  @Transition(to="OFFLINE",from="STANDBY")
  public void onBecomeOfflineFromStandby(Message message, NotificationContext context)
  {
    LOG.info("Become OFFLINE from STANDBY");
  }
}
