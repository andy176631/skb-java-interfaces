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

package de.vandermeer.skb.interfaces.translators;

/**
 * Interface for a character translator.
 *
 * @author     Sven van der Meer &lt;vdmeer.sven@mykolab.com&gt;
 * @version    v0.0.2 build 170502 (02-May-17) for Java 1.8
 * @since      v0.0.1
 */
public interface CharacterTranslator {

	/**
	 * Translates characters found in an input object to a target representation.
	 * @param input object (toString is used)
	 * @return translated string
	 */
	default String translateCharacters(Object input){
		if(input!=null){
			return this.translateCharacters(input.toString());
		}
		return null;
	}

	/**
	 * Translates characters found in an input string to a target representation.
	 * @param input string
	 * @return translated string
	 */
	String translateCharacters(String input);
}
