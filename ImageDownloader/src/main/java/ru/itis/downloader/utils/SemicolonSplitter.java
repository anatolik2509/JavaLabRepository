package ru.itis.downloader.utils;

import com.beust.jcommander.*;
import com.beust.jcommander.converters.*;
import java.util.*;

public class SemicolonSplitter implements IParameterSplitter {
  @Override
  public List<String> split(String value) {
    return Arrays.asList(value.split(";"));
  }
}