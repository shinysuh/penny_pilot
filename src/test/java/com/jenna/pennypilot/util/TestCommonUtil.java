package com.jenna.pennypilot.util;

import com.google.gson.Gson;
import org.springframework.core.io.ClassPathResource;

import java.io.File;
import java.io.FileReader;

public class TestCommonUtil {

    public static <T> T getJsonData(String fileName, Class<T> clz) {
        T jsonData = null;
        try {
            String path = "json" + File.separator + fileName + ".json";
            ClassPathResource resource = new ClassPathResource(path);
            File jsonFile = resource.getFile();
            FileReader fileReader = new FileReader(jsonFile);

            Gson gson = new Gson();
            return gson.fromJson(fileReader, clz);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonData;
    }


}
