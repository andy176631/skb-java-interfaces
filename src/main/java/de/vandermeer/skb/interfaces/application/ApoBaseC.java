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

package de.vandermeer.skb.interfaces.application;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import org.stringtemplate.v4.ST;
import org.stringtemplate.v4.STGroupFile;

/**
 * Base for a CLI option.
 * 
 * @author     Sven van der Meer &lt;vdmeer.sven@mykolab.com&gt;
 * @version    v0.0.1 build 170404 (04-Apr-17) for Java 1.8
 * @since      v0.0.2
 */
public interface ApoBaseC extends ApoBase {

	/**
	 * Tests if the CLI option is required meaning it must be used in the command line.
	 * @return true if required, false otherwise
	 */
	boolean cliIsRequired();

	/**
	 * Returns the long CLI option.
	 * @return long CLI option, must not be blank
	 */
	String getCliLong();

	/**
	 * Returns the short CLI option.
	 * @return short CLI option, null if none set
	 */
	Character getCliShort();

	/**
	 * Returns a CLI option either short or long.
	 * @return CLI option, short if short is not null, long otherwise
	 */
	default String getCliShortLong(){
		if(this.getCliShort()!=null){
			return this.getCliShort().toString();
		}
		return this.getCliLong();
	}

	@Override
	default ST getHelp(){
		return this.getHelpCli();
	}

	/**
	 * Returns an ST with help information for the option.
	 * @return ST with help information, must not be null
	 */
	default ST getHelpCli(){
		STGroupFile stg = new STGroupFile("de/vandermeer/skb/interfaces/application/option-help.stg");
		ST st = stg.getInstanceOf("optionHelp");

		st.add("cliShort", this.getCliShort());
		st.add("cliLong", this.getCliLong());

		if(this.cliIsRequired()){
			st.add("cliRequired", "true");
		}
		st.add("shortDescr", this.getDescription());
		st.add("longDescr", this.getLongDescription());

		return st;
	}

	/**
	 * Tests if the option was present in a parsed command line.
	 * @return true if it was present, false otherwise
	 */
	boolean inCli();

	/**
	 * Sets the option flag for being in a command line.
	 * @param inCli true if in a command line, false otherwise
	 */
	void setInCLi(boolean inCli);

	@Override
	default void validate() throws IllegalStateException {
		ApoBase.super.validate();
		Validate.validState(!StringUtils.isBlank(this.getCliShortLong()), "Apo: cliShor or cliLong must have a value");
	}
}
