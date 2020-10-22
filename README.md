# android-sdk

# Setup SDK

The SDK is composed of 2 phases: setup & functionality.

## Setup

### Configuration

Use the following function(s) to configure the SDK for use. You will be provided the values necessary as part of your
onboarding.

```kotlin
 Realifetech.getGeneral().configuration.apply {
    context = app_context
    apiUrl = "https://my-server-url.com"
    graphApiUrl = "https://my-server-url.com/graphql"
    clientSecret = "my_client_secret"
}
```

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
Realifetech.getCommunicate().registerForPushNotifications(notifications_token)
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
Realifetech.getAnalytics().logEvent(type, action, newDictionary, oldDictionary) { error ->
    // What should happen on completion 
}
```

As an example, if you want to log the login to an external system such as Ticketmaster, you would send the following:

```kotlin
val type = "user"
val action = "externalLogin"
val newDictionary = mapOf("userId" to "a3890e983e", "provider" to "ticketmaster")
Realifetech.getAnalytics().logEvent(type, action, newDictionary, null) { error ->
    // What should happen on completion 
}
```

If the logging fails (for instance due to connectivity issues) we will retry until it is successful.

## Audiences

To determine if the device is a member of an audience, you would use the following function, passing the `audienceId`:

```kotlin
RealifeTech.getAudience().deviceIsMemberOfAudience(audience_id) { error, doesBelong ->
    // To be executed on completion
}
```U