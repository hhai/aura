/*
 * Copyright (C) 2013 salesforce.com, inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.auraframework.impl.root.parser.handler;

import com.google.common.collect.ImmutableSet;
import org.auraframework.adapter.ConfigAdapter;
import org.auraframework.adapter.DefinitionParserAdapter;
import org.auraframework.def.ComponentDef;
import org.auraframework.def.DefDescriptor;
import org.auraframework.impl.root.component.ComponentDefImpl;
import org.auraframework.service.ContextService;
import org.auraframework.service.DefinitionService;
import org.auraframework.system.Source;
import org.auraframework.throwable.quickfix.QuickFixException;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.util.Set;

/**
 */
public class ComponentDefHandler extends BaseComponentDefHandler<ComponentDef, ComponentDefImpl.Builder> {

    public static final String TAG = "aura:component";

    private static final String ATTRIBUTE_ISTEMPLATE = "isTemplate";

    private static final Set<String> ALLOWED_ATTRIBUTES = new ImmutableSet.Builder<String>()
            .add(ATTRIBUTE_ISTEMPLATE)
            .addAll(BaseComponentDefHandler.ALLOWED_ATTRIBUTES).build();

    private static final Set<String> PRIVILEGED_ALLOWED_ATTRIBUTES = new ImmutableSet.Builder<String>()
            .addAll(ALLOWED_ATTRIBUTES)
            .addAll(BaseComponentDefHandler.PRIVILEGED_ALLOWED_ATTRIBUTES)
            .build();
    
    public ComponentDefHandler() {
        super();
    }

    public ComponentDefHandler(DefDescriptor<ComponentDef> componentDefDescriptor, Source<?> source,
                               XMLStreamReader xmlReader, boolean isInPrivilegedNamespace, DefinitionService definitionService,
                               ContextService contextService,
                               ConfigAdapter configAdapter, DefinitionParserAdapter definitionParserAdapter) {
        super(componentDefDescriptor, source, xmlReader, isInPrivilegedNamespace, definitionService, contextService, configAdapter, definitionParserAdapter);
    }

    @Override
    protected ComponentDefImpl.Builder createBuilder() {
        return new ComponentDefImpl.Builder();
    }

    @Override
    public String getHandledTag() {
        return TAG;
    }

    @Override
    public Set<String> getAllowedAttributes() {
        return isInPrivilegedNamespace ? PRIVILEGED_ALLOWED_ATTRIBUTES : ALLOWED_ATTRIBUTES;
    }

    @Override
    protected void readAttributes() throws QuickFixException {
        super.readAttributes();
        builder.isTemplate = getBooleanAttributeValue(ATTRIBUTE_ISTEMPLATE);
    }

    @Override
    protected void handleChildTag() throws XMLStreamException, QuickFixException {
        super.handleChildTag();
    }
}
