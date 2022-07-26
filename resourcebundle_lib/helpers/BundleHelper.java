package resourcebundle_lib.helpers;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.FileSystems;
import java.util.ResourceBundle;

import com.mendix.core.Core;
import com.mendix.logging.ILogNode;
import com.mendix.systemwideinterfaces.core.IContext;
import com.mendix.systemwideinterfaces.core.IMendixIdentifier;
import com.mendix.systemwideinterfaces.core.IMendixObject;

public class BundleHelper {
	/**
	 * Logger for this java action
	 */
	public static ILogNode logger = Core.getLogger("ResourceBundle_Lib");
	
	public static final String IDENTIFIER_DESCRIPTION = "description";
	
	public static final String IDENTIFIER_DISPLAYVALUE = "displayvalue";
	
	
	private static ClassLoader loader = null;
	
	static {
		logger.info("Initializing BundleHelper static");
		File file = new File (Core.getConfiguration().getTempPath() + FileSystems.getDefault().getSeparator());
		try {
			URL[] urls = { file.toURI().toURL() };
			loader = new URLClassLoader(urls);
		} catch (MalformedURLException e) {
			logger.error("Error initializing class loader", e);
		}
	}
	
	/**
	 * Create a file in the tmp folder
	 * @return
	 * @throws IOException
	 */
	public static File createResourceBundleFile(String resourceBundleName) throws IOException {
		File bundleFile = new File(Core.getConfiguration().getTempPath().toPath() + FileSystems.getDefault().getSeparator() + resourceBundleName+".properties");
		logger.debug("Created resource file: " + bundleFile);
		return bundleFile;
	}
		
	public static String getTranslationForKey(String TranslationIdentifier, String key, IContext context) throws MalformedURLException {
		ResourceBundle resourceBundle = ResourceBundle.getBundle(TranslationIdentifier, Core.getLocale(context), loader);

		// Get the key in the user locale
		if (resourceBundle.containsKey(key)) {
			return resourceBundle.getString(key);
		} else {
			// Key not found in user locale, using default locale to get translation
			resourceBundle = ResourceBundle.getBundle(TranslationIdentifier, Core.getLocale(Core.getDefaultLanguage().getCode()), loader);
			if (resourceBundle.containsKey(key)) 
				return resourceBundle.getString(key);
		}
		// In case nothing is found
		return "";
	}
	
	public static String getAssociationIdentifier(IMendixObject imo, String association, String fieldIdentifier, IContext context ) {
		if (imo.hasMember(association)){
			IMendixIdentifier imi = imo.getValue(context, association);
			if (imi != null)
				return String.valueOf(imi.toLong()) + fieldIdentifier;
		} else
			throw new com.mendix.systemwideinterfaces.MendixRuntimeException("Member " + association + " is not part of " + imo.getClass().getName());
		
		return null;
	}
	
	public static Boolean clearCache() {
		ResourceBundle.clearCache(loader);
		return true;
	}
}