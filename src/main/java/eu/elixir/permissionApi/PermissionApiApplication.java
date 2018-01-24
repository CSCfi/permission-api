package eu.elixir.permissionApi;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class PermissionApiApplication {

	@Value("${url_prefix:http://ebi.ac.uk/}")
	private String permissionUrlPrefix;
	
	public static void main(String[] args) {
		SpringApplication.run(PermissionApiApplication.class, args);
	}
	@RequestMapping(value = "/user/{userIdentifier}", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public Map<String,Object> user(@PathVariable String userIdentifier, @RequestParam Optional<String> affiliation) {
		Map<String, Object> response = new HashMap<>();
		List permissionsList = new ArrayList();
		
		Map<String, Object> permissions = new HashMap<String,Object>();

		permissions.put("url_prefix", this.permissionUrlPrefix);
		
		if (affiliation.isPresent())
			permissions.put("affiliation", affiliation.get());
		
		permissions.put("source_signature", signMessage(permissions.toString()));
		permissions.put("datasets", getPermissions(userIdentifier));
		
		permissionsList.add(permissions);
		response.put("permissions", permissionsList);
		
		
		return response;
	}
	
	public ArrayList<String> getPermissions(String userIdentifier) {
		ArrayList<String> datasets = new ArrayList<String>();
		datasets.add("EGA00001");
		datasets.add("EGA00002");
		
		return datasets;
	}

	public String signMessage(String message) {
		return "fwwffwe2421dgsgewtegw23b45m";
	}
}
