package com.mattworzala.artifact.json.extra;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.mattworzala.resource.json.VarListDeserializer;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;

import java.lang.reflect.Type;

public interface MiniMessageAdapters {
    JsonDeserializer<Component> COMPONENT = new Single();

    class Single implements JsonDeserializer<Component> {
        @Override
        public Component deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            return MiniMessage.get().deserialize(context.deserialize(json, String.class));
        }
    }

    class List extends VarListDeserializer<Component> {
        public List() { super(Component.class); }
    }
}
