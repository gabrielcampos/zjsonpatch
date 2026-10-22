/*
 * Copyright 2016 flipkart.com zjsonpatch.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.flipkart.zjsonpatch;

import com.fasterxml.jackson.databind.JsonNode;
import com.flipkart.zjsonpatch.mapping.JacksonVersionBridge;
import com.flipkart.zjsonpatch.mapping.JsonNodeFactoryWrapper;
import com.flipkart.zjsonpatch.mapping.JsonNodeWrapper;
import com.flipkart.zjsonpatch.mapping.jackson2.Jackson2NodeFactory;

import java.util.EnumSet;
import java.util.List;

/**
 * User: gopi.vishwakarma
 * Date: 30/07/14
 * @author Mariusz Sondecki
 */
public final class JsonDiff extends AbstractJsonDiff {

    private static final JsonNodeFactoryWrapper FACTORY = new Jackson2NodeFactory();

    private JsonDiff(EnumSet<DiffFlags> flags, List<String> identifiers) {
        super(flags, identifiers);
    }

    @Override
    protected JsonPointer getJsonPointerRoot() {
        return JsonPointer.ROOT;
    }

    @Override
    protected JsonPointer createJsonPointerInstance(List<RefToken> tokens) {
        return new JsonPointer(tokens);
    }

    public static JsonNode asJson(final JsonNode source, final JsonNode target) {
        return asJson(source, target, DiffFlags.defaults());
    }

    public static JsonNode asJson(final JsonNode source, final JsonNode target, EnumSet<DiffFlags> flags) {
        JsonNodeWrapper sourceWrapper = JacksonVersionBridge.wrap(source);
        JsonNodeWrapper targetWrapper = JacksonVersionBridge.wrap(target);
        return JacksonVersionBridge.unwrap(getJsonNode(sourceWrapper, targetWrapper, new JsonDiff(flags, DEFAULT_IDENTIFIERS), FACTORY));
    }
}
