# PlainText — Gerenciador de Senhas

Aplicativo Android de gerenciamento de senhas, desenvolvido em **Kotlin** com **Jetpack Compose**, para o Hands-On de Android da disciplina DevTITANS (Equipe 04).

Permite cadastrar, listar, editar e proteger senhas por trás de uma conta mestre, com persistência local via **Room** e injeção de dependências via **Hilt**.

## Funcionalidades

- **Login com conta mestre**: autenticação contra as credenciais configuradas nas Preferências.
- **Autofill de login**: opção de salvar e preencher automaticamente o campo de login.
- **Lista de senhas**: visualização de todas as senhas cadastradas, persistidas localmente.
- **Adicionar senha**: formulário para cadastrar nome, usuário, senha e notas.
- **Editar senha**: edição de um registro existente a partir da lista.
- **Preferências**: configuração da conta mestre (login/senha) e da opção de autofill.

## Tech Stack

| Camada | Tecnologia |
| --- | --- |
| UI | Jetpack Compose + Material 3 |
| Navegação | Navigation Compose (rotas tipadas com `@Serializable`) |
| Persistência | Room |
| Injeção de dependência | Hilt |
| Assincronismo | Kotlin Coroutines + Flow |
| Linguagem | Kotlin |

## Arquitetura

O projeto segue o padrão **MVVM**:

```
ui/screens/ (View)        ui/viewmodel/ (ViewModel)       data/ (Model)
   Composables      <--->     @HiltViewModel         <--->   Room DAO
   (Login, List,              expõe um ViewState             + Repository
   Preferences,               único por tela                 (converte
   EditList)                  (mutableStateOf)                Entity <-> Info)
```

- **Model** (`data/model`, `data/dao`, `data/repository`, `data/di`): entidades Room (`Password`), DAOs, repositório (`PasswordDBStore`) que converte entre a entidade de banco e o modelo de UI (`PasswordInfo`), e o módulo Hilt que provê o banco.
- **ViewModel** (`ui/viewmodel`): um `@HiltViewModel` por tela, cada um expondo um **único ViewState** (ex.: `ListViewState`, `PreferencesViewState`) atualizado via `.copy()` — evita múltiplos `mutableStateOf` soltos.
- **View** (`ui/screens`): Composables organizados por tela (`login`, `list`, `editList`, `preferences`), consumindo o ViewModel via `hiltViewModel()`.
- **Navegação**: rotas definidas como uma sealed class `Screen` (`PlainTextAppState.kt`), registradas no `NavHost` de `PlainTextApp.kt`.

## Estrutura do projeto

```
PlainText/app/src/main/java/com/example/plaintext/
├── data/
│   ├── dao/            # PasswordDao, PreferencesDao
│   ├── di/              # DataDiModule (Room + Hilt)
│   ├── model/           # Entities (Password) e modelos de UI (PasswordInfo)
│   └── repository/      # PasswordDBStore
├── ui/
│   ├── screens/          # Composables: login, list, editList, preferences, hello
│   ├── viewmodel/        # ListViewModel, PreferencesViewModel, SensorsViewModel
│   └── theme/            # Cores, tipografia, tema Compose
└── utils/                # Helpers (ex.: NavType para argumentos parcelable)
```

## Como rodar o projeto

**Pré-requisitos**: Android Studio, SDK do Android instalado, emulador ou dispositivo físico configurado.

1. Clone o repositório e abra a pasta `PlainText/` no Android Studio.
2. Crie o arquivo `PlainText/local.properties` (não é versionado) apontando para o seu SDK:
   ```
   sdk.dir=/caminho/para/seu/Android/Sdk
   ```
3. Certifique-se de que o **Gradle JDK** da IDE é compatível com o Gradle 8.9 (Java 8–22). Em `File > Settings > Build, Execution, Deployment > Build Tools > Gradle`, selecione um JDK 17 ou 21 caso o padrão da IDE seja incompatível.
4. Rode o projeto pelo botão **Run** do Android Studio, ou via linha de comando:
   ```bash
   cd PlainText
   ./gradlew :app:installDebug
   ```

## Equipe

| Integrante | Contribuição principal |
| --- | --- |
| Fernanda Costa | Modelo de dados, Room, Hilt (DI), padronização dos ViewModels |
| Antonio | Login, Preferências, tela de Lista |
| João Victor | Tela de Edição de senha (EditList) e navegação |
| Luiz | Padronização da navegação e documentação (wiki) |

## Convenção de branches

`feature/<escopo>` a partir de `develop`. PR de volta para `develop`; `master` recebe apenas a versão final.
