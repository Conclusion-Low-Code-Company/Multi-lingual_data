// This file was generated by Mendix Modeler.
//
// WARNING: Only the following code will be retained when actions are regenerated:
// - the import list
// - the code between BEGIN USER CODE and END USER CODE
// - the code between BEGIN EXTRA CODE and END EXTRA CODE
// Other code you write will be lost the next time you deploy the project.
// Special characters, e.g., é, ö, à, etc. are supported in comments.

package resourcebundle_lib.actions;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import com.mendix.systemwideinterfaces.core.IContext;
import com.mendix.systemwideinterfaces.core.IMendixObject;
import com.mendix.webui.CustomJavaAction;
import resourcebundle_lib.helpers.BundleHelper;

/**
 * Create a resource bundle by specifying
 * * the language the translations are in
 * * app wide unique identifier of this translation e.g. productname. Suggestion is to use an ENUM for the values. You need to have the same value for retrieving the value
 * 
 * * A list of translations (of that specific language) and specify the member that holds the value of the translation itself
 * 
 * * The reference to the contextobject or translatable entity, so the translation can be related to the context that is used whilst getting the translation.
 * 
 * In the end a .properties for a language is created with contents: 
 * <guid>=<the value of the translation>
 * 
 * thus
 * ProductName_en_US.properties
 * 123456788=Monitor
 * 123456789=Desk
 * 
 * ProductName_nl_NL.properties
 * 123456788=Beeldscherm
 * 123456789=Bureau
 */
public class JA_ResourceBundleFileCreate extends CustomJavaAction<java.lang.Boolean>
{
	private IMendixObject __BundleLanguage;
	private system.proxies.Language BundleLanguage;
	private java.util.List<IMendixObject> TranslationList;
	private java.lang.String TranslationMember;
	private java.lang.String TranslationIdentifier;
	private java.lang.String ReferenceToContext;

	public JA_ResourceBundleFileCreate(IContext context, IMendixObject BundleLanguage, java.util.List<IMendixObject> TranslationList, java.lang.String TranslationMember, java.lang.String TranslationIdentifier, java.lang.String ReferenceToContext)
	{
		super(context);
		this.__BundleLanguage = BundleLanguage;
		this.TranslationList = TranslationList;
		this.TranslationMember = TranslationMember;
		this.TranslationIdentifier = TranslationIdentifier;
		this.ReferenceToContext = ReferenceToContext;
	}

	@java.lang.Override
	public java.lang.Boolean executeAction() throws Exception
	{
		this.BundleLanguage = __BundleLanguage == null ? null : system.proxies.Language.initialize(getContext(), __BundleLanguage);

		// BEGIN USER CODE
		String value = "", line = "", key = "";

		String resourceBundleName = this.TranslationIdentifier + "_" + this.BundleLanguage.getCode();

		// Create a file
		File file = BundleHelper.createResourceBundleFile(resourceBundleName);

		FileOutputStream fos = new FileOutputStream(file, false);
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));

		// Iterate throug the list
		for (IMendixObject imo : this.TranslationList) {

			// Validate to get the translation member
			if (imo.hasMember(this.TranslationMember)) {
				key = null;

				// 1. Get the translation
				value = imo.getValue(getContext(), this.TranslationMember);

				// Get the guid of the association set for the description
				key = BundleHelper.getAssociationIdentifier(imo, this.ReferenceToContext, "", getContext());

				// Check if this translation is for a description
				if (key == null || key.isEmpty()) {
					throw new com.mendix.systemwideinterfaces.MendixRuntimeException("Member " + this.ReferenceToContext
							+ " is not part of " + this.TranslationList.getClass().getName());
				}

				value = imo.getValue(getContext(), this.TranslationMember);
				line = String.format("%s=%s", key, value);

				BundleHelper.logger.debug(line);
				bw.append(line);
				bw.append("\r\n");
			} else {
				throw new com.mendix.systemwideinterfaces.MendixRuntimeException(
						"Member " + this.TranslationMember + " is not part of " + imo.getClass().getName());
			}
		}
		
		bw.close();

		return true;	
		// END USER CODE
	}

	/**
	 * Returns a string representation of this action
	 */
	@java.lang.Override
	public java.lang.String toString()
	{
		return "JA_ResourceBundleFileCreate";
	}

	// BEGIN EXTRA CODE
	// END EXTRA CODE
}
