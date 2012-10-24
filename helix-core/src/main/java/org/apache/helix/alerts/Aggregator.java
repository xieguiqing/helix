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
package org.apache.helix.alerts;

public abstract class Aggregator {

	int _numArgs;
	
	public Aggregator()
	{
		
	}
	
	/*
	 * Take curr and new values.  Update curr.
	 */
	public abstract void merge(Tuple<String> currVal, Tuple<String> newVal, 
			Tuple<String> currTime, Tuple<String> newTime, String... args);
	
	public int getRequiredNumArgs()
	{
		return _numArgs;
	}
	
}
