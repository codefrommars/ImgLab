package cfm.neograph.core;

import java.lang.reflect.Type;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

public class NodeValueHandler implements JsonDeserializer<NodeValue>, JsonSerializer<NodeValue>{

	public OperationRegister reg;
	
	public NodeValueHandler(OperationRegister register){
		reg = register;
	}
	
	public NodeValue deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
		
		JsonObject obj = json.getAsJsonObject();
		JsonElement type = obj.get("type");
		JsonElement val = obj.get("value");
		
		GraphNodeType t = context.deserialize(type, GraphNodeType.class);
		Object v = context.deserialize(val, Object.class);
		
		if( t == GraphNodeType.Operation )
			v = reg.getOperation( (String)v );
		
		NodeValue n = new NodeValue();
		n.setType(t);
		n.setValue(v);
		
		return n;
//		System.err.println("Deserializing operation....");
//		
//		String opName = json.getAsString();
//		Operation o = reg.getOperation(opName);
//		
//		if( o == null )
//			System.err.println("Opertion not registered: " + opName);
//		
//		return o;
	}

	@Override
	public JsonElement serialize(NodeValue nodeVal, Type arg1, JsonSerializationContext ctx) {
		
		
		
		JsonObject o = new JsonObject();
		
		if( nodeVal.getType() != GraphNodeType.Image )			
			o.add("value", ctx.serialize(nodeVal.getValue()));
		
		o.add("type", ctx.serialize(nodeVal.getType()));
		
		return o;
	}
}
