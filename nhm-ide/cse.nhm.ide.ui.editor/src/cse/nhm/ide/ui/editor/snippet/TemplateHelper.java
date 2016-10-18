package cse.nhm.ide.ui.editor.snippet;

import java.io.IOException;
import java.util.HashMap;

import org.eclipse.jface.text.templates.ContextTypeRegistry;
import org.eclipse.jface.text.templates.persistence.TemplateStore;
import org.eclipse.ui.editors.text.templates.ContributionContextTypeRegistry;
import org.eclipse.ui.editors.text.templates.ContributionTemplateStore;

import cse.nhm.ide.ui.NHMUIPlugin;

public class TemplateHelper {
    /** The template store list. */
    private static HashMap<String, TemplateStore> fStoreList = new HashMap<String, TemplateStore>();
    
    /** The context type registry. */
    private static ContributionContextTypeRegistry fRegistry;

    /** Key to store custom templates. */
    public static final String CUSTOM_TEMPLATES_KEY = "cse.nhm.ide.ui.editor.snippets";

    /**
     * Returns this plug-in's template store.
     * 
     * @return the template store of this plug-in instance
     */
    public static TemplateStore getTemplateStore(String type) {
    	if (!fStoreList.containsKey(type)) {
    		ContributionTemplateStore store = new ContributionTemplateStore(TemplateHelper.getContextTypeRegistry(), 
    				NHMUIPlugin.getDefault().getPreferenceStore(), CUSTOM_TEMPLATES_KEY + "." + type);
    		try {
    			store.load();
    			fStoreList.put(type, store);
    		} catch (IOException e) {
    			e.printStackTrace();
    			throw new RuntimeException(e);
    		}
    		
    	}
    	
        return fStoreList.get(type);
    }
    

    /**
     * Returns this plug-in's context type registry.
     * 
     * @return the context type registry for this plug-in instance
     */
    public static ContextTypeRegistry getContextTypeRegistry() {
        if (fRegistry == null) {
            // create and configure the contexts available in the template editor
            fRegistry = new ContributionContextTypeRegistry();
            fRegistry.addContextType(ScenarioContextType.CTX_SCENARIO);
        }
        
        return fRegistry;
    }
}