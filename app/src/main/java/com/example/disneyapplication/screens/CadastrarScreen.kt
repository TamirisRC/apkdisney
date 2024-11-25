package com.example.disneyapplication.screens

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter


@Composable
fun UploadImageScreen() {
    var imageUri by remember { mutableStateOf<Uri?>(null) }
    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        imageUri = uri
    }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Upload de Imagem") })
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Mostra a imagem selecionada
            if (imageUri != null) {
                Image(
                    painter = rememberAsyncImagePainter(imageUri),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(150.dp)
                        .aspectRatio(1f)
                        .clip(CircleShape)
                )
            } else {
                Text("Nenhuma imagem selecionada")
            }

            // Botão para abrir o seletor de imagens
            Button(onClick = { imagePickerLauncher.launch("image/*") }) {
                Text("Selecionar Imagem")
            }
        }
    }
}

@Composable
fun CadastrarScreen(navController: NavHostController) {
    val service = FirebaseService()
    val scope = rememberCoroutineScope()

    var nome by remember { mutableStateOf(TextFieldValue("")) }
    var preco by remember { mutableStateOf(TextFieldValue("")) }
    var categoria by remember { mutableStateOf(TextFieldValue("")) }
    var descricao by remember { mutableStateOf(TextFieldValue("")) }
    var imagem by remember { mutableStateOf(TextFieldValue("")) }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Cadastrar Produto") })
        }
    ) {
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
            Button(
                onClick = {
                    scope.launch {
                        val produto = Produto(
                            nome = nome.text,
                            preco = preco.text.toDoubleOrNull() ?: 0.0,
                            categoria = categoria.text,
                            descricao = descricao.text,
                            imagem = imagem.text
                        )
                        service.addProduto(produto)
                        navController.popBackStack() // Retorna para a tela anterior
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Salvar")
            }
        }
    }
}
