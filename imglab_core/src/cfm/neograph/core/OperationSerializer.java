package cfm.neograph.core;

import java.lang.reflect.Type;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

public class OperationSerializer implements JsonSerializer<Operation>{

	public JsonElement serialize(Operation op, Type arg1, JsonSerializationContext arg2) {
		return new JsonPrimitive(op.getName());
	}

}
