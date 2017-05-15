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

package de.vandermeer.skb.interfaces.examples;

/**
 * Utility for running examples.
 *
 * @author     Sven van der Meer &lt;vdmeer.sven@mykolab.com&gt;
 * @version    v0.0.2 build 170502 (02-May-17) for Java 1.8
 * @since      v0.0.1
 */
public interface StandardExampleRunner {

	/**
	 * Runs an example showing output with frames.
	 * @param example the example to run
	 * 
	 */
	default void runExample(StandardExample example){
		System.out.println("================================================================");
		System.out.println(example.getClass().getSimpleName() + ": " + example.getDescription());
		System.out.println("----------------------------------------------------------------");
		example.showOutput();
		System.out.println("================================================================");
		System.out.println("\n\n");
	}

	/**
	 * Runs an example, first showing the source code and then output with frames.
	 * @param example the example to run
	 * 
	 */
	default void runExampleWithCode(StandardExample example){
		System.out.println("================================================================");
		System.out.println(example.getClass().getSimpleName() + ": " + example.getDescription());
		System.out.println("----------------------------------------------------------------");
		System.out.println(example.getSource());
		System.out.println("----------------------------------------------------------------");
		example.showOutput();
		System.out.println("================================================================");
		System.out.println("\n\n");
	}
}
