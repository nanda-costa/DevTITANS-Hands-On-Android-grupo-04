# Implementation Plan - Login + Preferences Integration

This plan implements the integration of login and preferences screens with the `PreferencesViewModel`, following the patterns found in `SensorsViewModel.kt`.

## Proposed Changes

### ViewModel

#### [MODIFY] [PreferencesViewModel.kt](file:///C:/Users/tonho/AndroidStudioProjects/DevTITANS-Hands-On-Android-grupo-04/PlainText/app/src/main/java/com/example/plaintext/ui/viewmodel/PreferencesViewModel.kt)
- Annotate with `@HiltViewModel`.
- Refactor `login`, `password`, and `preencher` to use `mutableStateOf` instead of `StateFlow`, following the pattern in `SensorsViewModel.kt`.
- Implement `updateLogin`, `updatePassword`, and `updatePreencher` to update these states.
- Keep `checkCredentials` logic.

### Screens

#### [MODIFY] [Login.kt](file:///C:/Users/tonho/AndroidStudioProjects/DevTITANS-Hands-On-Android-grupo-04/PlainText/app/src/main/java/com/example/plaintext/ui/screens/login/Login.kt)
- Ensure `Login_screen` correctly uses `PreferencesViewModel` for credential checking.
- Verify navigation to "List" works correctly.

#### [MODIFY] [Preferences.kt](file:///C:/Users/tonho/AndroidStudioProjects/DevTITANS-Hands-On-Android-grupo-04/PlainText/app/src/main/java/com/example/plaintext/ui/screens/preferences/Preferences.kt)
- Update to use the refactored `PreferencesViewModel` (direct state access instead of `collectAsState`).
- If applicable, extract the main UI to a `SettingsContent` composable to match the user's terminology.

#### [MODIFY] [PlainTextApp.kt](file:///C:/Users/tonho/AndroidStudioProjects/DevTITANS-Hands-On-Android-grupo-04/PlainText/app/src/main/java/com/example/plaintext/ui/screens/PlainTextApp.kt)
- Wire up the navigation for `Screen.Login` and `Screen.Preferences`.
- Ensure `PreferencesViewModel` is correctly provided (e.g., via `hiltViewModel()`).

## Verification Plan

### Automated Tests
- Build the project to ensure all references are resolved.

### Manual Verification
1. Open the app.
2. Navigate to Preferences and set a login/password.
3. Navigate to Login and verify that entering the correct credentials takes you to the list.
4. Verify that the "Preencher automaticamente" checkbox state is preserved while navigating.
