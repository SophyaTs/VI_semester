

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class City implements Serializable{
	private Long id;
	private Long countryid;
	private String name;
	private Long population;
}
