package com.example.disneyapplication.screens


import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

@Composable
fun MainScreen(navController: NavHostController) {
    NavHost(navController, startDestination = "consultar") {
        composable("consultar") { ConsultarScreen(navController) }
        composable("cadastrar") { CadastrarScreen(navController) }
        composable("editar/{id}") { backStackEntry ->
            val id = backStackEntry.arguments?.getString("id") ?: ""
            EditarScreen(navController, id)
        }
    }
}
