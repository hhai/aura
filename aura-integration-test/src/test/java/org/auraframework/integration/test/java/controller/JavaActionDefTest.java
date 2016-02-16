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
package org.auraframework.integration.test.java.controller;

import org.auraframework.def.ActionDef;
import org.auraframework.def.ActionDef.ActionType;
import org.auraframework.def.ControllerDef;
import org.auraframework.def.DefDescriptor;
import org.auraframework.def.TypeDef;
import org.auraframework.impl.AuraImplTestCase;
import org.auraframework.impl.java.controller.JavaActionDef;
import org.auraframework.impl.system.SubDefDescriptorImpl;
import org.auraframework.service.DefinitionService;
import org.auraframework.throwable.quickfix.QuickFixException;
import org.junit.Test;

import javax.inject.Inject;

public class JavaActionDefTest extends AuraImplTestCase {
    @Inject
    DefinitionService definitionService;

    @Test
    public void testCreatingJavaActionDef() throws Exception {
        DefDescriptor<ControllerDef> controllerDesc = definitionService.getDefDescriptor("java://org.auraframework.components.test.java.controller.TestController", ControllerDef.class);
        DefDescriptor<ActionDef> actionDefDesc = SubDefDescriptorImpl.getInstance("getString", controllerDesc, ActionDef.class);
        try{
            ActionDef actionDef = definitionService.getDefinition(actionDefDesc);
            assertNotNull(actionDef);
            assertTrue(actionDef instanceof JavaActionDef);
            assertTrue(actionDef.getParameters().isEmpty());
            assertEquals(definitionService.getDefDescriptor("java://java.lang.String", TypeDef.class), actionDef.getReturnType());
            assertEquals(ActionType.SERVER,actionDef.getActionType());
        }catch(Exception e){
            fail("Failed to create a valid java actiondef without parameters");
        }
        controllerDesc = definitionService.getDefDescriptor("java://org.auraframework.impl.java.controller.TestControllerWithParameters", ControllerDef.class);
        actionDefDesc = SubDefDescriptorImpl.getInstance("sumValues", controllerDesc, ActionDef.class);
        try{
            ActionDef actionDef = definitionService.getDefinition(actionDefDesc);
            assertNotNull(actionDef);
            assertEquals(2, actionDef.getParameters().size());
            assertEquals("a", actionDef.getParameters().get(0).getName());
            assertEquals("java://java.lang.Integer", actionDef.getParameters().get(0).getType().toString());
            assertEquals("b", actionDef.getParameters().get(1).getName());
            assertEquals("java://java.lang.Integer", actionDef.getParameters().get(0).getType().toString());
            assertEquals(definitionService.getDefDescriptor("java://java.lang.Integer", TypeDef.class), actionDef.getReturnType());
        }catch(Exception e){
            fail("Failed to create a valid java actiondef with parameters");
        }
    }

    @Test
    public void testGetLoggableParams()throws Exception{
        //No annotation specified for logging
        JavaActionDef actionDef = getJavaActionDef("java://org.auraframework.components.test.java.controller.TestController", "getString");
        assertEquals("Action with no parameters should not have any loggable parameters", 0, actionDef.getLoggableParams().size());
        
        //@key annotation is marked for logging
        actionDef = getJavaActionDef("java://org.auraframework.components.test.java.controller.JavaTestController", "getLoggableString");
        assertEquals("Action with parameters marked as loggable should be logged", 
                1, actionDef.getLoggableParams().size());
        assertEquals("param", actionDef.getLoggableParams().get(0));
        
        //Selected param set as loggable
        actionDef = getJavaActionDef("java://org.auraframework.components.test.java.controller.JavaTestController", "getSelectedParamLogging");
        assertEquals("Only parameters marked as loggable should be logged", 
                1, actionDef.getLoggableParams().size());
        assertEquals("strparam", actionDef.getLoggableParams().get(0));
        
        //Param explicitly set as not loggable
        actionDef = getJavaActionDef("java://org.auraframework.components.test.java.controller.JavaTestController", "getExplicitExcludeLoggable");
        assertEquals("parameters marked as loggable should not be logged", 
                0, actionDef.getLoggableParams().size());
    }
    
    private JavaActionDef getJavaActionDef(String controller, String actionName) throws QuickFixException {
        DefDescriptor<ControllerDef> controllerDesc = definitionService.getDefDescriptor(controller, 
                ControllerDef.class);
        DefDescriptor<ActionDef> actionDefDesc = SubDefDescriptorImpl.getInstance(actionName, controllerDesc, ActionDef.class);
        ActionDef actionDef = definitionService.getDefinition(actionDefDesc);
        assertTrue(actionDef instanceof JavaActionDef);
        return (JavaActionDef)actionDef;
    }
}
