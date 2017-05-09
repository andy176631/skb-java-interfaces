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
 * Standard Expected Runtime exceptions.
 * 
 * @author     Sven van der Meer &lt;vdmeer.sven@mykolab.com&gt;
 * @version    v0.0.2 build 170502 (02-May-17) for Java 1.8
 * @since      v0.0.3
 */
public enum Templates_ExceptionRuntimeExpected implements IsErrorTemplate {

	/**
	 * A null pointer exception was caught as part of the application flow.
	 * Arguments: application name, class/method, action, exception message.
	 */
	E_NULL_POINTER(
			-30,
			"{}: {} caught null pointer exception while {} with message: {}",
			"A null pointer exception was caught as part of the application flow."
	),

	/**
	 * An illegal state exception was caught as part of the application flow.
	 * Arguments: application name, class/method, action, exception message.
	 */
	E_ILLEGAL_STATE(
			-31,
			"{}: {} caught illegal state exception while {} with message: {}",
			"An illegal state exception was caught as part of the application flow."
	),

	/**
	 * An I/O exception was caught as part of the application flow.
	 * Arguments: application name, class/method, action, exception message.
	 */
	E_IO(
			-32,
			"{}: {} caught I/O exception while {} with message: {}",
			"An I/O exception was caught as part of the application flow."
	),


	;

	/** The numeric error code. */
	private final int code;

	/** The error message using `{}` for argument substitution. */
	private final String message;

	/** A description for the error code. */
	private final String description;

	/**
	 * Creates a new error.
	 * @param code the error code, must be smaller than 0, in the range of the used category, and unique in the enumerate
	 * @param message the message, must not be blank and contain at least one `{}` for the application name
	 * @param description a description for the error code, must not be blank
	 * @param category the error code category, will be used for testing code, must not be null
	 */
	Templates_ExceptionRuntimeExpected(final int code, final String message, final String description){
		//only for app name, all other validations in test code
		Validate.validState(message.contains("{}"));

		this.code = code;
		this.message = message;
		this.description = description;
	}

	@Override
	public IsErrorCategory getCategory() {
		return StandardErrorCategories.RUNTIME_EXCEPTIONS_EXPECTED;
	}

	@Override
	public int getCode() {
		return this.code;
	}

	@Override
	public String getDescription() {
		return this.description;
	}

	@Override
	public String getDisplayName() {
		return this.name();
	}

	@Override
	public String getMessage() {
		return this.message;
	}
}
