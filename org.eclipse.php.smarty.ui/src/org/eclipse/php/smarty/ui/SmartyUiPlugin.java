package org.eclipse.php.smarty.ui;

import java.io.IOException;

import org.eclipse.jface.text.templates.ContextTypeRegistry;
import org.eclipse.jface.text.templates.TemplateContextType;
import org.eclipse.jface.text.templates.persistence.TemplateStore;
import org.eclipse.php.internal.ui.Logger;
import org.eclipse.php.internal.ui.preferences.PHPTemplateStore;
import org.eclipse.php.internal.ui.util.ImageDescriptorRegistry;
import org.eclipse.php.smarty.ui.editor.templates.SmartyTemplateContextType;
import org.eclipse.php.smarty.ui.preferences.PreferenceConstants;
import org.eclipse.ui.editors.text.templates.ContributionContextTypeRegistry;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

/**
 * The activator class controls the plug-in life cycle
 */
public class SmartyUiPlugin extends AbstractUIPlugin {

	private ImageDescriptorRegistry fImageDescriptorRegistry;
	protected ContextTypeRegistry contentTypeRegistry = null;
	protected TemplateStore templateStore = null;
	// The plug-in ID
	public static final String PLUGIN_ID = "org.eclipse.php.smarty.ui";

	// The shared instance
	private static SmartyUiPlugin plugin;
	
	/**
	 * The constructor
	 */
	public SmartyUiPlugin() {
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#start(org.osgi.framework.BundleContext)
	 */
	public void start(BundleContext context) throws Exception {
		super.start(context);
		plugin = this;
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext context) throws Exception {
		plugin = null;
		super.stop(context);
	}

	/**
	 * Returns the shared instance
	 *
	 * @return the shared instance
	 */
	public static SmartyUiPlugin getDefault() {
		return plugin;
	}

	public static ImageDescriptorRegistry getImageDescriptorRegistry() {
		return getDefault().internalGetImageDescriptorRegistry();
	}

	private synchronized ImageDescriptorRegistry internalGetImageDescriptorRegistry() {
		if (fImageDescriptorRegistry == null)
			fImageDescriptorRegistry = new ImageDescriptorRegistry();
		return fImageDescriptorRegistry;
	}

	/**
	 * Returns the template context type registry for the xml plugin.
	 * 
	 * @return the template context type registry for the xml plugin
	 */
	public ContextTypeRegistry getTemplateContextRegistry() {
		if (contentTypeRegistry == null) {
			ContributionContextTypeRegistry registry = new SmartyContributionContextTypeRegistry();
			
			registry.addContextType(SmartyTemplateContextType.SMARTY_CONTEXT_TYPE_ID);
			
			contentTypeRegistry = registry;
		}

		return contentTypeRegistry;
	}

	/**
	 * Returns the template store for the xml editor templates.
	 * 
	 * @return the template store for the xml editor templates
	 */
	public TemplateStore getTemplateStore() {
		if (templateStore == null) {
			templateStore = new PHPTemplateStore(getTemplateContextRegistry(), getPreferenceStore(), PreferenceConstants.TEMPLATES_KEY);

			try {
				templateStore.load();
			} catch (IOException e) {
				//TODO change Logger to smarty's
				Logger.logException(e);
			}
		}
		return templateStore;
	}

}
