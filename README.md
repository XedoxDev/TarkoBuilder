# **Android APK Builder**  

**A lightweight tool for building APKs directly on Android devices—no complex toolchains required.**  

This tool enables Java compilation, resource processing, APK signing, and optimization (R8/D8) entirely on-device.  

> ⚠️ **Compatibility Note**: If using minSdk > 21, the builder may not work properly.

## **🔹 Features**  

```
✅ **Full on-device APK build pipeline**  
✅ **Java compilation support (adapted ECJ)**  
✅ **Built-in APK signing (modified ApkSigner)**  
✅ **Resource, assets, and native library processing**  
✅ **Optimization & DEX conversion (R8/D8)**  
✅ **Detailed logging & debugging**  
✅ **Configuration via properties files**  
```

## **🔹 Quick Start**  

### **1. Initialize the Builder**  
```java
ApkBuilder.BuildConfig config = new ApkBuilder.BuildConfig();
config.buildPath = "/path/to/build";                  // Build output directory  
config.androidJarPath = "/path/to/android.jar";       // android.jar for target SDK  
config.manifestPath = "/path/to/AndroidManifest.xml"; // App manifest  
config.resDir = "/path/to/res";                       // Resource directory (res/)  
config.javaSources.add("/path/to/java/src/");         // Java source directory  

ApkBuilder builder = new ApkBuilder(context, config);
```

### **2. Start the Build**  
```java
builder.build();  // Starts the APK build process  
```

## **🔹 Configuration**  

### **Core Settings**  
```java
config.appPackage = "com.example.app";  // Package name  
config.versionName = "1.0";             // App version  
config.versionCode = 1;                 // Version code  
config.minSdk = 21;                     // Minimum SDK version  
config.targetSdk = 33;                  // Target SDK version  
config.javaVersion = "17";              // Java version (8, 11, 17, etc.)  
```

### **Optional Settings**  
```java
config.assetsDir = "/path/to/assets";   // Assets directory  
config.nativeLibsDir = "/path/to/libs"; // Native libraries  
config.r8enabled = true;                // Enable R8 optimization  
config.debugMode = true;                // Verbose logging  
```

## **🔹 ApkbuilderProperties Utility**  

**Manage configurations via properties files:**  

```java
// Load configuration
Properties props = new Properties();
props.load(new FileInputStream("config.properties"));
ApkBuilder.BuildConfig config = ApkbuilderProperties.load(props);

// Save default config
ApkbuilderProperties.saveDefaultConfig("default.properties");
```

**Sample properties file:**  
```properties
androidJarPath=/path/to/android.jar
buildPath=build
manifestPath=AndroidManifest.xml
resDir=res
javaSources=src/;src2/

appPackage=com.example.app
minSdk=21
targetSdk=33
```

## **🔹 APK Signing**  

### **Option 1: PK8 + X509 (Recommended)**  
```java
config.apkSignEnable = true;
config.keyConfig.keyWithCert.keyPath = "path/to/key.pk8";
config.keyConfig.keyWithCert.certPath = "path/to/cert.x509.pem";
```

### **Option 2: Keystore (Experimental)**  
```java
config.apkSignEnable = true;
config.keyConfig.useKeystore = true;
config.keyConfig.keystore.path = "/path/to/keystore.jks";
config.keyConfig.keystore.alias = "keyalias";
config.keyConfig.keystore.storePassword = "password";
```

## **🔹 Java Compilation**  

Control ECJ compiler options:  
```java
config.java.classpath("path/to/library.jar"); 
config.java.option("-warn:-unused"); // ECJ-specific options
```

## **🔹 Requirements**  

- `android.jar` for target SDK version  
- Java sources with proper package structure  
- Android device with sufficient storage  

## **🔹 Support**  

Report issues in the project repository.  

## **📌 Notes**  
- **R8 is experimental** - disable with `r8enabled = false` if unstable  
- **Keystore signing may fail** - PK8+X509 is more reliable  
- **Requires matching android.jar** for target SDK  

## **Credits**  
- [UpdateDeveloper](https://gitlab.com/updateDeveloper) - Help with aapt2 and ecj