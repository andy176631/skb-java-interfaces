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

package de.vandermeer.skb.interfaces.messagesets.errors;

import org.apache.commons.lang3.Validate;

/**
 * Standard Environment Option errors.
 * 
 * @author     Sven van der Meer &lt;vdmeer.sven@mykolab.com&gt;
 * @version    v0.0.2 build 170502 (02-May-17) for Java 1.8
 * @since      v0.0.3
 */
public enum Templates_EnvironmentOptions implements IsErrorTemplate {

	/**
	 * An environment parser tried to set a mandatory argument for an option but its value was null.
	 * Arguments: application name, environment key.
	 */
	VALUE_REQUIRED_BLANK(
			-170,
			"{}: value for mandatory environment <{}> was blank",
			"An environment parser tried to set a mandatory argument for an option but its value was null."
	),

	/**
	 * An integer option tried to convert a string to an integer and caught an exception.
	 * Arguments: application name, environment key, value.
	 */
	ERROR_2_INT(
			-171,
			"{}: environment key <{}>  need integer value, but could not convert <{}>, error: {}",
			"An integer option tried to convert a string to an integer and caught an exception."
	),

	;

	/** The numeric error code. */
	private final int code;

	/** The error message using `{}` for argument substitution. */
	private final String message;

	/** A description for the error code. */
	private final String description;

	@Override
	public String getDescription() {
		return this.description;
	}

	/**
	 * Creates a new error.
	 * @param code the error code, must be smaller than 0, in the range of the used category, and unique in the enumerate
	 * @param message the message, must not be blank and contain at least one `{}` for the application name
	 * @param description a description for the error code, must not be blank
	 * @param category the error code category, will be used for testing code, must not be null
	 */
	Templates_EnvironmentOptions(final int code, final String message, final String description){
		//only for app name, all other validations in test code
		Validate.validState(message.contains("{}"));

		this.code = code;
		this.message = message;
		this.description = description;
	}

	@Override
	public IsErrorCategory getCategory() {
		return StandardErrorCategories.ENV_OPTIONS;
	}

	@Override
	public int getCode() {
		return this.code;
	}

	@Override
	public String getMessage() {
		return this.message;
	}

	@Override
	public String getDisplayName() {
		return this.name();
	}
}