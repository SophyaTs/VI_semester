package mod2lab1;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class City {
	private String id;
	private String countryId;
	private String name;
	private int population;
}
