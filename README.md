# **Android APK Builder**  

**Build APKs directly on your Android device—no PC or complex toolchains needed.**  

A lightweight tool that handles Java compilation, resource processing, APK signing, and optimization (R8/D8) entirely on-device.  

> ⚠️ **Compatibility**: May not work properly if `minSdk > 21`.

---

## **🔹 Key Features**  
- **Full on-device APK build pipeline**  
- **Java compilation** (modified ECJ)  
- **APK signing** (PK8+X509 or Keystore)  
- **Resource, assets & native library support**  
- **Optimization with R8/D8**  
- **Configurable via properties files**  

---

## **🔹 Quick Start**  

### **1. Initialize the Builder**  
```java
ApkBuilder.BuildConfig config = new ApkBuilder.BuildConfig();
config.buildPath = "/path/to/build";          // Output directory  
config.androidJarPath = "/path/to/android.jar"; // Target SDK's android.jar  
config.manifestPath = "AndroidManifest.xml";  // App manifest  
config.resDir = "res";                        // Resource directory  
config.javaSources.add("src/");               // Java source path  

ApkBuilder builder = new ApkBuilder(context, config);
```

### **2. Run the Build**  
```java
builder.build();  // Starts the process  
```

---

## **🔹 Configuration**  

### **Essential Settings**  
```java
config.appPackage = "com.example.app";  // Package name  
config.versionName = "1.0";             // App version  
config.minSdk = 21;                     // Minimum SDK  
config.targetSdk = 33;                  // Target SDK  
```

### **Optional Settings**  
```java
config.assetsDir = "assets";           // Assets folder  
config.nativeLibsDir = "libs";          // Native libraries  
config.r8enabled = true;               // Enable R8 optimization  
config.debugMode = true;                // Verbose logs  
```

---

## **🔹 Properties File Setup**  

**Load configuration:**  
```java
Properties props = new Properties();
props.load(new FileInputStream("config.properties"));
ApkBuilder.BuildConfig config = ApkbuilderProperties.load(props);
```

**Example `config.properties`:**  
```properties
androidJarPath=android_33.jar
buildPath=build
manifestPath=AndroidManifest.xml
resDir=res
javaSources=src/

appPackage=com.example.app
minSdk=21
targetSdk=33
```

---

## **🔹 APK Signing**  

### **Option 1: PK8 + X509 (Stable)**  
```java
config.apkSignEnable = true;
config.keyConfig.keyWithCert.keyPath = "key.pk8";
config.keyConfig.keyWithCert.certPath = "cert.x509.pem";
```

### **Option 2: Keystore (Experimental)**  
```java
config.apkSignEnable = true;
config.keyConfig.useKeystore = true;
config.keyConfig.keystore.path = "keystore.jks";
config.keyConfig.keystore.alias = "mykey";
config.keyConfig.keystore.storePassword = "password";
```

---

## **🔹 Requirements**  
- `android.jar` for your target SDK  
- Properly structured Java sources  
- Adequate device storage  

---

## **⚠️ Notes**  
- **R8 may be unstable**—disable with `r8enabled = false` if needed.  
- **Keystore signing is experimental**—PK8+X509 is recommended.  
- **Use matching `android.jar`** for your target SDK.  

---

**Credits**: [UpdateDeveloper](https://gitlab.com/updateDeveloper) for aapt2/ecj improvements. 

**Issues?** Report them in the project repository.
