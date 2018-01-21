import static com.jayway.jsonpath.Criteria.where;
import static com.jayway.jsonpath.Filter.filter;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Map;

import junit.framework.Assert;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;

import org.junit.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.jayway.jsonpath.Filter;
import com.jayway.jsonpath.JsonPath;
import com.ramit.domain.Coffee;
import com.ramit.persistence.model.Jobs;

public class TestClass {

	@Test
	public void jobPagination() {
		RestTemplate restTemplate = new RestTemplate();
		String resourceUrl = "http://127.0.0.1:8090/WebService/rest/jobs/page?pageSize=3&pageNo=4";
		ResponseEntity<String> response = restTemplate.exchange(resourceUrl, HttpMethod.GET, null, String.class);
		System.out.println("Pagination result: " + response.getBody());
		Assert.assertEquals(response.getStatusCode(), HttpStatus.OK);
	}

	@Test
	public void whereClause() {
		RestTemplate restTemplate = new RestTemplate();
		String resourceUrl = "http://127.0.0.1:8090/WebService/rest/jobs/title?title1=Ops&title2=Admin";
		ResponseEntity<List<Jobs>> response = restTemplate.exchange(resourceUrl, HttpMethod.GET, null,
				(Class<List<Jobs>>) ((Class) List.class));
		List<Jobs> results = response.getBody();
		System.out.println("Jpa 'OR' criteria result count : " + results.size());
		System.out.println(response.getBody());
		Assert.assertEquals(response.getStatusCode(), HttpStatus.OK);
	}

	@Test
	public void findAllJobs() {
		RestTemplate restTemplate = new RestTemplate();
		String resourceUrl = "http://127.0.0.1:8090/WebService/rest/jobs/all";
		ResponseEntity<List<Jobs>> response = restTemplate.exchange(resourceUrl, HttpMethod.GET, null,
				(Class<List<Jobs>>) ((Class) List.class));
		List<Jobs> results = response.getBody();
		System.out.println("Jpa find all result count : " + results.size());
		Assert.assertEquals(response.getStatusCode(), HttpStatus.OK);
		/*
		 * URL url; try { url = new
		 * URL("http://127.0.0.1:8090/WebService/rest/jobs/all");
		 * HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		 * conn.setRequestMethod("GET"); conn.setRequestProperty("Accept",
		 * "application/json"); // for json // output if (conn.getResponseCode()
		 * != 200) { throw new RuntimeException("Failed : HTTP error code : " +
		 * conn.getResponseCode()); }
		 * 
		 * BufferedReader br = new BufferedReader(new
		 * InputStreamReader((conn.getInputStream()))); // Converting JSON to
		 * Object (using Jackson) ObjectMapper mapper = new ObjectMapper();
		 * List<Jobs> jobList = mapper.readValue(br.readLine(),
		 * TypeFactory.defaultInstance().constructCollectionType(List.class,
		 * Jobs.class));
		 * 
		 * StringBuilder sb = new StringBuilder("["); for (Jobs job : jobList) {
		 * sb.append(job.getJobId()); sb.append(", "); } sb.append("]");
		 * System.out.println(jobList.size() +
		 * " job objects fethced from DB. Job Ids : " + sb.toString());
		 * 
		 * conn.disconnect(); } catch (Exception e) { e.printStackTrace(); }
		 */
	}

	@Test
	public void testSimpleGet() {
		URL url;
		try {
			url = new URL("http://127.0.0.1:8090/WebService/rest/coffee/latte/cold");
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Accept", "application/xml"); // for xml
																	// output
			if (conn.getResponseCode() != 200) {
				throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
			}
			BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));

			String output;
			System.out.println("Output from Server .... \n");
			while ((output = br.readLine()) != null) {
				System.out.println(output);
			}
			conn.disconnect();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testSimplePost() {
		RestTemplate restTemplate = new RestTemplate();
		String resourceUrl = "http://127.0.0.1:8090/WebService/rest/coffee/submit";
		Coffee coffee = new Coffee("Latte", "Hot", 200);
		HttpEntity<Coffee> request = new HttpEntity<Coffee>(coffee);  //change model into HttpEntity
		ResponseEntity<String> response = restTemplate.exchange(resourceUrl, HttpMethod.POST, request, String.class);
		String result = response.getBody();
		System.out.println("Coffee Post response : " + result);
		Assert.assertEquals(response.getStatusCode(), HttpStatus.OK);
	}

	@Test
	public void testException() {
		URL url;
		try {
			url = new URL("http://127.0.0.1:8090/WebService/rest/coffee/exception");
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Accept", "application/xml"); // for xml
																	// output
			System.out.println("Error code : " + conn.getResponseCode());
			BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));

			String output;
			System.out.println("Output from Server .... \n");
			while ((output = br.readLine()) != null) {
				System.out.println(output);
			}
			conn.disconnect();
		} catch (Exception e) {
			System.out.println("Exception handled");
		}
	}

	// http://127.0.0.1:8090/WebService/rest/swagger/index.html for swagger
	/**
	 * using JsonPath API for traversing swagger response
	 */
	@Test
	public void swagger() {
		String documentationUrl = "http://127.0.0.1:8090/WebService/rest/api-docs";
		// Documentation for Coffee =
		// http://127.0.0.1:8090/WebService/rest/api-docs/coffee
		String resourceType = "swaggerCoffee";
		RestTemplate tpl = new RestTemplate();
		String documentation = tpl.getForObject(documentationUrl, String.class);
		String basePath = JsonPath.read(documentation, "basePath"); // extracts
																	// http://localhost:8090/WebService
		// extracts /api-docs/ccoffee to discover the available operations on
		// the Coffee resource
		List<String> apiDocumentationPath = JsonPath.read(documentation, "apis[?].path", filter(where("description").is(resourceType)));
		// let's go to http://localhost:8090/WebService/rest/api-docs/coffee
		documentation = tpl.getForObject(basePath + "/rest/" + apiDocumentationPath.get(0), String.class);
		// selects the info about the "getCoffeeInXML()" operation on "coffee"
		// we know it's GET, but we need to know the URL (it's actually
		// "/contacts")
		List<String> apis = JsonPath.read(documentation, "$.apis[?].path", new OperationNicknameFilter("getCoffeeInXML", "GET"));
		// contains "/coffee"
		String resourcePath = apis.get(0);
		Object[] param = new Object[2];
		param[0] = "Latte";
		param[1] = 120;
		String coffeeResponse = tpl.getForObject(basePath + "/rest/" + resourcePath, String.class, param);

		System.out.println("swagger response : " + coffeeResponse);
	}

	private static class OperationNicknameFilter extends Filter.FilterAdapter<Map<String, Object>> {

		private final String operationNickname, httpMethod;

		public OperationNicknameFilter(String operation, String httpMethod) {
			super();
			this.operationNickname = operation;
			this.httpMethod = httpMethod;
		}

		@Override
		public boolean accept(Map<String, Object> map) {
			JSONObject operation = (JSONObject) ((JSONArray) map.get("operations")).get(0);
			return operation.get("nickname").equals(operationNickname) && operation.get("httpMethod").equals(httpMethod);
		}

	}
}
