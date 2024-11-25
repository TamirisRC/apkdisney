package com.example.disneyapplication.data

import com.example.disneyapplication.model.Produto
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class FirebaseService {

    private val db = FirebaseFirestore.getInstance()
    private val produtosCollection = db.collection("produtos")

    suspend fun getProdutos(): List<Produto> {
        return produtosCollection.get().await().documents.map { doc ->
            Produto(
                id = doc.id,
                nome = doc.getString("nome") ?: "",
                preco = doc.getDouble("preco") ?: 0.0,
                categoria = doc.getString("categoria") ?: "",
                descricao = doc.getString("descricao") ?: "",
                imagem = doc.getString("imagem") ?: ""
            )
        }
    }

    suspend fun addProduto(produto: Produto) {
        produtosCollection.add(produto).await()
    }

    suspend fun updateProduto(produto: Produto) {
        produtosCollection.document(produto.id).set(produto).await()
    }

    suspend fun deleteProduto(id: String) {
        produtosCollection.document(id).delete().await()
    }
}
