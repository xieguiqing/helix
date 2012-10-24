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
package org.apache.helix.tools;

import org.apache.helix.HelixManager;

public class TestCommand
{
  public enum CommandType
  {
    MODIFY,
    VERIFY,
    START,
    STOP
  }

  public static class NodeOpArg
  {
    public HelixManager _manager;
    public Thread _thread;

    public NodeOpArg(HelixManager manager, Thread thread)
    {
      _manager = manager;
      _thread = thread;
    }
  }

  public TestTrigger _trigger;
  public CommandType _commandType;
  public ZnodeOpArg _znodeOpArg;
  public NodeOpArg _nodeOpArg;

  public long _startTimestamp;
  public long _finishTimestamp;

  /**
   *
   * @param type
   * @param arg
   */
  public TestCommand(CommandType type, ZnodeOpArg arg)
  {
    this(type, new TestTrigger(), arg);
  }

  /**
   *
   * @param type
   * @param trigger
   * @param arg
   */
  public TestCommand(CommandType type, TestTrigger trigger, ZnodeOpArg arg)
  {
    _commandType = type;
    _trigger = trigger;
    _znodeOpArg = arg;
  }

  /**
   *
   * @param type
   * @param trigger
   * @param arg
   */
  public TestCommand(CommandType type, TestTrigger trigger, NodeOpArg arg)
  {
    _commandType = type;
    _trigger = trigger;
    _nodeOpArg = arg;
  }

  @Override
  public String toString()
  {
    String ret = super.toString().substring(super.toString().lastIndexOf(".") + 1) + " ";
    if (_finishTimestamp > 0)
    {
      ret += "FINISH@" + _finishTimestamp + "-START@" + _startTimestamp
               + "=" + (_finishTimestamp - _startTimestamp) + "ms ";
    }
    if (_commandType == CommandType.MODIFY || _commandType == CommandType.VERIFY)
    {
      ret += _commandType.toString() + "|" + _trigger.toString() + "|" + _znodeOpArg.toString();
    }
    else if (_commandType == CommandType.START || _commandType == CommandType.STOP)
    {
      ret += _commandType.toString() + "|" + _trigger.toString() + "|" + _nodeOpArg.toString();
    }

    return ret;
  }
}
