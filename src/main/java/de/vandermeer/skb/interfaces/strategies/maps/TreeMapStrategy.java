/* Copyright 2016 Sven van der Meer <vdmeer.sven@mykolab.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package de.vandermeer.skb.interfaces.strategies.maps;

import java.util.Map;
import java.util.TreeMap;

/**
 * Strategy for a tree map (as sorted map).
 *
 * @author     Sven van der Meer &lt;vdmeer.sven@mykolab.com&gt;
 * @version    v0.0.2 build 170502 (02-May-17) for Java 1.8
 * @since      v0.0.1
 */
public interface TreeMapStrategy<K extends Comparable<K>, V> extends IsSortedMapStrategy<K, V>, IsNavigableMapStrategy<K, V> {

	@Override
	default boolean isAbstractMap() {
		return false;
	}

	@Override
	default boolean isConcurrentMap(){
		return false;
	}

	@Override
	default boolean isHashtable(){
		return false;
	}

	@Override
	default boolean isNavigableMap(){
		return true;
	}

	@Override
	default boolean isSortedMap() {
		return true;
	}

	/**
	 * Creates a new tree map strategy.
	 * @param <K> type of the keys in the map
	 * @param <V> type of the values in the map
	 * @return hash tree strategy
	 */
	static <K extends Comparable<K>, V> TreeMapStrategy<K, V> create(){
		return new TreeMapStrategy<K, V>() {};
	}

	@Override
	default TreeMap<K, V> get() {
		return new TreeMap<>();
	}

	@Override
	default TreeMap<K, V> get(Map<K, V> map){
		return new TreeMap<>(map);
	}
}