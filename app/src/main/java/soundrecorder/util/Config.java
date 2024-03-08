package soundrecorder.util;

import org.yaml.snakeyaml.Yaml;

import java.io.InputStream;
import java.util.Map;

public class Config {
    @SuppressWarnings("unchecked")
    public static void init() {
        try (InputStream inputStream = Config.class.getClassLoader().getResourceAsStream("application.yml")) {
            Yaml yaml = new Yaml();
            Object obj = yaml.load(inputStream);

            if (obj instanceof java.util.Map) {
                java.util.Map<String, Object> map = (Map<String, Object>) obj;

                for (java.util.Map.Entry<String, Object> entry : map.entrySet()) {
                    String key = entry.getKey();
                    Object value = entry.getValue();

                    if (value instanceof java.util.Map) {
                        java.util.Map<String, Object> nestedMap = (Map<String, Object>) value;

                        for (java.util.Map.Entry<String, Object> nestedEntry : nestedMap.entrySet()) {
                            String envKey = key.toUpperCase() + "_" + nestedEntry.getKey().toUpperCase();
                            System.setProperty(envKey, nestedEntry.getValue().toString());
                        }
                    } else {
                        System.setProperty(key.toUpperCase(), value.toString());
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
