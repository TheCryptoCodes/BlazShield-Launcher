package de.potionmc.launcher.interfaces;

import org.apache.commons.io.IOCase;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Stack;

public class FilenameUtils {
  public static final char EXTENSION_SEPARATOR = '.';
  
  public static final String EXTENSION_SEPARATOR_STR = Character.toString('.');
  
  private static final char UNIX_SEPARATOR = '/';
  
  private static final char WINDOWS_SEPARATOR = '\\';
  
  private static final char SYSTEM_SEPARATOR = File.separatorChar;
  
  private static final char OTHER_SEPARATOR;
  
  static {
    if (isSystemWindows()) {
      OTHER_SEPARATOR = '/';
    } else {
      OTHER_SEPARATOR = '\\';
    } 
  }
  
  public static boolean isSystemWindows() {
    return (SYSTEM_SEPARATOR == '\\');
  }
  
  private static boolean isSeparator(char ch) {
    return (ch == '/' || ch == '\\');
  }
  
  public static String normalize(String filename) {
    return doNormalize(filename, SYSTEM_SEPARATOR, true);
  }
  
  public static String normalize(String filename, boolean unixSeparator) {
    char separator = unixSeparator ? '/' : '\\';
    return doNormalize(filename, separator, true);
  }
  
  public static String normalizeNoEndSeparator(String filename) {
    return doNormalize(filename, SYSTEM_SEPARATOR, false);
  }
  
  public static String normalizeNoEndSeparator(String filename, boolean unixSeparator) {
    char separator = unixSeparator ? '/' : '\\';
    return doNormalize(filename, separator, false);
  }
  
  private static String doNormalize(String filename, char separator, boolean keepSeparator) {
    if (filename == null)
      return null; 
    int size = filename.length();
    if (size == 0)
      return filename; 
    int prefix = getPrefixLength(filename);
    if (prefix < 0)
      return null; 
    char[] array = new char[size + 2];
    filename.getChars(0, filename.length(), array, 0);
    char otherSeparator = (separator == SYSTEM_SEPARATOR) ? OTHER_SEPARATOR : SYSTEM_SEPARATOR;
    for (int i = 0; i < array.length; i++) {
      if (array[i] == otherSeparator)
        array[i] = separator; 
    } 
    boolean lastIsDirectory = true;
    if (array[size - 1] != separator) {
      array[size++] = separator;
      lastIsDirectory = false;
    } 
    int j;
    for (j = prefix + 1; j < size; j++) {
      if (array[j] == separator && array[j - 1] == separator) {
        System.arraycopy(array, j, array, j - 1, size - j);
        size--;
        j--;
      } 
    } 
    for (j = prefix + 1; j < size; j++) {
      if (array[j] == separator && array[j - 1] == '.' && (j == prefix + 1 || array[j - 2] == separator)) {
        if (j == size - 1)
          lastIsDirectory = true; 
        System.arraycopy(array, j + 1, array, j - 1, size - j);
        size -= 2;
        j--;
      } 
    } 
    for (j = prefix + 2; j < size; j++) {
      if (array[j] == separator && array[j - 1] == '.' && array[j - 2] == '.' && (j == prefix + 2 || array[j - 3] == separator)) {
        if (j == prefix + 2)
          return null; 
        if (j == size - 1)
          lastIsDirectory = true; 
        int k = j - 4;
        while (true) {
          if (k >= prefix) {
            if (array[k] == separator) {
              System.arraycopy(array, j + 1, array, k + 1, size - j);
              size -= j - k;
              j = k + 1;
              break;
            } 
            k--;
            continue;
          } 
          System.arraycopy(array, j + 1, array, prefix, size - j);
          size -= j + 1 - prefix;
          j = prefix + 1;
          break;
        } 
      } 
    } 
    if (size <= 0)
      return ""; 
    if (size <= prefix)
      return new String(array, 0, size); 
    if (lastIsDirectory && keepSeparator)
      return new String(array, 0, size); 
    return new String(array, 0, size - 1);
  }
  
  public static String concat(String basePath, String fullFilenameToAdd) {
    int prefix = getPrefixLength(fullFilenameToAdd);
    if (prefix < 0)
      return null; 
    if (prefix > 0)
      return normalize(fullFilenameToAdd); 
    if (basePath == null)
      return null; 
    int len = basePath.length();
    if (len == 0)
      return normalize(fullFilenameToAdd); 
    char ch = basePath.charAt(len - 1);
    if (isSeparator(ch))
      return normalize(basePath + fullFilenameToAdd); 
    return normalize(basePath + '/' + fullFilenameToAdd);
  }
  
  public static boolean directoryContains(String canonicalParent, String canonicalChild) throws IOException {
    if (canonicalParent == null)
      throw new IllegalArgumentException("Directory must not be null"); 
    if (canonicalChild == null)
      return false; 
    if (IOCase.SYSTEM.checkEquals(canonicalParent, canonicalChild))
      return false; 
    return IOCase.SYSTEM.checkStartsWith(canonicalChild, canonicalParent);
  }
  
  public static String separatorsToUnix(String path) {
    if (path == null || path.indexOf('\\') == -1)
      return path; 
    return path.replace('\\', '/');
  }
  
  public static String separatorsToWindows(String path) {
    if (path == null || path.indexOf('/') == -1)
      return path; 
    return path.replace('/', '\\');
  }
  
  public static String separatorsToSystem(String path) {
    if (path == null)
      return null; 
    if (isSystemWindows())
      return separatorsToWindows(path); 
    return separatorsToUnix(path);
  }
  
  public static int getPrefixLength(String filename) {
    if (filename == null)
      return -1; 
    int len = filename.length();
    if (len == 0)
      return 0; 
    char ch0 = filename.charAt(0);
    if (ch0 == ':')
      return -1; 
    if (len == 1) {
      if (ch0 == '~')
        return 2; 
      return isSeparator(ch0) ? 1 : 0;
    } 
    if (ch0 == '~') {
      int posUnix = filename.indexOf('/', 1);
      int posWin = filename.indexOf('\\', 1);
      if (posUnix == -1 && posWin == -1)
        return len + 1; 
      posUnix = (posUnix == -1) ? posWin : posUnix;
      posWin = (posWin == -1) ? posUnix : posWin;
      return Math.min(posUnix, posWin) + 1;
    } 
    char ch1 = filename.charAt(1);
    if (ch1 == ':') {
      ch0 = Character.toUpperCase(ch0);
      if (ch0 >= 'A' && ch0 <= 'Z') {
        if (len == 2 || !isSeparator(filename.charAt(2)))
          return 2; 
        return 3;
      } 
      return -1;
    } 
    if (isSeparator(ch0) && isSeparator(ch1)) {
      int posUnix = filename.indexOf('/', 2);
      int posWin = filename.indexOf('\\', 2);
      if ((posUnix == -1 && posWin == -1) || posUnix == 2 || posWin == 2)
        return -1; 
      posUnix = (posUnix == -1) ? posWin : posUnix;
      posWin = (posWin == -1) ? posUnix : posWin;
      return Math.min(posUnix, posWin) + 1;
    } 
    return isSeparator(ch0) ? 1 : 0;
  }
  
  public static int indexOfLastSeparator(String filename) {
    if (filename == null)
      return -1; 
    int lastUnixPos = filename.lastIndexOf('/');
    int lastWindowsPos = filename.lastIndexOf('\\');
    return Math.max(lastUnixPos, lastWindowsPos);
  }
  
  public static int indexOfExtension(String filename) {
    if (filename == null)
      return -1; 
    int extensionPos = filename.lastIndexOf('.');
    int lastSeparator = indexOfLastSeparator(filename);
    return (lastSeparator > extensionPos) ? -1 : extensionPos;
  }
  
  public static String getPrefix(String filename) {
    if (filename == null)
      return null; 
    int len = getPrefixLength(filename);
    if (len < 0)
      return null; 
    if (len > filename.length())
      return filename + '/'; 
    return filename.substring(0, len);
  }
  
  public static String getPath(String filename) {
    return doGetPath(filename, 1);
  }
  
  public static String getPathNoEndSeparator(String filename) {
    return doGetPath(filename, 0);
  }
  
  private static String doGetPath(String filename, int separatorAdd) {
    if (filename == null)
      return null; 
    int prefix = getPrefixLength(filename);
    if (prefix < 0)
      return null; 
    int index = indexOfLastSeparator(filename);
    int endIndex = index + separatorAdd;
    if (prefix >= filename.length() || index < 0 || prefix >= endIndex)
      return ""; 
    return filename.substring(prefix, endIndex);
  }
  
  public static String getFullPath(String filename) {
    return doGetFullPath(filename, true);
  }
  
  public static String getFullPathNoEndSeparator(String filename) {
    return doGetFullPath(filename, false);
  }
  
  private static String doGetFullPath(String filename, boolean includeSeparator) {
    if (filename == null)
      return null; 
    int prefix = getPrefixLength(filename);
    if (prefix < 0)
      return null; 
    if (prefix >= filename.length()) {
      if (includeSeparator)
        return getPrefix(filename); 
      return filename;
    } 
    int index = indexOfLastSeparator(filename);
    if (index < 0)
      return filename.substring(0, prefix); 
    int end = index + (includeSeparator ? 1 : 0);
    if (end == 0)
      end++; 
    return filename.substring(0, end);
  }
  
  public static String getName(String filename) {
    if (filename == null)
      return null; 
    int index = indexOfLastSeparator(filename);
    return filename.substring(index + 1);
  }
  
  public static String getBaseName(String filename) {
    return removeExtension(getName(filename));
  }
  
  public static String getExtension(String filename) {
    if (filename == null)
      return null; 
    int index = indexOfExtension(filename);
    if (index == -1)
      return ""; 
    return filename.substring(index + 1);
  }
  
  public static String removeExtension(String filename) {
    if (filename == null)
      return null; 
    int index = indexOfExtension(filename);
    if (index == -1)
      return filename; 
    return filename.substring(0, index);
  }
  
  public static boolean equals(String filename1, String filename2) {
    return equals(filename1, filename2, false, IOCase.SENSITIVE);
  }
  
  public static boolean equalsOnSystem(String filename1, String filename2) {
    return equals(filename1, filename2, false, IOCase.SYSTEM);
  }
  
  public static boolean equalsNormalized(String filename1, String filename2) {
    return equals(filename1, filename2, true, IOCase.SENSITIVE);
  }
  
  public static boolean equalsNormalizedOnSystem(String filename1, String filename2) {
    return equals(filename1, filename2, true, IOCase.SYSTEM);
  }
  
  public static boolean equals(String filename1, String filename2, boolean normalized, IOCase caseSensitivity) {
    if (filename1 == null || filename2 == null)
      return (filename1 == null && filename2 == null); 
    if (normalized) {
      filename1 = normalize(filename1);
      filename2 = normalize(filename2);
      if (filename1 == null || filename2 == null)
        throw new NullPointerException("Error normalizing one or both of the file names"); 
    } 
    if (caseSensitivity == null)
      caseSensitivity = IOCase.SENSITIVE; 
    return caseSensitivity.checkEquals(filename1, filename2);
  }
  
  public static boolean isExtension(String filename, String extension) {
    if (filename == null)
      return false; 
    if (extension == null || extension.length() == 0)
      return (indexOfExtension(filename) == -1); 
    String fileExt = getExtension(filename);
    return fileExt.equals(extension);
  }
  
  public static boolean isExtension(String filename, String[] extensions) {
    if (filename == null)
      return false; 
    if (extensions == null || extensions.length == 0)
      return (indexOfExtension(filename) == -1); 
    String fileExt = getExtension(filename);
    for (String extension : extensions) {
      if (fileExt.equals(extension))
        return true; 
    } 
    return false;
  }
  
  public static boolean isExtension(String filename, Collection<String> extensions) {
    if (filename == null)
      return false; 
    if (extensions == null || extensions.isEmpty())
      return (indexOfExtension(filename) == -1); 
    String fileExt = getExtension(filename);
    for (String extension : extensions) {
      if (fileExt.equals(extension))
        return true; 
    } 
    return false;
  }
  
  public static boolean wildcardMatch(String filename, String wildcardMatcher) {
    return wildcardMatch(filename, wildcardMatcher, IOCase.SENSITIVE);
  }
  
  public static boolean wildcardMatchOnSystem(String filename, String wildcardMatcher) {
    return wildcardMatch(filename, wildcardMatcher, IOCase.SYSTEM);
  }
  
  public static boolean wildcardMatch(String filename, String wildcardMatcher, IOCase caseSensitivity) {
    if (filename == null && wildcardMatcher == null)
      return true; 
    if (filename == null || wildcardMatcher == null)
      return false; 
    if (caseSensitivity == null)
      caseSensitivity = IOCase.SENSITIVE; 
    String[] wcs = splitOnTokens(wildcardMatcher);
    boolean anyChars = false;
    int textIdx = 0;
    int wcsIdx = 0;
    Stack<int[]> backtrack = (Stack)new Stack<Integer>();
    do {
      if (backtrack.size() > 0) {
        int[] array = backtrack.pop();
        wcsIdx = array[0];
        textIdx = array[1];
        anyChars = true;
      } 
      while (wcsIdx < wcs.length) {
        if (wcs[wcsIdx].equals("?")) {
          textIdx++;
          if (textIdx > filename.length())
            break; 
          anyChars = false;
        } else if (wcs[wcsIdx].equals("*")) {
          anyChars = true;
          if (wcsIdx == wcs.length - 1)
            textIdx = filename.length(); 
        } else {
          if (anyChars) {
            textIdx = caseSensitivity.checkIndexOf(filename, textIdx, wcs[wcsIdx]);
            if (textIdx == -1)
              break; 
            int repeat = caseSensitivity.checkIndexOf(filename, textIdx + 1, wcs[wcsIdx]);
            if (repeat >= 0)
              backtrack.push(new int[] { wcsIdx, repeat }); 
          } else if (!caseSensitivity.checkRegionMatches(filename, textIdx, wcs[wcsIdx])) {
            break;
          } 
          textIdx += wcs[wcsIdx].length();
          anyChars = false;
        } 
        wcsIdx++;
      } 
      if (wcsIdx == wcs.length && textIdx == filename.length())
        return true; 
    } while (backtrack.size() > 0);
    return false;
  }
  
  static String[] splitOnTokens(String text) {
    if (text.indexOf('?') == -1 && text.indexOf('*') == -1)
      return new String[] { text }; 
    char[] array = text.toCharArray();
    ArrayList<String> list = new ArrayList<String>();
    StringBuilder buffer = new StringBuilder();
    for (int i = 0; i < array.length; i++) {
      if (array[i] == '?' || array[i] == '*') {
        if (buffer.length() != 0) {
          list.add(buffer.toString());
          buffer.setLength(0);
        } 
        if (array[i] == '?') {
          list.add("?");
        } else if (list.isEmpty() || (i > 0 && !((String)list.get(list.size() - 1)).equals("*"))) {
          list.add("*");
        } 
      } else {
        buffer.append(array[i]);
      } 
    } 
    if (buffer.length() != 0)
      list.add(buffer.toString()); 
    return list.<String>toArray(new String[list.size()]);
  }
}
