/*
 * Copyright (c) 2010, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.wso2.developerstudio.eclipse.capp.core.manifest;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class BundleManifest extends SimpleJarManifest {
	
	private static final String DYNAMIC_IMPORT_PACKAGE = "DynamicImport-Package";
	private static final String FRAGMENT_HOST = "Fragment-Host";
	private static final String BUNDLE_CLASS_PATH = "Bundle-ClassPath";
	private static final String IMPORT_PACKAGE = "Import-Package";
	private static final String EXPORT_PACKAGE = "Export-Package";
	private static final String BUNDLE_ACTIVATOR = "Bundle-Activator";
	private static final String BUNDLE_DESCRIPTION = "Bundle-Description";
	private static final String BUNDLE_VERSION = "Bundle-Version";
	private static final String BUNDLE_NAME = "Bundle-Name";
	private static final String BUNDLE_SYMBOLIC_NAME = "Bundle-SymbolicName";
	private static final String MANIFEST_VERSION = "Manifest-Version";
	
	private String bundleSymbolicName;
	private String bundleName;
	private String bundleVersion;
	private String bundleDescription;
	private String bundleActivatorName;
	private List<String> exportPackagesList;
	private List<String> importPackagesList;
	private List<String> bundleClassPath;
	private boolean dynamicImports=true;
	private String fragmentHost = null;
	
	/**
     * @return the bundleSymbolicName
     */
    public String getBundleSymbolicName() {
    	return bundleSymbolicName;
    }
	/**
     * @param bundleSymbolicName the bundleSymbolicName to set
     */
    public void setBundleSymbolicName(String bundleSymbolicName) {
    	this.bundleSymbolicName = bundleSymbolicName;
    }
	/**
     * @return the bundleName
     */
    public String getBundleName() {
    	return bundleName;
    }
	/**
     * @param bundleName the bundleName to set
     */
    public void setBundleName(String bundleName) {
    	this.bundleName = bundleName;
    }
	/**
     * @return the bundleVersion
     */
    public String getBundleVersion() {
    	return bundleVersion;
    }
	/**
     * @param bundleVersion the bundleVersion to set
     */
    public void setBundleVersion(String bundleVersion) {
    	this.bundleVersion = bundleVersion;
    }
	/**
     * @return the bundleDescription
     */
    public String getBundleDescription() {
    	return bundleDescription;
    }
	/**
     * @param bundleDescription the bundleDescription to set
     */
    public void setBundleDescription(String bundleDescription) {
    	this.bundleDescription = bundleDescription;
    }

	public List<String> getExportPackagesList() {
		if (exportPackagesList==null){
			exportPackagesList=new ArrayList<String>();
		}
		return exportPackagesList;
	}
	
	private String getExportPackageListString(){
		String packages=null;
		for (String packageName : getExportPackagesList()) {
			if (packages==null){
				packages=packageName;
			}else{
				packages += "," +packageName;
			}
		}
		return packages;
	}
	
	private String getImportPackageListString(){
		String imports=null;
		for (String packageName : getImportPackagesList()) {
			if (imports==null){
				imports=packageName;
			}else{
				imports += "," + packageName;
			}
		}
		return imports;
	}
	
	private String getBundleClassPathListString(){
		String classPaths=null;
		for (String bundleClassPath : getBundleClassPath()) {
			String osgiCompatibleBundlePath = bundleClassPath.replace(File.separatorChar, '/');
			if (classPaths==null){
				classPaths=osgiCompatibleBundlePath;
			}else{
				classPaths+= ","+osgiCompatibleBundlePath;
			}
		}
		return classPaths;
	}
	
	public String toString() {
		String manifest = null;
		List<String> headerLines = getHeaderLines();
		for (String line : headerLines) {
			manifest=appendLine(manifest, line);    
        }
		manifest=appendLine(manifest, "\n\n");
		return manifest;
	}

	private List<String> getHeaderLines() {
		List<String> list=new ArrayList<String>();
		
		list.add(getManifestHeaderLine(MANIFEST_VERSION,getManifestVersion()));
		list.add(getManifestHeaderLine(BUNDLE_SYMBOLIC_NAME,getBundleSymbolicName()));
		list.add(getManifestHeaderLine(BUNDLE_NAME,getBundleName()));
		list.add(getManifestHeaderLine(BUNDLE_VERSION,getEffectiveBundleVersion()));
		list.add(getManifestHeaderLine(BUNDLE_DESCRIPTION,getBundleDescription()));
		if (getBundleActivatorName()!=null){
			list.add(getManifestHeaderLine(BUNDLE_ACTIVATOR,getBundleActivatorName()));
		}
		String exportPackageListString = getExportPackageListString();
		if (exportPackageListString!=null){
			list.add(getManifestHeaderLine(EXPORT_PACKAGE,exportPackageListString));
		}
		String importPackageListString = getImportPackageListString();
		if (importPackageListString!=null){
			list.add(getManifestHeaderLine(IMPORT_PACKAGE,importPackageListString));
		}
		String bundleClassPathListString = getBundleClassPathListString();
		if (bundleClassPathListString!=null){
			list.add(getManifestHeaderLine(BUNDLE_CLASS_PATH,bundleClassPathListString));
		}
		if (getFragmentHost()!=null && !getFragmentHost().trim().equals("")){
			list.add(getManifestHeaderLine(FRAGMENT_HOST,getFragmentHost().trim()));			
		}
		if (isDynamicImports()){
			list.add(getManifestHeaderLine(DYNAMIC_IMPORT_PACKAGE,"*"));
		}
		list.addAll(getAdditionalHeaders());
		return list;
	}

	protected List<String> getAdditionalHeaders(){return new ArrayList<String>();}
	
	public String getTimestampedBundleVersion() {
	    return null;
    }
	
	public String getEffectiveBundleVersion(){
		if (getTimestampedBundleVersion()==null){
			return getBundleVersion();
		}else{
			return getTimestampedBundleVersion();
		}
	}
	
    protected String getDefaultName() {
	    return "MANIFEST.MF";
    }

	public List<String> getBundleClassPath() {
		if (bundleClassPath==null){
			bundleClassPath=new ArrayList<String>();
		}
	    return bundleClassPath;
    }

	public List<String> getImportPackagesList() {
		if (importPackagesList==null){
			importPackagesList=new ArrayList<String>();
		}
	    return importPackagesList;
    }

	public void setBundleActivatorName(String bundleActivatorName) {
	    this.bundleActivatorName = bundleActivatorName;
    }

	public String getBundleActivatorName() {
	    return bundleActivatorName;
    }
	public void setDynamicImports(boolean dynamicImports) {
		this.dynamicImports = dynamicImports;
	}
	public boolean isDynamicImports() {
		return dynamicImports;
	}
	public void setFragmentHost(String fragmentHost) {
		this.fragmentHost = fragmentHost;
	}
	public String getFragmentHost() {
		return fragmentHost;
	}
}
