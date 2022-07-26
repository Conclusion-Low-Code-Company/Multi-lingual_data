# Multi-lingual data

## Description
Support multi-lingual master data in your app with this performance friendly module.

## Typical usage scenario
Use this module if your application is Multi-lingual and you want to have translatable master-data or dropdowns.
An example is a Product catalog that you would like to display in the language of the user (Bike/Fiets/Velo).

## Features and limitations
Features
- Uses native JAVA ResourceBundles
- Performance friendly. Translating does not require database calls

Limitations
- Adding new translations requires a refresh of the resource bundles

## Dependencies
- v1.0.0 for MX 7.23.31 or higher
- v2.0.0 for MX 8.18.19 or higher 
- v2.0.0 for MX 9

## Installation
Download the module and add it to your project.

## Configuration
1. Select the Entity that has attributes that you would like to rename (e.g. attribute Name of entity Product).

   Change the translatable attribute to a calculated attribute, leave the microflow empty for now.
2. Create an entity that you will use to store translations (e.g. Translation)
3. Create a reference to the object that you are translating (e.g. Translation_Product_Name)
4. Create a reference to the Language of the translation (Translation_Language).

   a. Create a microflow that inits/refreshes the resource bundles
   
   b. It must call JA_ResourceBundleFileCreate once for each available language, and pass the translations for that language to the JAVA action
   
5. It must end with the ResourceBundle_ClearCache action
6. Call that microflow from an After startup action, and also make it available as a button somewhere in your app
7. Implement the calculated attribute microflow: it must call JA_Translation_Retrieve and return the result of that action.
8. Implement a page to edit the data (Product_NewEdit) and add a grid or list to manage the Translations for that object.
9. Run your app, add some translations, refresh the resource bundle
10. Your masterdata is now translated!
