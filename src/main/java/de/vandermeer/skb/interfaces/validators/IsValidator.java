/* Copyright 2017 Sven van der Meer <vdmeer.sven@mykolab.com>
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

package de.vandermeer.skb.interfaces.validators;

import de.vandermeer.skb.interfaces.categories.CategoryIs;
import de.vandermeer.skb.interfaces.categories.has.HasDescription;
import de.vandermeer.skb.interfaces.categories.has.HasErrNo;
import de.vandermeer.skb.interfaces.messages.sets.HasErrorSet;

/**
 * Base interface for a validator.
 *
 * @author     Sven van der Meer &lt;vdmeer.sven@mykolab.com&gt;
 * @version    v0.2.0 build 170404 (04-Apr-17) for Java 1.8
 * @since      v0.0.3
 */
public interface IsValidator extends CategoryIs, HasDescription, HasErrorSet, HasErrNo {

	@Override
	default int getErrNo(){
		return this.getErrorSet().getErrNo();
	}
}
