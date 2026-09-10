package com.examen.civique.ui.account

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.MarkEmailUnread
import androidx.compose.material.icons.filled.Sync
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.examen.civique.domain.model.AuthState
import com.examen.civique.domain.model.ThemeMode
import kotlinx.coroutines.launch

@Composable
fun AccountWelcomeScreen(onCreate: () -> Unit, onSignIn: () -> Unit, onGuest: () -> Unit) {
    Column(
        Modifier.fillMaxSize().padding(28.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Column(Modifier.widthIn(max = 480.dp), horizontalAlignment = Alignment.CenterHorizontally) {
            Surface(shape = MaterialTheme.shapes.extraLarge, color = MaterialTheme.colorScheme.primaryContainer) {
                Icon(Icons.Default.Sync, null, Modifier.padding(20.dp).size(42.dp), tint = MaterialTheme.colorScheme.primary)
            }
            Spacer(Modifier.height(28.dp))
            Text("Retrouvez votre progression partout", style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.SemiBold)
            Spacer(Modifier.height(12.dp))
            Text(
                "Créez un compte pour retrouver vos entraînements, vos favoris et votre progression sur tous vos appareils.",
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Spacer(Modifier.height(28.dp))
            Button(onClick = onCreate, Modifier.fillMaxWidth().heightIn(min = 48.dp)) { Text("Créer un compte") }
            Spacer(Modifier.height(10.dp))
            OutlinedButton(onClick = onSignIn, Modifier.fillMaxWidth().heightIn(min = 48.dp)) { Text("Se connecter") }
            Spacer(Modifier.height(18.dp))
            TextButton(onClick = onGuest, Modifier.heightIn(min = 48.dp)) { Text("Continuer sans compte") }
        }
    }
}

@Composable
fun AuthScreen(
    createAccount: Boolean,
    viewModel: AccountViewModel,
    onBack: () -> Unit,
    onSwitch: () -> Unit,
    onAuthenticated: () -> Unit,
    onConfirmationRequired: (String) -> Unit = {}
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmation by remember { mutableStateOf("") }
    var showPassword by remember { mutableStateOf(false) }
    var error by remember { mutableStateOf<String?>(null) }
    var loading by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()
    val authState by viewModel.authState.collectAsStateWithLifecycle()

    LaunchedEffect(authState) {
        if (authState is AuthState.Authenticated) onAuthenticated()
    }

    Column(Modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(24.dp), horizontalAlignment = Alignment.CenterHorizontally) {
        Column(Modifier.widthIn(max = 480.dp).fillMaxWidth()) {
            TextButton(onClick = onBack) { Text("Retour") }
            Spacer(Modifier.height(20.dp))
            Icon(Icons.Default.AccountCircle, null, Modifier.size(44.dp), tint = MaterialTheme.colorScheme.primary)
            Spacer(Modifier.height(16.dp))
            Text(if (createAccount) "Créer un compte" else "Se connecter", style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.SemiBold)
            Text("Votre progression locale reste disponible, même hors connexion.", color = MaterialTheme.colorScheme.onSurfaceVariant)
            Spacer(Modifier.height(28.dp))
            OutlinedTextField(
                value = email, onValueChange = { email = it; error = null }, label = { Text("Adresse email") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email), singleLine = true, modifier = Modifier.fillMaxWidth()
            )
            Spacer(Modifier.height(12.dp))
            OutlinedTextField(
                value = password, onValueChange = { password = it; error = null }, label = { Text("Mot de passe") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password), singleLine = true,
                visualTransformation = if (showPassword) VisualTransformation.None else PasswordVisualTransformation(),
                trailingIcon = { IconButton(onClick = { showPassword = !showPassword }) { Icon(if (showPassword) Icons.Default.VisibilityOff else Icons.Default.Visibility, if (showPassword) "Masquer le mot de passe" else "Afficher le mot de passe") } },
                modifier = Modifier.fillMaxWidth()
            )
            if (createAccount) {
                Spacer(Modifier.height(12.dp))
                OutlinedTextField(
                    value = confirmation, onValueChange = { confirmation = it; error = null }, label = { Text("Confirmer le mot de passe") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password), singleLine = true,
                    visualTransformation = if (showPassword) VisualTransformation.None else PasswordVisualTransformation(), modifier = Modifier.fillMaxWidth()
                )
            }
            error?.let { Text(it, color = MaterialTheme.colorScheme.error, modifier = Modifier.padding(top = 12.dp)) }
            Spacer(Modifier.height(22.dp))
            Button(
                onClick = {
                    if (!loading) scope.launch {
                        loading = true
                        error = if (createAccount) {
                            viewModel.signUp(email, password, confirmation)
                        } else {
                            viewModel.signIn(email, password)
                        }
                        if (createAccount && error == null) {
                            onConfirmationRequired(email.trim())
                        }
                        loading = false
                    }
                },
                enabled = !loading, modifier = Modifier.fillMaxWidth().heightIn(min = 48.dp)
            ) { Text(if (loading) "Veuillez patienter…" else if (createAccount) "Créer mon compte" else "Se connecter") }
            TextButton(onClick = onSwitch, Modifier.align(Alignment.CenterHorizontally)) {
                Text(if (createAccount) "Déjà un compte ? Se connecter" else "Créer un compte")
            }
        }
    }
}

@Composable
fun ConfirmEmailScreen(
    email: String,
    onConfirmed: () -> Unit,
    onUseAnotherEmail: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Column(
            modifier = Modifier.widthIn(max = 520.dp).fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Surface(
                shape = MaterialTheme.shapes.extraLarge,
                color = MaterialTheme.colorScheme.primaryContainer
            ) {
                Icon(
                    imageVector = Icons.Default.MarkEmailUnread,
                    contentDescription = null,
                    modifier = Modifier.padding(20.dp).size(44.dp),
                    tint = MaterialTheme.colorScheme.primary
                )
            }
            Spacer(Modifier.height(24.dp))
            Text(
                "Confirmez votre inscription",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.SemiBold
            )
            Spacer(Modifier.height(12.dp))
            Text(
                "Un lien de confirmation vient d’être envoyé à",
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Text(email, fontWeight = FontWeight.SemiBold)
            Spacer(Modifier.height(24.dp))
            Surface(
                modifier = Modifier.fillMaxWidth(),
                shape = MaterialTheme.shapes.large,
                color = MaterialTheme.colorScheme.surfaceVariant
            ) {
                Column(Modifier.padding(18.dp)) {
                    Text("E-mail attendu", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.SemiBold)
                    Spacer(Modifier.height(8.dp))
                    Text("Expéditeur : Supabase Auth, depuis une adresse se terminant par @supabase.io")
                    Text("Objet : Confirm your email address")
                }
            }
            Spacer(Modifier.height(20.dp))
            Text(
                "Ouvrez cet e-mail et appuyez sur le lien de confirmation. Revenez ensuite dans EXCI pour vous connecter.",
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Spacer(Modifier.height(12.dp))
            Text(
                "Vous ne le trouvez pas ? Patientez quelques minutes, puis regardez dans les courriers indésirables, les spams ou l’onglet Promotions.",
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Spacer(Modifier.height(28.dp))
            Button(
                onClick = onConfirmed,
                modifier = Modifier.fillMaxWidth().heightIn(min = 48.dp)
            ) {
                Text("J’ai confirmé mon adresse")
            }
            TextButton(onClick = onUseAnotherEmail) {
                Text("Utiliser une autre adresse email")
            }
        }
    }
}

@Composable
fun SettingsScreen(
    viewModel: AccountViewModel,
    themeMode: ThemeMode?,
    onThemeSelected: (ThemeMode) -> Unit,
    onCreate: () -> Unit,
    onSignIn: () -> Unit
) {
    val authState by viewModel.authState.collectAsStateWithLifecycle()
    Column(Modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(24.dp), horizontalAlignment = Alignment.CenterHorizontally) {
        Column(Modifier.widthIn(max = 700.dp).fillMaxWidth()) {
            Text("Paramètres", style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.SemiBold)
            Spacer(Modifier.height(28.dp))
            Row(verticalAlignment = Alignment.CenterVertically) { Icon(Icons.Default.AccountCircle, null); Spacer(Modifier.width(12.dp)); Text("Compte", style = MaterialTheme.typography.titleLarge) }
            Spacer(Modifier.height(12.dp))
            when (val state = authState) {
                AuthState.Guest -> {
                    Text("Vous utilisez EXCI sans compte.", color = MaterialTheme.colorScheme.onSurfaceVariant)
                    Text("Toutes les fonctionnalités et vos données locales restent disponibles.", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    Row(Modifier.padding(top = 16.dp), horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                        Button(onClick = onCreate) { Text("Créer un compte") }
                        OutlinedButton(onClick = onSignIn) { Text("Se connecter") }
                    }
                }
                is AuthState.Authenticated -> {
                    Text(state.user.displayName ?: state.user.email, fontWeight = FontWeight.Medium)
                    Text("Données enregistrées localement · synchronisation en attente", color = MaterialTheme.colorScheme.onSurfaceVariant)
                    TextButton(onClick = viewModel::signOut) { Text("Se déconnecter") }
                }
            }
            HorizontalDivider(Modifier.padding(vertical = 28.dp))
            Row(verticalAlignment = Alignment.CenterVertically) { Icon(Icons.Default.DarkMode, null); Spacer(Modifier.width(12.dp)); Text("Apparence", style = MaterialTheme.typography.titleLarge) }
            ThemeRow("Mode clair", ThemeMode.LIGHT, themeMode, onThemeSelected)
            ThemeRow("Mode sombre", ThemeMode.DARK, themeMode, onThemeSelected)
        }
    }
}

@Composable private fun ThemeRow(label: String, value: ThemeMode, selected: ThemeMode?, onSelected: (ThemeMode) -> Unit) {
    Row(Modifier.fillMaxWidth().clickable { onSelected(value) }.padding(vertical = 8.dp), verticalAlignment = Alignment.CenterVertically) {
        RadioButton(selected == value, onClick = { onSelected(value) }); Text(label, Modifier.padding(start = 8.dp))
    }
}
