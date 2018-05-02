package supervision.server.config;

/**
 * This converter will translate every LocalDateTime in java into Timestamp in SQL
 * If not present, the LocalDateTime will be converted to blobs
 * 
 * @date 25/04/2018
 * @author Cyril Gambis
 *
@Converter(autoApply = true)
public class LocalDateTimeConverter implements AttributeConverter<LocalDateTime, Timestamp> {

	@Override
	public Timestamp convertToDatabaseColumn(LocalDateTime localDateTime) {
		return Optional.ofNullable(localDateTime).map(Timestamp::valueOf).orElse(null);
	}

	@Override
	public LocalDateTime convertToEntityAttribute(Timestamp timestamp) {
		return Optional.ofNullable(timestamp).map(Timestamp::toLocalDateTime).orElse(null);
	}

}*/