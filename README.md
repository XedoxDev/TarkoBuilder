# **Android APK Builder**  

**A lightweight tool for building APKs directly on Android devices—no complex toolchains required.**  

This tool enables Java compilation, resource processing, APK signing, and optimization (R8/D8) entirely on-device.  

If you use min sdk > 21 - builder no works.
---  

## **🔹 Features**  

✅ **Full on-device APK build pipeline**  
✅ **Java compilation support (adapted ECJ)**  
✅ **Built-in APK signing (modified ApkSigner)**  
✅ **Resource, assets, and native library processing**  
✅ **Optimization & DEX conversion (R8/D8)**  
✅ **Detailed logging & debugging**  

---  

## **🔹 Quick Start**  

### **1. Initialize the Builder**  
```java
ApkBuilder.BuildConfig config = new ApkBuilder.BuildConfig();
config.buildPath = "/path/to/build";                  // Build output directory  
config.androidJarPath = "/path/to/android.jar";       // android.jar for target SDK  
config.manifestPath = "/path/to/AndroidManifest.xml"; // App manifest  
config.resDir = "/path/to/res";                       // Resource directory (res/)  
config.javaSources.add("/path/to/java/src/");         // Java source directory  
config.javaSources.add("/path/to/java/src2/"); 
config.javaSources.add("/path/to/YourClass.java"); 

ApkBuilder builder = new ApkBuilder(context, config);
```

### **1.5 java options**  

Also you can directly control javac, use field "java", and see JavacOptionsBuilder.

```java
config.java.classpath("path/to/library.jar); // add classpath
config.java.option("-warn:-unused"); // something ecj option

```

### **2. Start the Build**  
```java
builder.build();  // Starts the APK build process  
```

---  

## **🔹 Configuration**  

### **Core Settings**  
```java
config.appPackage = "com.example.app";  // Package name  
config.versionName = "1.0";             // App version  
config.versionCode = "1";                 // Version code  
config.minSdk = 21;                     // Minimum SDK version  
config.targetSdk = 33;                  // Target SDK version  
config.javaVersion = "17";              // Java version (8, 11, 17, etc.)  
```

### **Optional Settings**  
```java
config.assetsDir = "/path/to/assets";   // Assets directory (optional)  
config.nativeLibsDir = "/path/to/libs"; // Native libraries (armeabi-v7a, arm64-v8a, etc.)  
config.r8enabled = true;                // Enable R8 (experimental)  
config.proguardRulesPath = "/path/to/proguard-rules.pro"; // ProGuard rules  
```

### **APK Signing**  
Choose one of the following methods:  
- **Debug key (default)**  
- **Custom key (PK8 + X509)**  
- **Keystore (JKS)** *(unstable, not recommended)*  

#### **Option 1: PK8 + X509 (recommended)**  
```java
config.apkSignEnable = true;
config.keyConfig.keyWithCert.keyPath = "path/to/key.pk8";
config.keyConfig.keyWithCert.certPath = "path/to/cert.x509.pem";
```

#### **Option 2: Keystore (JKS)** *(experimental)*  
```java
config.apkSignEnable = true;
config.keyConfig.useKeystore = true;
config.keyConfig.keystore.path = "/path/to/keystore.jks";
config.keyConfig.keystore.alias = "keyalias";
config.keyConfig.keystore.storePassword = "password";
config.keyConfig.keystore.keyPassword = "password";
```

---  

## **🔹 Logging & Debugging**  

Enable debug mode for detailed logs:  
```java
config.debugMode = true; // Verbose build logs  
```

### **Custom Log Output**  
```java
PrintStream logStream = System.out; // Or use a file stream  
ApkBuilder builder = new ApkBuilder(context, logStream, config);
```

---  

## **🔹 Requirements**  

- `android.jar` for the target SDK version  
- Java sources with correct package structure  
- Android device with sufficient storage  

---  

## **🔹 Support**  

For issues or feature requests, please open an issue in the project repository.  

---  

### **📌 Notes**  
- **`R8` is experimental** – disable with `r8enabled = false` if unstable.  
- **Keystore signing may faile** – `PK8`+`X509` is more reliable.  
- **`android.jar` is required** – ensure it matches your target SDK.  

## Thanks to
 - (Update)[https://gitlab.com/updateDeveloper] - help in creating the builder# TarkoBuilder
# TarkoBuilder
