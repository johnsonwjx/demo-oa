package nospring;

import java.io.IOException;

import org.codehaus.jackson.JsonEncoding;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.map.ObjectMapper;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import xiong.domain.Department;
import xiong.domain.Employee;

public class JacksonTest {
	private JsonGenerator jsonGenerator = null;
	private ObjectMapper objectMapper = null;
	private Employee bean = null;
	private Department bean2 = null;

	@Before
	public void init() {
		bean = new Employee();
		bean.setUsername("df");
		bean.setPassword("df");
		bean2 = new Department("df", "df");
		objectMapper = new ObjectMapper();
		try {
			jsonGenerator = objectMapper.getJsonFactory().createJsonGenerator(
					System.out, JsonEncoding.UTF8);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@After
	public void destory() {
		try {
			if (jsonGenerator != null) {
				jsonGenerator.flush();
			}
			if (!jsonGenerator.isClosed()) {
				jsonGenerator.close();
			}
			jsonGenerator = null;
			objectMapper = null;
			bean = null;
			System.gc();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void writeEntityJSON() {

		try {
//			System.out.println("jsonGenerator");
//			// writeObject可以转换java对象，eg:JavaBean/Map/List/Array等
//			jsonGenerator.writeObject(bean);
//			System.out.println();
//			jsonGenerator.writeObject(bean2);
//			System.out.println("ObjectMapper");
//			// writeValue具有和writeObject相同的功能
//			objectMapper.writeValue(System.out, bean);
			Department d = new Department("dfd", "dfd") ;
			Department child=new Department("chid","df");
			d.setChild(child);
			jsonGenerator.writeObject(d);
			System.out.println();
			jsonGenerator.writeObject(child);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
