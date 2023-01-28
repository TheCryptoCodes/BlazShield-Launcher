package de.potionmc.launcher.configuration.interfaces;

import com.google.gson.JsonElement;
import java.io.File;
import java.util.Set;

public interface ConfigurationAbstract {
  <T extends ConfigurationAbstract> T append(String paramString1, String paramString2);
  
  <T extends ConfigurationAbstract> T append(String paramString, Number paramNumber);
  
  <T extends ConfigurationAbstract> T append(String paramString, Boolean paramBoolean);
  
  <T extends ConfigurationAbstract> T append(String paramString, JsonElement paramJsonElement);
  
  <T extends ConfigurationAbstract> T remove(String paramString);
  
  Set<String> keys();
  
  String getstring(String paramString);
  
  int getInt(String paramString);
  
  short getShort(String paramString);
  
  float getFloat(String paramString);
  
  boolean getBoolean(String paramString);
  
  double getDouble(String paramString);
  
  long getLong(String paramString);
  
  String convertToJson();
  
  boolean saveAsConfig(File paramFile);
  
  boolean saveAsConfig(String paramString);
  
  <T extends ConfigurationAbstract> T getdokument(String paramString);
}
