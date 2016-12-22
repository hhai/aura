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
({

	handleZIndex: function(cmp) {
		cmp.getElement().style.zIndex = cmp.get('v.zIndex');
	},

	repositionPanel: function(cmp, evt, helper) {
		helper.reposition(cmp, null);
	},

	listCollapse: function(cmp, evt, helper) {
		cmp.set('v.visible', false);
	},

	handleVisibilityChange: function(cmp, evt, helper) {
		if (cmp.get('v.visible')) {
			helper.positionList(cmp);
		} else {
			helper.clearPositionConstraint(cmp);
		}
	},

	handleReferenceChange: function(cmp, evt, helper) {
		if (cmp.get('v.visible') && !cmp.positionConstraint) {
			helper.positionList(cmp);
		} else {
			helper.reposition(cmp, null);
		}
	}
})// eslint-disable-line semi
