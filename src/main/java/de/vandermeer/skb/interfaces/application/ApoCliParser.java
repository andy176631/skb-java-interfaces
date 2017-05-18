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

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import org.apache.commons.cli.AlreadySelectedException;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.MissingArgumentException;
import org.apache.commons.cli.MissingOptionException;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.commons.cli.UnrecognizedOptionException;
import org.apache.commons.lang3.ClassUtils;
import org.apache.commons.lang3.text.StrBuilder;
import org.stringtemplate.v4.ST;
import org.stringtemplate.v4.STGroupFile;

import de.vandermeer.skb.interfaces.messages.errors.IsError;
import de.vandermeer.skb.interfaces.messages.errors.Templates_CliGeneral;
import de.vandermeer.skb.interfaces.messages.sets.HasErrorSet;
import de.vandermeer.skb.interfaces.messages.sets.IsErrorSet;
import de.vandermeer.skb.interfaces.transformers.textformat.Text_To_FormattedText;

/**
 * Base for a CLI parser.
 * 
 * @author     Sven van der Meer &lt;vdmeer.sven@mykolab.com&gt;
 * @version    v0.0.2 build 170502 (02-May-17) for Java 1.8
 * @since      v0.0.2
 */
public interface ApoCliParser extends HasErrorSet {

	/**
	 * Creates a new default parser using Apache CLI.
	 * @return new default parser
	 */
	static ApoCliParser defaultParser(){
		return new ApoCliParser() {
			protected final transient ApoCliOptionSet options = ApoCliOptionSet.setApacheOptions();
			protected final transient IsErrorSet errorSet = IsErrorSet.create();

			@Override
			public IsErrorSet getErrorSet() {
				return this.errorSet;
			}

			@Override
			public ApoCliOptionSet getOptions() {
				return this.options;
			}

			@Override
			public void parse(String[] args) {
				final CommandLineParser parser = new DefaultParser();
				final Options options = new Options();
				for(final Object obj : this.getOptions().getSimpleMap().values()){
					options.addOption((Option)obj);
				}
				for(final Object obj : this.getOptions().getTypedMap().values()){
					options.addOption((Option)obj);
				}

				CommandLine cmdLine = null;
				try {
					cmdLine = parser.parse(options, args, true);
				}
				catch(AlreadySelectedException ase){
					this.getErrorSet().add(Templates_CliGeneral.ALREADY_SELECTED.getError(ase.getMessage()));
				}
				catch(MissingArgumentException mae){
					this.getErrorSet().add(Templates_CliGeneral.MISSING_ARGUMENT.getError(mae.getMessage()));
				}
				catch(MissingOptionException moe){
					this.getErrorSet().add(Templates_CliGeneral.MISSING_OPTION.getError(moe.getMessage()));
				}
				catch(UnrecognizedOptionException uoe){
					this.getErrorSet().add(Templates_CliGeneral.UNRECOGNIZED_OPTION.getError(uoe.getMessage()));
				}
				catch (ParseException ex) {
					this.getErrorSet().add(Templates_CliGeneral.PARSE_EXCEPTION.getError(ex.getMessage()));
				}

				if(cmdLine!=null){
					for(final Apo_SimpleC simple : this.getOptions().getSimpleMap().keySet()){
						simple.setInCLi(cmdLine.hasOption((simple.getCliShort()==null)?simple.getCliLong():simple.getCliShort().toString()));
					}
					for(final Apo_TypedC<?> typed : this.getOptions().getTypedMap().keySet()){
						typed.setInCLi(cmdLine.hasOption((typed.getCliShort()==null)?typed.getCliLong():typed.getCliShort().toString()));
						if(typed.inCli()){
							IsError error = typed.setCliValue(cmdLine.getOptionValue((typed.getCliShort()==null)?typed.getCliLong():typed.getCliShort().toString()));
							if(error!=null){
								this.errorSet.add(error);
							}
						}
					}
				}
			}
		};
	}

	/**
	 * Returns the number of the last error, 0 if none occurred.
	 * @return last error number
	 */
	default int getErrNo(){
		return this.getErrorSet().getErrNo();
	}

	/**
	 * Returns the parser's option set.
	 * @return parser's option set, must not be null
	 */
	ApoCliOptionSet getOptions();

	/**
	 * Parses command line arguments and sets values for CLI options.
	 * Parsing errors are in the local error set.
	 * Latest parsing error code is in the local `errNo` member.
	 * @param args command line arguments
	 */
	void parse(String[] args);

	/**
	 * Prints usage information for the CLI parser including all CLI options.
	 * @return list of lines with usage information
	 */
	default ArrayList<StrBuilder> usage(){
		return this.usage(80);
	}

	/**
	 * Prints usage information for the CLI parser including all CLI options.
	 * @param width the console columns or width of each output line
	 * @return list of lines with usage information
	 */
	default ArrayList<StrBuilder> usage(int width){
		TreeMap<String, ApoBaseC> map = this.getOptions().sortedMap();
		Map<String, String> helpMap = new LinkedHashMap<>();
		int length = 0;
		STGroupFile stg = new STGroupFile("de/vandermeer/skb/interfaces/application/help.stg");
		for(Object option : map.values()){
			ST sto = stg.getInstanceOf("cliOption");
			String description = null;
			if(ClassUtils.isAssignable(option.getClass(), Apo_SimpleC.class)){
				sto.add("cliShort", ((Apo_SimpleC)option).getCliShort());
				sto.add("cliLong", ((Apo_SimpleC)option).getCliLong());
				description = ((Apo_SimpleC)option).getDescription();
			}
			if(ClassUtils.isAssignable(option.getClass(), Apo_TypedC.class)){
				sto.add("cliShort", ((Apo_TypedC<?>)option).getCliShort());
				sto.add("cliLong", ((Apo_TypedC<?>)option).getCliLong());
				sto.add("argName", ((Apo_TypedC<?>)option).getCliArgumentName());
				sto.add("argOptional", ((Apo_TypedC<?>)option).cliArgIsOptional());
				description = ((Apo_TypedC<?>)option).getDescription();
			}
			String line = sto.render();
			if(line.length()>length){
				length = line.length();
			}
			helpMap.put(line, description);
		}
		length += 4;

		ArrayList<StrBuilder> ret = new ArrayList<>();
		for(Entry<String, String> entry : helpMap.entrySet()){
			StrBuilder argLine = new StrBuilder();
			argLine.append(entry.getKey()).appendPadding(length-argLine.length(), ' ');
			StrBuilder padLine = new StrBuilder();
			padLine.appendPadding(length, ' ');

			Collection<StrBuilder> text = Text_To_FormattedText.left(entry.getValue(), width-length);
			int i = 0;
			for(StrBuilder b : text){
				if(i==0){
					b.insert(0, argLine);
				}
				else{
					b.insert(0, padLine);
				}
				ret.add(b);
				i++;
			}
		}
		return ret;
	}
}
