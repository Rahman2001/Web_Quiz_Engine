package engine.JPAConverter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.Arrays;

@Converter
public class StringArrayToString implements AttributeConverter<String[], String> {
    @Override
    public String convertToDatabaseColumn(String[] attribute) {
        return Arrays.toString(attribute).replaceAll("\\[|\\]", "");
    }

    @Override
    public String[] convertToEntityAttribute(String dbData) {
        return Arrays.stream(dbData.split(", ")).toArray(String[]::new);
    }
}
