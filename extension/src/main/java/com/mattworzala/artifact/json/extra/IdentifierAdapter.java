package com.mattworzala.artifact.json.extra;

import com.google.gson.*;
import com.mattworzala.resource.Identifier;

import java.lang.reflect.Type;

public interface IdentifierAdapter {
    JsonSerializer<Identifier> SERIALIZER = new IdentifierAdapter.Serializer() {};
    JsonDeserializer<Identifier> DESERIALIZER = new IdentifierAdapter.Deserializer() {};

    interface Serializer extends JsonSerializer<Identifier> {
        @Override
        default JsonElement serialize(Identifier src, Type typeOfSrc, JsonSerializationContext context) {
            return context.serialize(src.toString(), String.class);
        }
    }

    interface Deserializer extends JsonDeserializer<Identifier> {
        @Override
        default Identifier deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            String value = json.getAsString();
//            if (value == null) throw new JsonParseException("Not an Identifier: " + json);
            return Identifier.of(value);
        }
    }
}
