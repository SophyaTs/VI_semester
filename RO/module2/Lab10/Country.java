package mod2lab1;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Country {
	private String id;
	private String name;
	private List<City> cities = new ArrayList<>();
}
