package engine.JPAConverter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.Arrays;

@Converter
public class IntegerArrayToString implements AttributeConverter<int[], String> {
    @Override
    public String convertToDatabaseColumn(int[] attribute) {
        return attribute != null ? Arrays.toString(attribute).replaceAll("\\[|\\]", "")
                :null;
    }

    @Override
    public int[] convertToEntityAttribute(String dbData) {
        return dbData != null && !dbData.equals("") ? Arrays.stream(dbData.split(", ")).mapToInt(Integer::parseInt).toArray()
                : null;
    }
}
