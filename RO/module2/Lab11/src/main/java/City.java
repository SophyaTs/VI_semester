

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class City {
	private Long id;
	private Long countryId;
	private String name;
	private Long population;
}
