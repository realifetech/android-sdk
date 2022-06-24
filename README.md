# RealifeTech SDK

# Getting started

## 1. Ensure the following requirements are met

* Android Studio 4.0+
* Deployment Target of Android 30.0
* Min Supported SDK: 23
* Kotlin

## 2. Install the RealifeTechSDK dependency

In order to use the SDK you will have to add it to your project. Currently, the SDK is located in a Maven Repository,
deployed using Github Package. In order to use the Maven Repository where the SDK resides:

1. In your project, **root** `build.gradle` file, in the section for `repositories` add the RealifeTech SDK repo as
   following:

```groovy
allprojects {
    repositories {
        //  .....

        maven {
            name = "RealifeTech SDK Artifactory"
            url = uri("https://maven.pkg.github.com/realifetech/android-sdk")

            credentials {
                Properties properties = new Properties()
                properties.load(project.rootProject.file('local.properties').newDataInputStream())

                username = properties.getProperty("gpr.user") ?: System.getenv("GITHUB_USER")
                password = properties.getProperty("gpr.key") ?: System.getenv("GITHUB_TOKEN")
            }
        }
    }
}
```

To access the repository it requires authentification:

- username, your Github user name.
- password, a generated Personal Access Token for Github. Generated token should have the following required
  permissions: `write:packages` & `read:packages`. How to generate a token in Github can be
  found https://docs.github.com/en/free-pro-team@latest/github/authenticating-to-github/creating-a-personal-access-token

When you have the username and the token, you can provide them using different approaches:

- Recommended way. Save it in your `local.properties` file, which is not saved in the repository, this way avoiding
  committing sensitive information. In your `local.properties` add 2 lines with the correct information, as following:

  ```groovy
    gpr.user=john-doe
    grp.key=my_github_token
  ```

- Create 2 environment variables, GITHUB_USER & GITHUB_TOKEN respectively, and provide the information there.
- Hardcode the values in the `build.gradle` file. Not recommended due to sensitiveness.

2. When the repository information was added, you can proceed by adding the dependency for the SDK to your project. For
   example, in your **app** `build.gradle` file, under dependencies section, add the following:
   ```groovy
   dependencies {
        implementation 'com.realifetech:sdk:<version>'
   }
    ```
3. Now sync your project, and proceed with coding.

# Setup SDK

The SDK is composed of 2 phases: Configuration & Functionality.

### Configuration

#Important: Using the SDK without calling configuring can lead to runtime errors. Do not forget to configure the SDK.

Use the following function to configure the SDK for use. You will be provided the values necessary as part of your onboarding. Note that the SDK provides default values for its API endpoints, unless you are provided with unique endpoints as part of onboarding leave these fields blank.

```kotlin
 val configuration = CoreConfiguration(
             appCode: "APPLICATION_CODE",
             clientSecret: "API_SECRET",
             apiUrl: "http://API_URL", // OPTIONAL
             graphApiUrl: "http://GRAPH_API_URL" // OPTIONAL
             webOrderingJourneyUrl = "http://WEB_ORDERING_Journey_URL" // OPTIONAL
        )
 RealifeTech.configureSdk(context, configuration)

```

There are 5 properties which needs to be configured. `context`, `clientSecret` and `appCode`
are required, as they don't provide a default value. Whereas `graphApiUrl`, `apiUrl` & `webOrderingJourneyUrl` do have default values.

Note: _The above setup is recommended to be done at the start of the app, in the `Application` class._

### Device Registration

Interfacing with our backend systems requires that your device be registered with them. You can use the below function
to register the device.

```kotlin
Realifetech.getGeneral().registerDevice()
```

If the registration fails (for instance due to connectivity issues) we will retry until it is successful. You will not
be able to use the majority of the SDK until the device has been registered, and can check the status of it using:

```kotlin
Realifetech.getGeneral().isSdkReady
```

## Push Notifications

Use the following function to register the device for push notifications.

```kotlin
Realifetech.getCommunicate().registerForPushNotifications(token: String)
```

In order to register for push notifications, a `token` is required. The token will be sent from Firebase using their
Cloud Messaging SDK. The top-overview of the process:

1. Connect your project to Firebase
2. Add Firebase Messaging SDK library to your project
3. Register a service (which will be a subclass of `FirebaseMessagingService`) which will listen for the new tokens from
   Firebase. Make sure the service is registered in Android Manifest.
4. In the service override the method `onNewToken`, which will be called when there is a new token available from
   Firebase
5. When the token is available, execute the call to the SDK to register for push notifications using the snippet above.

Example service which listens for tokens and after that calls the SDK to register for push notifications:

```kotlin
class ExampleServiceFcm : FirebaseMessagingService() {
    override fun onNewToken(newToken: String) {
        Realifetech.getCommunicate().registerForPushNotifications(newToken)
    }
}
```

_Note: for more information about setting up the Firebase Messaging
see: https://firebase.google.com/docs/cloud-messaging/android/client_

## Analytics

Use the following function to log an analytic event

```kotlin
Realifetech.getAnalytics().track(type, action, newDictionary, oldDictionary) { error ->
    // What should happen on completion
}
```

As an example, if you want to log the login to an external system such as Ticketmaster, you would send the following:

```kotlin
val type = "user"
val action = "externalLogin"
val newDictionary = mapOf("userId" to "a3890e983e", "provider" to "ticketmaster")
Realifetech.getAnalytics().track(type, action, newDictionary, null) { error ->
    // What should happen on completion
}
```

If the logging fails (for instance due to connectivity issues) we will retry until it is successful.

## Audiences

To determine if the device is a member of an audience, you would use the following function, passing the `audienceId`:

```kotlin
RealifeTech.getAudience()
    .deviceIsMemberOfAudience(externalAudienceId: String, callback:(error: Error?, result: Boolean) -> Unit)
```
