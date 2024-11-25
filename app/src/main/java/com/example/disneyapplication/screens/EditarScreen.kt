package com.example.disneyapplication.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.disneyapplication.data.FirebaseService
import com.example.disneyapplication.model.Produto
import kotlinx.coroutines.launch

@Composable
fun EditarScreen(navController: NavHostController, produtoId: String) {
    val service = FirebaseService()
    val scope = rememberCoroutineScope()

    var produto by remember { mutableStateOf<Produto?>(null) }
    var nome by remember { mutableStateOf(TextFieldValue("")) }
    var preco by remember { mutableStateOf(TextFieldValue("")) }
    var categoria by remember { mutableStateOf(TextFieldValue("")) }
    var descricao by remember { mutableStateOf(TextFieldValue("")) }
    var imagem by remember { mutableStateOf(TextFieldValue("")) }

    LaunchedEffect(produtoId) {
        val produtoFromDb = service.getProdutos().find { it.id == produtoId }
        produto = produtoFromDb
        if (produtoFromDb != null) {
            nome = TextFieldValue(produtoFromDb.nome)
            preco = TextFieldValue(produtoFromDb.preco.toString())
            categoria = TextFieldValue(produtoFromDb.categoria)
            descricao = TextFieldValue(produtoFromDb.descricao)
            imagem = TextFieldValue(produtoFromDb.imagem)
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Editar Produto") })
        }
    ) {
        if (produto != null) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                TextField(
                    value = nome,
                    onValueChange = { nome = it },
                    label = { Text("Nome do Produto") },
                    modifier = Modifier.fillMaxWidth()
                )
                TextField(
                    value = preco,
                    onValueChange = { preco = it },
                    label = { Text("Preço") },
                    modifier = Modifier.fillMaxWidth()
                )
                TextField(
                    value = categoria,
                    onValueChange = { categoria = it },
                    label = { Text("Categoria") },
                    modifier = Modifier.fillMaxWidth()
                )
                TextField(
                    value = descricao,
                    onValueChange = { descricao = it },
                    label = { Text("Descrição") },
                    modifier = Modifier.fillMaxWidth()
                )
                TextField(
                    value = imagem,
                    onValueChange = { imagem = it },
                    label = { Text("URL da Imagem") },
                    modifier = Modifier.fillMaxWidth()
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Button(
                        onClick = {
                            scope.launch {
                                val updatedProduto = produto!!.copy(
                                    nome = nome.text,
                                    preco = preco.text.toDoubleOrNull() ?: 0.0,
                                    categoria = categoria.text,
                                    descricao = descricao.text,
                                    imagem = imagem.text
                                )
                                service.updateProduto(updatedProduto)
                                navController.popBackStack() // Retorna para a tela anterior
                            }
                        }
                    ) {
                        Text("Salvar")
                    }
                    Button(
                        onClick = {
                            scope.launch {
                                service.deleteProduto(produtoId)
                                navController.popBackStack() // Retorna para a tela anterior
                            }
                        },
                        colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.error)
                    ) {
                        Text("Excluir")
                    }
                }
            }
        }
    }
}
