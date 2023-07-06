package YOUmI.common.converter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class BooleanToYNConverter implements AttributeConverter<Boolean, Character> {

    // 데이터베이스에 삽입될떄
    @Override
    public Character convertToDatabaseColumn(Boolean attribute) {
        return attribute ? 'Y' : 'N';
    }

    @Override
    public Boolean convertToEntityAttribute(Character dbData) {
        return dbData == 'Y';
    }
}
