import { injectGlobalWebcomponentCss } from 'Frontend/generated/jar-resources/theme-util.js';

import '@vaadin/polymer-legacy-adapter/style-modules.js';
import '@vaadin/combo-box/src/vaadin-combo-box.js';
import 'Frontend/generated/jar-resources/flow-component-renderer.js';
import 'Frontend/generated/jar-resources/comboBoxConnector.js';
import '@vaadin/integer-field/src/vaadin-integer-field.js';
import '@vaadin/tooltip/src/vaadin-tooltip.js';
import '@vaadin/tabs/src/vaadin-tab.js';
import '@vaadin/icon/src/vaadin-icon.js';
import '@vaadin/form-layout/src/vaadin-form-item.js';
import '@vaadin/multi-select-combo-box/src/vaadin-multi-select-combo-box.js';
import '@vaadin/button/src/vaadin-button.js';
import '@vaadin/form-layout/src/vaadin-form-row.js';
import '@vaadin/number-field/src/vaadin-number-field.js';
import '@vaadin/text-field/src/vaadin-text-field.js';
import '@vaadin/icons/vaadin-iconset.js';
import 'Frontend/generated/jar-resources/vaadin-big-decimal-field.js';
import '@vaadin/form-layout/src/vaadin-form-layout.js';
import '@vaadin/vertical-layout/src/vaadin-vertical-layout.js';
import '@vaadin/tabsheet/src/vaadin-tabsheet.js';
import '@vaadin/horizontal-layout/src/vaadin-horizontal-layout.js';
import '@vaadin/tabs/src/vaadin-tabs.js';
import 'Frontend/generated/jar-resources/disableOnClickFunctions.js';
import '@vaadin/notification/src/vaadin-notification.js';
import '@vaadin/common-frontend/ConnectionIndicator.js';
import '@vaadin/vaadin-lumo-styles/sizing.js';
import '@vaadin/vaadin-lumo-styles/spacing.js';
import '@vaadin/vaadin-lumo-styles/style.js';
import '@vaadin/vaadin-lumo-styles/vaadin-iconset.js';
import 'Frontend/generated/jar-resources/ReactRouterOutletElement.tsx';
const loadOnDemand = (key) => { return Promise.resolve(0); }
window.Vaadin = window.Vaadin || {};
window.Vaadin.Flow = window.Vaadin.Flow || {};
window.Vaadin.Flow.loadOnDemand = loadOnDemand;
window.Vaadin.Flow.resetFocus = () => {
 let ae=document.activeElement;
 while(ae&&ae.shadowRoot) ae = ae.shadowRoot.activeElement;
 return !ae || ae.blur() || ae.focus() || true;
}