package de.potionmc.launcher.configuration.interfaces;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.StringBufferInputStream;
import java.lang.reflect.Type;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class FileHandler implements ConfigurationAbstract {
  protected static final Gson GSON = (new GsonBuilder()).serializeNulls().setPrettyPrinting().disableHtmlEscaping().create();
  
  protected static final JsonParser PARSER = new JsonParser();
  
  protected String name;
  
  protected JsonObject dataCatcher;
  
  public String getName() {
    return this.name;
  }
  
  public void setName(String name) {
    this.name = name;
  }
  
  public FileHandler(String name) {
    this.name = name;
    this.dataCatcher = new JsonObject();
  }
  
  public FileHandler(String name, JsonObject source) {
    this.name = name;
    this.dataCatcher = source;
  }
  
  public FileHandler(FileHandler defaults) {
    this.dataCatcher = defaults.dataCatcher;
  }
  
  public FileHandler(FileHandler defaults, String name) {
    this.dataCatcher = defaults.dataCatcher;
    this.name = name;
  }
  
  public FileHandler() {
    this.dataCatcher = new JsonObject();
  }
  
  public FileHandler(JsonObject source) {
    this.dataCatcher = source;
  }
  
  public JsonObject obj() {
    return this.dataCatcher;
  }
  
  public boolean contains(String key) {
    return this.dataCatcher.has(key);
  }
  
  public FileHandler append(String key, String value) {
    this.dataCatcher.addProperty(key, value);
    return this;
  }
  
  public FileHandler append(String key, Number value) {
    this.dataCatcher.addProperty(key, value);
    return this;
  }
  
  public FileHandler append(String key, Boolean value) {
    this.dataCatcher.addProperty(key, value);
    return this;
  }
  
  public FileHandler append(String key, JsonElement value) {
    this.dataCatcher.add(key, value);
    return this;
  }
  
  public FileHandler append(String key, List<String> value) {
    JsonArray jsonElements = new JsonArray();
    for (String b : value)
      jsonElements.add((JsonElement)jsonElements); 
    this.dataCatcher.add(key, (JsonElement)jsonElements);
    return this;
  }
  
  public FileHandler append(String key, FileHandler value) {
    this.dataCatcher.add(key, (JsonElement)value.dataCatcher);
    return this;
  }
  
  @Deprecated
  public FileHandler append(String key, Object value) {
    if (value == null)
      return this; 
    this.dataCatcher.add(key, GSON.toJsonTree(value));
    return this;
  }
  
  public FileHandler remove(String key) {
    this.dataCatcher.remove(key);
    return this;
  }
  
  public Set<String> keys() {
    Set<String> c = new HashSet<>();
    for (Map.Entry<String, JsonElement> x : (Iterable<Map.Entry<String, JsonElement>>)this.dataCatcher.entrySet())
      c.add(x.getKey()); 
    return c;
  }
  
  public String getstring(String paramString) {
    return null;
  }
  
  public String getString(String key) {
    if (!this.dataCatcher.has(key))
      return null; 
    return this.dataCatcher.get(key).getAsString();
  }
  
  public int getInt(String key) {
    if (!this.dataCatcher.has(key))
      return 0; 
    return this.dataCatcher.get(key).getAsInt();
  }
  
  public long getLong(String key) {
    if (!this.dataCatcher.has(key))
      return 0L; 
    return this.dataCatcher.get(key).getAsLong();
  }
  
  public double getDouble(String key) {
    if (!this.dataCatcher.has(key))
      return 0.0D; 
    return this.dataCatcher.get(key).getAsDouble();
  }
  
  public boolean getBoolean(String key) {
    if (!this.dataCatcher.has(key))
      return false; 
    return this.dataCatcher.get(key).getAsBoolean();
  }
  
  public float getFloat(String key) {
    if (!this.dataCatcher.has(key))
      return 0.0F; 
    return this.dataCatcher.get(key).getAsFloat();
  }
  
  public short getShort(String key) {
    if (!this.dataCatcher.has(key))
      return 0; 
    return this.dataCatcher.get(key).getAsShort();
  }
  
  public <T> T getObject(String key, Class<T> class_) {
    if (!this.dataCatcher.has(key))
      return null; 
    JsonElement element = this.dataCatcher.get(key);
    return (T)GSON.fromJson(element, class_);
  }
  
  public FileHandler getConfiguration(String key) {
    return new FileHandler(this.dataCatcher.get(key).getAsJsonObject());
  }
  
  public JsonArray getArray(String key) {
    return this.dataCatcher.get(key).getAsJsonArray();
  }
  
  public String convertToJson() {
    return GSON.toJson((JsonElement)this.dataCatcher);
  }
  
  public String convertToJsonString() {
    Gson gson = new Gson();
    return gson.toJson((JsonElement)this.dataCatcher);
  }
  
  public boolean saveAsConfig(File backend) {
    if (backend == null)
      return false; 
    if (backend.exists())
      backend.delete(); 
    try (OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(backend), "UTF-8")) {
      GSON.toJson((JsonElement)this.dataCatcher, writer);
      return true;
    } catch (IOException ex) {
      ex.getStackTrace();
      return false;
    } 
  }
  
  public boolean saveAsConfig(String path) {
    return saveAsConfig(new File(path));
  }
  
  public <T extends ConfigurationAbstract> T getdokument(String paramString) {
    return null;
  }
  
  public static FileHandler loadConfiguration(File backend) {
    try (InputStreamReader reader = new InputStreamReader(new FileInputStream(backend), "UTF-8")) {
      JsonObject object = PARSER.parse(new BufferedReader(reader)).getAsJsonObject();
      return new FileHandler(object);
    } catch (Exception ex) {
      ex.getStackTrace();
      return new FileHandler();
    } 
  }
  
  public FileHandler loadToExistingConfiguration(File backend) {
    try (InputStreamReader reader = new InputStreamReader(new FileInputStream(backend), "UTF-8")) {
      this.dataCatcher = PARSER.parse(reader).getAsJsonObject();
      return this;
    } catch (Exception ex) {
      ex.getStackTrace();
      return new FileHandler();
    } 
  }
  
  public static FileHandler load(String input) {
    try (InputStreamReader reader = new InputStreamReader(new StringBufferInputStream(input), "UTF-8")) {
      return new FileHandler(PARSER.parse(new BufferedReader(reader)).getAsJsonObject());
    } catch (IOException e) {
      e.printStackTrace();
      return new FileHandler();
    } 
  }
  
  public static FileHandler load(JsonObject input) {
    return new FileHandler(input);
  }
  
  public <T> T getObject(String key, Type type) {
    return (T)GSON.fromJson(this.dataCatcher.get(key), type);
  }
}
