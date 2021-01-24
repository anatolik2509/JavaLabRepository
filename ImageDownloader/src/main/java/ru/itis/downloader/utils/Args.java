package ru.itis.downloader.utils;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.converters.*;
import java.util.*;

public class Args {
	@Parameter(names="--multi-thread-mode", arity = 1)
	public boolean multyThreadMode = false;

	@Parameter(names="--count")
	public int count;

	@Parameter(names="--files", converter = StringConverter.class, splitter = SemicolonSplitter.class)
	public List<String> files;

	@Parameter(names="--folder")
	public String folder = "C:/test";
}