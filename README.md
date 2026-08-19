# DevTITANS Hands-On Android — Equipe 04

Implementação do **PlainText App** (gerenciador de senhas em Kotlin + Jetpack Compose) para o Hands-On de Android.

## Divisão de tarefas

O app já vem com o esqueleto pronto (Compose, Room, Hilt, Navigation); o trabalho de cada pessoa é completar os pontos em aberto (TODOs, funções vazias e telas não implementadas).

### Fernanda — Modelo de dados + Room + DI
Branch: `feature/database-room-di`

- `data/model/Password.kt`: completar a entity `Password` (faltam colunas `name`, `login`, `password`, `notes`) e o operator `getValue` de `PasswordInfo`.
- `data/dao/PasswordDao.kt`: adicionar `@Query` para listar tudo (`Flow<List<Password>>`), buscar por id, e checar se está vazio.
- `data/repository/PasswordStore.kt`: implementar os 6 métodos TODO (`getList`, `add`, `update`, `get`, `save`, `isEmpty`), convertendo `Password` <-> `PasswordInfo`.
- `data/di/DataDiModule.kt`: prover `PlainTextDatabase` via `Room.databaseBuilder` e `PasswordDao`; remover o `dbSimulator`/`hello.ListViewModel`.

### Luiz — Navegação + Tela de Lista
Branch: `feature/navigation-list`

- `ui/screens/PlainTextAppState.kt` + `ui/screens/PlainTextApp.kt`: registrar as rotas que faltam no `NavHost` (`Screen.List`, `Screen.Preferences`, `Screen.sensors`) e criar as funções de navegação (`navigateToList`, `navigateToEdit`, `navigateBack`, `navigateToPreferences`).
- `ui/screens/list/List.kt`: implementar `ListView()` (Scaffold + `TopBarComponent` + `AddButton` + `ListItemContent`).
- `ui/viewmodel/ListViewModel.kt`: injetar `PasswordDBStore`, coletar `getList()` no `init`, implementar `savePassword`.

### João Victor — Tela de Edição + Splash/Hello
Branch: `feature/edit-hello`

- `ui/screens/editList/EditList.kt`: implementar o composable `EditList()` (formulário com `EditInput` para nome/usuário/senha/notas, botão salvar/voltar).
- `ui/screens/hello/Hello.kt`: está com código de exemplo bagunçado (um `ListViewModel`/`dbSimulator` fake duplicando o real). Precisa decidir o papel real dessa tela (splash → Login, provavelmente) e limpar a duplicação.
- Ligar `navigateBack` / `savePassword` dessas telas ao `PlainTextAppState` e ao `ListViewModel`.

### Antonio — Login + Preferências
Branch: `feature/login-preferences`

- `ui/screens/login/Login.kt`: implementar `Login_screen()` (inputs de login/senha, usa `checkCredentials` do `PreferencesViewModel`, navega para Lista ou mostra erro).
- `ui/viewmodel/PreferencesViewModel.kt`: implementar `updateLogin`, `updatePassword`, `updatePreencher`.
- `ui/screens/preferences/Preferences.kt`: ligar `SettingsContent` ao estado real do `PreferencesViewModel` (hoje os campos estão hardcoded/vazios).
- Dar uma olhada em `SensorsViewModel.kt`.

## Como trabalhar em paralelo

O bloco da Fernanda (Room + DI) é pré-requisito para os dados reais, mas **Luiz e Antonio podem começar já** usando dados mockados (listas fixas, `PasswordInfo` de exemplo, `@Preview` com valores fake) enquanto o banco não fica pronto. Depois que `feature/database-room-di` for mergeada, cada um troca o mock pela integração real com `PasswordDBStore` / `ListViewModel`.

## Convenção de branches

`feature/<escopo>` a partir de `develop`. PR de volta para `develop`; `master` só recebe merge da versão final.
