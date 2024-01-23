package com.LoginSignUp.utils;

import java.io.IOException;
import java.io.StringReader;

import com.fasterxml.jackson.databind.ObjectMapper;

public class ClassUtil {
    
    public static <T> T  convertOneToAnother(Object source, Class<T> resultClass) throws IOException {
        try {
            ObjectMapper mapper = new ObjectMapper();
            String obj = mapper.writeValueAsString(source);

            // Replace the class name in the JSON representation
            obj = obj.replace("\"" + source.getClass().getSimpleName().toLowerCase() + "\"", "\"" + resultClass.getSimpleName().toLowerCase() + "\"");

            return new ObjectMapper().readValue(new StringReader(obj), resultClass);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
