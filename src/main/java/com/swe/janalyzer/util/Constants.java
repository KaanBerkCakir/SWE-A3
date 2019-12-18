package com.swe.janalyzer.util;

import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Eine Klasse für alle systemweiten Konstanten.
 * {@link Constants#SEPERATOR} - Trennzeichen zwischen den beiden Attributen in der .json-Datei
 */
public class Constants {
		public static final String SEPERATOR = "@=@";

		public static final String DEFAULT_PATH = "default";
		public static final String CUSTOM_PATH = "custom";
		public static final Path OPTION_PATH = Paths.get("./output/options.json");
}
